package com.sage.codex.sagecodex.model;

import java.util.List;

/**
 * @Description：
 * @Author: xionghao
 * @Date: 2023/11/20 18:40
 */
public class GenerationResp {

    private String id;
    private String object;
    private String model;
    private Usage usage;

    private List<Choice> Choices;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Usage getUsage() {
        return usage;
    }

    public void setUsage(Usage usage) {
        this.usage = usage;
    }

    public List<Choice> getChoices() {
        return Choices;
    }

    public void setChoices(List<Choice> choices) {
        Choices = choices;
    }
}

