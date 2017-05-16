package com.seven.manager.model.offer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 报价-房间-分类
 *
 * @author seven
 */

public class OfferHouse implements Serializable {

    public OfferHouse() {
        list = new ArrayList<>();
    }

    //房间类型
    private int type;

    private List<OfferHouseModel> list;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void addList(OfferHouseModel model) {
        this.list.add(model);
    }

    public List<OfferHouseModel> getList() {
        return list;
    }

    public void clearList() {
        this.list.clear();
    }
}
