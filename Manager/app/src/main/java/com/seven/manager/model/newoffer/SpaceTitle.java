package com.seven.manager.model.newoffer;

import com.seven.library.config.RunTimeConfig;

/**
 * @author seven
 */

public class SpaceTitle extends OfferSpaceModel {

    public SpaceTitle() {
        setViewType(RunTimeConfig.ModelConfig.SPACE_TITLE);
    }

    /**
     * 地址
     */
    private String house;

    /**
     * 业主名字
     */
    private String ownerName;

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    @Override
    public String toString() {
        return "SpaceTitle{" +
                "house='" + house + '\'' +
                ", ownerName='" + ownerName + '\'' +
                '}';
    }
}
