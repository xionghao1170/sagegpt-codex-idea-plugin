package com.sage.codex.sagecodex.process;

import com.alibaba.fastjson.JSONObject;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.wm.ToolWindow;
import com.sage.codex.sagecodex.enums.ActionEventEnum;
import com.sage.codex.sagecodex.model.BaseResponse;
import com.sage.codex.sagecodex.model.GenerationReq;
import com.sage.codex.sagecodex.service.SageCodeXManageService;
import org.cef.browser.CefBrowser;

/**
 * @Description：
 * @Author: xionghao
 * @Date: 2023/12/27 15:36
 */
public class ChatEventProcess extends ActionEventProcess {
    @Override
    public boolean canProcess(String eventName) {
        return ActionEventEnum.SESSION_EVENT.getEventName().equals(eventName);
    }

    @Override
    public BaseResponse<String> doProcess(ToolWindow toolWindow, CefBrowser browser, JSONObject parameters) {
        String chatId = parameters.get("chatId").toString();
        String dialogueId = parameters.get("dialogueId").toString();
        String content = parameters.get("content").toString();

        SageCodeXManageService applicationService = ApplicationManager.getApplication().getService(SageCodeXManageService.class);
        GenerationReq req = applicationService.buildGenerationReq(chatId, dialogueId, content, false);
        String response = applicationService.doHttpPost(toolWindow.getProject(), req, false);
        System.out.println("---response: " + response);
        return BaseResponse.success(response);
    }
}
