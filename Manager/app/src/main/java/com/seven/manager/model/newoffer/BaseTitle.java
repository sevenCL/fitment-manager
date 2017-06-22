package com.seven.manager.model.newoffer;

import com.seven.library.config.RunTimeConfig;

/**
 * @author seven
 */

public class BaseTitle extends OfferBaseModel {

    public BaseTitle() {

        setViewTpye(RunTimeConfig.ModelConfig.BASE_TITLE);

    }

    /**
     * 阶段id
     */
    private long id;

    /**
     * 阶段名称
     */
    private String name;

    //----------------------------自定义字段

    /**
     * 阶段小计
     */
    private double total;

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

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
}
