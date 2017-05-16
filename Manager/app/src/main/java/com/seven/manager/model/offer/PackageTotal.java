package com.seven.manager.model.offer;

import com.seven.library.config.RunTimeConfig;

/**
 * 报价-套餐-total
 *
 * @author seven
 */

public class PackageTotal extends OfferPackageModel {

    public PackageTotal() {
        setViewType(RunTimeConfig.ModelConfig.OFFER_PACKAGE_TOTAL);
    }

    /**
     * 套餐价格
     */
    private double price;

    /**
     * 最小计价面积
     */
    private double minArea;

    /**
     * 户型信息
     */
    private String houseInfo;

    /**
     * 总面积
     */
    private double totalArea;

    /**
     * 总价
     */
    private double totalMoney;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getMinArea() {
        return minArea;
    }

    public void setMinArea(double minArea) {
        this.minArea = minArea;
    }

    public String getHouseInfo() {
        return houseInfo;
    }

    public void setHouseInfo(String houseInfo) {
        this.houseInfo = houseInfo;
    }

    public double getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(double totalArea) {
        this.totalArea = totalArea;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    @Override
    public String toString() {
        return "PackageTotal{" +
                "price=" + price +
                ", minArea=" + minArea +
                ", houseInfo='" + houseInfo + '\'' +
                ", totalArea=" + totalArea +
                ", totalMoney=" + totalMoney +
                '}';
    }
}
