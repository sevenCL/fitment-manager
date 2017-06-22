package com.seven.manager.ui.dialog.newoffer;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.seven.library.application.LibApplication;
import com.seven.library.base.BaseDialog;
import com.seven.library.callback.DialogClickCallBack;
import com.seven.library.callback.ListItemCallBack;
import com.seven.library.config.RunTimeConfig;
import com.seven.library.utils.CheckUtils;
import com.seven.library.view.AutoLoadRecyclerView;
import com.seven.manager.R;
import com.seven.manager.adapter.newoffer.TermVolumeAdapter;
import com.seven.manager.model.newoffer.TermVolumeItem;
import com.seven.manager.model.newoffer.TermVolumeModel;
import com.seven.manager.model.newoffer.TermVolumeTitle;
import com.seven.manager.model.newoffer.TermVolumeTotal;
import com.seven.manager.model.newoffer.BaseTerm;
import com.seven.manager.model.newoffer.SpaceItemList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author seven
 */

public class TermVolumeDialog extends BaseDialog implements ListItemCallBack {

    private AutoLoadRecyclerView mRecyclerView;

    private RelativeLayout mCancel;
    private RelativeLayout mSure;

    private BaseTerm mTerm;

    private double totalArea;

    private TextView mNumber;

    private List<TermVolumeModel> mDataList;
    private TermVolumeAdapter mAdapter;

    public TermVolumeDialog(Activity activity, int theme, DialogClickCallBack dialogClickCallBack) {
        super(activity, theme, dialogClickCallBack);
    }

    public TermVolumeDialog(Activity activity, int theme, BaseTerm term, DialogClickCallBack dialogClickCallBack) {
        this(activity, theme, dialogClickCallBack);
        init(term);
    }

    public TermVolumeDialog(Activity activity, int theme, BaseTerm term, double area, DialogClickCallBack dialogClickCallBack) {
        this(activity, theme, dialogClickCallBack);
        init(term);
        this.totalArea = area;
    }

    private void init(BaseTerm term) {

        mTerm = new BaseTerm();
        mTerm.setWord(term.getWord());
        mTerm.setId(term.getId());
        mTerm.setName(term.getName());
        mTerm.setBaseItemId(term.getBaseItemId());
        mTerm.setBaseItemName(term.getBaseItemName());
        mTerm.setBaseItemPid(term.getBaseItemPid());
        mTerm.setBaseItemPidName(term.getBaseItemPidName());
        mTerm.setCategoryId(term.getCategoryId());
        mTerm.setDefaultItem(term.isDefaultItem());
        mTerm.setDescription(term.getDescription());
        mTerm.setFormula(term.getFormula());
        mTerm.setNumber(term.getNumber());
        mTerm.setOffer(term.getOffer());
        mTerm.setOverallItem(term.isOverallItem());
        mTerm.setStatus(term.getStatus());
        mTerm.setUnitId(term.getUnitId());
        mTerm.setUnitName(term.getUnitName());
        mTerm.setWorkType(term.getWorkType());
        SpaceItemList newList;
        for (SpaceItemList itemList : term.getList()) {
            newList = new SpaceItemList();
            newList.setId(itemList.getId());
            newList.setShowName(itemList.isShowName());
            newList.setVolume(itemList.getVolume());
            newList.setName(itemList.getName());
            newList.setTag(itemList.getTag());
            newList.setArea(itemList.getArea());
            newList.setSpaceId(itemList.getSpaceId());
            newList.setType(itemList.getType());
            mTerm.addList(newList);
        }
        mDataList = new ArrayList<>();

    }

    @Override
    public int getRootLayoutId() {

        isTouch = true;

        return R.layout.dialog_term_volume;
    }

    @Override
    public void onInitRootView(Bundle savedInstanceState) {

        initView();

        initData();

        getData();

        setRecyclerView();

    }

    @Override
    public void initView() {

        mRecyclerView = getView(mRecyclerView, R.id.term_volume_recycler);

        mCancel = getView(mCancel, R.id.term_volume_cancel_rl);
        mSure = getView(mSure, R.id.term_volume_sure_rl);

        mNumber = getView(mNumber, R.id.term_volume_number);
    }

    @Override
    public void initData() {

        mNumber.setText("编号" + mTerm.getNumber());

        mCancel.setOnClickListener(this);
        mSure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id) {

            case R.id.term_volume_sure_rl:

                if (!mTerm.isDefaultItem()) {

                    if (mTerm.isOverallItem()) {

                        if (mTerm.getList().size() == 0) {

                            SpaceItemList itemList = new SpaceItemList();

                            for (TermVolumeModel model : mDataList) {

                                if (model.getViewType() == RunTimeConfig.ModelConfig.TERM_VOLUME_ITEM) {

                                    itemList.setName(((TermVolumeItem) model).getName());
                                    itemList.setShowName(((TermVolumeItem) model).isShowName());
                                    itemList.setTag(((TermVolumeItem) model).getTag());
                                    itemList.setVolume(((TermVolumeItem) model).getVolume());

                                    mTerm.addList(itemList);

                                }
                            }
                        } else {
                            for (TermVolumeModel model : mDataList) {

                                if (model.getViewType() == RunTimeConfig.ModelConfig.TERM_VOLUME_ITEM) {

                                    mTerm.getList().get(0).setVolume(((TermVolumeItem) model).getVolume());

                                }
                            }
                        }

                    } else {

                        for (TermVolumeModel model : mDataList) {

                            if (model.getViewType() == RunTimeConfig.ModelConfig.TERM_VOLUME_ITEM) {

                                for (SpaceItemList itemList : mTerm.getList()) {

                                    if (((TermVolumeItem) model).getId() == itemList.getId() &&
                                            ((TermVolumeItem) model).getTag() == itemList.getTag()) {

                                        itemList.setVolume(((TermVolumeItem) model).getVolume());
                                    }

                                }

                            }

                        }
                    }
                    mCallBack.onClick(v, mTerm);
                }
                break;
        }

        dismiss();

    }

    private void getData() {

        if (this.mTerm == null)
            return;

        TermVolumeTitle title = new TermVolumeTitle();
        title.setName(mTerm.getName());
        title.setPrice(mTerm.getOffer());
        title.setUnit(mTerm.getUnitName());
        title.setDescribe(mTerm.getDescription());
        mDataList.add(title);

        TermVolumeItem item = null;

        if (mTerm.isOverallItem()) {

            if (mTerm.getList().size() == 0) {
                item = new TermVolumeItem();
                item.setName("全屋");
                item.setShowName(true);
                item.setTag(1);
                item.setVolume(CheckUtils.getInstance().formula(totalArea, mTerm.getFormula()));
                item.setDefaultItem(mTerm.isDefaultItem());
                mDataList.add(item);
            } else {
                for (SpaceItemList itemList : mTerm.getList()) {

                    item = new TermVolumeItem();
                    item.setName(itemList.getName());
                    item.setId(itemList.getId());
                    item.setArea(itemList.getArea());
                    item.setSpaceId(itemList.getSpaceId());
                    item.setType(itemList.getType());
                    item.setShowName(itemList.isShowName());
                    item.setTag(itemList.getTag());
                    item.setVolume(itemList.getVolume());
                    item.setDefaultItem(mTerm.isDefaultItem());

                    mDataList.add(item);
                }
            }
        } else {

            if (mTerm.isDefaultItem()) {


                item = new TermVolumeItem();
                item.setName("全屋");
                item.setShowName(true);
                item.setTag(1);
                item.setVolume(CheckUtils.getInstance().formula(totalArea, mTerm.getFormula()));
                item.setDefaultItem(mTerm.isDefaultItem());
                mDataList.add(item);

            } else {

                for (SpaceItemList itemList : mTerm.getList()) {

                    item = new TermVolumeItem();
                    item.setName(itemList.getName());
                    item.setId(itemList.getId());
                    item.setArea(itemList.getArea());
                    item.setSpaceId(itemList.getSpaceId());
                    item.setType(itemList.getType());
                    item.setShowName(itemList.isShowName());
                    item.setTag(itemList.getTag());
                    item.setVolume(itemList.getVolume());
                    item.setDefaultItem(mTerm.isDefaultItem());

                    mDataList.add(item);
                }
            }
        }
        getTotal(false);
    }

    private void getTotal(boolean isUpdate) {

        double volume = 0;

        for (TermVolumeModel model : mDataList) {

            if (model.getViewType() == RunTimeConfig.ModelConfig.TERM_VOLUME_ITEM) {

                volume += ((TermVolumeItem) model).getVolume();

            }

        }

        if (isUpdate) {

            if (mDataList.get(mDataList.size() - 1).getViewType() == RunTimeConfig.ModelConfig.TERM_VOLUME_TOTAL) {

                ((TermVolumeTotal) mDataList.get(mDataList.size() - 1)).setTotal(volume);

            }

            Handler handler = new Handler();
            final Runnable r = new Runnable() {
                public void run() {
                    if (mAdapter != null)
                        mAdapter.notifyItemChanged(mDataList.size() - 1);
                }
            };
            handler.post(r);

        } else {

            TermVolumeTotal total = new TermVolumeTotal();
            total.setUnit(mTerm.getUnitName());
            total.setTotal(volume);
            mDataList.add(total);
        }
    }

    private void setRecyclerView() {

        mRecyclerView.setHasFixedSize(false);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(LibApplication.getInstance()));
        mRecyclerView.setOnPauseListenerParams(false, true);

        mAdapter = new TermVolumeAdapter(LibApplication.getInstance(), mDataList, this);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onItemClick(View view, int position, Object... object) {

        int id = view.getId();

        switch (id) {

            case R.id.volume_et:

                getTotal(true);

                break;
            case R.id.volume_delete_iv:

                if (mDataList.get(position).getViewType() == RunTimeConfig.ModelConfig.TERM_VOLUME_ITEM) {

                    ((TermVolumeItem) mDataList.get(position)).setVolume(0);

                }

                if (mAdapter != null)
                    mAdapter.notifyItemChanged(position);

                getTotal(true);

                break;

        }

    }
}
