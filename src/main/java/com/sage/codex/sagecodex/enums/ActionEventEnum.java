package com.sage.codex.sagecodex.enums;

public enum ActionEventEnum {

    LOGIN_EVENT("login_event", "用户登录"),
    PROMPT_EVENT("prompt_event", "获取prompt"),

    SESSION_EVENT("session_event", "创建会话"),

    OPEN_MANUAL_EVENT("open_manual_event", "打开用户手册"),

    CHAT_EVENT("chat_event", "聊天事件"),

    INSTRUCT_EVENT("instruct_event", "获取指令事件"),

    LIKE_STOMP_EVENT("like_stomp_event", "点赞/点踩事件"),

    ;


    private String eventName;

    private String eventDescription;

    ActionEventEnum(String eventName, String eventDescription) {
        this.eventName = eventName;
        this.eventDescription = eventDescription;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
}
