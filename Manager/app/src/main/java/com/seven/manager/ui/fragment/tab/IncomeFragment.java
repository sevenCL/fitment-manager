package com.seven.manager.ui.fragment.tab;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.seven.library.base.BaseFragment;
import com.seven.manager.R;

/**
 * 收益
 *
 * @author seven
 */
@SuppressLint("ValidFragment")
public class IncomeFragment extends BaseFragment{

    public IncomeFragment() {
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_income;
    }

    @Override
    public void onInitRootView(Bundle savedInstanceState) {

        initView();

        initData();

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }
}
