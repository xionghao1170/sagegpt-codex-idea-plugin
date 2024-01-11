package com.sage.codex.sagecodex.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import org.jetbrains.annotations.NotNull;

/**
 * @Description：
 * @Author: xionghao
 * @Date: 2024/1/7 20:37
 */
public class SageCodeXOpenChatAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(e.getProject());
        ToolWindow toolWindow = toolWindowManager.getToolWindow("SageCodeX");
        if (toolWindow != null) {
            toolWindow.show(null);
        }
    }
}
