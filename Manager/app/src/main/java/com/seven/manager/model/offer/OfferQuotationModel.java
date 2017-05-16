package com.seven.manager.model.offer;

import java.io.Serializable;

/**
 * 报价单
 *
 * @author seven
 */

public class OfferQuotationModel implements Serializable {

    private int viewType;

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }


    //套餐总面积
    private double area;

    //工程总价
    private double totalAmount;

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "OfferQuotationModel{" +
                "viewType=" + viewType +
                ", area=" + area +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
