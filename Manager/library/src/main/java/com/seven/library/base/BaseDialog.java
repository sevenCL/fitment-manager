package com.seven.library.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.seven.library.callback.DialogClickCallBack;
import com.seven.library.utils.LogUtils;


/**
 * dialog 基类
 *
 * @author seven
 */
public abstract class BaseDialog extends Dialog implements View.OnClickListener{

    //dialog的环境依赖于activity
    protected Activity mActivity;

    //回调
    protected DialogClickCallBack mCallBack;

    //弹框外区域是否可点击
    protected boolean isTouch;

    public BaseDialog(Activity activity) {
        super(activity);
    }

    public BaseDialog(Activity activity, int theme) {
        super(activity, theme);

        this.mActivity = activity;

    }

    public BaseDialog(Activity activity, int theme, DialogClickCallBack dialogClickCallBack) {

        this(activity, theme);

        this.mCallBack = dialogClickCallBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(getRootLayoutId());

        } catch (Exception e) {
            LogUtils.println(" Base dialog setContentView exception "+e);
            return;
        }

        onInitRootView(savedInstanceState);

        //弹框外区域是否可点击
        setCanceledOnTouchOutside(isTouch);
    }

    /**
     * 动态设置显示的弹窗宽度
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    @Override
    public void show() {
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        int width = 0;

        Point size = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            size = new Point();
            wm.getDefaultDisplay().getSize(size);
        } else {
            width = wm.getDefaultDisplay().getWidth() * 9 / 10;
        }

        super.show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            getWindow().setLayout((size.x * 9 / 10),
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        } else {
            getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    /**
     * 布局xml
     *
     * @return
     */
    public abstract int getRootLayoutId();

    /**
     * 当初始化view后回调，仅次于onCreate之后调用
     *
     * @param savedInstanceState
     */
    public abstract void onInitRootView(Bundle savedInstanceState);

    /**
     * 初始化布局
     */
    public abstract void initView();

    /**
     * 初始化参数、值
     *
     */
    public abstract void initData();

    /**
     * 简化findView by id操作
     *
     * @param view
     * @param id
     * @param <T>
     * @return
     */
    protected <T> T getView(T view, int id) {
        if (null == view) {
            view = (T) this.findViewById(id);
        }

        return view;
    }


}
