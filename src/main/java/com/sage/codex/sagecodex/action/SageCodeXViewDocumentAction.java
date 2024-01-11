package com.sage.codex.sagecodex.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.sage.codex.sagecodex.constant.Constant;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @Description：
 * @Author: xionghao
 * @Date: 2024/1/7 20:30
 */
public class SageCodeXViewDocumentAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        try {
            // 打开的链接
            Desktop.getDesktop().browse(new URI(Constant.SAGE_CODE_X_DOCUMENT_URL));
        } catch (IOException | URISyntaxException ex) {
            ex.printStackTrace();
        }
    }
}
