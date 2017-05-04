package com.seven.library.callback;

/**
 * 数据回调
 *
 * @author seven
 */
public interface HttpRequestCallBack {

    void onSucceed(String result, int requestId);

    void onFailure(String error, int requestId);

    void onProgress(long progress, int requestId);
}
