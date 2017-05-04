package com.seven.manager.model.http;

import java.io.Serializable;

/**
 * 注册返回数据模型
 *
 * @author seven
 */
public class RegisterModel implements Serializable{

    private String userCode; //用户码

    private String token; //记号

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "RegisterModel{" +
                "userCode='" + userCode + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
