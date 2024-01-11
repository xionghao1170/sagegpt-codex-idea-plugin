package com.sage.codex.sagecodex.constant;

import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.AnimatedIcon.Default;

import javax.swing.*;

/**
 * @Description：
 * @Author: xionghao
 * @Date: 2024/1/7 18:39
 */
public final class SageCodeXIcons {

    public static final Icon SageCodeX = forUi("sageCodeX.svg");
    public static final Icon Logout = forUi("logout.svg");

    public static final Icon Login = forUi("login.svg");
    public static final Icon Document = forUi("document.svg");

    public static final Icon Setting = forUi("setting.svg");
    public static final Icon Disabled = forUi("disabled.svg");
    public static final Icon Enable = forUi("enable.svg");

    public static final Icon Chat = forUi("chat.svg");

    public static final Icon StatusBarCompletionInProgress = new Default();

    public static final Icon CopilotDisconnected = forUi("copilot_disconnected.svg");
    public static final Icon CopilotNotAvailable = forUi("copilot_not_available.svg");
    public static final Icon CopilotWarning = forUi("copilot_warning.svg");

    private SageCodeXIcons() {
    }

    private static Icon forUi(String iconFileName) {
        return IconLoader.getIcon("/icon/" + iconFileName, SageCodeXIcons.class);
    }
}
