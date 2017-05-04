package com.seven.library.db.share;

import android.content.Context;
import android.content.SharedPreferences;

import com.seven.library.application.LibApplication;
import com.seven.library.config.RunTimeConfig;


/**
 * @author seven
 */
public class SharedData {

    private SharedPreferences mSharedPreferences;

    private SharedPreferences.Editor mEditor;

    public static volatile SharedData sharedData;


    private SharedData() {

        mSharedPreferences = LibApplication.getInstance().getSharedPreferences(
                RunTimeConfig.SharedConfig.SHARED_NAME, Context.MODE_PRIVATE);

        mEditor = mSharedPreferences.edit();
    }

    public static SharedData getInstance() {

        if (sharedData == null) {

            synchronized (SharedData.class) {
                sharedData = new SharedData();
            }
        }
        return sharedData;
    }

    /**
     * 是否第一次登录
     *
     * @return
     */
    public boolean getIsFirst() {
        return mSharedPreferences.getBoolean(RunTimeConfig.SharedConfig.FIRST, true);
    }

    public void setIsFirst(boolean isFirst) {
        mEditor.putBoolean(RunTimeConfig.SharedConfig.FIRST, isFirst).commit();
    }

    /**
     * 用户码
     *
     * @return
     */
    public String getUserCode() {
        return mSharedPreferences.getString(RunTimeConfig.SharedConfig.USER_CODE, "");
    }

    public void setUserCode(String userCode) {
        mEditor.putString(RunTimeConfig.SharedConfig.USER_CODE, userCode).commit();
    }

    /**
     * 认证提示时间
     *
     * @return
     */
    public String getAttestationTime() {
        return mSharedPreferences.getString(RunTimeConfig.SharedConfig.ATTESTATION_TIME, "");
    }

    public void setAttestationTime(String attestationTime) {
        mEditor.putString(RunTimeConfig.SharedConfig.ATTESTATION_TIME, attestationTime).commit();
    }

//
//    /**
//     * 使用4g的开始时间 1天过后再次提醒
//     *
//     * @return
//     */
//    public long get4GTime() {
//        return mSharedPreferences.getLong(RunTimeConfig.SharedConfig.NET_4G, 0);
//    }
//
//    public void set4GTime(long net4g) {
//        mEditor.putLong(RunTimeConfig.SharedConfig.NET_4G, net4g).commit();
//    }
//
//    /**
//     * 是否可以更新
//     *
//     * @return
//     */
//    public boolean getIsUpdate() {
//        return mSharedPreferences.getBoolean(RunTimeConfig.SharedConfig.IS_UPDATE, true);
//    }
//
//    public void setIsUpdate(boolean update) {
//        mEditor.putBoolean(RunTimeConfig.SharedConfig.IS_UPDATE, update).commit();
//    }

}
