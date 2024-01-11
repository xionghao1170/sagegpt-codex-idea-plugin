package com.sage.codex.sagecodex.window;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.ListPopup;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.StatusBarWidget;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.openapi.wm.impl.status.EditorBasedStatusBarPopup;
import com.sage.codex.sagecodex.constant.SageCodeXBundle;
import com.sage.codex.sagecodex.constant.SageCodeXIcons;
import com.sage.codex.sagecodex.enums.SageCodeXStatus;
import com.sage.codex.sagecodex.theme.SageCodeXStatusService;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @Description：
 * @Author: xionghao
 * @Date: 2024/1/7 16:45
 */
public class SageCodeXStatusBarWidget extends EditorBasedStatusBarPopup {

    public static void update(@NotNull Project project) {
        SageCodeXStatusBarWidget widget = findWidget(project);
        if (widget != null) {
            widget.update(() -> {
                widget.myStatusBar.updateWidget(SageCodeXBundle.get("sageCodeX.barWidget.id"));
            });
        }

    }


    public SageCodeXStatusBarWidget(@NotNull Project project) {
        super(project, false);
    }

    @Override
    public @NonNls @NotNull String ID() {
        return SageCodeXBundle.get("sageCodeX.barWidget.id");
    }


    @Override
    protected @NotNull WidgetState getWidgetState(@Nullable VirtualFile file) {
        Pair<SageCodeXStatus, String> statusAndMessage = SageCodeXStatusService.getCurrentStatus();
        SageCodeXStatus status = statusAndMessage.first;
        String message = statusAndMessage.second;
        String toolTip = message == null ? SageCodeXBundle.get("statusBar.tooltipForError", new Object[]{status.getPresentableText()}) : SageCodeXBundle.get("statusBar.tooltipForErrorCustomMessage", new Object[]{status.getPresentableText(), message});
        WidgetState state = new WidgetState(toolTip, "", true);
        state.setIcon(status.getIcon());
        return state;
    }

    @Override
    protected @Nullable ListPopup createPopup(DataContext context) {
        SageCodeXStatus currentStatus = SageCodeXStatusService.getCurrentStatus().first;
        AnAction configuredGroup = ActionManager.getInstance().getAction(this.findPopupMenuId(currentStatus));
        DataContext dataContext = DataManager.getInstance().getDataContext(getComponent());
        return JBPopupFactory.getInstance().createActionGroupPopup("SageCodeX Status", (ActionGroup) configuredGroup, dataContext, JBPopupFactory.ActionSelectionAid.SPEEDSEARCH, true);
    }

    @NotNull
    private String findPopupMenuId(@NotNull SageCodeXStatus currentStatus) {
        if (currentStatus == SageCodeXStatus.Ready || currentStatus == SageCodeXStatus.CompletionInProgress) {
            return SageCodeXBundle.get("sageCodeX.group.ready.id");
        } else if (currentStatus == SageCodeXStatus.NotSignedIn) {
            return SageCodeXBundle.get("sageCodeX.group.notSigned.id");
        } else {
            return currentStatus.isDisablingClientRequests() ? "copilot.statusBarRestartPopup" : "copilot.statusBarErrorPopup";
        }
    }

    @Override
    protected @NotNull StatusBarWidget createInstance(@NotNull Project project) {
        return null;
    }

    @Override
    public void dispose() {
        // 清理资源
    }

    @Nullable
    private static SageCodeXStatusBarWidget findWidget(@NotNull Project project) {
        StatusBar bar = WindowManager.getInstance().getStatusBar(project);
        if (bar != null) {
            StatusBarWidget widget = bar.getWidget(SageCodeXBundle.get("sageCodeX.barWidget.id"));
            if (widget instanceof SageCodeXStatusBarWidget) {
                return (SageCodeXStatusBarWidget) widget;
            }
        }
        return null;
    }

}
