package com.seven.manager.model.newoffer;

import com.seven.library.config.RunTimeConfig;

/**
 * @author seven
 */

public class BaseControl extends OfferBaseModel{

    public BaseControl() {

        setViewTpye(RunTimeConfig.ModelConfig.BASE_CONTROL);

    }

    /**
     * 是否展开
     */
    private boolean isShow;

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
