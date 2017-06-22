package com.seven.manager.model.newoffer;

import java.io.Serializable;

/**
 * @author seven
 */

public class HouseModel implements Serializable {

    /**
     * 空间id
     */
    private long id;

    /**
     * 空间名称
     */
    private String name;

    /**
     * 空间类型
     */
    private int type;

    /**
     * 是否显示
     */
    private int isShow;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIsShow() {
        return isShow;
    }

    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }

    //---------------------------------------------自定义状态

    /**
     * 选中状态
     */
    public int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "HouseModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", isShow=" + isShow +
                ", status=" + status +
                '}';
    }
}
