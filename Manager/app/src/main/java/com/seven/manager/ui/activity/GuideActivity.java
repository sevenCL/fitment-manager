package com.seven.manager.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.seven.library.application.LibApplication;
import com.seven.library.base.BaseActivity;
import com.seven.library.callback.ListItemCallBack;
import com.seven.library.config.RunTimeConfig;
import com.seven.library.task.ActivityStack;
import com.seven.manager.R;
import com.seven.manager.adapter.GuideVPAdapter;
import com.seven.manager.ui.activity.user.LoginActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页
 *
 * @author seven
 */
public class GuideActivity extends BaseActivity implements ListItemCallBack {

    private ViewPager mGuideVp;
    private List<View> mViewList;

    private LinearLayout mIndicatorLL;

    private ImageView[] mGroupImgArray;
    private ImageView mGroupImg;

    /**
     * 跳转方法
     *
     * @param isFinishSrcAct 是否关闭启动页面
     */
    public static void start(boolean isFinishSrcAct) {
        Bundle param = new Bundle();
        param.putBoolean(RunTimeConfig.IntentCodeConfig.FINISH, isFinishSrcAct);
        ActivityStack.getInstance().startActivity(GuideActivity.class, isFinishSrcAct, param);
    }

    @Override
    public void onInitRootView(Bundle savedInstanceState) {

        initView();

        initData(null);

    }

    @Override
    public int getRootLayoutId() {

        transparency=true;

        return R.layout.activity_guide;
    }

    @Override
    public void initView() {

        mGuideVp = getView(mGuideVp, R.id.guide_vp);
        mIndicatorLL = getView(mIndicatorLL, R.id.guide_indicator_ll);

    }

    @Override
    public void initData(Intent intent) {

        viewpager();

    }

    /**
     * 引导页面
     */
    private void viewpager() {

        mViewList = new ArrayList<>();

        for (int i = 0; i < 4; i++) {

            mViewList.add(LayoutInflater.from(LibApplication.getInstance()).inflate(R.layout.adapter_guide, null));

        }

        mGuideVp.setAdapter(new GuideVPAdapter(LibApplication.getInstance(), mViewList,this));

        mGuideVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

//                for (int i = 0; i < mGroupImgArray.length; i++) {
//
//                    if (position % mGroupImgArray.length != i) {
//                        mGroupImgArray[i].setBackgroundResource(R.drawable.guide_group);
//                    } else {
//                        mGroupImgArray[position % mGroupImgArray.length].setBackgroundResource(R.drawable.guide_group_press);
//                    }
//                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    /**
     * 引导点
     */
    private void guideGroup() {

        mGroupImgArray = new ImageView[3];

        for (int i = 0; i < mGroupImgArray.length; i++) {

            mGroupImg = new ImageView(this);
            mGroupImg.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mGroupImg.getLayoutParams();
            params.leftMargin = 15;
            params.rightMargin = 15;

            mGroupImg.setLayoutParams(params);

            mGroupImgArray[i] = mGroupImg;

            if (i == 0) {
                mGroupImg.setBackgroundResource(R.drawable.guide_group_press);
            } else {
                mGroupImg.setBackgroundResource(R.drawable.guide_group);
            }

            mIndicatorLL.addView(mGroupImgArray[i]);
        }

    }


    @Override
    public void onItemClick(View view, int position, Object... object) {

        LoginActivity.start(true);
    }
}
