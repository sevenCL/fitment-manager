package com.seven.manager.model.newoffer;

import com.seven.library.config.RunTimeConfig;

/**
 * @author seven
 */

public class TermVolumeTotal extends TermVolumeModel {

    public TermVolumeTotal() {

        setViewType(RunTimeConfig.ModelConfig.TERM_VOLUME_TOTAL);

    }

    private double total;

    private String unit;

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
