package com.seven.manager.ui.fragment.offer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.frankchen.mvc.aidl.Task;
import com.seven.library.application.LibApplication;
import com.seven.library.base.BaseFragment;
import com.seven.library.callback.ListItemCallBack;
import com.seven.library.config.RunTimeConfig;
import com.seven.library.view.AutoLoadRecyclerView;
import com.seven.manager.R;
import com.seven.manager.adapter.offer.OfferPersonalityAdapter;
import com.seven.manager.model.offer.OfferHouseModel;
import com.seven.manager.model.offer.OfferPersonalityModel;
import com.seven.manager.model.offer.OfferPersonalityTermModel;
import com.seven.manager.model.offer.PersonalityItem;
import com.seven.manager.model.offer.PersonalityTotal;
import com.seven.manager.ui.activity.offer.OfferPersonalitySpaceActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 报价-个性项
 *
 * @author seven
 */
@SuppressLint("ValidFragment")
public class PersonalityFragment extends BaseFragment implements ListItemCallBack {

    //自动加载的recycler
    private AutoLoadRecyclerView mRecyclerView;

    //无数据提示图
    private LinearLayout mNullLayout;

    //添加按钮
    private RelativeLayout mAddPersonalityBtn;

    private List<OfferPersonalityModel> mDataList;

    private OfferPersonalityAdapter mAdapter;

    public PersonalityFragment() {
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_personality;
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

        mRecyclerView = getView(mRecyclerView, R.id.offer_personality_recycler_view);
        mNullLayout = getView(mNullLayout, R.id.offer_personality_null_ll);
        mAddPersonalityBtn = getView(mAddPersonalityBtn, R.id.offer_add_personality);

    }

    @Override
    public void initData() {

        mAddPersonalityBtn.setOnClickListener(this);

        mDataList = new ArrayList<>();

    }

    @Override
    public void onReceiveNotification(Task task) {
        super.onReceiveNotification(task);

        switch (task.getWhat()) {

            case RunTimeConfig.ActionWhatConfig.PERSONNALITY:

                OfferHouseModel houseModel = (OfferHouseModel) task.getParamObjects()[0];
                OfferPersonalityTermModel termModel = (OfferPersonalityTermModel) task.getParamObjects()[1];

                if (houseModel == null || termModel == null)
                    return;

                boolean isAdd = true;

                for (OfferPersonalityModel model : mDataList) {

                    if (model.getViewType() == RunTimeConfig.ModelConfig.OFFER_PERSONALITY_ITEM) {

                        if (((PersonalityItem) model).getSpaceId() == houseModel.getId() && ((PersonalityItem) model).getTermId() == termModel.getId()) {
                            ((PersonalityItem) model).setVolume(((PersonalityItem) model).getVolume() + termModel.getVolume());
                            double smallTotal = ((PersonalityItem) model).getSmallTotal() + (termModel.getOffer() * termModel.getVolume());
                            ((PersonalityItem) model).setSmallTotal(smallTotal);
                            isAdd = false;
                        }
                    }

                }

                if (mDataList.size() > 0 && mDataList.get(mDataList.size() - 1).getViewType() == RunTimeConfig.ModelConfig.OFFER_PERSONALITY_TOTAL)
                    mDataList.remove(mDataList.size() - 1);

                if (isAdd) {
                    PersonalityItem item = new PersonalityItem();

                    item.setSpaceId(houseModel.getId());
                    item.setSpace(houseModel.getName());
                    item.setTermId(termModel.getId());
                    item.setTerm(termModel.getName());
                    item.setVolume(termModel.getVolume());
                    item.setPrice(termModel.getOffer());
                    item.setUnit(termModel.getUnitName());

                    double smallTotal = termModel.getOffer() * termModel.getVolume();

                    item.setSmallTotal(smallTotal);

                    mDataList.add(item);
                }

                getData();

                break;

        }
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id) {

            //添加房间
            case R.id.offer_add_personality:

                OfferPersonalitySpaceActivity.start(false);

                break;


        }
    }

    private void getData() {

        if (mDataList.size() > 0) {

            PersonalityTotal personalityTotal = new PersonalityTotal();
            int count = 0;
            double total = 0;

            for (OfferPersonalityModel model : mDataList) {

                if (model.getViewType() == RunTimeConfig.ModelConfig.OFFER_PERSONALITY_ITEM) {
                    count++;
                    total += ((PersonalityItem) model).getSmallTotal();
                }

            }
            personalityTotal.setCount(count);
            personalityTotal.setTotal(total);

            mDataList.add(personalityTotal);
        }

        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();

        updateView();
    }

    public void updateView() {

        if (mDataList == null || mDataList.size() == 0) {
            if (mNullLayout != null)
                mNullLayout.setVisibility(View.VISIBLE);
            doAction(RunTimeConfig.ActionConfig.OFFER, Task.PUBLIC, Task.MIN_AUTHORITY,
                    RunTimeConfig.ActionWhatConfig.OFFER_ADD, View.GONE,
                    RunTimeConfig.OfferFGTagConfig.TAG_PERSONALITY, 0.00);
        } else {
            mNullLayout.setVisibility(View.GONE);
            doAction(RunTimeConfig.ActionConfig.OFFER, Task.PUBLIC, Task.MIN_AUTHORITY,
                    RunTimeConfig.ActionWhatConfig.OFFER_ADD, View.VISIBLE,
                    RunTimeConfig.OfferFGTagConfig.TAG_PERSONALITY, ((PersonalityTotal) mDataList.get(mDataList.size() - 1)).getTotal());
        }
    }

    private void setRecyclerView() {

        mRecyclerView.setHasFixedSize(false);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(LibApplication.getInstance()));
        mRecyclerView.setOnPauseListenerParams(false, true);

        mAdapter = new OfferPersonalityAdapter(LibApplication.getInstance(), mDataList, this);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onItemClick(View view, int position, Object... object) {

        int id = view.getId();

        switch (id) {

            case R.id.personality_item_remove:

                mDataList.remove(position);
                mDataList.remove(mDataList.size() - 1);
                getData();

                break;
        }
    }

    public List<OfferPersonalityModel> getPersonalityList() {

        return mDataList;

    }

}
