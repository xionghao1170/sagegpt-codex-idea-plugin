package com.sage.codex.sagecodex.process;

import com.alibaba.fastjson.JSONObject;
import com.intellij.openapi.wm.ToolWindow;
import com.sage.codex.sagecodex.model.BaseResponse;
import org.cef.browser.CefBrowser;

public interface IActionEventProcess<T> {


    boolean canProcess(String eventName);

    BaseResponse<T> doProcess(ToolWindow toolWindow, CefBrowser browser, JSONObject parameters);


}
