package com.seven.manager.model.newoffer;

import java.io.Serializable;

/**
 * @author seven
 */

public class SpaceItemList implements Serializable {

    /**
     * 房间id
     */
    private long id;

    /**
     * 房间名称
     */
    private String name;

    /**
     * 房间类型
     */
    private int type;

    /**
     * 空间id
     *
     */
    private long spaceId;

    /**
     * 面积
     */
    private double area;

    //-------------------------------------------------自定义

    /**
     * 是否展示名字
     */
    private boolean isShowName;

    private int tag;

    private double volume;

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public boolean isShowName() {
        return isShowName;
    }

    public void setShowName(boolean showName) {
        isShowName = showName;
    }

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

    public long getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(long spaceId) {
        this.spaceId = spaceId;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "SpaceItemList{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", spaceId=" + spaceId +
                ", area=" + area +
                '}';
    }
}
