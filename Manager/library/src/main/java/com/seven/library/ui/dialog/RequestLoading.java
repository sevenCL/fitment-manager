package com.seven.library.ui.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.seven.library.R;
import com.seven.library.base.BaseDialog;
import com.seven.library.callback.DialogClickCallBack;


/**
 * 请求菊花
 *
 * @author seven
 */
public class RequestLoading extends BaseDialog {

    private ImageView loading;

    public RequestLoading(Activity activity, int theme, DialogClickCallBack callBack) {
        super(activity, theme, callBack);
    }

    @Override
    public void onInitRootView(Bundle savedInstanceState) {

        initView();

        initData();

    }

    @Override
    public int getRootLayoutId() {
        return R.layout.dialog_request_loading;
    }

    @Override
    public void initView() {

        loading = getView(loading, R.id.request_loading_iv);

    }

    @Override
    public void initData() {

        Animation anim = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(500);
        anim.setRepeatCount(-1);
        LinearInterpolator lir = new LinearInterpolator();
        anim.setInterpolator(lir);
        loading.startAnimation(anim);

    }

    @Override
    public void onClick(View v) {

    }
}
