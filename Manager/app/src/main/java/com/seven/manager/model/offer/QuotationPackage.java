package com.seven.manager.model.offer;

import com.seven.library.config.RunTimeConfig;

/**
 * 报价单-套餐
 *
 * @author seven
 */

public class QuotationPackage extends OfferQuotationModel {

    public QuotationPackage() {

        setViewType(RunTimeConfig.ModelConfig.QUOTATION_PACKAGE);

    }

    //户型信息
    private String houseInfo;

    //套餐面积
    private double area;

    //套餐总价
    private double packageMoney;

    //套餐集合
    private String itemsListJson;

    public String getHouseInfo() {
        return houseInfo;
    }

    public void setHouseInfo(String houseInfo) {
        this.houseInfo = houseInfo;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getPackageMoney() {
        return packageMoney;
    }

    public void setPackageMoney(double packageMoney) {
        this.packageMoney = packageMoney;
    }

    public String getItemsListJson() {
        return itemsListJson;
    }

    public void setItemsListJson(String itemsListJson) {
        this.itemsListJson = itemsListJson;
    }

    @Override
    public String toString() {
        return "QuotationPackage{" +
                "houseInfo='" + houseInfo + '\'' +
                ", area=" + area +
                ", packageMoney=" + packageMoney +
                ", itemsListJson='" + itemsListJson + '\'' +
                '}';
    }
}
