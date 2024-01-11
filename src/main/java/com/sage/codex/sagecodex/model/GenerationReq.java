package com.sage.codex.sagecodex.model;

import java.util.List;

/**
 * @Description：
 * @Author: xionghao
 * @Date: 2023/11/20 18:40
 */
public class GenerationReq {

    private String chat_id;
    private String dialogue_id;
    private String p_dialogue_id;
    private String action;
    private List<Message> messages;
    private String language;
    private String code_env;
    private String code_purpose;
    private String model;
    private  Boolean stream;
    private String prompt_id;
    private List<String> repository_id;

    private PageContext page_context;

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public String getDialogue_id() {
        return dialogue_id;
    }

    public void setDialogue_id(String dialogue_id) {
        this.dialogue_id = dialogue_id;
    }

    public String getP_dialogue_id() {
        return p_dialogue_id;
    }

    public void setP_dialogue_id(String p_dialogue_id) {
        this.p_dialogue_id = p_dialogue_id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCode_env() {
        return code_env;
    }

    public void setCode_env(String code_env) {
        this.code_env = code_env;
    }

    public String getCode_purpose() {
        return code_purpose;
    }

    public void setCode_purpose(String code_purpose) {
        this.code_purpose = code_purpose;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Boolean getStream() {
        return stream;
    }

    public void setStream(Boolean stream) {
        this.stream = stream;
    }

    public String getPrompt_id() {
        return prompt_id;
    }

    public void setPrompt_id(String prompt_id) {
        this.prompt_id = prompt_id;
    }

    public List<String> getRepository_id() {
        return repository_id;
    }

    public void setRepository_id(List<String> repository_id) {
        this.repository_id = repository_id;
    }

    public PageContext getPage_context() {
        return page_context;
    }

    public void setPage_context(PageContext page_context) {
        this.page_context = page_context;
    }
}

