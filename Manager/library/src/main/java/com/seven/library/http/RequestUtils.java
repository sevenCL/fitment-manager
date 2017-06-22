package com.seven.library.http;

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

    public static String uri;

    private RequestUtils() {

    }

    public static RequestUtils getInstance(String url) {

        uri = url;

        if (LibApplication.type == 1) {
            uri = url.replace("dev", "test");
        }

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
     * @param type
     * @param callBack
     */
    public void upload(final int requestId, List<String> list, int type, final HttpRequestCallBack callBack) {

        RequestParams params = new RequestParams(uri);

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
     * @param mobile
     * @param flow
     * @param callBack
     */
    public void sms(final int requestId, String mobile, String flow, HttpRequestCallBack callBack) {

        showDialog();

        RequestParams params = new RequestParams(uri);
        params.addBodyParameter("mobile", mobile);
        params.addBodyParameter("flow", flow);

        post(params, requestId, callBack);
    }

    /**
     * 验证码验证
     *
     * @param requestId
     * @param mobile
     * @param code
     * @param flow
     * @param callBack
     */
    public void smsCheck(final int requestId, String mobile, String code, String flow, HttpRequestCallBack callBack) {

        showDialog();

        RequestParams params = new RequestParams(uri);
        params.addBodyParameter("mobile", mobile);
        params.addBodyParameter("code", code);
        params.addBodyParameter("flow", flow);

        post(params, requestId, callBack);
    }

    /**
     * 注册
     *
     * @param requestId
     * @param mobile
     * @param password
     * @param cityId
     * @param callBack
     */
    public void register(int requestId, String mobile, String password, long cityId, HttpRequestCallBack callBack) {

        showDialog();

        RequestParams params = new RequestParams(uri);
        params.addBodyParameter("mobile", mobile);
        params.addBodyParameter("password", password);
        params.addBodyParameter("serviceCity", String.valueOf(cityId));

        post(params, requestId, callBack);
    }

    /**
     * 忘记密码
     *
     * @param requestId
     * @param mobile
     * @param password
     * @param callBack
     */
    public void passwordForget(int requestId, String mobile, String password, HttpRequestCallBack callBack) {

        showDialog();

        RequestParams params = new RequestParams(uri);
        params.addBodyParameter("mobile", mobile);
        params.addBodyParameter("password", password);

        post(params, requestId, callBack);
    }

    /**
     * 登录
     *
     * @param requestId
     * @param mobile
     * @param password
     * @param callBack
     */
    public void login(int requestId, String mobile, String password, HttpRequestCallBack callBack) {

        showDialog();

        RequestParams params = new RequestParams(uri);
        params.addBodyParameter("mobile", mobile);
        params.addBodyParameter("password", password);

        post(params, requestId, callBack);
    }

    /**
     * 免登录
     *
     * @param requestId
     * @param userCode
     * @param callBack
     */
    public void loginAvoid(int requestId, String userCode, HttpRequestCallBack callBack) {

        RequestParams params = new RequestParams(uri);
        params.addBodyParameter("userCode", userCode);

        post(params, requestId, callBack);
    }

    /**
     * 用户信息
     *
     * @param requestId
     * @param hashCode
     * @param callBack
     */
    public void userInfo(int requestId, int hashCode, HttpRequestCallBack callBack) {

        RequestParams params = new RequestParams(uri);
        params.addBodyParameter("hashCode", String.valueOf(hashCode));

        post(params, requestId, callBack);
    }

    /**
     * 实名认证
     *
     * @param requestId
     * @param userName
     * @param idCard
     * @param callBack
     */
    public void idAudit(int requestId, String userName, String idCard, HttpRequestCallBack callBack) {

        RequestParams params = new RequestParams(uri);
        params.addBodyParameter("userName", userName);
        params.addBodyParameter("idCard", idCard);

        post(params, requestId, callBack);
    }

    /**
     * 保存资料并提交审核
     *
     * @param requestId
     * @param userName
     * @param sex
     * @param idCard
     * @param seniority
     * @param callBack
     */
    public void dataSave(int requestId, String userName, String idCard, int sex,
                         String seniority, HttpRequestCallBack callBack) {

        showDialog();

        RequestParams params = new RequestParams(uri);
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
     * @param hashCode
     * @param callBack
     */
    public void userData(int requestId, int hashCode, HttpRequestCallBack callBack) {

        RequestParams params = new RequestParams(uri);
        params.addBodyParameter("hashCode", String.valueOf(hashCode));

        post(params, requestId, callBack);
    }

    /**
     * 校验密码
     *
     * @param requestId
     * @param password
     * @param callBack
     */
    public void passwordCheck(int requestId, String password, HttpRequestCallBack callBack) {

        showDialog();

        RequestParams params = new RequestParams(uri);
        params.addBodyParameter("password", password);

        post(params, requestId, callBack);
    }

    /**
     * 重置手机号码
     *
     * @param requestId
     * @param mobile
     * @param code
     * @param callBack
     */
    public void resetMobile(int requestId, String mobile, String code, HttpRequestCallBack callBack) {

        showDialog();

        RequestParams params = new RequestParams(uri);
        params.addBodyParameter("mobile", mobile);
        params.addBodyParameter("code", code);

        post(params, requestId, callBack);

    }

    /**
     * 获取关联机构信息
     *
     * @param requestId
     * @param hashCode
     * @param callBack
     */
    public void company(int requestId, int hashCode, HttpRequestCallBack callBack) {

        RequestParams params = new RequestParams(uri);
        params.addBodyParameter("hashCode", String.valueOf(hashCode));

        post(params, requestId, callBack);
    }

    /**
     * 获取预约订单
     *
     * @param requestId
     * @param callBack
     */
    public void orderList(int requestId, HttpRequestCallBack callBack) {

        RequestParams params = new RequestParams(uri);

        post(params, requestId, callBack);
    }

    /**
     * 预约单状态  接受、拒绝
     *
     * @param requestId
     * @param orderNumber
     * @param status
     */
    public void orderStatus(int requestId, String orderNumber, int status, HttpRequestCallBack callBack) {

        showDialog();

        RequestParams params = new RequestParams(uri);
        params.addBodyParameter("orderNumber", orderNumber);
        params.addBodyParameter("status", String.valueOf(status));

        post(params, requestId, callBack);

    }

    /**
     * 报价房间
     *
     * @param requestId
     * @param planId
     * @param page
     * @param limit
     * @param callBack
     */
    public void offerHouse(int requestId, long planId, int page, int limit, HttpRequestCallBack callBack) {

        RequestParams params = new RequestParams(uri);
        params.addBodyParameter("planId", String.valueOf(planId));
        params.addBodyParameter("page", String.valueOf(page));
        params.addBodyParameter("limit", String.valueOf(limit));

        post(params, requestId, callBack);

    }

    /**
     * 报价套餐
     *
     * @param requestId
     * @param id
     * @param branchId
     * @param hashCode
     * @param callBack
     */
    public void offerPackage(int requestId, long id, long branchId, int hashCode, HttpRequestCallBack callBack) {

        RequestParams params = new RequestParams(uri);
        params.addBodyParameter("id", String.valueOf(id));
        params.addBodyParameter("branchId", String.valueOf(branchId));
        params.addBodyParameter("hashCode", String.valueOf(hashCode));

        post(params, requestId, callBack);


    }

    /**
     * 发送报价单
     *
     * @param requestId
     * @param branchId
     * @param ownerId
     * @param planId
     * @param projectId
     * @param area
     * @param halles
     * @param rooms
     * @param cookhouse
     * @param washroom
     * @param balcony
     * @param others
     * @param itemsListJson
     * @param addItemsListJson
     * @param hashCode
     * @param callBack
     */
    public void sendQuotation(int requestId, long branchId, long ownerId, long planId, long projectId,
                              double area, int halles, int rooms, int cookhouse, int washroom, int balcony,
                              int others, String itemsListJson, String addItemsListJson, int hashCode, HttpRequestCallBack callBack) {

        showDialog();

        RequestParams params = new RequestParams(uri);
        params.addBodyParameter("branchId", String.valueOf(branchId));
        params.addBodyParameter("ownerId", String.valueOf(ownerId));
        params.addBodyParameter("planId", String.valueOf(planId));
        params.addBodyParameter("projectId", String.valueOf(projectId));
        params.addBodyParameter("area", String.valueOf(area));
        params.addBodyParameter("halles", String.valueOf(halles));
        params.addBodyParameter("rooms", String.valueOf(rooms));
        params.addBodyParameter("cookhouse", String.valueOf(cookhouse));
        params.addBodyParameter("washroom", String.valueOf(washroom));
        params.addBodyParameter("balcony", String.valueOf(balcony));
        params.addBodyParameter("others", String.valueOf(others));
        params.addBodyParameter("itemsListJson", itemsListJson);
        params.addBodyParameter("addItemsListJson", addItemsListJson);
        params.addBodyParameter("hashCode", String.valueOf(hashCode));

        post(params, requestId, callBack);

    }

    /**
     * 获取奖励金信息
     *
     * @param requestId
     * @param number
     * @param hashCode
     * @param callBack
     */
    public void getReward(int requestId, String number, int hashCode, HttpRequestCallBack callBack) {

        RequestParams params = new RequestParams(uri);
        params.addBodyParameter("number", number);
        params.addBodyParameter("hashCode", String.valueOf(hashCode));

        post(params, requestId, callBack);
    }

    /**
     * 保存报价
     *
     * @param requestId
     * @param orderJsonData
     * @param hashCode
     * @param callBack
     */
    public void offerSave(int requestId, String orderJsonData, int hashCode, HttpRequestCallBack callBack) {

        showDialog();

        RequestParams params = new RequestParams(uri);
        params.addBodyParameter("orderJsonData", orderJsonData);
        params.addBodyParameter("hashCode", String.valueOf(hashCode));

        post(params, requestId, callBack);

    }

    //----------------------------------------------------------------------------------res

    /**
     * 获取资源
     *
     * @param requestId
     * @param hashCode
     * @param callBack
     */
    public void resource(final int requestId, int hashCode, final HttpRequestCallBack callBack) {
        RequestParams params = new RequestParams(uri);
        params.addBodyParameter("hashCode", String.valueOf(hashCode));

        post(params, requestId, callBack);
    }

}
