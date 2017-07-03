package com.seven.manager.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.seven.library.application.LibApplication;
import com.seven.library.base.BaseActivity;
import com.seven.library.callback.HttpRequestCallBack;
import com.seven.library.callback.JsonCallBack;
import com.seven.library.config.RunTimeConfig;
import com.seven.library.db.share.SharedData;
import com.seven.library.http.RequestUtils;
import com.seven.library.http.Urls;
import com.seven.library.json.JsonHelper;
import com.seven.library.utils.LogUtils;
import com.seven.library.utils.PermissionUtils;
import com.seven.manager.R;
import com.seven.manager.model.user.RegisterModel;
import com.seven.manager.service.ResourceService;
import com.seven.manager.ui.activity.user.LoginActivity;

/**
 * 开屏页面
 * <p>
 * 1.权限引导
 * <p>
 * 2.资源包加载
 *
 * @author seven
 */
public class SplashActivity extends BaseActivity implements HttpRequestCallBack {

    private ImageView mSplashIv;

    @Override
    public void onInitRootView(Bundle savedInstanceState) {

        initView();

        initData(null);

    }

    @Override
    public int getRootLayoutId() {

        transparency = true;

        return R.layout.activity_splash;
    }

    @Override
    public void initView() {

        mSplashIv = getView(mSplashIv, R.id.splash_iv);

    }

    @Override
    public void initData(Intent intent) {

        //存储调用、手机状态权限
        PermissionUtils.getInstance(getPackageManager()).sdcardPermission(this);

        Intent serviceIntent = new Intent(LibApplication.getInstance(), ResourceService.class);

        startService(serviceIntent);

        splash();

    }

    public void btClick(View view) {

        int id = view.getId();

        switch (id) {

            case R.id.splash_test_btn:

                LibApplication.type = 1;

                break;
        }

    }

    /**
     * 界面加载过度动画
     */
    private void splash() {

        Animation animation = AnimationUtils.
                loadAnimation(SplashActivity.this, R.anim.splash_fade);
        animation.setFillAfter(true);
        animation.setDuration(0);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

//                if (SharedData.getInstance().getIsFirst()) {
//
//                    SharedData.getInstance().setIsFirst(false);
//
//                    //引导页
//                    GuideActivity.start(true);
//
//                } else {

                    next();

//                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mSplashIv.startAnimation(animation);
    }

    /**
     * 下一步
     */
    private void next() {

        if (TextUtils.isEmpty(SharedData.getInstance().getUserCode()))
            LoginActivity.start(true);
        else
            RequestUtils.getInstance(Urls.LOGIN_AVOID).loginAvoid(RunTimeConfig.RequestConfig.LOGIN_AVOID,
                    SharedData.getInstance().getUserCode(), this);

    }

    @Override
    public void onSucceed(String result, int requestId) {

        switch (requestId) {

            //免登录
            case RunTimeConfig.RequestConfig.LOGIN_AVOID:

                LogUtils.println(this.getClass().getName() + " onSucceed LOGIN_AVOID request " + result);

                JsonHelper.getInstance().jsonObject(result, RegisterModel.class,
                        true, null, new JsonCallBack() {
                            @Override
                            public void onSucceed(Object data) {
                                if (data == null)
                                    return;

                                LibApplication.token = ((RegisterModel) data).getToken();

                                LibApplication.branchId = ((RegisterModel) data).getBranchId();

                                HomeActivity.start(true);
                            }

                            @Override
                            public void onFailure(String error) {
                                LoginActivity.start(true);
                            }
                        });

                break;

        }
    }

    @Override
    public void onFailure(String error, int requestId) {

        LogUtils.println(this.getClass().getName() + " onFailure LOGIN_AVOID request " + error);

    }

    @Override
    public void onProgress(long progress, int requestId) {

    }
}
