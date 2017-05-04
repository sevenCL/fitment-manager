package com.seven.library.callback;

import android.view.View;


/**
 * @author sewen
 */
public interface DialogClickCallBack {

    void onCancelClick(View view);

    void onSureClick(View view);

    void onClick(View view, Object... object);

}
