package com.seven.manager.model.offer;

import com.seven.manager.model.newoffer.HouseModel;

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

    private List<HouseModel> list;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void addList(HouseModel model) {
        this.list.add(model);
    }

    public List<HouseModel> getList() {
        return list;
    }

    public void clearList() {
        this.list.clear();
    }
}
