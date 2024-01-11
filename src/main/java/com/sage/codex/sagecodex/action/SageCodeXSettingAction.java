package com.sage.codex.sagecodex.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.Project;
import com.sage.codex.sagecodex.config.SageCodeXSettingConfiguration;
import org.jetbrains.annotations.NotNull;

/**
 * @Description：
 * @Author: xionghao
 * @Date: 2024/1/7 20:37
 */
public class SageCodeXSettingAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        ShowSettingsUtil.getInstance().showSettingsDialog(project, SageCodeXSettingConfiguration.class);

    }
}
