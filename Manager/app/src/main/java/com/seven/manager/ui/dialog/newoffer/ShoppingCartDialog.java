package com.seven.manager.ui.dialog.newoffer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;

import com.seven.library.application.LibApplication;
import com.seven.library.base.BaseDialog;
import com.seven.library.callback.DialogClickCallBack;
import com.seven.library.callback.ListItemCallBack;
import com.seven.library.view.AutoLoadRecyclerView;
import com.seven.manager.R;
import com.seven.manager.adapter.newoffer.ShoppingCartAdapter;
import com.seven.manager.model.newoffer.BaseItem;

import java.util.List;

/**
 * 报价-添加房间
 *
 * @author seven
 */

public class ShoppingCartDialog extends BaseDialog implements ListItemCallBack {

    private AutoLoadRecyclerView mRecyclerView;

    private RelativeLayout mCancel;
    private RelativeLayout mSure;

    private List<BaseItem> mDataList;

    private ShoppingCartAdapter mAdapter;

    public ShoppingCartDialog(Activity activity, int theme, DialogClickCallBack dialogClickCallBack) {
        super(activity, theme, dialogClickCallBack);
    }

    public ShoppingCartDialog(Activity activity, int theme, List<BaseItem> list, DialogClickCallBack dialogClickCallBack) {
        this(activity, theme, dialogClickCallBack);

        this.mDataList = list;

    }


    @Override
    public int getRootLayoutId() {

        isTouch = true;

        return R.layout.dialog_shopping_cart;
    }

    @Override
    public void onInitRootView(Bundle savedInstanceState) {

        initView();

        initData();

        setRecyclerView();

    }

    @Override
    public void initView() {

        mRecyclerView = getView(mRecyclerView, R.id.shopping_cart_recycler);

        mCancel = getView(mCancel, R.id.shopping_cart_cancel_rl);
        mSure = getView(mSure, R.id.shopping_cart_sure_rl);

    }

    @Override
    public void initData() {

        mCancel.setOnClickListener(this);
        mSure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id) {

            case R.id.shopping_cart_sure_rl:

                mCallBack.onSureClick(v);

                break;
            case R.id.shopping_cart_cancel_rl:

                mCallBack.onCancelClick(v);

                break;
        }

        dismiss();

    }

    private void setRecyclerView() {

        mRecyclerView.setHasFixedSize(false);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(LibApplication.getInstance()));
        mRecyclerView.setOnPauseListenerParams(false, true);

        mAdapter = new ShoppingCartAdapter(LibApplication.getInstance(), mDataList, this);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onItemClick(View view, int position, Object... object) {

        int id = view.getId();

        switch (id) {

            case R.id.shopping_remove_btn:

                mDataList.remove(position);

                if (mDataList.size() == 0)
                    this.dismiss();

                if (mAdapter != null)
                    mAdapter.notifyDataSetChanged();


                mCallBack.onClick(view);

                break;

        }
    }
}
