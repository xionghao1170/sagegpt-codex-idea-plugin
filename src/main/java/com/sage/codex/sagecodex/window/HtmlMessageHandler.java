package com.sage.codex.sagecodex.window;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.intellij.openapi.wm.ToolWindow;
import com.sage.codex.sagecodex.enums.ActionEventEnum;
import com.sage.codex.sagecodex.enums.RequestStatauEnum;
import com.sage.codex.sagecodex.model.BaseResponse;
import com.sage.codex.sagecodex.process.*;
import com.sage.codex.sagecodex.setting.SageConfigurationSetting;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.callback.CefQueryCallback;
import org.cef.handler.CefMessageRouterHandlerAdapter;

/**
 * @Description：
 * @Author: xionghao
 * @Date: 2023/12/27 11:10
 */
public class HtmlMessageHandler extends CefMessageRouterHandlerAdapter {

    private ToolWindow toolWindow;


    public HtmlMessageHandler(ToolWindow toolWindow) {
        this.toolWindow = toolWindow;
    }

    @Override
    public boolean onQuery(CefBrowser browser, CefFrame frame, long queryId, String request, boolean persistent, CefQueryCallback callback) {
        if (SageConfigurationSetting.checkServerUrlIsEmpty(toolWindow.getProject())) {
            // 未设置服务地址，直接返回
            return false;
        }

        // 解析 JavaScript 请求
        JSONObject jsonObject = JSON.parseObject(request);
        String eventName = jsonObject.get("event").toString();
        JSONObject parameter = (JSONObject) jsonObject.get("parameter");
        IActionEventProcess eventProcess;
        if (ActionEventEnum.LOGIN_EVENT.getEventName().equals(eventName)) {
            // 登录事件处理
            eventProcess = new LoginEventProcess();
            BaseResponse<String> response = eventProcess.doProcess(toolWindow, browser, parameter);
            if (this.checkCallback(response.getCode())) {
                callback.success(response.getCode());
                return true;
            }
        } else if (ActionEventEnum.PROMPT_EVENT.getEventName().equals(eventName)) {
            // 获取prompt
            eventProcess = new PromptEventProcess();
            BaseResponse<String> response = eventProcess.doProcess(toolWindow, browser, parameter);
            if (this.checkCallback(response.getCode())) {
                callback.success(response.getData());
                return true;
            }
        } else if (ActionEventEnum.SESSION_EVENT.getEventName().equals(eventName)) {
            // 创建会话
            eventProcess = new SessionEventProcess();
            BaseResponse<String> response = eventProcess.doProcess(toolWindow, browser, parameter);
            if (this.checkCallback(response.getCode())) {
                callback.success(response.getData());
                return true;
            }
        } else if (ActionEventEnum.OPEN_MANUAL_EVENT.getEventName().equals(eventName)) {
            //  打开手动操作页面
            eventProcess = new ManualEventProcess();
            eventProcess.doProcess(toolWindow, browser, parameter);
            return true;
        } else if (ActionEventEnum.CHAT_EVENT.getEventName().equals(eventName)) {
            //   聊天事件
            eventProcess = new ChatEventProcess();
            BaseResponse<String> response = eventProcess.doProcess(toolWindow, browser, parameter);
            if (this.checkCallback(response.getCode())) {
                callback.success(response.getData());
                return true;
            }
        } else if (ActionEventEnum.INSTRUCT_EVENT.getEventName().equals(eventName)) {
            //   聊天事件
            eventProcess = new InstructEventProcess();
            BaseResponse<String> response = eventProcess.doProcess(toolWindow, browser, parameter);
            if (this.checkCallback(response.getCode())) {
                callback.success(response.getData());
                return true;
            }
        } else if (ActionEventEnum.LIKE_STOMP_EVENT.getEventName().equals(eventName)) {
            //   聊天事件
            eventProcess = new LikeStompEventProcess();
            BaseResponse<String> response = eventProcess.doProcess(toolWindow, browser, parameter);
            if (this.checkCallback(response.getCode())) {
                callback.success(response.getData());
                return true;
            }
        }
        return false;
    }

    private boolean checkCallback(String code) {
        return RequestStatauEnum.SUCCESS.getCode().equals(code);
    }

    @Override
    public void onQueryCanceled(CefBrowser browser, CefFrame frame, long queryId) {
        System.out.println("收到来自html页面的请求1：");
    }

}
