package com.sage.codex.sagecodex.model;

/**
 * @Description： 用户登录model对象
 * @Author: xionghao
 * @Date: 2023/11/27 17:03
 */
public class UserLoginReq {

    private String tellerId;

    private String password;

    private String loginType = "W";

    public UserLoginReq(String tellerId, String password) {
        this.tellerId = tellerId;
        this.password = password;
    }

    public String getTellerId() {
        return tellerId;
    }

    public void setTellerId(String tellerId) {
        this.tellerId = tellerId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
}
