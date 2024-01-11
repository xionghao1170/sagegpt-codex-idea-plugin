package com.sage.codex.sagecodex.setting;

/**
 * @Description：
 * @Author: xionghao
 * @Date: 2024/1/9 17:51
 */
public class SageConfigurationState {


    /**
     * 服务访问地址
     */
    public String serverUrl;

    /**
     * 是否开启自动补全， 默认是开启的
     */
    public boolean completionSwitch = true;

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public boolean isCompletionSwitch() {
        return completionSwitch;
    }

    public void setCompletionSwitch(boolean completionSwitch) {
        this.completionSwitch = completionSwitch;
    }
}
