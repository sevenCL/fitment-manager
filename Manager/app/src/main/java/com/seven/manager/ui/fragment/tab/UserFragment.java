package com.seven.manager.ui.fragment.tab;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.seven.library.application.LibApplication;
import com.seven.library.base.BaseFragment;
import com.seven.library.db.share.SharedData;
import com.seven.manager.R;
import com.seven.manager.ui.activity.offer.OfferActivity;
import com.seven.manager.ui.activity.user.LoginActivity;

/**
 * 收益
 *
 * @author seven
 */
@SuppressLint("ValidFragment")
public class UserFragment extends BaseFragment {

    private Button mExitBtn;

    public UserFragment() {
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    public void onInitRootView(Bundle savedInstanceState) {

        initView();

        initData();

    }

    @Override
    public void initView() {

        mExitBtn = getView(mExitBtn, R.id.user_exit_btn);

    }

    @Override
    public void initData() {

        mExitBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id) {

            case R.id.user_exit_btn:

                exit();

                break;

        }
    }

    /**
     * 退出
     */
    private void exit() {

        SharedData.getInstance().setUserCode("");
        LibApplication.token = null;

        LoginActivity.start(true);

    }

}
