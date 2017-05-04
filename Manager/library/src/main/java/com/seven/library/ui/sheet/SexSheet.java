package com.seven.library.ui.sheet;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.seven.library.R;
import com.seven.library.base.BaseSheet;
import com.seven.library.callback.DialogClickCallBack;
import com.seven.library.config.RunTimeConfig;

/**
 * 性别选择器
 *
 * @author seven
 */
public class SexSheet extends BaseSheet {

    //男 女 取消
    private RelativeLayout mMan;
    private RelativeLayout mWoman;
    private RelativeLayout mCancel;

    public SexSheet(Activity activity, int theme, DialogClickCallBack dialogClickCallBack) {
        super(activity, theme, dialogClickCallBack);
    }

    @Override
    public int getRootLayoutId() {

        isTouch=true;

        return R.layout.sheet_sex;
    }

    @Override
    public void onInitRootView(Bundle savedInstanceState) {

        initView();

        initData();

    }

    @Override
    public void initView() {

        mMan = getView(mMan, R.id.sex_man_rl);
        mMan.setOnClickListener(this);
        mWoman = getView(mWoman, R.id.sex_woman_rl);
        mWoman.setOnClickListener(this);
        mCancel = getView(mCancel, R.id.sex_cancel_rl);
        mCancel.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.sex_man_rl)
            mCallBack.onClick(v, RunTimeConfig.CommonConfig.SEX_MAN);
        else if (id == R.id.sex_woman_rl)
            mCallBack.onClick(v, RunTimeConfig.CommonConfig.SEX_WOMAN);

        dismiss();

    }
}
