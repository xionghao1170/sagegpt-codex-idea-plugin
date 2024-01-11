package com.sage.codex.sagecodex.setting;

import com.sage.codex.sagecodex.enums.SageCodeXStatus;

/**
 * @Description：
 * @Author: xionghao
 * @Date: 2024/1/9 17:36
 */
public class UserInfoConfigurationState {


    /**
     * 用户ID
     */
    public String tellerId;

    /**
     * 用户名称
     */
    public String tellerName;

    /**
     * token信息
     */
    public String xToken;

    /**
     * 会话Id
     */
    public String chatId;

    /**
     * 用户状态
     */
    public SageCodeXStatus status;

    public String getTellerId() {
        return tellerId;
    }

    public void setTellerId(String tellerId) {
        this.tellerId = tellerId;
    }

    public String getTellerName() {
        return tellerName;
    }

    public void setTellerName(String tellerName) {
        this.tellerName = tellerName;
    }

    public String getxToken() {
        return xToken;
    }

    public void setxToken(String xToken) {
        this.xToken = xToken;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public SageCodeXStatus getStatus() {
        return status;
    }

    public void setStatus(SageCodeXStatus status) {
        this.status = status;
    }
}
