package com.seven.manager.model.http;

import java.io.Serializable;

/**
 * 预约模型
 *
 * @author seven
 */

public class OrderModel implements Serializable {

    /*
            "area": 80,
            "address": "保利百合花园",
            "orderNumber": "YY11703280001",
            "ownerName": "",
            "startTime": 1490670072000,
            "endTime": 1493111041023,
            "status": -1
    * */

    //预约单号
    private String orderNumber;

    //业主名字
    private String ownerName;

    //面积
    private double area;

    //地址
    private String address;

    //提交时间
    private long startTime;

    //结束时间
    private long endTime;

    //预约单状态
    private int status;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OrderModel{" +
                "orderNumber='" + orderNumber + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", area=" + area +
                ", address='" + address + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", status=" + status +
                '}';
    }
}
