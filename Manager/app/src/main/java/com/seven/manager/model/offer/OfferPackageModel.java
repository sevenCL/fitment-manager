package com.seven.manager.model.offer;

import java.io.Serializable;

/**
 * 报价信息
 *
 * @author seven
 */

public class OfferPackageModel implements Serializable{

    private int viewType;

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

}
