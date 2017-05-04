package com.seven.library.ui.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.seven.library.R;
import com.seven.library.base.BaseDialog;
import com.seven.library.callback.DialogClickCallBack;
import com.seven.library.utils.LogUtils;
import com.seven.library.utils.ScreenUtils;
import com.seven.library.view.RoundProgressBar;

/**
 * 上传进度
 *
 * @author seven
 */
public class UploadingDialog extends BaseDialog {

    private RoundProgressBar mBar;

    public UploadingDialog(Activity activity, int theme, DialogClickCallBack dialogClickCallBack) {
        super(activity, theme, dialogClickCallBack);
    }

    @Override
    public int getRootLayoutId() {

        return R.layout.dialog_upload;
    }

    @Override
    public void onInitRootView(Bundle savedInstanceState) {

        initView();

        initData();

    }

    @Override
    public void initView() {

        mBar = getView(mBar, R.id.upload_bar);

    }

    @Override
    public void initData() {

        mBar.setDefaultConfig();
        mBar.setTextSize(ScreenUtils.getInstance().sp2px(10));
        mBar.setCricleProgressColor(mActivity.getResources().getColor(R.color.color_primary));

        mBar.setProgress(0);
    }

    public void refreshProgress(int progress) {

        mBar.setProgress(progress);

        if (progress >= 100) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dismiss();
                }
            }, 1000);
        }
    }

    @Override
    public void onClick(View v) {

    }
}
