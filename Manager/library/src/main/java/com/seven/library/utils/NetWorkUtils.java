package com.seven.library.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.seven.library.application.LibApplication;


/**
 * 网络工具辅助类
 *
 * @author seven
 */
public class NetWorkUtils {

    private static volatile NetWorkUtils netWorkUtil;

    private NetWorkUtils() {
    }


    public static NetWorkUtils getInstance() {

        if (netWorkUtil == null) {

            synchronized (NetWorkUtils.class) {
                netWorkUtil = new NetWorkUtils();
            }
        }

        return netWorkUtil;

    }

    /**
     * 判断当前网络是否已连接
     *
     * @return
     */
    public boolean isNetWorkConnected() {
        boolean result;
        ConnectivityManager cm = (ConnectivityManager) LibApplication.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        result = netInfo != null && netInfo.isConnected();
        return result;
    }


    /**
     * 判断当前的网络连接方式是否为WIFI
     *
     * @return
     */
    public boolean isWifiConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) LibApplication.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return wifiNetworkInfo.isConnected();
    }

}
