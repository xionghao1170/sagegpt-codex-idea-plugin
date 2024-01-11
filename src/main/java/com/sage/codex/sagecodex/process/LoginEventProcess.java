package com.sage.codex.sagecodex.process;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.wm.ToolWindow;
import com.sage.codex.sagecodex.constant.Constant;
import com.sage.codex.sagecodex.enums.ActionEventEnum;
import com.sage.codex.sagecodex.model.BaseResponse;
import com.sage.codex.sagecodex.service.SageCodeXManageService;
import org.cef.browser.CefBrowser;

/**
 * @Description：
 * @Author: xionghao
 * @Date: 2023/12/27 15:36
 */
public class LoginEventProcess extends ActionEventProcess {
    @Override
    public boolean canProcess(String eventName) {
        return ActionEventEnum.LOGIN_EVENT.getEventName().equals(eventName);
    }

    @Override
    public BaseResponse<Boolean> doProcess(ToolWindow toolWindow, CefBrowser browser, JSONObject parameters) {
        System.out.println("登录事件处理:" + JSON.toJSON(parameters));
        String account = parameters.get("userName").toString();
        String password = parameters.get("password").toString();
        SageCodeXManageService applicationService = ApplicationManager.getApplication().getService(SageCodeXManageService.class);
        // 登陆验证
        boolean loginFlag = applicationService.login(toolWindow.getProject(), account, password);
        if (loginFlag) {
            String url = browser.getURL();
            if (Constant.LOGIN_THEME_BLACK.equals(url)) {
                browser.loadURL(Constant.INDEX_THEME_BLACK);
            } else {
                browser.loadURL(Constant.INDEX_THEME);
            }
            return BaseResponse.success();
        } else {
            return BaseResponse.error("500", "登录失败");
        }
    }
}
