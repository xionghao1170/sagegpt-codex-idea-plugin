package com.sage.codex.sagecodex.process;

import com.alibaba.fastjson.JSONObject;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.wm.ToolWindow;
import com.sage.codex.sagecodex.enums.ActionEventEnum;
import com.sage.codex.sagecodex.enums.CodeEnvEnum;
import com.sage.codex.sagecodex.model.BaseResponse;
import com.sage.codex.sagecodex.service.SageCodeXManageService;
import org.cef.browser.CefBrowser;

/**
 * @Description：
 * @Author: xionghao
 * @Date: 2023/12/27 15:36
 */
public class InstructEventProcess extends ActionEventProcess {
    @Override
    public boolean canProcess(String eventName) {
        return ActionEventEnum.SESSION_EVENT.getEventName().equals(eventName);
    }

    @Override
    public BaseResponse<String> doProcess(ToolWindow toolWindow, CefBrowser browser, JSONObject parameters) {
        String query = parameters.get("content").toString();
        SageCodeXManageService applicationService = ApplicationManager.getApplication().getService(SageCodeXManageService.class);
       return BaseResponse.success(applicationService.getInstructList(toolWindow.getProject(), CodeEnvEnum.CHAT,query,browser));
    }
}
