package com.seven.manager.service;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.seven.library.application.LibApplication;
import com.seven.library.callback.HttpRequestCallBack;
import com.seven.library.config.RunTimeConfig;
import com.seven.library.db.res.ResCity;
import com.seven.library.db.res.ResPersonality;
import com.seven.library.http.RequestUtils;
import com.seven.library.http.Urls;
import com.seven.library.json.JsonField;
import com.seven.library.json.JsonHelper;
import com.seven.library.task.ActivityStack;
import com.seven.library.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

/**
 * 资源服务
 *
 * @author seven
 */
public class ResourceService extends Service implements HttpRequestCallBack {

    private DbManager db;


    private ResPersonality resPersonality;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        db = x.getDb(LibApplication.daoConfig);

        try {
            resPersonality = db.selector(ResPersonality.class).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null) {

            personality();

        }

        return START_STICKY;
    }

    private void personality() {

        int hashCode = 0;

        if (resPersonality != null)
            hashCode = resPersonality.getHashCode();

        request(RunTimeConfig.RequestConfig.RES_PERSONALITY, Urls.RES_PERSONALITY, hashCode);
    }

    /**
     * 请求
     *
     * @param requestId
     * @param url
     * @param hashCode
     */
    private void request(int requestId, String url, int hashCode) {

        Activity activity = ActivityStack.getInstance().peekActivity();

        try {
            if (activity != null)
                RequestUtils.getInstance(url).resource(requestId, hashCode, this);
        } catch (NullPointerException e) {
            LogUtils.println(this.getClass().getName() + e);
        }

    }

    @Override
    public void onSucceed(String result, int requestId) {

        switch (requestId) {
            case RunTimeConfig.RequestConfig.RES_PERSONALITY:

                LogUtils.println(this.getClass().getName() + " onSucceed RES_PERSONALITY request " + result);

                try {

                    if (TextUtils.isEmpty(new JSONObject(result).getJSONArray(JsonField.DATA).toString()))
                        return;

                    resPersonality = db.selector(ResPersonality.class).findFirst();

                    if (resPersonality == null) {//第一次创建表并添加数据

                        if (TextUtils.isEmpty(result))
                            return;

                        resPersonality = new ResPersonality();
                        resPersonality.setId(1);
                        resPersonality.setHashCode(0);
                        resPersonality.setParams(Urls.RES_PERSONALITY);
                        resPersonality.setContent(result);
                        resPersonality.setCreateTime(System.currentTimeMillis());

                        db.save(resPersonality);

                    } else {//更新

                        if (new JSONObject(result).getInt(JsonField.HASHCODE) == resPersonality.getHashCode())
                            return;

                        resPersonality.setHashCode(0);
                        resPersonality.setContent(result);
                        resPersonality.setCreateTime(System.currentTimeMillis());

                        db.saveOrUpdate(resPersonality);
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    @Override
    public void onFailure(String error, int requestId) {

    }

    @Override
    public void onProgress(long progress, int requestId) {

    }

}
