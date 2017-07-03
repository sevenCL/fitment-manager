package com.seven.manager.ui.activity.newoffer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seven.library.application.LibApplication;
import com.seven.library.base.BaseTitleActivity;
import com.seven.library.callback.DialogClickCallBack;
import com.seven.library.callback.HttpRequestCallBack;
import com.seven.library.callback.JsonCallBack;
import com.seven.library.callback.ListItemCallBack;
import com.seven.library.config.RunTimeConfig;
import com.seven.library.http.RequestUtils;
import com.seven.library.http.Urls;
import com.seven.library.json.JsonHelper;
import com.seven.library.task.ActivityStack;
import com.seven.library.utils.CheckUtils;
import com.seven.library.utils.LogUtils;
import com.seven.library.utils.ResourceUtils;
import com.seven.library.utils.ToastUtils;
import com.seven.library.view.AutoLoadRecyclerView;
import com.seven.manager.R;
import com.seven.manager.adapter.newoffer.OfferSpaceAdapter;
import com.seven.manager.db.DbOfferHouse;
import com.seven.manager.model.newoffer.HouseModel;
import com.seven.manager.model.newoffer.OfferHouseModel;
import com.seven.manager.model.newoffer.OfferRewardModel;
import com.seven.manager.model.newoffer.OfferSpaceModel;
import com.seven.manager.model.newoffer.SpaceItem;
import com.seven.manager.model.newoffer.SpaceItemList;
import com.seven.manager.model.newoffer.SpaceTitle;
import com.seven.manager.model.order.OrderModel;
import com.seven.manager.ui.dialog.CommonDialog;
import com.seven.manager.ui.dialog.offer.OfferHouseDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author seven
 *         <p>
 *         空间信息
 */

public class OfferSpaceActivity extends BaseTitleActivity implements HttpRequestCallBack, ListItemCallBack {

    //预约单信息
    private OrderModel model;

    //用户信息：地址、名字
    private LinearLayout mNullLayout;
    private TextView mAddress;
    private TextView mName;

    //数据库
    private DbManager db;
    private DbOfferHouse dbOfferHouse;

    //房间信息
    private List<HouseModel> mHouseList;
    private OfferHouseDialog mHouseDialog;

    //选择的空间信息
    private List<OfferSpaceModel> mDataList;
    private List<OfferSpaceModel> mTempList;
    private AutoLoadRecyclerView mRecyclerView;
    private OfferSpaceAdapter mAdapter;

    //空间整合信息
    private List<Map<String, Integer>> mSpaceList;
    private Map<String, Integer> mSpaceMap;
    private TextView mSpaceInfo;
    private TextView mSpaceArea;

    private OfferRewardModel rewardModel;

    private CommonDialog commonDialog;

    /**
     * 跳转方法
     *
     * @param isFinishSrcAct 是否关闭启动页面
     */
    public static void start(boolean isFinishSrcAct, Serializable serializable) {
        Bundle param = new Bundle();
        param.putBoolean(RunTimeConfig.IntentCodeConfig.FINISH, isFinishSrcAct);
        param.putSerializable(RunTimeConfig.IntentCodeConfig.SERIALIZABLE, serializable);
        ActivityStack.getInstance().startActivity(OfferSpaceActivity.class, isFinishSrcAct, param);
    }


    @Override
    public void onInitContentView(Bundle savedInstanceState) {

        initView();

        initData(null);

        getData();

        setRecyclerView();

    }

    @Override
    public int getContentLayoutId() {

        isLeftBtn = true;

        return R.layout.activity_offer_space;
    }

    @Override
    public void onLeftButtonClicked() {

        finish();

    }

    @Override
    public void onRightButtonClicked() {

        showHouseDialog();

    }

    @Override
    public void onRightTextClicked() {

    }

    @Override
    public void initView() {

        mNullLayout = getView(mNullLayout, R.id.space_null_ll);
        mAddress = getView(mAddress, R.id.space_address_tv);
        mName = getView(mName, R.id.space_name_tv);

        mRecyclerView = getView(mRecyclerView, R.id.space_recycler);

        mSpaceInfo = getView(mSpaceInfo, R.id.space_info_tv);
        mSpaceArea = getView(mSpaceArea, R.id.space_area_tv);

    }

    @Override
    public void initData(Intent intent) {

        setTitle(ResourceUtils.getInstance().getText(R.string.offer));

        setRightButtonBackground(R.drawable.tianjia);

        if (intent == null)
            intent = getIntent();

        model = (OrderModel) intent.getSerializableExtra(RunTimeConfig.IntentCodeConfig.SERIALIZABLE);

        if (model != null) {
            mAddress.setText(model.getHouse());
            mName.setText(model.getOwnerName());
        }

        mHouseList = new ArrayList<>();
        mDataList = new ArrayList<>();
        mTempList = new ArrayList<>();
        mSpaceList = new ArrayList<>();

        try {

            //数据库
            db = x.getDb(LibApplication.daoConfig);

            dbOfferHouse = db.selector(DbOfferHouse.class).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        getHouseInfo();

        getReward();
    }

    public void btClick(View view) {

        int id = view.getId();

        switch (id) {

            //添加房间
            case R.id.space_add_btn:

                if (dbOfferHouse == null || TextUtils.isEmpty(dbOfferHouse.getContent())) {
                    ToastUtils.getInstance().showToast(ResourceUtils.getInstance().getText(R.string.toast_data_failure));
                    return;
                }
                showHouseDialog();

                break;

            //下一步
            case R.id.space_next_btn:

                try {

                    if (rewardModel == null) {
                        ToastUtils.getInstance().showToast(R.string.toast_base_reward);
                        return;
                    }

                    if (mHouseList.size() == 0) {
                        ToastUtils.getInstance().showToast(R.string.toast_space_null);
                        return;
                    }
                    showNextDialog();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

        }

    }

    private void showNextDialog() {

        if (commonDialog == null || !commonDialog.isShowing()) {

            commonDialog = new CommonDialog(OfferSpaceActivity.this, R.style.Dialog,
                    RunTimeConfig.DialogTagConfig.TAG_NEXT, new DialogClickCallBack() {
                @Override
                public void onCancelClick(View view) {

                }

                @Override
                public void onSureClick(View view) {

                    OfferBaseActivity.start(false, model.getProjectId(), (Serializable) mDataList,
                            rewardModel, (Serializable) mSpaceList, mHouseList.get(mHouseList.size() - 1));

                }

                @Override
                public void onClick(View view, Object... object) {

                }
            });
            commonDialog.show();
        }

    }

    /**
     * 选择空间
     */
    private void showHouseDialog() {

        if (mHouseList.size() >= 0)
            mHouseList.clear();

        JsonHelper.getInstance().jsonArray(dbOfferHouse.getContent(),
                OfferHouseModel.class, true, new JsonCallBack() {
                    @Override
                    public void onSucceed(Object data) {

                        for (OfferHouseModel model : (List<OfferHouseModel>) data)
                            houseSpace(model);

                    }

                    @Override
                    public void onFailure(String error) {

                    }
                });

        if (mHouseList.size() == 0)
            return;

        if (mHouseDialog == null || !mHouseDialog.isShowing()) {


            mHouseDialog = new OfferHouseDialog(OfferSpaceActivity.this, R.style.Dialog, mHouseList, new DialogClickCallBack() {
                @Override
                public void onCancelClick(View view) {

                }

                @Override
                public void onSureClick(View view) {

                }

                @Override
                public void onClick(View view, Object... object) {

                    if (mDataList.size() == 0 && model != null) {

                        SpaceTitle spaceTitle = new SpaceTitle();
                        spaceTitle.setHouse(model.getHouse());
                        spaceTitle.setOwnerName(model.getOwnerName());

                        mDataList.add(spaceTitle);
                    }

                    for (HouseModel houseModel : (List<HouseModel>) object[0]) {

                        boolean isAdd = true;

                        for (OfferSpaceModel spaceModel : mDataList) {

                            isAdd = classify(houseModel, spaceModel);

                            if (!isAdd)
                                break;
                        }

                        if (isAdd)
                            addSpaceItem(houseModel);

                    }

                    getData();

                }
            });
            mHouseDialog.show();

        }

    }

    /**
     * 过滤得到房间
     *
     * @param model
     */
    private void houseSpace(OfferHouseModel model) {

        JsonHelper.getInstance().jsonArraySingle(model.getSpaceList(),
                HouseModel.class, true, new JsonCallBack() {
                    @Override
                    public void onSucceed(Object data) {

                        for (HouseModel model : (List<HouseModel>) data)
                            mHouseList.add(model);
                    }

                    @Override
                    public void onFailure(String error) {

                    }
                });

    }

    /**
     * 分空间添加
     */
    private boolean classify(HouseModel houseModel, OfferSpaceModel spaceModel) {

        SpaceItemList newSpaceItemList = null;

        if (spaceModel.getViewType() == RunTimeConfig.ModelConfig.SPACE_ITEM) {

            for (SpaceItemList spaceItemList : ((SpaceItem) spaceModel).getList()) {

                if (spaceItemList.getSpaceId() == houseModel.getId()) {

                    int tag = 0;

                    for (Map<String, Integer> map : mSpaceList) {

                        if (map.get("type") == houseModel.getType()) {
                            map.put("count", map.get("count") + 1);
                            tag = map.get("count") + 1;
                        }

                    }

                    newSpaceItemList = new SpaceItemList();
                    newSpaceItemList.setId(houseModel.getId());
                    newSpaceItemList.setType(houseModel.getType());
                    newSpaceItemList.setName(houseModel.getName());
                    newSpaceItemList.setSpaceId(houseModel.getId());
                    newSpaceItemList.setShowName(false);
                    newSpaceItemList.setTag(tag);

                    ((SpaceItem) spaceModel).getList().add(newSpaceItemList);

                    return false;
                }
            }

        }
        return true;
    }

    private void addSpaceItem(HouseModel houseModel) {

        SpaceItemList newSpaceItemList = null;

        SpaceItem newSpaceItem = new SpaceItem();

        newSpaceItemList = new SpaceItemList();
        newSpaceItemList.setId(houseModel.getId());
        newSpaceItemList.setType(houseModel.getType());
        newSpaceItemList.setName(houseModel.getName());
        newSpaceItemList.setSpaceId(houseModel.getId());
        newSpaceItemList.setShowName(true);
        newSpaceItemList.setTag(1);

        newSpaceItem.addList(newSpaceItemList);

        mTempList.add(newSpaceItem);

        boolean isAdd = true;

        for (Map<String, Integer> map : mSpaceList) {

            if (map.get("type") == houseModel.getType()) {
                map.put("count", map.get("count") + 1);
                isAdd = false;
            }

        }

        if (isAdd) {
            mSpaceMap = new HashMap<>();
            mSpaceMap.put("type", houseModel.getType());
            mSpaceMap.put("count", 1);
            mSpaceList.add(mSpaceMap);
        }
    }

    private void getData() {

        for (OfferSpaceModel spaceModel : mTempList)
            mDataList.add(spaceModel);

        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();

        updateView();

        spaceInfo();

        totalArea();

        if (mTempList.size() > 0)
            mTempList.clear();

    }

    public void updateView() {

        if (mDataList == null || mDataList.size() == 0) {
            if (mNullLayout != null)
                mNullLayout.setVisibility(View.VISIBLE);
            setRightButtonVisible(View.GONE);
        } else {
            mNullLayout.setVisibility(View.GONE);
            setRightButtonVisible(View.VISIBLE);
        }
    }

    private void spaceInfo() {

        StringBuffer buffer = new StringBuffer();

        for (Map<String, Integer> map : mSpaceList) {
            buffer.append(map.get("count") + ResourceUtils.getInstance().houseType(map.get("type")));
        }

        mSpaceInfo.setText(TextUtils.isEmpty(buffer.toString()) ?
                ResourceUtils.getInstance().getText(R.string.offer_space_default_value) : buffer.toString());

    }

    private void totalArea() {

        double totalArea = 0.0;

        for (OfferSpaceModel spaceModel : mDataList) {

            if (spaceModel.getViewType() == RunTimeConfig.ModelConfig.SPACE_ITEM) {

                for (SpaceItemList itemList : ((SpaceItem) spaceModel).getList()) {

                    totalArea += itemList.getArea();
                }
            }

        }

        mSpaceArea.setText(totalArea == 0 ? ResourceUtils.getInstance().getText(R.string.offer_space_default_value)
                : String.valueOf(CheckUtils.getInstance().format(totalArea) + "m²"));

    }

    private void setRecyclerView() {

        mRecyclerView.setHasFixedSize(false);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(LibApplication.getInstance()));
        mRecyclerView.setOnPauseListenerParams(false, true);

        mAdapter = new OfferSpaceAdapter(LibApplication.getInstance(), mDataList, this);
        mRecyclerView.setAdapter(mAdapter);

    }

    /**
     * 获取空间信息
     */
    private void getHouseInfo() {

        // TODO: 2017/6/8 获取所需的空间信息

        RequestUtils.getInstance(Urls.OFFER_HOUSE).offerHouse(
                RunTimeConfig.RequestConfig.OFFER_HOUSE, 1, 1, 1000, this);

    }


    private void getReward() {

        RequestUtils.getInstance(Urls.OFFER_REWARD).getReward(
                RunTimeConfig.RequestConfig.OFFER_REWARD, "BJ-001", 0, this);

    }

    @Override
    public void onSucceed(String result, int requestId) {

        switch (requestId) {
            //获取套餐房间
            case RunTimeConfig.RequestConfig.OFFER_HOUSE:

                try {

                    LogUtils.println(this.getClass().getName() + " onSucceed OFFER_HOUSE request " + result);

                    if (TextUtils.isEmpty(new JSONObject(result).getJSONArray("data").toString()))
                        return;

                    dbOfferHouse = db.selector(DbOfferHouse.class).findFirst();

                    if (dbOfferHouse == null) {//第一次创建表并添加数据

                        if (TextUtils.isEmpty(result))
                            return;

                        dbOfferHouse = new DbOfferHouse();
                        dbOfferHouse.setId(1);
                        dbOfferHouse.setProjectId(1);
//                        dbOderList.setHashCode(JsonHelper.getInstance().jsonStringHash(result));
                        dbOfferHouse.setParams(Urls.OFFER_HOUSE);
                        dbOfferHouse.setContent(result);
                        dbOfferHouse.setCreateTime(System.currentTimeMillis());

                        db.save(dbOfferHouse);

                    } else {//更新

//                        if (new JSONObject(result).getJSONObject("data").getInt("hashCode") == dbOderList.getHashCode())
//                            return;

//                        dbOderList.setHashCode(JsonHelper.getInstance().jsonStringHash(result));
                        dbOfferHouse.setContent(result);
                        dbOfferHouse.setCreateTime(System.currentTimeMillis());

                        db.saveOrUpdate(dbOfferHouse);
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
            case RunTimeConfig.RequestConfig.OFFER_REWARD:

                LogUtils.println(this.getClass().getName() + " request OFFER_REWARD onSucceed " + result);

                JsonHelper.getInstance().jsonObject(result,
                        OfferRewardModel.class, true, null, new JsonCallBack() {
                            @Override
                            public void onSucceed(Object data) {

                                rewardModel = (OfferRewardModel) data;

                            }

                            @Override
                            public void onFailure(String error) {

                            }
                        });

                break;
        }

    }

    @Override
    public void onFailure(String error, int requestId) {

        switch (requestId) {

            //获取套餐房间
            case RunTimeConfig.RequestConfig.OFFER_HOUSE:

                LogUtils.println(this.getClass().getName() + " onFailure OFFER_HOUSE request " + error);

                break;

            case RunTimeConfig.RequestConfig.OFFER_REWARD:

                LogUtils.println(this.getClass().getName() + " request OFFER_REWARD onFailure " + error);

                break;

        }

    }

    @Override
    public void onProgress(long progress, int requestId) {

    }

    @Override
    public void onItemClick(View view, int position, Object... object) {

        int id = view.getId();

        switch (id) {

            case R.id.space_item_area_et:

                totalArea();

                break;

            case R.id.space_item_remove:

                int parentPosition = (int) object[0];

                List<SpaceItemList> list = ((SpaceItem) (mDataList.get(parentPosition))).getList();

                Map<String, Integer> tempMap = null;

                for (Map<String, Integer> map : mSpaceList) {
                    if (list.get(position).getType() == map.get("type")) {

                        if (map.get("count") > 1)
                            map.put("count", map.get("count") - 1);
                        else
                            tempMap = map;
                    }
                }

                if (tempMap != null)
                    mSpaceList.remove(tempMap);

                list.remove(position);

                if (list.size() > 0) {
                    if (!list.get(0).isShowName())
                        list.get(0).setShowName(true);
                } else {
                    mDataList.remove(parentPosition);
                }

                mAdapter.notifyDataSetChanged();

                if (mDataList.size() == 1)
                    mDataList.clear();

                updateView();

                spaceInfo();

                totalArea();

                break;
        }
    }
}
