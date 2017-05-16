package com.seven.manager.model.offer;

import java.io.Serializable;

/**
 * 报价-个性项
 *
 * @author seven
 */

public class OfferPersonalityModel implements Serializable {

    private int viewType;

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

}
