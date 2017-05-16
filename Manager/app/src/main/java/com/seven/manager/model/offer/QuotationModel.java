package com.seven.manager.model.offer;

import java.io.Serializable;

/**
 * 报价单
 *
 * @author seven
 */

public class QuotationModel implements Serializable {

    //分公司id
    private long branchId;

    //业主id
    private long ownerId;

    //套餐id
    private long planId;

    //项目id
    private long projectId;

    //总面积
    private double area;

    //厅、室、厨房、卫生间、阳台、其他
    private int halls;
    private int rooms;
    private int cookhouse;
    private int washroom;
    private int balcony;
    private int others;

    //套餐集合
    private String itemsListJson;

    //个性项集合
    private String addItemsListJson;

    //个性项哈希
    private int hashCode;

    public long getBranchId() {
        return branchId;
    }

    public void setBranchId(long branchId) {
        this.branchId = branchId;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public long getPlanId() {
        return planId;
    }

    public void setPlanId(long planId) {
        this.planId = planId;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public int getHalls() {
        return halls;
    }

    public void setHalls(int halls) {
        this.halls = halls;
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

    public String getItemsListJson() {
        return itemsListJson;
    }

    public void setItemsListJson(String itemsListJson) {
        this.itemsListJson = itemsListJson;
    }

    public String getAddItemsListJson() {
        return addItemsListJson;
    }

    public void setAddItemsListJson(String addItemsListJson) {
        this.addItemsListJson = addItemsListJson;
    }

    public int getHashCode() {
        return hashCode;
    }

    public void setHashCode(int hashCode) {
        this.hashCode = hashCode;
    }

    @Override
    public String toString() {
        return "QuotationModel{" +
                "branchId=" + branchId +
                ", ownerId=" + ownerId +
                ", planId=" + planId +
                ", projectId=" + projectId +
                ", area=" + area +
                ", halls=" + halls +
                ", rooms=" + rooms +
                ", cookhouse=" + cookhouse +
                ", washroom=" + washroom +
                ", balcony=" + balcony +
                ", others=" + others +
                ", itemsListJson='" + itemsListJson + '\'' +
                ", addItemsListJson='" + addItemsListJson + '\'' +
                ", hashCode=" + hashCode +
                '}';
    }
}
