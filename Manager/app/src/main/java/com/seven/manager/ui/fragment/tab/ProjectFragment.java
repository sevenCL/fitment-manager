package com.seven.manager.ui.fragment.tab;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.seven.library.base.BaseFragment;
import com.seven.manager.R;

/**
 * 工程
 *
 * @author seven
 */
@SuppressLint("ValidFragment")
public class ProjectFragment extends BaseFragment{

    public ProjectFragment() {
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_project;
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
