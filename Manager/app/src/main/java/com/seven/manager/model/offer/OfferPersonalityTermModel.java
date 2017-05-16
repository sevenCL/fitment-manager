package com.seven.manager.model.offer;

import java.io.Serializable;

/**
 * 报价-个性项-施工项
 *
 * @author seven
 */

public class OfferPersonalityTermModel implements Serializable{

    /**
     * 施工项id
     */
    private long id;

    /**
     * 个性项id
     */
    private long baseItemPid;

    /**
     * 个性项名称
     */
    private String baseItemPidName;

    /**
     * 子项id
     */
    private long baseItemId;

    /**
     * 子项名称
     */
    private String baseItemName;

    /**
     * 施工项编码
     */
    private String number;

    /**
     * 子项id
     */
    private long categoryId;

    /**
     * 施工项名称
     */
    private String name;

    /**
     * 施工项单位id
     */
    private String unitId;

    /**
     * 施工项单位
     */
    private String unitName;

    /**
     * 价格
     */
    private double offer;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBaseItemPid() {
        return baseItemPid;
    }

    public void setBaseItemPid(long baseItemPid) {
        this.baseItemPid = baseItemPid;
    }

    public String getBaseItemPidName() {
        return baseItemPidName;
    }

    public void setBaseItemPidName(String baseItemPidName) {
        this.baseItemPidName = baseItemPidName;
    }

    public long getBaseItemId() {
        return baseItemId;
    }

    public void setBaseItemId(long baseItemId) {
        this.baseItemId = baseItemId;
    }

    public String getBaseItemName() {
        return baseItemName;
    }

    public void setBaseItemName(String baseItemName) {
        this.baseItemName = baseItemName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public double getOffer() {
        return offer;
    }

    public void setOffer(double offer) {
        this.offer = offer;
    }

    //---------------------------------------------自定义字段

    /**
     * 方量
     */
    private double volume;

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "OfferPersonalityTermModel{" +
                "id=" + id +
                ", baseItemPid=" + baseItemPid +
                ", baseItemPidName='" + baseItemPidName + '\'' +
                ", baseItemId=" + baseItemId +
                ", baseItemName='" + baseItemName + '\'' +
                ", number='" + number + '\'' +
                ", categoryId=" + categoryId +
                ", name='" + name + '\'' +
                ", unitId='" + unitId + '\'' +
                ", unitName='" + unitName + '\'' +
                ", offer=" + offer +
                ", volume=" + volume +
                '}';
    }
}
