package com.seven.library.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frankchen.mvc.aidl.Task;
import com.frankchen.mvc.model.common.ActionFactory;
import com.seven.library.task.ActivityStack;
import com.seven.library.utils.ScreenUtils;


/**
 * 基类 fragment
 *
 * @author seven
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    private View view;

    //屏幕的width、height
    public int screenWidth;
    public int screenHeight;

    public float notificationHeight;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        //启用硬件加速，针对3.0以上的设备有效
        getActivity().getWindow().setFlags(0x01000000, 0x01000000);
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        super.onCreate(savedInstanceState);

        //将当前activity加入到栈中
        ActivityStack.getInstance().pushActivity(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(getRootLayoutId(), container, false);


        DisplayMetrics dm = new DisplayMetrics();// 初始化一个系统屏幕对象
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);// 通过调用系统服务给系统屏幕对象赋值
        screenWidth = dm.widthPixels;// 得到系统屏幕的宽度值
        screenHeight = dm.heightPixels;// 得到系统屏幕的高度值

        notificationHeight = ScreenUtils.getInstance().getStatusBarHeight();

        onInitRootView(savedInstanceState);

        return view;
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
            view = (T) this.view.findViewById(id);
        }

        return view;
    }

    /**
     * 发起消息
     *
     * @param actionName 消息名称
     * @param model      模式
     * @param authority  权限等级
     * @param what       消息唯一标识
     * @param objects    扩展(可用于传输数据)
     */
    public void doAction(String actionName, int model, int authority, int what, Object... objects) {
        ActionFactory.getSingleInstance().doAction(actionName, this.getClass().getName(), model, authority, what, objects);
    }

    /**
     * 消息接收
     *
     * @param task
     */
    public void onReceiveNotification(Task task) {
        task.decreaseObserver();
        if (task.getObserver() <= 0) {
            task.setCanRecycled(true);
            task.recycle();
        }

    }

}
