package com.seven.library.callback;

import java.util.List;

/**
 * 上传结果回调
 *
 * @author seven
 */
public interface UploadCallBack {

    void uploadCallBack(int requestId,List<String> list);

}
