package com.seven.manager.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.seven.library.application.LibApplication;
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
import com.seven.manager.model.user.RegisterModel;

/**
 * 设置密码
 *
 * @author seven
 */
public class PasswordActivity extends BaseTitleActivity implements HttpRequestCallBack {

    //流程 REGISTER 注册 FORGET_PASSWORD 忘记密码
    private int flow;

    //城市id
    private long cityId;

    //手机号码
    private String mobile;

    //密码
    private EditText mPasswordEt;
    private ImageView mPasswordLookIv;

    //再次输入密码
    private EditText mPasswordAgainEt;
    private ImageView mPasswordLookAgainIv;

    //注册协议
    private LinearLayout mAgreementLl;

    //注册
    private Button mRegisterBtn;

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
        ActivityStack.getInstance().startActivity(PasswordActivity.class, isFinishSrcAct, param);
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

        return R.layout.activity_password;
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

        mPasswordEt = getView(mPasswordEt, R.id.password_et);
        mPasswordLookIv = getView(mPasswordLookIv, R.id.password_look_iv);

        mPasswordAgainEt = getView(mPasswordAgainEt, R.id.again_password_et);
        mPasswordLookAgainIv = getView(mPasswordLookAgainIv, R.id.again_password_look_iv);

        mAgreementLl = getView(mAgreementLl, R.id.agreement_ll);

        mRegisterBtn = getView(mRegisterBtn, R.id.register_btn);
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

        //添加标题
        setTitle(flow == RunTimeConfig.FlowConfig.REGISTER ? ResourceUtils.getInstance().getText(R.string.register) : ResourceUtils.getInstance().getText(R.string.password_forget));

        //根据流程是否显示注册协议
        if (flow == RunTimeConfig.FlowConfig.FORGET_PASSWORD) {
            mAgreementLl.setVisibility(View.GONE);
            mRegisterBtn.setText(ResourceUtils.getInstance().getText(R.string.reset_password));
        }
    }

    public void btClick(View view) {

        int id = view.getId();

        if (id == R.id.password_look_iv)//输入密码
//            editLook(mPasswordEt, mPasswordLookIv);
            mPasswordEt.setText("");
        else if (id == R.id.again_password_look_iv)//再次输入密码
//            editLook(mPasswordAgainEt, mPasswordLookAgainIv);
            mPasswordAgainEt.setText("");
        else if (id == R.id.agreement_ll)//注册协议
            ToastUtils.getInstance().showToast(R.string.agreement);
        else if (id == R.id.register_btn)//完成注册
            register();

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
                mPasswordLookIv.setVisibility(s.length() > 0 ? View.VISIBLE : View.INVISIBLE);
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
                mPasswordLookAgainIv.setVisibility(s.length() > 0 ? View.VISIBLE : View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 输入框输入类型
     *
     * @param editText
     */
    private void editLook(@NonNull EditText editText, @NonNull ImageView imageView) {
        editText.setInputType(editText.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD) ?
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD));
        imageView.setImageResource(editText.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD) ?
                R.drawable.password_visible : R.drawable.password_invisible);
        editText.setSelection(TextUtils.isEmpty(editText.getText().toString()) ? 0 : editText.getText().toString().length());
    }

    /**
     * 注册
     */
    private void register() {

        if (!CheckUtils.getInstance().isPassword(mPasswordEt.getText().toString()) ||
                !CheckUtils.getInstance().isPassword(mPasswordAgainEt.getText().toString())) {
            ToastUtils.getInstance().showToast(R.string.hint_password);
            return;
        }

        if (!mPasswordEt.getText().toString().equals(mPasswordAgainEt.getText().toString())) {
            ToastUtils.getInstance().showToast(R.string.hint_in_conformity);
            return;
        }

        // TODO: 2016/12/20 注册

        if (flow == RunTimeConfig.FlowConfig.REGISTER)
            RequestUtils.getInstance(Urls.REGISTER).register(RunTimeConfig.RequestConfig.REGISTER,
                    mobile, mPasswordAgainEt.getText().toString(), cityId, this);
        else
            RequestUtils.getInstance(Urls.PASSWORD_FORGET).passwordForget(RunTimeConfig.RequestConfig.PASSWORD_FORGET,
                    mobile, mPasswordAgainEt.getText().toString(), this);
    }

    @Override
    public void onSucceed(String result, int requestId) {

        switch (requestId) {

            //注册
            case RunTimeConfig.RequestConfig.REGISTER:

                LogUtils.println(this.getClass().getName() + " onSucceed REGISTER request " + result);

                JsonHelper.getInstance().jsonObject(result, RegisterModel.class, true, null, new JsonCallBack() {
                    @Override
                    public void onSucceed(Object data) {
                        if (data == null)
                            return;

                        LibApplication.branchId = ((RegisterModel) data).getBranchId();
                        LibApplication.token = ((RegisterModel) data).getToken();

                        ToastUtils.getInstance().showToast(R.string.hint_register_success);

                        ActivityStack.getInstance().finishActivity(CityActivity.class);
                        ActivityStack.getInstance().finishActivity(SmsActivity.class);
                        finish();
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

                                ToastUtils.getInstance().showToast(R.string.hint_forget_password);

                                ActivityStack.getInstance().finishActivity(SmsActivity.class);
                                finish();
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
