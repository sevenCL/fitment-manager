package com.seven.manager.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.seven.library.base.BaseActivity;
import com.seven.library.config.RunTimeConfig;
import com.seven.library.task.ActivityStack;
import com.seven.library.utils.ResourceUtils;
import com.seven.library.utils.ToastUtils;
import com.seven.manager.R;
import com.seven.manager.ui.activity.HomeActivity;
import com.seven.manager.ui.dialog.PasswordDialog;

/**
 * 用户流程结束页
 *
 * @author seven
 */

public class UserOverActivity extends BaseActivity {

    //流程 REGISTER 注册 FORGET_PASSWORD 忘记密码
    private int flow;

    //流程结束语
    private TextView mFlowTv;

    //提示语
    private TextView mHint1;
    private TextView mHint2;

    //结束跳转按钮文本
    private TextView mOverBtnTv;
    //主页按钮
    private RelativeLayout mHomeBtn;

    /**
     * 跳转方法
     *
     * @param isFinishSrcAct 是否关闭启动页面
     */
    public static void start(boolean isFinishSrcAct, int flow) {
        Bundle param = new Bundle();
        param.putBoolean(RunTimeConfig.IntentCodeConfig.FINISH, isFinishSrcAct);
        param.putInt(RunTimeConfig.IntentCodeConfig.FLOW, flow);
        ActivityStack.getInstance().startActivity(UserOverActivity.class, isFinishSrcAct, param);
    }

    @Override
    public void onInitRootView(Bundle savedInstanceState) {

        initView();

        initData(null);

    }

    @Override
    public int getRootLayoutId() {
        transparency = true;
        return R.layout.activity_user_over;
    }

    @Override
    public void initView() {

        mFlowTv = getView(mFlowTv, R.id.over_tv);

        mHint1 = getView(mHint1, R.id.over_hint_1_tv);
        mHint2 = getView(mHint2, R.id.over_hint_2_tv);

        mOverBtnTv = getView(mOverBtnTv, R.id.over_btn_tv);
        mHomeBtn = getView(mHomeBtn, R.id.over_home_btn);
    }

    @Override
    public void initData(Intent intent) {

        if (intent == null)
            intent = getIntent();

        //默认是注册流程
        flow = intent.getIntExtra(RunTimeConfig.IntentCodeConfig.FLOW, RunTimeConfig.FlowConfig.REGISTER);

        if (flow == RunTimeConfig.FlowConfig.FORGET_PASSWORD) {
            mFlowTv.setText(ResourceUtils.getInstance().getText(R.string.password_over));
            mHint1.setText(ResourceUtils.getInstance().getText(R.string.hint_password_success));
            mHint2.setText(ResourceUtils.getInstance().getText(R.string.hint_password_success_2));
            mOverBtnTv.setText(ResourceUtils.getInstance().getText(R.string.reset_login));
            mHomeBtn.setVisibility(View.GONE);
        }

        autoSkip();

    }

    public void btClick(View view) {

        int id = view.getId();

        switch (id) {

            //流程天转
            case R.id.over_btn:

                if (flow == RunTimeConfig.FlowConfig.REGISTER) {
                    ToastUtils.getInstance().showToast("跳转到实名认证界面");
                    ActivityStack.getInstance().finishActivity(LoginActivity.class);
                } else
                    finish();

                break;

            //主页
            case R.id.over_home_btn:

                HomeActivity.start(true);
                ActivityStack.getInstance().finishActivity(LoginActivity.class);

                break;
        }
    }

    /**
     * 自动跳转
     */
    private void autoSkip() {

        new Handler().postDelayed(new Runnable() {
            public void run() {

                if (flow == RunTimeConfig.FlowConfig.REGISTER) {
                    ToastUtils.getInstance().showToast("跳转到实名认证界面");
                    ActivityStack.getInstance().finishActivity(LoginActivity.class);
                } else
                    finish();
            }
        }, 3000);

    }

}
