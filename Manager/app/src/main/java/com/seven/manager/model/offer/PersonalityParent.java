package com.seven.manager.model.offer;

import java.io.Serializable;

/**
 * 个性项-parent
 *
 * @author seven
 */

public class PersonalityParent implements Serializable {

    /**
     * 工种id
     */
    private long id;

    /**
     * 工种名称
     */
    private String name;

    /**
     * 子项集合
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
        return "PersonalityParent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", itemCategoryList='" + itemCategoryList + '\'' +
                '}';
    }
}
