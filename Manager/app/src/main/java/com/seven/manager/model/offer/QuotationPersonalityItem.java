package com.seven.manager.model.offer;

import java.io.Serializable;

/**
 * 报价单-个性项-item
 *
 * @author seven
 */

public class QuotationPersonalityItem implements Serializable {

    //个性项名称
    private String name;

    //方量
    private double quantity;

    //单价
    private double price;

    //单位
    private String unit;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "QuotationPersonalityItem{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", unit='" + unit + '\'' +
                '}';
    }
}
