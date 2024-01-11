package com.sage.codex.sagecodex.theme;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationAction;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.util.EmptyRunnable;
import com.intellij.openapi.util.Pair;
import com.intellij.util.concurrency.annotations.RequiresEdt;
import com.sage.codex.sagecodex.constant.SageCodeXBundle;
import com.sage.codex.sagecodex.constant.SageCodeXNotifications;
import com.sage.codex.sagecodex.enums.SageCodeXStatus;
import com.sage.codex.sagecodex.setting.UserInfoConfigurationSetting;
import com.sage.codex.sagecodex.utils.ApplicationUtil;
import com.sage.codex.sagecodex.window.SageCodeXStatusBarWidget;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.concurrent.GuardedBy;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * @Description：
 * @Author: xionghao
 * @Date: 2024/1/7 17:54
 */
public class SageCodeXStatusService implements SageCodeXStatusListener, Disposable {


    private final Object lock = new Object();

    private static final AtomicBoolean clientRequestsDisabled = new AtomicBoolean();

    @GuardedBy("lock")
    @NotNull
    private SageCodeXStatus status;

    @GuardedBy("lock")
    @Nullable
    private String message;

    @Override
    public void dispose() {

    }

    public SageCodeXStatusService() {
        // 默认是未登录状态
        this.status = SageCodeXStatus.NotSignedIn;
        SageCodeXStatus sageCodeXStatus = UserInfoConfigurationSetting.settings().status;
        if (Objects.nonNull(sageCodeXStatus)) {
            this.status = sageCodeXStatus;
        }
        ApplicationManager.getApplication().getMessageBus().connect(this).subscribe(SageCodeXStatusListener.TOPIC, this);
    }


    public static void notifyApplication(@NotNull SageCodeXStatus status) {
        notifyApplication(status, (String) null);
    }

    public static void notifyApplication(@NotNull SageCodeXStatus status, @Nullable String customMessage) {
        (ApplicationManager.getApplication().getMessageBus().syncPublisher(SageCodeXStatusListener.TOPIC)).onSageCodeXStatus(status, customMessage);
    }


    @Override
    public void onSageCodeXStatus(@NotNull SageCodeXStatus status, @Nullable String customMessage) {
        boolean notify = false;
        synchronized (this.lock) {
            SageCodeXStatus oldStatus = this.status;
            if (!oldStatus.isDisablingClientRequests()) {
                notify = this.status != status;
                this.status = status;
                this.message = customMessage;
            }
        }

        if (status.isDisablingClientRequests()) {
            boolean changed = clientRequestsDisabled.compareAndSet(false, true);
            if (changed && status == SageCodeXStatus.IncompatibleClient) {
                Project project = ApplicationUtil.findCurrentProject();
                if (project != null) {
                    ApplicationManager.getApplication().invokeLater(() -> {
                        showRequestsDisabledNotification(project);
                    });
                }
            }
        }

        if (notify) {
            this.updateAllStatusBarIcons();
        }

    }


    private void updateAllStatusBarIcons() {
        Runnable action = () -> {
            Project[] var0 = ProjectManager.getInstance().getOpenProjects();
            int var1 = var0.length;

            for (int var2 = 0; var2 < var1; ++var2) {
                Project project = var0[var2];
                if (!project.isDisposed()) {
                    SageCodeXStatusBarWidget.update(project);
                }
            }

        };
        Application application = ApplicationManager.getApplication();
        if (application.isDispatchThread()) {
            action.run();
        } else {
            application.invokeLater(action);
        }

    }

    @RequiresEdt
    private static void showRequestsDisabledNotification(@NotNull Project project) {
        Notification notification = SageCodeXNotifications.createFullContentNotification(
                SageCodeXBundle.get("requestsDisabledNotification.text"),
                NotificationType.ERROR);
        notification.addAction(NotificationAction.createSimpleExpiring(SageCodeXBundle.get("requestsDisabledNotification.hide"), EmptyRunnable.getInstance()));
        notification.notify(project);
    }

    @NotNull
    public static Pair<SageCodeXStatus, String> getCurrentStatus() {
        return (ApplicationManager.getApplication().getService(SageCodeXStatusService.class)).getStatus();
    }


    @NotNull
    private Pair<SageCodeXStatus, String> getStatus() {
        synchronized (this.lock) {
            return Pair.create(this.status, this.message);
        }
    }
}
