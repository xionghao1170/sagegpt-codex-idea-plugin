package com.sage.codex.sagecodex.window;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.jcef.JBCefBrowser;
import com.sage.codex.sagecodex.constant.SageCodeXIcons;
import com.sage.codex.sagecodex.theme.SageCodeXManage;
import org.cef.browser.CefMessageRouter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

/**
 * @Description： 工具栏窗口工厂
 * @Author: xionghao
 * @Date: 2023/11/22 11:48
 */
public class SageCodeXToolsWindow implements ToolWindowFactory {


    // 创建工具栏视图
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {


        // 创建 JCEF 浏览器
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

        // 设置icon
        toolWindow.setIcon(SageCodeXIcons.SageCodeX);
        toolWindow.getComponent().add(browser.getComponent());

    }

}
