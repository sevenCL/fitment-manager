package com.seven.manager.model.offer;

import com.seven.library.config.RunTimeConfig;

/**
 * 报价单-头部
 *
 * @author seven
 */

public class QuotationTitle extends OfferQuotationModel {

    public QuotationTitle() {

        setViewType(RunTimeConfig.ModelConfig.QUOTATION_TITLE);

    }

    //业主id
    private long ownerId;

    //业主名字
    private String ownerName;

    //项目id
    private long projectId;

    //地址
    private String houseNumber;

    //总价
    private double totalAmount;

    //总面积
    private double area;

    //厅
    private int halles;

    //室
    private int rooms;

    //厨房
    private int cookhouse;

    //卫生间
    private int washroom;

    //阳台
    private int balcony;

    //其他
    private int others;

    //户型信息
    private String houseInfo;

    //套餐总价
    private double packageMoney;

    //个性项数量
    private int personalityCount;

    //个性项总价
    private double personalityMoney;

    //哈希
    private int hashCode;

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public int getHalles() {
        return halles;
    }

    public void setHalles(int halles) {
        this.halles = halles;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public int getCookhouse() {
        return cookhouse;
    }

    public void setCookhouse(int cookhouse) {
        this.cookhouse = cookhouse;
    }

    public int getWashroom() {
        return washroom;
    }

    public void setWashroom(int washroom) {
        this.washroom = washroom;
    }

    public int getBalcony() {
        return balcony;
    }

    public void setBalcony(int balcony) {
        this.balcony = balcony;
    }

    public int getOthers() {
        return others;
    }

    public void setOthers(int others) {
        this.others = others;
    }

    public String getHouseInfo() {
        return houseInfo;
    }

    public void setHouseInfo(String houseInfo) {
        this.houseInfo = houseInfo;
    }

    public double getPackageMoney() {
        return packageMoney;
    }

    public void setPackageMoney(double packageMoney) {
        this.packageMoney = packageMoney;
    }

    public int getPersonalityCount() {
        return personalityCount;
    }

    public void setPersonalityCount(int personalityCount) {
        this.personalityCount = personalityCount;
    }

    public double getPersonalityMoney() {
        return personalityMoney;
    }

    public void setPersonalityMoney(double personalityMoney) {
        this.personalityMoney = personalityMoney;
    }

    public int getHashCode() {
        return hashCode;
    }

    public void setHashCode(int hashCode) {
        this.hashCode = hashCode;
    }

    @Override
    public String toString() {
        return "QuotationTitle{" +
                "ownerId=" + ownerId +
                ", ownerName='" + ownerName + '\'' +
                ", projectId=" + projectId +
                ", houseNumber='" + houseNumber + '\'' +
                ", totalAmount=" + totalAmount +
                ", area=" + area +
                ", halles=" + halles +
                ", rooms=" + rooms +
                ", cookhouse=" + cookhouse +
                ", washroom=" + washroom +
                ", balcony=" + balcony +
                ", others=" + others +
                ", houseInfo='" + houseInfo + '\'' +
                ", packageMoney=" + packageMoney +
                ", personalityCount=" + personalityCount +
                ", personalityMoney=" + personalityMoney +
                ", hashCode=" + hashCode +
                '}';
    }
}
