package com.sage.codex.sagecodex.enums;

public enum RequestStatauEnum {

    SUCCESS("0", "成功"),
    SESSION_FAIL("ASU0010", "会话失效，请重新登录");



    RequestStatauEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    private String code;

    private String description;

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}


