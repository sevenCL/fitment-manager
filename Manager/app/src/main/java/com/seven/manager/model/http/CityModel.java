package com.seven.manager.model.http;

import java.io.Serializable;

/**
 * 城市
 *
 * @author seven
 */
public class CityModel implements Serializable {

    private long regionId; //城市id

    private String regionName; //城市名称

    public long getRegionId() {
        return regionId;
    }

    public void setRegionId(long regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    //===================================================== 自定义

    private boolean select; //是否选中

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    @Override
    public String toString() {
        return "CityModel{" +
                "regionId=" + regionId +
                ", regionName='" + regionName + '\'' +
                ", select=" + select +
                '}';
    }
}
