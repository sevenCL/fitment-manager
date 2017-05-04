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

    private ResCity resCity;

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
            resCity = db.selector(ResCity.class).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null) {

            city();

        }

        return START_STICKY;
    }

    /**
     * 服务城市
     */
    private void city() {

        int hashCode = 0;
        if (resCity != null)
            hashCode = resCity.getHashCode();

        request(RunTimeConfig.RequestConfig.RES_CITY, Urls.CITY, hashCode);
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

        if (activity != null)
            RequestUtils.getInstance().resource(requestId, url, hashCode, this);

    }

    @Override
    public void onSucceed(String result, int requestId) {

        switch (requestId) {

            //服务城市
            case RunTimeConfig.RequestConfig.RES_CITY:

                LogUtils.println(this.getClass().getName()+" onSucceed RES_CITY request "+result);

                try {
                    resCity = db.selector(ResCity.class).findFirst();

                    if (resCity == null) {//第一次创建表并添加数据

                        if (TextUtils.isEmpty(result))
                            return;

                        resCity = new ResCity();
                        resCity.setId(1);
//                        resCity.setHashCode(JsonHelper.getInstance().jsonStringHash(result));
                        resCity.setParams(Urls.CITY);
                        resCity.setContent(result);
                        resCity.setCreateTime(System.currentTimeMillis());

                        db.save(resCity);

                    } else {//更新

//                        try {
//                            if (new JSONObject(result).getJSONObject(JsonField.DATA).getInt(
//                                    JsonField.HASHCODE) == resCity.getHashCode())
//                                return;
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }

//                        resCity.setHashCode(JsonHelper.getInstance().jsonStringHash(result));
                        resCity.setContent(result);
                        resCity.setCreateTime(System.currentTimeMillis());

                        db.saveOrUpdate(resCity);
                    }
                } catch (DbException e) {
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
