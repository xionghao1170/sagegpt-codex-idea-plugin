package com.sage.codex.sagecodex.model;

import java.util.Map;

/**
 * @Description：
 * @Author: xionghao
 * @Date: 2023/11/27 17:43
 */
public class CommonResponse {

    private Map<String,Object> data;
    private String code;
    private String message;


    public CommonResponse() {
        //nothing
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

