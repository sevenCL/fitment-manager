package com.seven.manager.model.offer;

import java.io.Serializable;

/**
 * 报价-添加房间
 *
 * @author seven
 */

public class OfferHouseModel implements Serializable {

    /**
     *  "id": 11,
     "  name": "景观阳台",
     "  type": 5,
     "  planId": 1,
     */

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
     * 套餐id
     */
    private long planId;

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

    public long getPlanId() {
        return planId;
    }

    public void setPlanId(long planId) {
        this.planId = planId;
    }

    //---------------------------------------------自定义状态

    /**
     * 选中状态
     */
    public int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OfferHouseModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", planId=" + planId +
                ", status=" + status +
                '}';
    }
}
