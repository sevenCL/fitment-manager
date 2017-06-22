package com.seven.manager.model.newoffer;

import java.io.Serializable;

/**
 * @author seven
 */

public class OfferSpaceModel implements Serializable {

    private int viewType;

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
