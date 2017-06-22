package com.seven.manager.model.newoffer;

import com.seven.library.config.RunTimeConfig;

/**
 * @author seven
 */

public class TermVolumeTitle extends TermVolumeModel {

    public TermVolumeTitle() {

        setViewType(RunTimeConfig.ModelConfig.TERM_VOLUME_TITLE);
    }

    private String name;

    private double price;

    private String unit;

    private String describe;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
