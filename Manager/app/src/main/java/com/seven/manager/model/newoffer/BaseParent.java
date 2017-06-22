package com.seven.manager.model.newoffer;

import java.io.Serializable;

/**
 * @author seven
 */

public class BaseParent implements Serializable {

    /**
     * 阶段id
     */
    private long id;

    /**
     * 阶段名称
     */
    private String name;

    /**
     * 施工类集合
     */
    private String itemCategoryList;

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

    public String getItemCategoryList() {
        return itemCategoryList;
    }

    public void setItemCategoryList(String itemCategoryList) {
        this.itemCategoryList = itemCategoryList;
    }

    @Override
    public String toString() {
        return "BaseParent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", itemCategoryList='" + itemCategoryList + '\'' +
                '}';
    }
}
