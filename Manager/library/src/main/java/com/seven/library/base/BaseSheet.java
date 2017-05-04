package com.seven.library.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.seven.library.R;
import com.seven.library.callback.DialogClickCallBack;

/**
 * sheet 基类
 *
 * @author seven
 */
public abstract class BaseSheet extends BaseDialog {

    private Window window;

    public BaseSheet(Activity activity, int theme, DialogClickCallBack dialogClickCallBack) {
        super(activity, theme, dialogClickCallBack);
    }

    /**
     * 重写show方法 控制dialog大小
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
            width = wm.getDefaultDisplay().getWidth();
        }

        super.show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        } else {
            getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    /**
     * 弹框显示的位置
     *
     * @param x
     * @param y
     */
    public void showDialog(int x, int y) {
        window = getWindow(); //得到对话框
        window.setWindowAnimations(R.style.dialogWindowAnim); //设置窗口弹出动画
        //window.setBackgroundDrawableResource(R.color.vifrification); //设置对话框背景为透明
        WindowManager.LayoutParams wl = window.getAttributes();
        //根据x，y坐标设置窗口需要显示的位置
        wl.x = x; //x小于0左移，大于0右移
        wl.y = y; //y小于0上移，大于0下移
        //wl.alpha = 0.6f; //设置透明度
        wl.gravity = Gravity.BOTTOM; //设置重力
        wl.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wl);
        show();
    }

}
