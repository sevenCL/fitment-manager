package com.seven.library.ui.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.seven.library.R;
import com.seven.library.base.BaseDialog;
import com.seven.library.callback.DialogClickCallBack;

/**
 * 网络判断
 *
 * @author seven
 */
public class NetWorkDialog extends BaseDialog {

    private Button sure;

    public NetWorkDialog(Activity activity, int theme, DialogClickCallBack callBack) {
        super(activity, theme, callBack);
    }

    @Override
    public void onInitRootView(Bundle savedInstanceState) {

        initView();

        initData();

    }

    @Override
    public int getRootLayoutId() {

        isTouch = true;

        return R.layout.dialog_network;
    }

    @Override
    public void initView() {

        sure = getView(sure, R.id.network_sure_btn);

    }

    @Override
    public void initData() {

        sure.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        dismiss();

    }
}
