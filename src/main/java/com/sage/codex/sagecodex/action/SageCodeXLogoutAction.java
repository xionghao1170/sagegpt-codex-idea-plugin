package com.sage.codex.sagecodex.action;

import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.sage.codex.sagecodex.constant.SageCodeXBundle;
import com.sage.codex.sagecodex.constant.SageCodeXNotifications;
import com.sage.codex.sagecodex.enums.SageCodeXStatus;
import com.sage.codex.sagecodex.theme.SageCodeXManage;
import com.sage.codex.sagecodex.theme.SageCodeXStatusService;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @Description：
 * @Author: xionghao
 * @Date: 2024/1/7 20:14
 */
public class SageCodeXLogoutAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        SwingUtilities.invokeLater(() -> {
            // 清空用户持久化的信息并跳转到登录页
            SageCodeXManage.initLoginPage(e.getProject());
            // 发布状态通更知更新barWidget : 未登录
            SageCodeXStatusService.notifyApplication(SageCodeXStatus.NotSignedIn);
            // 弹出消息提示
            SageCodeXNotifications.createFullContentNotification(
                            SageCodeXBundle.get("sageCodeX.logout.success.message"),
                            NotificationType.INFORMATION)
                    .notify(e.getProject());

        });


    }


}
