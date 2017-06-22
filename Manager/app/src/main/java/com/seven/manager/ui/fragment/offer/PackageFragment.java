package com.seven.manager.ui.fragment.offer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.frankchen.mvc.aidl.Task;
import com.seven.library.application.LibApplication;
import com.seven.library.base.BaseFragment;
import com.seven.library.callback.DialogClickCallBack;
import com.seven.library.callback.JsonCallBack;
import com.seven.library.callback.ListItemCallBack;
import com.seven.library.config.RunTimeConfig;
import com.seven.library.json.JsonHelper;
import com.seven.library.task.ActivityStack;
import com.seven.library.view.AutoLoadRecyclerView;
import com.seven.manager.R;
import com.seven.manager.adapter.offer.OfferPackageAdapter;
import com.seven.manager.db.DbOfferHouse;
import com.seven.manager.db.DbOfferPackage;
import com.seven.manager.model.offer.OfferHouseModel;
import com.seven.manager.model.offer.PackageItem;
import com.seven.manager.model.offer.OfferPackageModel;
import com.seven.manager.model.offer.PackageTotal;
import com.seven.manager.ui.activity.offer.OfferActivity;
import com.seven.manager.ui.dialog.offer.OfferHouseDialog;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报价-套餐
 *
 * @author seven
 */
@SuppressLint("ValidFragment")
public class PackageFragment extends BaseFragment implements ListItemCallBack {

    //自动加载的recycler
    private AutoLoadRecyclerView mRecyclerView;

    //无数据提示图
    private LinearLayout mNullLayout;

    //添加按钮
    private RelativeLayout mAddHouseBtn;

    private List<OfferPackageModel> mDataList;

    private OfferPackageAdapter mAdapter;

    //房间数据
    private DbManager db;
    private DbOfferHouse dbOfferHouse;
    private DbOfferPackage dbOfferPackage;

    private List<OfferHouseModel> mHouseList;

    private OfferHouseDialog mHouseDialog;

    public PackageFragment() {
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_package;
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

        mRecyclerView = getView(mRecyclerView, R.id.offer_package_recycler_view);
        mNullLayout = getView(mNullLayout, R.id.offer_package_null_ll);
        mAddHouseBtn = getView(mAddHouseBtn, R.id.offer_add_house);

    }

    @Override
    public void initData() {

        mAddHouseBtn.setOnClickListener(this);

        mDataList = new ArrayList<>();
        mHouseList = new ArrayList<>();

        //数据库

        db = x.getDb(LibApplication.daoConfig);

        try {
            dbOfferHouse = db.selector(DbOfferHouse.class).findFirst();
            dbOfferPackage = db.selector(DbOfferPackage.class).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id) {

            //添加房间
            case R.id.offer_add_house:

                showHouseDialog();

                break;

        }
    }

    /**
     * 添加房间
     */
    public void showHouseDialog() {

        if (mHouseList.size() >= 0)
            mHouseList.clear();

        if (dbOfferHouse == null || TextUtils.isEmpty(dbOfferHouse.getContent()))
            return;

        JsonHelper.getInstance().jsonArray(dbOfferHouse.getContent(),
                OfferHouseModel.class, true, new JsonCallBack() {
                    @Override
                    public void onSucceed(Object data) {

                        for (OfferHouseModel model : (List<OfferHouseModel>) data)
                            mHouseList.add(model);

                    }

                    @Override
                    public void onFailure(String error) {

                    }
                });

        if (mHouseList.size() == 0)
            return;

        if (mHouseDialog == null || !mHouseDialog.isShowing()) {

            Activity activity = ActivityStack.getInstance().findActivityByClass(OfferActivity.class);

            if (activity == null)
                return;

//            mHouseDialog = new OfferHouseDialog(activity, R.style.Dialog, mHouseList, new DialogClickCallBack() {
//                @Override
//                public void onCancelClick(View view) {
//
//                }
//
//                @Override
//                public void onSureClick(View view) {
//
//                }
//
//                @Override
//                public void onClick(View view, Object... object) {
//
//                    if (mDataList.size() > 0 && mDataList.get(mDataList.size() - 1).getViewType() == RunTimeConfig.ModelConfig.OFFER_PACKAGE_TOTAL)
//                        mDataList.remove(mDataList.size() - 1);
//
//                    PackageItem packageItem = null;
//
//                    for (OfferHouseModel model : (List<OfferHouseModel>) object[0]) {
//                        packageItem = new PackageItem();
//                        packageItem.setId(model.getId());
//                        packageItem.setType(model.getType());
//                        packageItem.setName(model.getName());
//                        packageItem.setSpaceId(model.getId());
//                        mDataList.add(packageItem);
//                    }
//                    getData();
//                }
//            });
            mHouseDialog.show();
        }
    }

    private void getData() {

        if (mDataList.size() > 0) {

            if (dbOfferPackage == null || TextUtils.isEmpty(dbOfferPackage.getContent()))
                return;

            JsonHelper.getInstance().jsonObject(dbOfferPackage.getContent(),
                    PackageTotal.class, true, null, new JsonCallBack() {
                        @Override
                        public void onSucceed(Object data) {

                            PackageTotal total = (PackageTotal) data;

                            if (total == null)
                                return;

                            mDataList.add(updateTotal(total));

                        }

                        @Override
                        public void onFailure(String error) {

                        }
                    });
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
                    RunTimeConfig.ActionWhatConfig.OFFER_ADD, View.GONE, RunTimeConfig.OfferFGTagConfig.TAG_PACKAGE, 0.00);
        } else {
            mNullLayout.setVisibility(View.GONE);
            doAction(RunTimeConfig.ActionConfig.OFFER, Task.PUBLIC, Task.MIN_AUTHORITY,
                    RunTimeConfig.ActionWhatConfig.OFFER_ADD, View.VISIBLE,
                    RunTimeConfig.OfferFGTagConfig.TAG_PACKAGE, ((PackageTotal) mDataList.get(mDataList.size() - 1)).getTotalMoney());
        }

    }

    private PackageTotal updateTotal(PackageTotal total) {

        List<Map<String, Object>> spaceList = new ArrayList<>();
        Map<String, Object> spaceMap = null;

        for (OfferPackageModel model : mDataList) {

            boolean isAdd = true;

            if (model.getViewType() == RunTimeConfig.ModelConfig.OFFER_PACKAGE_ITEM) {

                for (Map<String, Object> map : spaceList) {
                    if (map.get("name").equals(((PackageItem) model).getName())) {
                        map.put("size", (int) map.get("size") + 1);
                        isAdd = false;
                    }
                }
                if (isAdd) {
                    spaceMap = new HashMap<>();
                    spaceMap.put("name", ((PackageItem) model).getName());
                    spaceMap.put("size", 1);
                    spaceList.add(spaceMap);
                }
            }
        }

        StringBuffer buffer = new StringBuffer();

        for (Map<String, Object> map : spaceList) {

            if (TextUtils.isEmpty(buffer))
                buffer.append(String.valueOf(map.get("size")) + map.get("name"));
            else
                buffer.append("," + map.get("size") + map.get("name"));
        }

        total.setHouseInfo(buffer.toString());

        double totalArea = 0;
        double totalMoney = 0;

        for (OfferPackageModel model : mDataList) {
            if (model.getViewType() == RunTimeConfig.ModelConfig.OFFER_PACKAGE_ITEM)
                totalArea += ((PackageItem) model).getArea();
        }


        if (totalArea < total.getMinArea())
            totalMoney = total.getMinArea() * total.getPrice();
        else
            totalMoney = totalArea * total.getPrice();

        total.setTotalArea(totalArea);
        total.setTotalMoney(totalMoney);

        return total;
    }

    private void setRecyclerView() {

        mRecyclerView.setHasFixedSize(false);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(LibApplication.getInstance()));
        mRecyclerView.setOnPauseListenerParams(false, true);

        mAdapter = new OfferPackageAdapter(LibApplication.getInstance(), mDataList, this);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onItemClick(View view, int position, Object... object) {

        int id = view.getId();

        switch (id) {

            case R.id.package_item_remove:

                mDataList.remove(position);

                updateTotal((PackageTotal) mDataList.get(mDataList.size() - 1));

                mAdapter.notifyDataSetChanged();

                if (mDataList.size() == 1 && mDataList.get(0).getViewType() == RunTimeConfig.ModelConfig.OFFER_PACKAGE_TOTAL) {
                    mDataList.clear();
                    mNullLayout.setVisibility(View.VISIBLE);
                    doAction(RunTimeConfig.ActionConfig.OFFER, Task.PUBLIC, Task.MIN_AUTHORITY,
                            RunTimeConfig.ActionWhatConfig.OFFER_ADD, View.GONE, RunTimeConfig.OfferFGTagConfig.TAG_PACKAGE,0.00);
                }

                break;

            case R.id.package_total_area:

                doAction(RunTimeConfig.ActionConfig.OFFER, Task.PUBLIC, Task.MIN_AUTHORITY,
                        RunTimeConfig.ActionWhatConfig.OFFER_ADD, View.VISIBLE,
                        RunTimeConfig.OfferFGTagConfig.TAG_PACKAGE, ((PackageTotal) mDataList.get(mDataList.size() - 1)).getTotalMoney());

                break;

        }
    }

    public List<OfferPackageModel> getPackageList() {

        return mDataList;

    }

}
