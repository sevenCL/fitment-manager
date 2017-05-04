package com.seven.manager.ui.dialog;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.seven.library.utils.ToastUtils;
import com.seven.library.utils.blur.BlurBehind;
import com.seven.manager.R;
import com.seven.manager.model.http.RegisterModel;
import com.seven.manager.ui.activity.user.UserOverActivity;

/**
 * @author seven
 */

public class PasswordDialog extends BaseActivity implements HttpRequestCallBack {

    //流程 REGISTER 注册 FORGET_PASSWORD 忘记密码
    private int flow;

    //城市id
    private long cityId;

    //手机号码
    private String mobile;

    //密码
    private EditText mPasswordEt;

    //再次输入密码
    private EditText mPasswordAgainEt;

    /**
     * 跳转方法
     *
     * @param isFinishSrcAct 是否关闭启动页面
     */
    public static void start(boolean isFinishSrcAct, int flow, String mobile, long cityId) {
        Bundle param = new Bundle();
        param.putBoolean(RunTimeConfig.IntentCodeConfig.FINISH, isFinishSrcAct);
        param.putInt(RunTimeConfig.IntentCodeConfig.FLOW, flow);
        param.putString(RunTimeConfig.IntentCodeConfig.MOBILE, mobile);
        param.putLong(RunTimeConfig.IntentCodeConfig.CITY, cityId);
        ActivityStack.getInstance().startActivity(PasswordDialog.class, isFinishSrcAct, param);
    }

    @Override
    public void onInitRootView(Bundle savedInstanceState) {

        BlurBehind.getInstance()
                .withAlpha(50)
                .withFilterColor(Color.parseColor("#F9CC01"))
                .setBackground(this, true);

        initView();

        initData(null);

        listener();

    }

    @Override
    public int getRootLayoutId() {
        return R.layout.dialog_password;
    }

    @Override
    public void initView() {

        mPasswordEt = getView(mPasswordEt, R.id.password_et);

        mPasswordAgainEt = getView(mPasswordAgainEt, R.id.password_again_et);

    }

    @Override
    public void initData(Intent intent) {

        if (intent == null)
            intent = getIntent();

        //默认是注册流程
        flow = intent.getIntExtra(RunTimeConfig.IntentCodeConfig.FLOW, RunTimeConfig.FlowConfig.REGISTER);
        //手机号码
        mobile = intent.getStringExtra(RunTimeConfig.IntentCodeConfig.MOBILE);
        //城市id
        cityId = intent.getLongExtra(RunTimeConfig.IntentCodeConfig.CITY, 0);
    }

    public void btClick(View view) {

        int id = view.getId();

        switch (id) {

            //点击蒙层区域dismiss=finish
            case R.id.password_touch_rl:

                finish();

                break;

            //提交
            case R.id.password_submit_btn:

                submit();

                break;
        }

    }

    //事件
    private void listener() {

        //密码
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

        //确认密码
        mPasswordAgainEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPasswordAgainEt.setTextSize(s.length() > 0 ? 14 : 12);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 提交
     */
    private void submit() {

        if (!CheckUtils.getInstance().isPassword(mPasswordEt.getText().toString()) ||
                !CheckUtils.getInstance().isPassword(mPasswordAgainEt.getText().toString())) {
            ToastUtils.getInstance().showToast(R.string.hint_password);
            return;
        }

        if (!mPasswordEt.getText().toString().equals(mPasswordAgainEt.getText().toString())) {
            ToastUtils.getInstance().showToast(R.string.hint_in_conformity);
            return;
        }

        // TODO: 2016/12/20 提交

        if (flow == RunTimeConfig.FlowConfig.REGISTER)
            RequestUtils.getInstance().register(RunTimeConfig.RequestConfig.REGISTER, Urls.REGISTER,
                    mobile, mPasswordAgainEt.getText().toString(), cityId, this);
        else
            RequestUtils.getInstance().passwordForget(RunTimeConfig.RequestConfig.PASSWORD_FORGET,
                    Urls.PASSWORD_FORGET, mobile, mPasswordAgainEt.getText().toString(), this);
    }

    @Override
    public void onSucceed(String result, int requestId) {

        switch (requestId) {

            //注册
            case RunTimeConfig.RequestConfig.REGISTER:

                LogUtils.println(this.getClass().getName() + " onSucceed REGISTER request " + result);

                JsonHelper.getInstance().jsonObject(result, RegisterModel.class,
                        true, null, new JsonCallBack() {
                            @Override
                            public void onSucceed(Object data) {
                                if (data == null)
                                    return;

                                LibApplication.token = ((RegisterModel) data).getToken();
                                SharedData.getInstance().setUserCode(((RegisterModel) data).getUserCode());

                                UserOverActivity.start(true, flow);
                            }

                            @Override
                            public void onFailure(String error) {
                                ToastUtils.getInstance().showToast(error);
                            }
                        });

                break;

            //忘记密码
            case RunTimeConfig.RequestConfig.PASSWORD_FORGET:

                LogUtils.println(this.getClass().getName() + " onSucceed PASSWORD_FORGET request " + result);

                JsonHelper.getInstance().jsonObject(result, RegisterModel.class,
                        true, null, new JsonCallBack() {
                            @Override
                            public void onSucceed(Object data) {
                                if (data == null)
                                    return;

                                LibApplication.token = null;
                                SharedData.getInstance().setUserCode("");

                                UserOverActivity.start(true, flow);
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

}
