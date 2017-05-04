package com.seven.library.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import com.frankchen.mvc.aidl.Task;
import com.frankchen.mvc.controller.common.BaseAppCompatActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.seven.library.R;
import com.seven.library.task.ActivityStack;
import com.seven.library.utils.LogUtils;
import com.seven.library.utils.ScreenUtils;
import com.seven.library.utils.image.ImageLoadProxy;


/**
 * 基类 activity
 *
 * @author seven
 */
public abstract class BaseActivity extends BaseAppCompatActivity {

    //屏幕的width、height、notification
    public int screenWidth;
    public int screenHeight;
    public int notificationHeight;

    //是否透明化通知栏
    public boolean transparency;

    //图片加载配置
    public DisplayImageOptions mOptions;

    //全局消息
    @Override
    public void onReceiveNotification(Task task) {
        super.onReceiveNotification(task);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        //启用硬件加速，针对3.0以上的设备有效
        getWindow().setFlags(0x01000000, 0x01000000);
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        super.onCreate(savedInstanceState);

        //将当前activity加入到栈中
        ActivityStack.getInstance().pushActivity(this);

        //获得手机的屏幕信息(宽、高、通知栏高度)
        DisplayMetrics dm = new DisplayMetrics();// 初始化一个系统屏幕对象
        getWindowManager().getDefaultDisplay().getMetrics(dm);// 通过调用系统服务给系统屏幕对象赋值
        screenWidth = dm.widthPixels;// 得到系统屏幕的宽度值
        screenHeight = dm.heightPixels;// 得到系统屏幕的高度值
        notificationHeight = ScreenUtils.getInstance().getStatusBarHeight();

        mOptions = ImageLoadProxy.getOptions4PictureList(R.drawable.loading_large, R.drawable.loading_large_fail);

        //初始化布局
        try {

            setContentView(getRootLayoutId());

            if (transparency) {
                //透明化通知栏
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Window window = getWindow();
                    window.setFlags(
                            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.setFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                }
            }

        } catch (Exception e) {
            LogUtils.println(" Base activity setContentView exception " + e);
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
     * 用于fragment的切换
     *
     * @param id_content
     * @param fragment
     */
    public void replaceFragment(int id_content, Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(id_content, fragment);
        transaction.commit();
    }

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
