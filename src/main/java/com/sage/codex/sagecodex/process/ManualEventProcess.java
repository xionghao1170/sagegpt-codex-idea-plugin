package com.sage.codex.sagecodex.process;

import com.alibaba.fastjson.JSONObject;
import com.intellij.openapi.wm.ToolWindow;
import com.sage.codex.sagecodex.constant.Constant;
import com.sage.codex.sagecodex.constant.SageCodeXNotifications;
import com.sage.codex.sagecodex.enums.ActionEventEnum;
import com.sage.codex.sagecodex.model.BaseResponse;
import org.cef.browser.CefBrowser;

import java.awt.*;
import java.net.URI;

/**
 * @Description：
 * @Author: xionghao
 * @Date: 2023/12/27 15:36
 */
public class ManualEventProcess extends ActionEventProcess {
    @Override
    public boolean canProcess(String eventName) {
        return ActionEventEnum.OPEN_MANUAL_EVENT.getEventName().equals(eventName);
    }

    @Override
    public BaseResponse<String> doProcess(ToolWindow toolWindow, CefBrowser browser, JSONObject parameters) {
        // 执行点击事件的操作
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(Constant.SAGE_CODE_X_DOCUMENT_URL));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            SageCodeXNotifications.notifications(toolWindow.getProject(), "无法打开手册，请检查系统配置");
            return BaseResponse.error("500", "无法打开手册，请检查系统配置");
        }
        return BaseResponse.success();
    }
}
