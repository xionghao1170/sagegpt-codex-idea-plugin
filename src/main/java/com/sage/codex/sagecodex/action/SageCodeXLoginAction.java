package com.sage.codex.sagecodex.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
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
public class SageCodeXLoginAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        SwingUtilities.invokeLater(() -> {
            // 跳转到登录页
            SageCodeXManage.initLoginPage(e.getProject());
            SageCodeXStatusService.notifyApplication(SageCodeXStatus.NotSignedIn);
        });
    }


}
