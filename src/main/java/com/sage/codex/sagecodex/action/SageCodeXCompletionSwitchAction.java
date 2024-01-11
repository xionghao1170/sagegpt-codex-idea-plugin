package com.sage.codex.sagecodex.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.application.ApplicationManager;
import com.sage.codex.sagecodex.constant.SageCodeXIcons;
import com.sage.codex.sagecodex.setting.SageConfigurationSetting;
import com.sage.codex.sagecodex.setting.SageConfigurationState;
import org.jetbrains.annotations.NotNull;

/**
 * @Description：
 * @Author: xionghao
 * @Date: 2024/1/8 10:08
 */
public class SageCodeXCompletionSwitchAction extends AnAction {

    @Override
    public void update(@NotNull AnActionEvent e) {
        // 将动作的可用性设置为 false，表示不可点击
        Presentation presentation = e.getPresentation();
        boolean completionSwitch = SageConfigurationSetting.settings().isCompletionSwitch();
        if (completionSwitch) {
            presentation.setIcon(SageCodeXIcons.Enable);
            presentation.setText("Disable Completions");

        } else {
            presentation.setIcon(SageCodeXIcons.Disabled);
            presentation.setText("Enable Completions");
        }

    }


    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        System.out.println("SageCodeXCompletionSwitchAction");
        // 当点击该 Action 时，修改其文本
        AnAction codeXCompletionSwitchAction = e.getActionManager().getAction("SageCodeXCompletionSwitchAction");
        if (codeXCompletionSwitchAction != null) {
            Presentation presentation = e.getPresentation();
            String text = presentation.getText();
            boolean completionSwitch = true;
            if ("Disable Completions".equals(text)) {
                presentation.setText("Enable Completions");
                presentation.setIcon(SageCodeXIcons.Disabled);
                completionSwitch = false;
            } else {
                presentation.setText("Disable Completions");
                presentation.setIcon(SageCodeXIcons.Enable);
                completionSwitch = true;
            }
            SageConfigurationSetting.settings().completionSwitch = completionSwitch;
            SageConfigurationSetting sageConfigurationSetting = ApplicationManager.getApplication().getService(SageConfigurationSetting.class);
            SageConfigurationState currentState = sageConfigurationSetting.getState();
            // 修改 currentState 对象的属性
            sageConfigurationSetting.loadState(currentState);
        }

    }


}
