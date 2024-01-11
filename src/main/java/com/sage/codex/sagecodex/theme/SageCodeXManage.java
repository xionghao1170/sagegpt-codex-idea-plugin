package com.sage.codex.sagecodex.theme;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.colors.EditorColorsListener;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.jcef.JBCefBrowser;
import com.sage.codex.sagecodex.config.ProjectGlobalConfig;
import com.sage.codex.sagecodex.constant.Constant;
import com.sage.codex.sagecodex.handler.SageCodeXDocumentListener;
import com.sage.codex.sagecodex.setting.UserInfoConfigurationSetting;
import com.sage.codex.sagecodex.setting.UserInfoConfigurationState;
import com.sage.codex.sagecodex.window.HtmlMessageHandler;
import org.apache.commons.lang3.StringUtils;
import org.cef.browser.CefMessageRouter;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * @Description：
 * @Author: xionghao
 * @Date: 2024/1/7 21:27
 */
public class SageCodeXManage {


    private SageCodeXManage() {
        //nothing
    }

    public static void subscribeEditorColors(JBCefBrowser browser) {
        // 监听主题变化
        ApplicationManager.getApplication().getMessageBus().connect().subscribe(EditorColorsManager.TOPIC, (EditorColorsListener) scheme -> {
            // 获取当前主题颜色
            Color backgroundColor = scheme.getDefaultBackground();
            String hexColor = String.format("#%06X", (0xFFFFFF & backgroundColor.getRGB()));
            //  调用方法切换主题
            switchThemes(hexColor, browser);
        });
    }


    public static void switchThemes(String hexColor, JBCefBrowser browser) {

        URL url = SageCodeXManage.class.getResource("/webview/login-black2.html");
        String localLoadUrl = url.toString();
        System.out.println("localLoadUrl==" + localLoadUrl);
        browser.loadURL(localLoadUrl);
//        String loadUrl;
//        String xToken = UserInfoConfigurationSetting.settings().getxToken();
//        if (StringUtils.isNotEmpty(xToken)) {
//            // 有token,设置为index黑色主题
//            loadUrl = Constant.INDEX_THEME_BLACK;
//        } else {
//            // 没有token,设置为login黑色主题
//            loadUrl = Constant.LOGIN_THEME_BLACK;
//        }
//        if ("#FFFFFF".equals(hexColor)) {
//            // 白色
//            if (Constant.INDEX_THEME_BLACK.equals(loadUrl)) {
//                loadUrl = Constant.INDEX_THEME;
//            } else {
//                loadUrl = Constant.LOGIN_THEME;
//            }
//        }
//        String url = browser.getCefBrowser().getURL();
//        if (!loadUrl.equals(url)) {
//            browser.loadURL(loadUrl);
//        }
    }


    public static void initLoginPage(Project project) {
        ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
        ToolWindow toolWindow = toolWindowManager.getToolWindow("SageCodeX");
        Component content = toolWindow.getContentManager().getComponent();
        if (content instanceof JPanel) {
            // 清空之前的用户登录信息
            UserInfoConfigurationState settings = UserInfoConfigurationSetting.settings();
            settings.tellerId = null;
            settings.tellerName = null;
            settings.xToken = null;
            settings.chatId = null;

            UserInfoConfigurationSetting userInfoConfigurationSetting = ApplicationManager.getApplication().getService(UserInfoConfigurationSetting.class);
            UserInfoConfigurationState currentState = userInfoConfigurationSetting.getState();
            // 修改 currentState 对象的属性
            userInfoConfigurationSetting.loadState(currentState);

            JPanel panel = (JPanel) content;
            // 移除旧的组件
            for (Component component : panel.getComponents()) {
                panel.remove(component);
            }
            JBCefBrowser browser = new JBCefBrowser();
            // 创建CefMessageRouter对象，并添加处理程序
            CefMessageRouter messageRouter = CefMessageRouter.create(new CefMessageRouter.CefMessageRouterConfig("sageCodeXCefQuery", "sageCodeCefQueryCancel"));
            messageRouter.addHandler(new HtmlMessageHandler(toolWindow), true);
            browser.getJBCefClient().getCefClient().addMessageRouter(messageRouter);

            // 获取 ToolWindow 的组件
            JComponent toolWindowComponent = toolWindow.getContentManager().getComponent();
            // 获取当前主题的背景颜色
            Color defaultGroundColor = toolWindowComponent.getBackground();
            String defaultColor = String.format("#%06X", (0xFFFFFF & defaultGroundColor.getRGB()));
            SageCodeXManage.switchThemes(defaultColor, browser);
            // 订阅idea颜色变化主题
            SageCodeXManage.subscribeEditorColors(browser);
            // 设置新的 JBCefBrowser
            panel.add(browser.getComponent());

            // 更新 UI
            panel.revalidate();
            panel.repaint();
        }

        toolWindow.show(null);
    }


    public static void checkAndRemoveListener(Editor editor) {
        SageCodeXDocumentListener documentListener = ProjectGlobalConfig.getDocumentListener();
        if (editor != null && ProjectGlobalConfig.hasRegister()) {
            editor.getDocument().removeDocumentListener(documentListener);
            ProjectGlobalConfig.setNotRegister();
        }
    }

    public static void checkAndAddDocumentListener(Editor editor) {
        if (!ProjectGlobalConfig.hasRegister()) {
            editor.getDocument().addDocumentListener(ProjectGlobalConfig.getDocumentListener());
            ProjectGlobalConfig.setIsRegister();
        }

    }
}

