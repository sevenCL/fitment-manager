package com.seven.manager.model.user;

import java.io.Serializable;

/**
 * 注册返回数据模型
 *
 * @author seven
 */
public class RegisterModel implements Serializable {

    private String userCode; //用户码

    private String token; //记号

    private long branchId; //分公司id

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

    public long getBranchId() {
        return branchId;
    }

    public void setBranchId(long branchId) {
        this.branchId = branchId;
    }

    @Override
    public String toString() {
        return "RegisterModel{" +
                "userCode='" + userCode + '\'' +
                ", token='" + token + '\'' +
                ", branchId=" + branchId +
                '}';
    }
}
