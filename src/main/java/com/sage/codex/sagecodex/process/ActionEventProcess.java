package com.sage.codex.sagecodex.process;

import com.alibaba.fastjson.JSONObject;
import com.intellij.openapi.wm.ToolWindow;
import com.sage.codex.sagecodex.model.BaseResponse;
import org.cef.browser.CefBrowser;

/**
 * @Description：
 * @Author: xionghao
 * @Date: 2023/12/27 17:44
 */
public abstract class ActionEventProcess implements IActionEventProcess {

    @Override
    public  BaseResponse doProcess(ToolWindow toolWindow, CefBrowser browser, JSONObject parameters) {
        return BaseResponse.success();
    }



}
