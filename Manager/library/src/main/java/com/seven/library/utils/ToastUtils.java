package com.seven.library.utils;

import android.widget.Toast;

import com.seven.library.application.LibApplication;


/**
 * toast通知工具辅助类
 *
 * @author seven
 */
public class ToastUtils {

    private static volatile ToastUtils toastHelper;

    private ToastUtils() {

    }

    public static ToastUtils getInstance() {

        if (toastHelper == null) {

            synchronized (ToastUtils.class) {

                toastHelper = new ToastUtils();

            }
        }

        return toastHelper;
    }

    /**
     * @param id string_id
     */
    public void showToast(int id) {
        Toast.makeText(LibApplication.getInstance(), ResourceUtils.getInstance().getText(id), Toast.LENGTH_SHORT).show();
    }

    /**
     * @param msg
     */
    public void showToast(String msg) {
        Toast.makeText(LibApplication.getInstance(), msg, Toast.LENGTH_SHORT).show();
    }

    public void showToastError(int code) {

        int id = 0;

        switch (code) {


        }

        showToast(id);

    }

}
