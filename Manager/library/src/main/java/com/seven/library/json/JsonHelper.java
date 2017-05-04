package com.seven.library.json;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.seven.library.callback.JsonCallBack;
import com.seven.library.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 解析数据
 *
 * @author seven
 */
public class JsonHelper {

    private static final String TAG = "JsonHelper";

    private static volatile JsonHelper jsonHelper;

    private JsonHelper() {

    }

    public static JsonHelper getInstance() {

        if (jsonHelper == null) {

            synchronized (JsonHelper.class) {
                jsonHelper = new JsonHelper();
            }
        }

        return jsonHelper;

    }

    /**
     * 常规返回
     *
     * @param content
     * @param isReturn
     * @param callBack
     */
    public void jsonString(String content, boolean isReturn, JsonCallBack callBack) {

        try {

            JSONObject jsonObject = new JSONObject(content);
            int status = jsonObject.getInt("status");
            if (status == 1) {

                callBack.onSucceed(jsonObject.getString("data"));

            } else {
                callBack.onFailure(jsonObject.getString("msg"));
            }

        } catch (JSONException e) {

            LogUtils.println(TAG + e);

        }

    }

    /**
     * 返回data中单个数据
     *
     * @param content
     * @param isReturn
     * @param callBack
     */
    public void jsonStringSingle(String content, boolean isReturn, String key, JsonCallBack callBack) {

        try {

            JSONObject jsonObject = new JSONObject(content);
            int status = jsonObject.getInt("status");
            if (status == 1) {

                callBack.onSucceed(jsonObject.getJSONObject("data").get(key));

            } else {
                callBack.onFailure(jsonObject.getString("msg"));
            }

        } catch (JSONException e) {

            LogUtils.println(TAG + e);

        }

    }

    /**
     * 返回code
     *
     * @param content
     * @param isReturn
     * @param callBack
     */
    public void jsonStringCode(String content, boolean isReturn, JsonCallBack callBack) {

        try {

            JSONObject jsonObject = new JSONObject(content);
            int status = jsonObject.getInt("status");
            if (status == 1) {

                callBack.onSucceed(jsonObject.getString("data"));

            } else {
                callBack.onFailure(jsonObject.getString("code"));
            }

        } catch (JSONException e) {

            LogUtils.println(TAG + e);

        }

    }

    /**
     * 返回hashCode
     *
     * @param content
     * @return
     */
    public int jsonStringHash(String content) {

        int hashCode = 0;

        try {

            JSONObject jsonObject = new JSONObject(content);
            int status = jsonObject.getInt("status");
            if (status == 1) {

                hashCode = jsonObject.getJSONObject("data").getInt("hashCode");

            }
        } catch (JSONException e) {

            LogUtils.println(TAG + e);

        }
        return hashCode;
    }

    /**
     * 解析单一数据
     *
     * @param content
     * @param clazz
     * @param isReturn
     * @param callBack
     * @param <T>
     * @return
     */
    public <T> List<T> jsonArraySingle(String content, Class<T> clazz, boolean isReturn, JsonCallBack callBack) {
        List<T> data = null;

        JSONArray jsonArray = JSON.parseArray(content);

        data = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {

            data.add(jsonArray.getObject(i, clazz));
        }

        if (isReturn) {
            if (callBack != null) {
                callBack.onSucceed(data);
            }
        }
        return data;

    }

    /**
     * 解析数组
     *
     * @param content
     * @param clazz
     * @param isReturn
     * @param callBack
     * @param <T>
     * @return
     */
    public <T> List<T> jsonArray(String content, Class<T> clazz, boolean isReturn, JsonCallBack callBack) {
        try {
            JSONObject jsonObject = new JSONObject(content);
            int status = jsonObject.getInt("status");
            if (status == 1) {

                List<T> data = null;

                JSONArray jsonArray = JSON.parseArray(jsonObject.getJSONArray("data").toString());

                data = new ArrayList<>();

                for (int i = 0; i < jsonArray.size(); i++) {

                    data.add(jsonArray.getObject(i, clazz));
                }

                if (isReturn) {
                    if (callBack != null) {
                        callBack.onSucceed(data);
                    }
                }
                return data;

            } else {
                callBack.onFailure(jsonObject.getString("msg"));
            }
        } catch (JSONException e) {

            LogUtils.println(TAG + e);

        }

        return null;
    }

    /**
     * 根据key解析数组
     *
     * @param content
     * @param clazz
     * @param isReturn
     * @param key
     * @param callBack
     * @param <T>
     * @return
     */
    public <T> List<T> jsonList(String content, Class<T> clazz, boolean isReturn, String key, JsonCallBack callBack) {

        try {
            JSONObject jsonObject = new JSONObject(content);
            int status = jsonObject.getInt("status");
            if (status == 1) {

                if (clazz == null) {
                    callBack.onSucceed("");
                    return null;
                }

                List<T> data = null;
                if (!TextUtils.isEmpty(key) && !jsonObject.getJSONObject("data").isNull(key)) {

                    data = JsonUtil.convertJsonToList(
                            jsonObject.getJSONObject("data").getString(key), clazz);

                } else {

                    data = JsonUtil.convertJsonToList(
                            jsonObject.toString(), clazz);

                }

                if (isReturn) {
                    if (callBack != null) {
                        callBack.onSucceed(data);
                    }
                }
                return data;
            } else {

                callBack.onFailure(jsonObject.getString("msg"));

            }

        } catch (JSONException e) {

            LogUtils.println(TAG + e);

        }

        return null;
    }

    /**
     * 解析对象
     *
     * @param content
     * @param clazz
     * @param isReturn
     * @param key
     * @param callBack
     * @param <T>
     * @return
     */
    public <T> T jsonObject(String content, Class<T> clazz, boolean isReturn, String key, JsonCallBack callBack) {

        try {

            JSONObject jsonObject = new JSONObject(content);
            int status = jsonObject.getInt("status");
            if (status == 1) {

                if (clazz == null) {
                    callBack.onSucceed("");
                    return null;
                }
                T data = null;
                if (!TextUtils.isEmpty(key) && !jsonObject.getJSONObject("data").isNull(key)) {
                    data = JsonUtil.convertJsonToObject(
                            jsonObject.getJSONObject("data").getString(key), clazz);
                } else {
                    data = JsonUtil.convertJsonToObject(
                            jsonObject.getString("data"), clazz);

                }

                if (isReturn) {
                    if (callBack != null) {
                        callBack.onSucceed(data);
                    }
                }
                return data;
            } else {
                callBack.onFailure(jsonObject.getString("msg"));
            }

        } catch (JSONException e) {
            LogUtils.println(TAG + e);
        }

        return null;

    }
}
