package com.sage.codex.sagecodex.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

import java.net.URL;

/**
 * @Description：
 * @Author: xionghao
 * @Date: 2024/1/11 17:53
 */
public class SageCodeXLoadUrlAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        String htmlFilePath = "/your-folder/your-html-file.html";
        URL resourceUrl = getClass().getResource(htmlFilePath);
        String url = resourceUrl.toString();


    }
}
