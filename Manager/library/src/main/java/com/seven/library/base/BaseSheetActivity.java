package com.seven.library.base;


import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.frankchen.mvc.controller.common.BaseActivity;
import com.seven.library.R;
import com.seven.library.task.ActivityStack;
import com.seven.library.utils.LogUtils;
import com.seven.library.utils.ScreenUtils;
import com.seven.library.utils.image.ImageLoadProxy;

/**
 * 基于activity的sheet 基类
 *
 * 1.使用场景：动作必须遵循activity方法时使用
 *
 * 2.不能继承android.support.v7.app.AppCompatActivity
 *
 * 3.想继承android.support.v7.app.AppCompatActivity情况下 根据提示来使用AppCompat的theme
 *
 * @author seven
 */
public abstract class BaseSheetActivity extends BaseActivity{

    //屏幕的width、height、notification
    public int screenWidth;
    public int screenHeight;
    public int notificationHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //启用硬件加速，针对3.0以上的设备有效
//        getWindow().setFlags(0x01000000, 0x01000000);
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        super.onCreate(savedInstanceState);

        //将当前activity加入到栈中
        ActivityStack.getInstance().pushActivity(this);

        //获得手机的屏幕信息(宽、高、通知栏高度)
        DisplayMetrics dm = new DisplayMetrics();// 初始化一个系统屏幕对象
        getWindowManager().getDefaultDisplay().getMetrics(dm);// 通过调用系统服务给系统屏幕对象赋值
        screenWidth = dm.widthPixels;// 得到系统屏幕的宽度值
        screenHeight = dm.heightPixels;// 得到系统屏幕的高度值
        notificationHeight = ScreenUtils.getInstance().getStatusBarHeight();

        //初始化布局
        try {

            setContentView(getRootLayoutId());

            //填满屏幕
            getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);

        } catch (Exception e) {
            LogUtils.println(" Base sheet activity setContentView exception " + e);
            return;
        }

        onInitRootView(savedInstanceState);
    }

    /**
     * 当初始化view后回调，仅次于onCreate之后调用
     *
     * @param savedInstanceState
     */
    public abstract void onInitRootView(Bundle savedInstanceState);

    /**
     * 初始化布局xml
     *
     * @return
     */
    public abstract int getRootLayoutId();

    /**
     * 初始化控件
     */
    public abstract void initView();

    /**
     * 初始化参数、值
     *
     * @param intent onNewIntent
     */
    public abstract void initData(Intent intent);

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

    @Override
    protected void onDestroy() {

        //在栈中销毁对应的activity
        ActivityStack.getInstance().removeActivity(this);

        //释放当前activity在内存中缓存的图像数据(bitmap)
        ImageLoadProxy.getImageLoader().clearMemoryCache();

        super.onDestroy();
    }

    /**
     * 左出右进动画
     */
    public void animLeftOut() {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }

    /**
     * 左进右出动画
     */
    public void animRightOut() {
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
    }

    /**
     * 下进上出动画
     */
    public void animTopOut() {
        overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.slide_out_to_top);
    }

    /**
     * 上进下出动画
     */
    public void animBottomOut() {
        overridePendingTransition(R.anim.slide_in_from_top, R.anim.slide_out_to_bottom);
    }
}
