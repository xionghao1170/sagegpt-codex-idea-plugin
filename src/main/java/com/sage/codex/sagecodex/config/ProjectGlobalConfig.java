package com.sage.codex.sagecodex.config;

import com.intellij.ui.jcef.JBCefBrowser;
import com.sage.codex.sagecodex.handler.SageCodeXDocumentListener;

/**
 * @Description：
 * @Author: xionghao
 * @Date: 2024/1/3 19:52
 */
public class ProjectGlobalConfig {
    private static SageCodeXDocumentListener documentListener;

    private static boolean hasRegister = false;

    public static void setDocumentListener(SageCodeXDocumentListener listener) {
        documentListener = listener;
        hasRegister = true;
    }

    public static SageCodeXDocumentListener getDocumentListener() {
        return documentListener;
    }

    public static boolean hasRegister() {
        return hasRegister;
    }

    public static void setIsRegister() {
        hasRegister = true;
    }

    public static void setNotRegister() {
        hasRegister = false;
    }

}
