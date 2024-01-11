package com.sage.codex.sagecodex.process;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.google.gson.Gson;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.wm.ToolWindow;
import com.sage.codex.sagecodex.enums.ActionEventEnum;
import com.sage.codex.sagecodex.model.BaseResponse;
import com.sage.codex.sagecodex.service.SageCodeXManageService;
import org.apache.commons.collections.CollectionUtils;
import org.cef.browser.CefBrowser;

import java.util.List;

/**
 * @Description：
 * @Author: xionghao
 * @Date: 2023/12/27 15:36
 */
public class PromptEventProcess extends ActionEventProcess {
    @Override
    public boolean canProcess(String eventName) {
        return ActionEventEnum.PROMPT_EVENT.getEventName().equals(eventName);
    }

    @Override
    public BaseResponse<String> doProcess(ToolWindow toolWindow, CefBrowser browser, JSONObject parameters) {
        System.out.println("获取prompt:" + JSON.toJSON(parameters));
        SageCodeXManageService applicationService = ApplicationManager.getApplication().getService(SageCodeXManageService.class);
        List<String> prompts = applicationService.loadPrompt(toolWindow.getProject(), browser);
        if (CollectionUtils.isEmpty(prompts)) {
            return BaseResponse.success("");
        }
        Gson gson = new Gson();
        return BaseResponse.success(gson.toJson(prompts));
    }
}
