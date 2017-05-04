package com.seven.library.http;

import android.content.Intent;

import com.seven.library.R;
import com.seven.library.application.LibApplication;
import com.seven.library.callback.HttpRequestCallBack;
import com.seven.library.task.ActivityStack;
import com.seven.library.ui.dialog.NetWorkDialog;
import com.seven.library.ui.dialog.RequestLoading;
import com.seven.library.utils.LogUtils;
import com.seven.library.utils.NetWorkUtils;
import com.seven.library.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.List;

/**
 * 网络请求
 *
 * @author seven
 */
public class RequestUtils {

    private static final String TAG = "RequestUtils";

    private static RequestUtils requestUtils;

    private static NetWorkDialog netWorkDialog;

    private RequestLoading loadingDialog;

    private RequestUtils() {
    }

    public static RequestUtils getInstance() {

        if (requestUtils == null) {
            synchronized (RequestUtils.class) {
                requestUtils = new RequestUtils();
            }
        }

        if (!NetWorkUtils.getInstance().isNetWorkConnected()) {

            if (netWorkDialog == null || !netWorkDialog.isShowing()) {
                netWorkDialog = new NetWorkDialog(ActivityStack.getInstance().peekActivity(), R.style.Dialog, null);
                netWorkDialog.show();
                return null;
            }
        }

        return requestUtils;
    }

    /**
     * 等待加载框
     */
    private void showDialog() {

        if (loadingDialog != null && !loadingDialog.isShowing())
            loadingDialog.cancel();

        loadingDialog = new RequestLoading(ActivityStack.getInstance().peekActivity(), R.style.Dialog, null);
        loadingDialog.show();
    }

    /**
     * @param params
     * @param requestId
     * @param callBack
     */
    private void post(RequestParams params, final int requestId, final HttpRequestCallBack callBack) {

        LogUtils.println(" token " + LibApplication.token);

        params.setHeader("token", LibApplication.token);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (loadingDialog != null)
                    loadingDialog.cancel();

                try {

                    JSONObject jsonObject = new JSONObject(result);
                    int status = jsonObject.getInt("status");
                    int code = jsonObject.getInt("code");
                    String msg = jsonObject.getString("msg");
                    if (status == 0 && code == 2) {
                        ToastUtils.getInstance().showToast(msg);
                        LibApplication.getInstance().login();
                        return;
                    }

                } catch (JSONException e) {
                    LogUtils.println(TAG + e);
                }

                callBack.onSucceed(result, requestId);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (loadingDialog != null)
                    loadingDialog.dismiss();
                callBack.onFailure(ex.getMessage(), requestId);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                if (loadingDialog != null)
                    loadingDialog.dismiss();
                LogUtils.println(TAG + " onCancelled ");
            }

            @Override
            public void onFinished() {
                if (loadingDialog != null)
                    loadingDialog.dismiss();
            }
        });
    }

    /**
     * 上传文件
     *
     * @param requestId
     * @param url
     * @param type
     * @param callBack
     */
    public void upload(final int requestId, String url, List<String> list, int type, final HttpRequestCallBack callBack) {

        RequestParams params = new RequestParams(url);

        params.addBodyParameter("type", String.valueOf(type));

        for (String path : list)
            params.addBodyParameter("fileData", new File(path));

        params.setHeader("token", LibApplication.token);
        params.setConnectTimeout(60000);

        x.http().post(params, new Callback.ProgressCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {

                    JSONObject jsonObject = new JSONObject(result);
                    int status = jsonObject.getInt("status");
                    int code = jsonObject.getInt("code");
                    String msg = jsonObject.getString("msg");
                    if (status == 0 && code == 2) {
                        ToastUtils.getInstance().showToast(msg);
                        LibApplication.getInstance().login();
                        return;
                    }

                } catch (JSONException e) {
                    LogUtils.println(TAG + e);
                }
                callBack.onSucceed(result, requestId);
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                callBack.onFailure(throwable.getMessage(), requestId);
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onLoading(long l, long l1, boolean b) {

                callBack.onProgress(l1, requestId);

            }
        });
    }

    /**
     * 短信验证码
     *
     * @param requestId
     * @param url
     * @param mobile
     * @param flow
     * @param callBack
     */
    public void sms(final int requestId, String url, String mobile, String flow, HttpRequestCallBack callBack) {

        showDialog();

        RequestParams params = new RequestParams(url);
        params.addBodyParameter("mobile", mobile);
        params.addBodyParameter("flow", flow);

        post(params, requestId, callBack);
    }

    /**
     * 验证码验证
     *
     * @param requestId
     * @param url
     * @param mobile
     * @param code
     * @param flow
     * @param callBack
     */
    public void smsCheck(final int requestId, String url, String mobile, String code, String flow, HttpRequestCallBack callBack) {

        showDialog();

        RequestParams params = new RequestParams(url);
        params.addBodyParameter("mobile", mobile);
        params.addBodyParameter("code", code);
        params.addBodyParameter("flow", flow);

        post(params, requestId, callBack);
    }

    /**
     * 注册
     *
     * @param requestId
     * @param url
     * @param mobile
     * @param password
     * @param cityId
     * @param callBack
     */
    public void register(int requestId, String url, String mobile, String password, long cityId, HttpRequestCallBack callBack) {

        showDialog();

        RequestParams params = new RequestParams(url);
        params.addBodyParameter("mobile", mobile);
        params.addBodyParameter("password", password);
        params.addBodyParameter("serviceCity", String.valueOf(cityId));

        post(params, requestId, callBack);
    }

    /**
     * 忘记密码
     *
     * @param requestId
     * @param url
     * @param mobile
     * @param password
     * @param callBack
     */
    public void passwordForget(int requestId, String url, String mobile, String password, HttpRequestCallBack callBack) {

        showDialog();

        RequestParams params = new RequestParams(url);
        params.addBodyParameter("mobile", mobile);
        params.addBodyParameter("password", password);

        post(params, requestId, callBack);
    }

    /**
     * 登录
     *
     * @param requestId
     * @param url
     * @param mobile
     * @param password
     * @param callBack
     */
    public void login(int requestId, String url, String mobile, String password, HttpRequestCallBack callBack) {

        showDialog();

        RequestParams params = new RequestParams(url);
        params.addBodyParameter("mobile", mobile);
        params.addBodyParameter("password", password);

        post(params, requestId, callBack);
    }

    /**
     * 免登录
     *
     * @param requestId
     * @param url
     * @param userCode
     * @param callBack
     */
    public void loginAvoid(int requestId, String url, String userCode, HttpRequestCallBack callBack) {

        RequestParams params = new RequestParams(url);
        params.addBodyParameter("userCode", userCode);

        post(params, requestId, callBack);
    }

    /**
     * 用户信息
     *
     * @param requestId
     * @param url
     * @param hashCode
     * @param callBack
     */
    public void userInfo(int requestId, String url, int hashCode, HttpRequestCallBack callBack) {

        RequestParams params = new RequestParams(url);
        params.addBodyParameter("hashCode", String.valueOf(hashCode));

        post(params, requestId, callBack);
    }

    /**
     * 实名认证
     *
     * @param requestId
     * @param url
     * @param userName
     * @param idCard
     * @param callBack
     */
    public void idAudit(int requestId, String url, String userName, String idCard, HttpRequestCallBack callBack) {

        RequestParams params = new RequestParams(url);
        params.addBodyParameter("userName", userName);
        params.addBodyParameter("idCard", idCard);

        post(params, requestId, callBack);
    }

    /**
     * 保存资料并提交审核
     *
     * @param requestId
     * @param url
     * @param userName
     * @param sex
     * @param idCard
     * @param seniority
     * @param callBack
     */
    public void dataSave(int requestId, String url, String userName, String idCard, int sex,
                         String seniority, HttpRequestCallBack callBack) {

        showDialog();

        RequestParams params = new RequestParams(url);
        params.addBodyParameter("userName", userName);
        params.addBodyParameter("idCard", idCard);
        params.addBodyParameter("sex", String.valueOf(sex));
        params.addBodyParameter("seniority", seniority);

        post(params, requestId, callBack);
    }

    /**
     * 获取用户资料
     *
     * @param requestId
     * @param url
     * @param hashCode
     * @param callBack
     */
    public void userData(int requestId, String url, int hashCode, HttpRequestCallBack callBack) {

        RequestParams params = new RequestParams(url);
        params.addBodyParameter("hashCode", String.valueOf(hashCode));

        post(params, requestId, callBack);
    }

    /**
     * 校验密码
     *
     * @param requestId
     * @param url
     * @param password
     * @param callBack
     */
    public void passwordCheck(int requestId, String url, String password, HttpRequestCallBack callBack) {

        showDialog();

        RequestParams params = new RequestParams(url);
        params.addBodyParameter("password", password);

        post(params, requestId, callBack);
    }

    /**
     * 重置手机号码
     *
     * @param requestId
     * @param url
     * @param mobile
     * @param code
     * @param callBack
     */
    public void resetMobile(int requestId, String url, String mobile, String code, HttpRequestCallBack callBack) {

        showDialog();

        RequestParams params = new RequestParams(url);
        params.addBodyParameter("mobile", mobile);
        params.addBodyParameter("code", code);

        post(params, requestId, callBack);

    }

    /**
     * 获取关联机构信息
     *
     * @param requestId
     * @param url
     * @param hashCode
     * @param callBack
     */
    public void company(int requestId, String url, int hashCode, HttpRequestCallBack callBack) {

        RequestParams params = new RequestParams(url);
        params.addBodyParameter("hashCode", String.valueOf(hashCode));

        post(params, requestId, callBack);
    }

    /**
     * 获取预约订单
     *
     * @param requestId
     * @param url
     * @param callBack
     */
    public void orderList(int requestId, String url, HttpRequestCallBack callBack) {

        RequestParams params = new RequestParams(url);

        post(params, requestId, callBack);
    }

    /**
     *
     * @param requestId
     * @param url
     * @param orderNumber
     * @param status
     */
    public void orderStatus(int requestId,String url,String orderNumber,int status,HttpRequestCallBack callBack){

        showDialog();

        RequestParams params = new RequestParams(url);
        params.addBodyParameter("orderNumber", orderNumber);
        params.addBodyParameter("status", String.valueOf(status));

        post(params, requestId, callBack);

    }

    //----------------------------------------------------------------------------------res

    /**
     * 获取资源
     *
     * @param requestId
     * @param url
     * @param hashCode
     * @param callBack
     */
    public void resource(final int requestId, String url, int hashCode, final HttpRequestCallBack callBack) {
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("hashCode", String.valueOf(hashCode));

        post(params, requestId, callBack);
    }

}
