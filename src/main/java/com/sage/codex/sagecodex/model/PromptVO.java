package com.sage.codex.sagecodex.model;

import java.util.Date;

/**
 * @Description：
 * @Author: xionghao
 * @Date: 2023/12/22 16:08
 */
public class PromptVO {

    private Long id;

    private String repositoryId;

    private String promptType;

    private String prompt;

    private String promptDescripe;

    private String status;

    private Long tellerId;

    private String tellerName;

    private Date transTime;
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId;
    }

    public String getPromptType() {
        return promptType;
    }

    public void setPromptType(String promptType) {
        this.promptType = promptType;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getPromptDescripe() {
        return promptDescripe;
    }

    public void setPromptDescripe(String promptDescripe) {
        this.promptDescripe = promptDescripe;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTellerId() {
        return tellerId;
    }

    public void setTellerId(Long tellerId) {
        this.tellerId = tellerId;
    }

    public String getTellerName() {
        return tellerName;
    }

    public void setTellerName(String tellerName) {
        this.tellerName = tellerName;
    }

    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
