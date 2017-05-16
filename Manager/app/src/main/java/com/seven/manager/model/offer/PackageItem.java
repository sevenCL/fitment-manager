package com.seven.manager.model.offer;

import com.seven.library.config.RunTimeConfig;

/**
 * 报价-套餐-item
 *
 * @author seven
 */

public class PackageItem extends OfferPackageModel {

    public PackageItem() {
        setViewType(RunTimeConfig.ModelConfig.OFFER_PACKAGE_ITEM);
    }

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

    /**
     * 周长
     */
    private double perimeter;


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

    public double getPerimeter() {
        return perimeter;
    }

    public void setPerimeter(double perimeter) {
        this.perimeter = perimeter;
    }

    @Override
    public String toString() {
        return "PackageItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", area=" + area +
                ", perimeter=" + perimeter +
                '}';
    }
}
