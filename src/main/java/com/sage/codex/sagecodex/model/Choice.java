package com.sage.codex.sagecodex.model;

/**
 * @Description：
 * @Author: xionghao
 * @Date: 2023/11/21 11:15
 */
public class Choice {

    private Integer index;

    private String finish_reason;

    private Message message;

    private Delta delta;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getFinish_reason() {
        return finish_reason;
    }

    public void setFinish_reason(String finish_reason) {
        this.finish_reason = finish_reason;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Delta getDelta() {
        return delta;
    }

    public void setDelta(Delta delta) {
        this.delta = delta;
    }
}
