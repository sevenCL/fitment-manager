package com.seven.manager.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.seven.library.application.LibApplication;
import com.seven.library.base.BaseActivity;
import com.seven.library.callback.HttpRequestCallBack;
import com.seven.library.callback.JsonCallBack;
import com.seven.library.config.RunTimeConfig;
import com.seven.library.db.share.SharedData;
import com.seven.library.http.RequestUtils;
import com.seven.library.http.Urls;
import com.seven.library.json.JsonHelper;
import com.seven.library.task.ActivityStack;
import com.seven.library.utils.CheckUtils;
import com.seven.library.utils.LogUtils;
import com.seven.library.utils.PermissionUtils;
import com.seven.library.utils.ToastUtils;
import com.seven.library.utils.blur.BlurBehind;
import com.seven.library.utils.blur.OnBlurCompleteListener;
import com.seven.manager.R;
import com.seven.manager.model.user.RegisterModel;
import com.seven.manager.ui.activity.HomeActivity;
import com.seven.manager.ui.dialog.user.SmsDialog;

/**
 * 用户注册
 *
 * @author seven
 */

public class LoginActivity extends BaseActivity implements HttpRequestCallBack {

    //用户名
    private EditText mUserEt;

    //用户密码
    private EditText mPasswordEt;

    /**
     * 跳转方法
     *
     * @param isFinishSrcAct 是否关闭启动页面
     */
    public static void start(boolean isFinishSrcAct) {
        Bundle param = new Bundle();
        param.putBoolean(RunTimeConfig.IntentCodeConfig.FINISH, isFinishSrcAct);
        ActivityStack.getInstance().startActivity(LoginActivity.class, isFinishSrcAct, param);
    }

    @Override
    public void onInitRootView(Bundle savedInstanceState) {

        initView();

        initData(null);

        listener();
    }

    @Override
    public int getRootLayoutId() {
        transparency = true;
        return R.layout.activity_login;
    }

    @Override
    public void initView() {

        mUserEt = getView(mUserEt, R.id.user_et);

        mPasswordEt = getView(mPasswordEt, R.id.password_et);
    }

    @Override
    public void initData(Intent intent) {

        //存储调用、手机状态权限
        PermissionUtils.getInstance(getPackageManager()).sdcardPermission(this);

    }

    public void btClick(View view) {

        int id = view.getId();

        switch (id) {

            //清空用户名
            case R.id.user_clear_iv:

                mUserEt.setText("");

                break;

            //清空密码
            case R.id.password_clear_iv:

                mPasswordEt.setText("");

                break;

            //登录
            case R.id.login_btn:

                login();

                break;

            //注册 flow 0 注册 1 忘记密码
            case R.id.login_register_btn:

                //cityId 现阶段固定1
                BlurBehind.getInstance().execute(this, new OnBlurCompleteListener() {
                    @Override
                    public void onBlurComplete() {
                        SmsDialog.start(false, RunTimeConfig.FlowConfig.REGISTER, 1);
                    }
                });

                break;

            //忘记密码
            case R.id.forger_password_btn:

                BlurBehind.getInstance().execute(this, new OnBlurCompleteListener() {
                    @Override
                    public void onBlurComplete() {
                        SmsDialog.start(false, RunTimeConfig.FlowConfig.FORGET_PASSWORD, 1);
                    }
                });
                break;
        }

    }

    /**
     * 事件
     */
    private void listener() {

        //用户名
        mUserEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUserEt.setTextSize(s.length() > 0 ? 14 : 12);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //用户密码
        mPasswordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPasswordEt.setTextSize(s.length() > 0 ? 14 : 12);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 登录
     */
    private void login() {

        if (!CheckUtils.getInstance().isMobile(mUserEt.getText().toString())) {
            ToastUtils.getInstance().showToast(R.string.error_user);
            return;
        }

        if (!CheckUtils.getInstance().isPassword(mPasswordEt.getText().toString())) {
            ToastUtils.getInstance().showToast(R.string.error_password);
            return;
        }

        // TODO: 2016/12/16 登录

        RequestUtils.getInstance(Urls.LOGIN).login(RunTimeConfig.RequestConfig.LOGIN,
                mUserEt.getText().toString(), mPasswordEt.getText().toString(), this);

    }

    @Override
    public void onSucceed(String result, int requestId) {

        switch (requestId) {

            //登录
            case RunTimeConfig.RequestConfig.LOGIN:

                LogUtils.println(this.getClass().getName() + " onSucceed LOGIN request " + result);

                JsonHelper.getInstance().jsonObject(result, RegisterModel.class, true, null, new JsonCallBack() {
                    @Override
                    public void onSucceed(Object data) {
                        if (data == null)
                            return;

                        LibApplication.token = ((RegisterModel) data).getToken();
                        LibApplication.branchId = ((RegisterModel) data).getBranchId();
                        SharedData.getInstance().setUserCode(((RegisterModel) data).getUserCode());

                        ToastUtils.getInstance().showToast(R.string.hint_login_success);

                        ActivityStack.getInstance().finishActivity(LoginActivity.class);
                        ActivityStack.getInstance().finishActivity(SmsActivity.class);

                        HomeActivity.start(true);
                    }

                    @Override
                    public void onFailure(String error) {
                        ToastUtils.getInstance().showToast(error);
                    }
                });

                break;
        }
    }

    @Override
    public void onFailure(String error, int requestId) {

    }

    @Override
    public void onProgress(long progress, int requestId) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            ActivityStack.getInstance().finishActivity(HomeActivity.class);

        }
        return super.onKeyDown(keyCode, event);
    }
}
