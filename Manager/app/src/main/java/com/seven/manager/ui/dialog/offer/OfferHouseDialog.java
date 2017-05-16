package com.seven.manager.ui.dialog.offer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;

import com.seven.library.application.LibApplication;
import com.seven.library.base.BaseDialog;
import com.seven.library.callback.DialogClickCallBack;
import com.seven.library.callback.ListItemCallBack;
import com.seven.library.config.RunTimeConfig;
import com.seven.library.view.AutoLoadRecyclerView;
import com.seven.manager.R;
import com.seven.manager.adapter.offer.OfferHouseAdapter;
import com.seven.manager.model.offer.OfferHouse;
import com.seven.manager.model.offer.OfferHouseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 报价-添加房间
 *
 * @author seven
 */

public class OfferHouseDialog extends BaseDialog implements ListItemCallBack {

    private AutoLoadRecyclerView mRecyclerView;

    private RelativeLayout mCancel;
    private RelativeLayout mSure;

    private List<OfferHouse> mDataList;

    private OfferHouseAdapter mAdapter;

    private List<OfferHouseModel> mSelectList;

    public OfferHouseDialog(Activity activity, int theme, DialogClickCallBack dialogClickCallBack) {
        super(activity, theme, dialogClickCallBack);
    }

    public OfferHouseDialog(Activity activity, int theme, List<OfferHouseModel> list, DialogClickCallBack dialogClickCallBack) {
        this(activity, theme, dialogClickCallBack);

        //1厅  2室  3厨  4卫  5阳台 6其他
        mDataList = new ArrayList<>();
        mSelectList = new ArrayList<>();

        OfferHouse offerHouse = new OfferHouse();

        for (OfferHouseModel model : list) {

            if (offerHouse.getType() != model.getType()) {
                offerHouse = new OfferHouse();
                mDataList.add(offerHouse);
                offerHouse.setType(model.getType());
            }
            offerHouse.addList(model);
        }

    }


    @Override
    public int getRootLayoutId() {

        isTouch = true;

        return R.layout.dialog_offer_house;
    }

    @Override
    public void onInitRootView(Bundle savedInstanceState) {

        initView();

        initData();

        setRecyclerView();

    }

    @Override
    public void initView() {

        mRecyclerView = getView(mRecyclerView, R.id.offer_house_recycler_view);

        mCancel = getView(mCancel, R.id.offer_house_cancel_rl);
        mSure = getView(mSure, R.id.offer_house_sure_rl);

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

            case R.id.offer_house_sure_rl:

                for (OfferHouse house : mDataList) {
                    for (OfferHouseModel model : house.getList()) {

                        if (model.getStatus() == RunTimeConfig.SelectConfig.SELECTED)
                            mSelectList.add(model);

                    }
                }

                mCallBack.onClick(v, mSelectList);

                break;
        }

        dismiss();

    }

    private void setRecyclerView() {

        mRecyclerView.setHasFixedSize(false);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(LibApplication.getInstance()));
        mRecyclerView.setOnPauseListenerParams(false, true);

        mAdapter = new OfferHouseAdapter(LibApplication.getInstance(), mDataList, this);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onItemClick(View view, int position, Object... object) {

        int id = view.getId();

        switch (id) {

            case R.id.house_content_btn:

                OfferHouseModel model = mDataList.get(position).getList().get((Integer) object[0]);

                model.setStatus(model.getStatus() == RunTimeConfig.SelectConfig.SELECT_NOT ?
                        RunTimeConfig.SelectConfig.SELECTED : RunTimeConfig.SelectConfig.SELECT_NOT);

                mAdapter.notifyItemChanged(position);

                break;
        }
    }
}
