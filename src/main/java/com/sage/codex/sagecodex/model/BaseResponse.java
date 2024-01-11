package com.sage.codex.sagecodex.model;

/**
 * @Description：
 * @Author: xionghao
 * @Date: 2023/12/22 16:05
 */
public class BaseResponse<T> {

    private T data;
    private String code;
    private String message;

    public T getData() {
        return data;
    }

    public void setData(T data) {
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

    public static <T> BaseResponse<T> success(T data) {
        BaseResponse<T> response = new BaseResponse<T>();
        response.setCode("0");
        response.setMessage("success");
        response.setData(data);
        return response;
    }

    public static <T> BaseResponse<T> success() {
        BaseResponse<T> response = new BaseResponse<T>();
        response.setCode("0");
        response.setMessage("success");
        return response;
    }

    public static <T> BaseResponse<T> error(String code, String msg) {
        BaseResponse<T> response = new BaseResponse<T>();
        response.setCode(code);
        response.setMessage(msg);
        return response;
    }
}
