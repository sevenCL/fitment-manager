package com.seven.manager.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.seven.library.base.BaseTitleActivity;
import com.seven.library.callback.HttpRequestCallBack;
import com.seven.library.callback.JsonCallBack;
import com.seven.library.config.RunTimeConfig;
import com.seven.library.http.RequestUtils;
import com.seven.library.http.Urls;
import com.seven.library.json.JsonHelper;
import com.seven.library.task.ActivityStack;
import com.seven.library.utils.CheckUtils;
import com.seven.library.utils.LogUtils;
import com.seven.library.utils.ResourceUtils;
import com.seven.library.utils.ToastUtils;
import com.seven.manager.R;

/**
 * 注册
 *
 * @author seven
 */
public class SmsActivity extends BaseTitleActivity implements HttpRequestCallBack {

    //流程 REGISTER 注册 FORGET_PASSWORD 忘记密码
    private int flow;

    //城市id
    private long cityId;

    //手机号码
    private EditText mMobileEt;
    private ImageView mMobileClearIv;

    //验证码
    private EditText mCodeEt;
    private Button mCodeBtn;
    private ImageView mCodeClearIv;

    //下一步
    private Button mNextBtn;

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
        ActivityStack.getInstance().startActivity(SmsActivity.class, isFinishSrcAct, param);
    }

    @Override
    public void onInitContentView(Bundle savedInstanceState) {

        initView();

        initData(null);

        listener();
    }

    @Override
    public int getContentLayoutId() {

        isLeftBtn = true;

        return R.layout.activity_sms;
    }

    @Override
    public void onLeftButtonClicked() {
        finish();
    }

    @Override
    public void onRightButtonClicked() {

    }

    @Override
    public void onRightTextClicked() {

    }

    @Override
    public void initView() {

        mMobileEt = getView(mMobileEt, R.id.mobile_et);
        mMobileClearIv = getView(mMobileClearIv, R.id.mobile_clear_iv);

        mCodeEt = getView(mCodeEt, R.id.sms_code_et);
        mCodeBtn = getView(mCodeBtn, R.id.sms_code_btn);
        mCodeClearIv = getView(mCodeClearIv, R.id.sms_clear_iv);

        mNextBtn = getView(mNextBtn, R.id.next_btn);
    }

    @Override
    public void initData(Intent intent) {

        if (intent == null)
            intent = getIntent();

        //默认是注册流程
        flow = intent.getIntExtra(RunTimeConfig.IntentCodeConfig.FLOW, RunTimeConfig.FlowConfig.REGISTER);
        //城市
        cityId = intent.getLongExtra(RunTimeConfig.IntentCodeConfig.CITY, 0);

        //添加标题
        setTitle(flow == RunTimeConfig.FlowConfig.REGISTER ? ResourceUtils.getInstance().getText(R.string.register) : ResourceUtils.getInstance().getText(R.string.password_forget));

    }

    public void btClick(View view) {

        int id = view.getId();

        switch (id) {

            //清空手机号
            case R.id.mobile_clear_iv:

                mMobileEt.setText("");

                break;

            //获取验证码
            case R.id.sms_code_btn:

                smsCode();

                break;

            //清空验证码
            case R.id.sms_clear_iv:

                mCodeEt.setText("");

                break;

            //下一步
            case R.id.next_btn:

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

                mMobileClearIv.setVisibility(s.length() > 0 ? View.VISIBLE : View.INVISIBLE);
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

                mCodeClearIv.setVisibility(s.length() > 0 ? View.VISIBLE : View.INVISIBLE);
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

        RequestUtils.getInstance(Urls.SMS).sms(RunTimeConfig.RequestConfig.SMS,
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

        RequestUtils.getInstance(Urls.SMS_CHECK).smsCheck(RunTimeConfig.RequestConfig.SMS_CHECK,
                mMobileEt.getText().toString(), mCodeEt.getText().toString(), String.valueOf(flow), this);
    }

    @Override
    public void onSucceed(String result, int requestId) {

        switch (requestId) {

            //短信
            case RunTimeConfig.RequestConfig.SMS:

                LogUtils.println(" SmsActivity onSucceed SMS request " + result);

                JsonHelper.getInstance().jsonStringSingle(result, true, "code", new JsonCallBack() {
                    @Override
                    public void onSucceed(Object data) {

                        if (data == null)
                            return;

                        ToastUtils.getInstance().showToast(R.string.hint_sms_success);
                        mCodeBtn.setClickable(false);
                        mCodeBtn.setBackgroundColor(getResources().getColor(R.color.hint));

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
                                mCodeBtn.setBackgroundColor(getResources().getColor(R.color.color_primary));

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

                LogUtils.println(" SmsActivity onSucceed SMS_CHECK request " + result);

                JsonHelper.getInstance().jsonString(result, true, new JsonCallBack() {
                    @Override
                    public void onSucceed(Object data) {
                        PasswordActivity.start(false, flow, mMobileEt.getText().toString(), cityId);
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
