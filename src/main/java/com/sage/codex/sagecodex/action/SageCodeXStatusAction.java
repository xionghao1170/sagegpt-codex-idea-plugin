package com.sage.codex.sagecodex.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.util.Pair;
import com.sage.codex.sagecodex.enums.SageCodeXStatus;
import com.sage.codex.sagecodex.theme.SageCodeXStatusService;
import org.jetbrains.annotations.NotNull;

/**
 * @Description：
 * @Author: xionghao
 * @Date: 2024/1/7 19:58
 */
public class SageCodeXStatusAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        System.out.println("SageCodeXSwitchAction");
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        // 将动作的可用性设置为 false，表示不可点击
        Presentation presentation = e.getPresentation();
        presentation.setEnabled(false);
        Pair<SageCodeXStatus, String> status = SageCodeXStatusService.getCurrentStatus();
        presentation.setDisabledIcon((status.first).getIcon());
        presentation.setText("Status: " + (status.first).getPresentableText());
    }
}
