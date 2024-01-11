package com.sage.codex.sagecodex.enums;

public enum CodeEnvEnum {

    CHAT("chat", "问答区"),
    CHAT_WS("chatws", "工作区"),
    REPO("repo", "业务仓库问答区"),
    REPO_WS("repows", "业务仓库工作区");

    CodeEnvEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    private String code;

    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
