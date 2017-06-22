package com.seven.manager.model.newoffer;

import java.io.Serializable;

/**
 * @author seven
 */

public class OfferHouseModel implements Serializable {

    /**
     * 房间分类
     */
    private String name;

    /**
     * 房间类型 1 室 2 厅 3 卫 4 厨 5 阳台 6 全屋
     */
    private int type;

    /**
     * 是否显示
     */
    private int isShow;

    /**
     * 房间集合 室（主卧、次卧、书房）
     */
    private String spaceList;

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

    public String getSpaceList() {
        return spaceList;
    }

    public void setSpaceList(String spaceList) {
        this.spaceList = spaceList;
    }

    @Override
    public String toString() {
        return "OfferHouseModel{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", isShow=" + isShow +
                ", spaceList='" + spaceList + '\'' +
                '}';
    }
}
