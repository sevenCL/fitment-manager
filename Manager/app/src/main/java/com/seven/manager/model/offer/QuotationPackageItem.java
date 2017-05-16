package com.seven.manager.model.offer;

import java.io.Serializable;

/**
 * 报价单-套餐-item
 *
 * @author seven
 */

public class QuotationPackageItem implements Serializable {

    /**
     * 房间名称
     */
    private String name;


    /**
     * 面积
     */
    private double spaceArea;

    /**
     * 周长
     */
    private double perimeter;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSpaceArea() {
        return spaceArea;
    }

    public void setSpaceArea(double spaceArea) {
        this.spaceArea = spaceArea;
    }

    public double getPerimeter() {
        return perimeter;
    }

    public void setPerimeter(double perimeter) {
        this.perimeter = perimeter;
    }
}
