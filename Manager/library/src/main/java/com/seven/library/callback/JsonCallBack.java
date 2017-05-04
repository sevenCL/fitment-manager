package com.seven.library.callback;

/**
 * 数据回调
 *
 * @author sewen
 */
public interface JsonCallBack {

    void onSucceed(Object data);

    void onFailure(String error);

}
