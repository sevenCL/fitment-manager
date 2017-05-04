package com.seven.manager.ui.dialog;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.seven.library.base.BaseActivity;
import com.seven.library.callback.HttpRequestCallBack;
import com.seven.library.callback.JsonCallBack;
import com.seven.library.config.RunTimeConfig;
import com.seven.library.http.RequestUtils;
import com.seven.library.http.Urls;
import com.seven.library.json.JsonField;
import com.seven.library.json.JsonHelper;
import com.seven.library.task.ActivityStack;
import com.seven.library.utils.CheckUtils;
import com.seven.library.utils.LogUtils;
import com.seven.library.utils.ResourceUtils;
import com.seven.library.utils.ToastUtils;
import com.seven.library.utils.blur.BlurBehind;
import com.seven.manager.R;

/**
 * 获取验证码
 *
 * @author seven
 */

public class SmsDialog extends BaseActivity implements HttpRequestCallBack {

    //流程 REGISTER 注册 FORGET_PASSWORD 忘记密码
    private int flow;

    //城市id
    private long cityId;

    //手机号码
    private EditText mMobileEt;

    //验证码
    private EditText mCodeEt;
    private TextView mCodeBtn;

    /**
     * 跳转方法
     *
     * @param isFinishSrcAct 是否关闭启动页面
     */
    public static void start(boolean isFinishSrcAct, int flow, long cityId) {
        Bundle param = new Bundle();
        param.putBoolean(RunTimeConfig.IntentCodeConfig.FINISH, isFinishSrcAct);
        param.putInt(RunTimeConfig.IntentCodeConfig.FLOW, flow);
        param.putLong(RunTimeConfig.IntentCodeConfig.CITY, cityId);
        ActivityStack.getInstance().startActivity(SmsDialog.class, isFinishSrcAct, param, Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }

    @Override
    public void onInitRootView(Bundle savedInstanceState) {

        BlurBehind.getInstance()
                .withAlpha(50)
                .withFilterColor(Color.parseColor("#F9CC01"))
                .setBackground(this,false);

        initView();

        initData(null);

        listener();

    }

    @Override
    public int getRootLayoutId() {
        return R.layout.dialog_sms;
    }

    @Override
    public void initView() {

        mMobileEt = getView(mMobileEt, R.id.sms_mobile_et);

        mCodeEt = getView(mCodeEt, R.id.sms_code_et);
        mCodeBtn = getView(mCodeBtn, R.id.sms_code_btn);

    }

    @Override
    public void initData(Intent intent) {

        if (intent == null)
            intent = getIntent();

        //默认是注册流程
        flow = intent.getIntExtra(RunTimeConfig.IntentCodeConfig.FLOW, RunTimeConfig.FlowConfig.REGISTER);
        //城市
        cityId = intent.getLongExtra(RunTimeConfig.IntentCodeConfig.CITY, 0);
    }

    public void btClick(View view) {

        int id = view.getId();

        switch (id) {

            //点击蒙层区域dismiss=finish
            case R.id.sms_touch_rl:

                finish();

                break;

            //获取验证码
            case R.id.sms_code_btn:

                smsCode();

                break;

            //下一步
            case R.id.sms_next_btn:

                next();

                break;
        }

    }

    /**
     * 事件
     */
    private void listener() {

        //手机号码
        mMobileEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mMobileEt.setTextSize(s.length() > 0 ? 14 : 12);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //验证码
        mCodeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCodeEt.setTextSize(s.length() > 0 ? 14 : 12);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    /**
     * 获取短信验证码
     */
    private void smsCode() {

        if (!CheckUtils.getInstance().isMobile(mMobileEt.getText().toString())) {
            ToastUtils.getInstance().showToast(R.string.error_user);
            return;
        }

        // TODO: 2016/12/19  短信请求

        RequestUtils.getInstance().sms(RunTimeConfig.RequestConfig.SMS, Urls.SMS,
                mMobileEt.getText().toString(), String.valueOf(flow), this);
    }

    /**
     * 下一步
     */
    private void next() {

        if (!CheckUtils.getInstance().isMobile(mMobileEt.getText().toString())) {
            ToastUtils.getInstance().showToast(R.string.error_user);
            return;
        }

        if (mCodeEt.getText().toString().length() != 4) {
            ToastUtils.getInstance().showToast(R.string.error_code);
            return;
        }

        // TODO: 2016/12/28 检验验证码

        RequestUtils.getInstance().smsCheck(RunTimeConfig.RequestConfig.SMS_CHECK, Urls.SMS_CHECK,
                mMobileEt.getText().toString(), mCodeEt.getText().toString(), String.valueOf(flow), this);
    }

    @Override
    public void onSucceed(String result, int requestId) {

        switch (requestId) {

            //短信
            case RunTimeConfig.RequestConfig.SMS:

                LogUtils.println(this.getClass().getName() + " onSucceed SMS request " + result);

                JsonHelper.getInstance().jsonStringSingle(result, true, JsonField.CODE, new JsonCallBack() {
                    @Override
                    public void onSucceed(Object data) {

                        if (data == null)
                            return;

                        ToastUtils.getInstance().showToast(R.string.hint_sms_success);
                        mCodeBtn.setClickable(false);
                        mCodeBtn.setTextColor(getResources().getColor(R.color.border_line));
                        new CountDownTimer(60 * 1000, 1000) {

                            @Override
                            public void onTick(long millisUntilFinished) {

                                String mill = String.valueOf(millisUntilFinished);

                                if (mill.length() == 5)//10s以上
                                    mCodeBtn.setText(ResourceUtils.getInstance().getFormatText(
                                            R.string.count_down_unit, String.valueOf(millisUntilFinished).substring(0, 2)));
                                else if (mill.length() == 4)//10s以内
                                    mCodeBtn.setText(ResourceUtils.getInstance().getFormatText(
                                            R.string.count_down_unit, String.valueOf(millisUntilFinished).substring(0, 1)));
                            }

                            @Override
                            public void onFinish() {

                                mCodeBtn.setClickable(true);
                                mCodeBtn.setText(ResourceUtils.getInstance().getText(R.string.sms));
                                mCodeBtn.setTextColor(getResources().getColor(R.color.black_30));
                            }
                        }.start();

                    }

                    @Override
                    public void onFailure(String error) {
                        ToastUtils.getInstance().showToast(error);
                    }
                });

                break;

            //检验验证码
            case RunTimeConfig.RequestConfig.SMS_CHECK:

                LogUtils.println(this.getClass().getName() + " onSucceed SMS_CHECK request " + result);

                JsonHelper.getInstance().jsonString(result, true, new JsonCallBack() {
                    @Override
                    public void onSucceed(Object data) {
                        PasswordDialog.start(true, flow, mMobileEt.getText().toString(), cityId);
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

        switch (requestId) {
            //短信
            case RunTimeConfig.RequestConfig.SMS:
                ToastUtils.getInstance().showToast(R.string.hint_sms_failure);
                break;
        }
    }

    @Override
    public void onProgress(long progress, int requestId) {

    }
}
