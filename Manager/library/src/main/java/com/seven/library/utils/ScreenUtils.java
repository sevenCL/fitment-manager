package com.seven.library.utils;



import com.seven.library.application.LibApplication;

import java.lang.reflect.Field;

/**
 * 尺寸获取、转换工具辅助类
 *
 * @author seven
 */
public class ScreenUtils {

    private static volatile ScreenUtils screenUtil;

    private ScreenUtils() {

    }

    public static ScreenUtils getInstance() {

        if (screenUtil == null) {

            synchronized (ScreenUtils.class) {
                screenUtil = new ScreenUtils();
            }
        }

        return screenUtil;
    }

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue
     * @return
     */
    public int px2dip(float pxValue) {
        final float scale = LibApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue dp
     * @return px
     */
    public int dip2px(float dipValue) {
        final float scale = LibApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue px
     * @return sp
     */
    public int px2sp(float pxValue) {
        final float fontScale = LibApplication.getInstance().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue sp
     * @return px
     */
    public int sp2px(float spValue) {
        final float fontScale = LibApplication.getInstance().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取通知栏的高度
     *
     * @return px
     */
    public int getStatusBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int heightDp = 0;
        int heightPx = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            heightDp = Integer.parseInt(field.get(obj).toString());
            heightPx = LibApplication.getInstance().getResources().getDimensionPixelSize(heightDp);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return heightPx;
    }

}
