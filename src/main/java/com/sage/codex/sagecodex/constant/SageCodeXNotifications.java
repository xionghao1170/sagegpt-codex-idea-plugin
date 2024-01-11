package com.sage.codex.sagecodex.constant;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationAction;
import com.intellij.notification.NotificationType;
import com.intellij.notification.NotificationListener.UrlOpeningListener;
import com.intellij.notification.Notifications;
import com.intellij.notification.impl.NotificationFullContent;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NlsContexts.NotificationContent;
import com.intellij.openapi.util.NlsContexts.NotificationTitle;
import com.sage.codex.sagecodex.config.SageCodeXSettingConfiguration;
import org.jetbrains.annotations.NotNull;

/**
 * @Description：
 * @Author: xionghao
 * @Date: 2024/1/7 18:23
 */
public class SageCodeXNotifications {

    private SageCodeXNotifications() {
    }

    public static void notifications(Project project, String content) {
        Notification notification = buildNotification(content, NotificationType.ERROR);
        Notifications.Bus.notify(notification, project);
    }


    public static void serverEmptyNotifications(Project project) {
        String content = "请先设置服务url地址";
        Notification notification = buildNotification(content, NotificationType.WARNING);

        // 添加到通知内容中
        notification.addAction(NotificationAction.createSimpleExpiring(
                "Click here to open settings",
                () -> ShowSettingsUtil.getInstance().showSettingsDialog(project, SageCodeXSettingConfiguration.class)
        ));

        Notifications.Bus.notify(notification, project);
    }

    private static Notification buildNotification(String content, NotificationType type) {
        return new Notification(
                SageCodeXBundle.get("sageCodeX.notificationGroup.id"),
                SageCodeXBundle.get("sageCodeX.notification.title"),
                content,
                type
        );
    }


    public static Notification createFullContentNotification(@NotNull String content, @NotNull NotificationType type) {
        SageCodeXNotifications.FullContent notification = new SageCodeXNotifications.FullContent(
                SageCodeXBundle.get("sageCodeX.notificationGroup.id"),
                SageCodeXBundle.get("sageCodeX.notification.title"),
                content,
                type);
        return notification;
    }

    private static class FullContent extends Notification implements NotificationFullContent {
        public FullContent(@NotNull String groupId, @NotNull @NotificationTitle String title, @NotNull @NotificationContent String content, @NotNull NotificationType type) {
            super(groupId, title, content, type);
        }
    }
}
