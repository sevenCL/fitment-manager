package com.seven.manager.ui.activity.newoffer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frankchen.mvc.aidl.Task;
import com.seven.library.application.LibApplication;
import com.seven.library.base.BaseActivity;
import com.seven.library.callback.DialogClickCallBack;
import com.seven.library.callback.HttpRequestCallBack;
import com.seven.library.callback.JsonCallBack;
import com.seven.library.callback.ListItemCallBack;
import com.seven.library.config.RunTimeConfig;
import com.seven.library.db.res.ResPersonality;
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
import com.seven.manager.adapter.newoffer.BaseClassAdapter;
import com.seven.manager.adapter.newoffer.BaseSearchAdapter;
import com.seven.manager.adapter.newoffer.BaseTermAdapter;
import com.seven.manager.adapter.newoffer.OfferBaseAdapter;
import com.seven.manager.model.newoffer.BaseChild;
import com.seven.manager.model.newoffer.BaseControl;
import com.seven.manager.model.newoffer.BaseItem;
import com.seven.manager.model.newoffer.BaseParent;
import com.seven.manager.model.newoffer.BaseTerm;
import com.seven.manager.model.newoffer.BaseTitle;
import com.seven.manager.model.newoffer.HouseModel;
import com.seven.manager.model.newoffer.OfferBaseModel;
import com.seven.manager.model.newoffer.OfferRewardModel;
import com.seven.manager.model.newoffer.OfferSpaceModel;
import com.seven.manager.model.newoffer.SpaceItem;
import com.seven.manager.model.newoffer.SpaceItemList;
import com.seven.manager.ui.dialog.CommonDialog;
import com.seven.manager.ui.dialog.newoffer.ShoppingCartDialog;
import com.seven.manager.ui.dialog.newoffer.SubmitOfferDialog;
import com.seven.manager.ui.dialog.newoffer.TermVolumeDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author seven
 *         <p>
 *         基础项
 */

public class OfferBaseActivity extends BaseActivity implements ListItemCallBack, HttpRequestCallBack {

    private long projectId;
    private List<OfferSpaceModel> mHouseList;
    private OfferRewardModel reward;
    private List<Map<String, Integer>> mSpaceList;
    private HouseModel houseModel;

    private TextView mTitleTv;

    private double totalArea;
    private double baseTotal;

    private TextView mOfferTv;
    private TextView mAreaTv;
    private TextView mRewardTv;
    private TextView mAmountTv;

    private DbManager db;
    private ResPersonality resPersonality;

    private List<BaseTerm> mAllList;
    private List<BaseParent> mParentList;

    private List<OfferBaseModel> mDataList;
    private AutoLoadRecyclerView mRecyclerView;
    private OfferBaseAdapter mAdapter;

    private TermVolumeDialog baseItemDialog;

    //--------------------
    private DrawerLayout mDrawer;
    private TextView mDrawerTitle;

    private EditText mSearchEt;
    private ImageView mDelete;

    private LinearLayout mTermLayout;

    private AutoLoadRecyclerView mClassRecycler;
    private List<BaseChild> mChildAllList;
    private List<BaseChild> mChildList;
    private BaseClassAdapter mClassAdapter;

    private AutoLoadRecyclerView mTermRecycler;
    private List<BaseTerm> mTermList;
    private BaseTermAdapter mTermAdapter;

    private RelativeLayout mTermSearchLayout;
    private AutoLoadRecyclerView mSearchRecycler;
    private List<BaseTerm> mSearchList;
    private BaseSearchAdapter mSearchAdapter;

    private TermVolumeDialog volumeDialog;

    private TextView mTermCountTv;
    private List<BaseItem> mTermCountList;

    private ShoppingCartDialog shoppingCartDialog;

    private long tempAddId;

    private boolean isBack;

    private SubmitOfferDialog submitOfferDialog;

    private CommonDialog commonDialog;

    /**
     * 跳转方法
     *
     * @param isFinishSrcAct 是否关闭启动页面
     */
    public static void start(boolean isFinishSrcAct, long projectId, Serializable serializable,
                             Serializable reward, Serializable spaceList, Serializable houseModel) {
        Bundle param = new Bundle();
        param.putBoolean(RunTimeConfig.IntentCodeConfig.FINISH, isFinishSrcAct);
        param.putLong(RunTimeConfig.IntentCodeConfig.PROJECTID, projectId);
        param.putSerializable(RunTimeConfig.IntentCodeConfig.SERIALIZABLE, serializable);
        param.putSerializable(RunTimeConfig.IntentCodeConfig.REWARD, reward);
        param.putSerializable(RunTimeConfig.IntentCodeConfig.SPACE_LIST, spaceList);
        param.putSerializable(RunTimeConfig.IntentCodeConfig.HOUSE_MODEL, houseModel);
        ActivityStack.getInstance().startActivity(OfferBaseActivity.class, isFinishSrcAct, param);
    }

    @Override
    public void onInitRootView(Bundle savedInstanceState) {

        initView();

        initData(null);

        listener();

        getData();

        setRecyclerView();

        setSearchRecycler();
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_offer_base;
    }

    @Override
    public void initView() {

        mTitleTv = getView(mTitleTv, R.id.base_title_tv);

        mRecyclerView = getView(mRecyclerView, R.id.base_recycler);

        mOfferTv = getView(mOfferTv, R.id.base_offer_tv);
        mAreaTv = getView(mAreaTv, R.id.base_area_tv);
        mRewardTv = getView(mRewardTv, R.id.base_reward_tv);
        mAmountTv = getView(mAmountTv, R.id.base_amount_tv);

        //----------------------------------------------------

        mDrawer = getView(mDrawer, R.id.base_drawer_layout);
        mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        mDrawerTitle = getView(mDrawerTitle, R.id.drawer_title_tv);

        mSearchEt = getView(mSearchEt, R.id.drawer_search_et);
        mDelete = getView(mDelete, R.id.drawer_delete_iv);

        mTermLayout = getView(mTermLayout, R.id.drawer_term_ll);
        mClassRecycler = getView(mClassRecycler, R.id.drawer_class_recycler);
        mTermRecycler = getView(mTermRecycler, R.id.drawer_term_recycler);

        mTermSearchLayout = getView(mTermSearchLayout, R.id.drawer_search_term_rl);
        mSearchRecycler = getView(mSearchRecycler, R.id.drawer_search_recycler);

        mTermCountTv = getView(mTermCountTv, R.id.drawer_count_tv);
    }

    @Override
    public void initData(Intent intent) {

        mTitleTv.setText(ResourceUtils.getInstance().getText(R.string.offer));
        mDrawerTitle.setText(ResourceUtils.getInstance().getText(R.string.offer_add_term));

        if (intent == null)
            intent = getIntent();

        mHouseList = (List<OfferSpaceModel>) intent.getSerializableExtra(RunTimeConfig.IntentCodeConfig.SERIALIZABLE);
        reward = (OfferRewardModel) intent.getSerializableExtra(RunTimeConfig.IntentCodeConfig.REWARD);
        projectId = intent.getLongExtra(RunTimeConfig.IntentCodeConfig.PROJECTID, 0);
        mSpaceList = (List<Map<String, Integer>>) intent.getSerializableExtra(RunTimeConfig.IntentCodeConfig.SPACE_LIST);
        houseModel = (HouseModel) intent.getSerializableExtra(RunTimeConfig.IntentCodeConfig.HOUSE_MODEL);

        if (mHouseList == null || mHouseList.size() == 0 ||
                reward == null || projectId == 0 ||
                mSpaceList == null || mSpaceList.size() == 0 || houseModel == null)
            return;

        for (OfferSpaceModel spaceModel : mHouseList) {

            if (spaceModel.getViewType() == RunTimeConfig.ModelConfig.SPACE_ITEM) {

                for (SpaceItemList itemList : ((SpaceItem) spaceModel).getList())
                    totalArea += itemList.getArea();

            }

        }

        mAllList = new ArrayList<>();
        mParentList = new ArrayList<>();
        mChildAllList = new ArrayList<>();
        mChildList = new ArrayList<>();
        mTermList = new ArrayList<>();
        mSearchList = new ArrayList<>();
        mTermCountList = new ArrayList<>();

        mDataList = new ArrayList<>();

        //数据库
        db = x.getDb(LibApplication.daoConfig);

        try {
            resPersonality = db.selector(ResPersonality.class).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }

        getParentData();

    }

    public void btClick(View view) {

        int id = view.getId();

        switch (id) {

            //返回
            case R.id.base_left_rl:

                showBackDialog();

                break;

            //关闭侧边栏
            case R.id.drawer_left_rl:

                if (!isBack) {

                    if (mTermCountList.size() > 0) {
                        isBack = true;
                        showShoppingCart();
                    } else {
                        mDrawer.closeDrawer(Gravity.END);
                        mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

                        mDelete.setVisibility(View.GONE);
                        mTermLayout.setVisibility(View.VISIBLE);
                        mTermSearchLayout.setVisibility(View.GONE);
                        mSearchEt.setText("");

                        isBack = false;
                    }
                } else {
                    mDrawer.closeDrawer(Gravity.END);
                    mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

                    mDelete.setVisibility(View.GONE);
                    mTermLayout.setVisibility(View.VISIBLE);
                    mTermSearchLayout.setVisibility(View.GONE);
                    mSearchEt.setText("");

                    isBack = false;
                }
                break;
            //购物车
            case R.id.drawer_right_rl:

                showShoppingCart();

                break;
            //删除搜索索引
            case R.id.drawer_delete_iv:

                mSearchEt.setText("");

                break;

            //提交报价
            case R.id.base_submit_btn:

                submit();

                break;
        }
    }

    private void listener() {

        mSearchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mDelete.setVisibility(s.toString().length() > 0 ? View.VISIBLE : View.GONE);
                mTermLayout.setVisibility(s.toString().length() > 0 ? View.GONE : View.VISIBLE);
                mTermSearchLayout.setVisibility(s.toString().length() > 0 ? View.VISIBLE : View.GONE);
                getSearch(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void getParentData() {

        if (resPersonality == null || TextUtils.isEmpty(resPersonality.getContent()))
            return;

        JsonHelper.getInstance().jsonArray(resPersonality.getContent(),
                BaseParent.class, true, new JsonCallBack() {
                    @Override
                    public void onSucceed(Object data) {

                        if (data == null)
                            return;

                        for (BaseParent parent : (List<BaseParent>) data) {
                            mParentList.add(parent);
                            getChildData(parent);
                        }

                    }

                    @Override
                    public void onFailure(String error) {

                    }
                });
    }

    private void getChildData(final BaseParent parent) {

        JsonHelper.getInstance().jsonArraySingle(parent.getItemCategoryList(),
                BaseChild.class, true, new JsonCallBack() {
                    @Override
                    public void onSucceed(Object data) {

                        if (data == null)
                            return;

                        for (BaseChild child : (List<BaseChild>) data) {
                            child.setPname(parent.getName());
                            getTermData(child);
                            mChildAllList.add(child);
                        }

                    }

                    @Override
                    public void onFailure(String error) {

                    }
                });

    }

    private void getTermData(final BaseChild child) {

        JsonHelper.getInstance().jsonArraySingle(child.getContractItemsList(),
                BaseTerm.class, true, new JsonCallBack() {
                    @Override
                    public void onSucceed(Object data) {

                        if (data == null)
                            return;

                        for (BaseTerm term : (List<BaseTerm>) data) {

                            term.setBaseItemPid(child.getPid());
                            term.setBaseItemPidName(child.getPname());
                            term.setBaseItemId(child.getId());
                            term.setBaseItemName(child.getName());

                            if (!term.isOverallItem()) {
                                for (OfferSpaceModel spaceModel : mHouseList) {

                                    if (spaceModel.getViewType() == RunTimeConfig.ModelConfig.SPACE_ITEM) {

                                        for (SpaceItemList itemList : ((SpaceItem) spaceModel).getList()) {
                                            term.addList(itemList);
                                        }

                                    }

                                }
                            }
                            mAllList.add(term);
                        }
                    }

                    @Override
                    public void onFailure(String error) {

                    }
                });

    }

    private void getData() {

        BaseTitle baseTitle;
        BaseItem baseItem;
        BaseControl baseControl;

        for (BaseParent baseParent : mParentList) {

            baseTitle = new BaseTitle();
            baseTitle.setId(baseParent.getId());
            baseTitle.setName(baseParent.getName());

            mDataList.add(baseTitle);

            for (BaseTerm baseTerm : mAllList) {

                if (baseParent.getId() == baseTerm.getBaseItemPid() && baseTerm.isDefaultItem()) {

                    baseItem = new BaseItem();
                    baseItem.setId(baseTerm.getId());
                    baseItem.setName(baseTerm.getName());
                    baseItem.setBaseItemPid(baseTerm.getBaseItemPid());
                    baseItem.setBaseItemPidName(baseTerm.getBaseItemPidName());
                    baseItem.setBaseItemId(baseTerm.getBaseItemId());
                    baseItem.setBaseItemName(baseTerm.getBaseItemName());
                    baseItem.setCategoryId(baseTerm.getCategoryId());
                    baseItem.setDefaultItem(baseTerm.isDefaultItem());
                    baseItem.setDescription(baseTerm.getDescription());
                    baseItem.setFormula(baseTerm.getFormula());
                    baseItem.setNumber(baseTerm.getNumber());
                    baseItem.setOffer(baseTerm.getOffer());
                    baseItem.setOverallItem(baseTerm.isOverallItem());
                    baseItem.setStatus(baseTerm.getStatus());
                    baseItem.setUnitName(baseTerm.getUnitName());
                    baseItem.setUnitId(baseTerm.getUnitId());
                    baseItem.setWorkType(baseTerm.getWorkType());

                    baseItem.setVolume(CheckUtils.getInstance().formula(totalArea, baseTerm.getFormula()));
                    baseItem.setTotal(baseItem.getVolume() * baseTerm.getOffer());

                    //是否是全屋
                    if (!baseTerm.isOverallItem()) {
                        addSpace(baseItem);
                        baseTitle.setTotal(baseTitle.getTotal() + baseItem.getTotal());
                        baseTotal += baseItem.getTotal();
                    } else {
                        mDataList.add(baseItem);
                        baseTitle.setTotal(baseTitle.getTotal() + baseItem.getTotal());
                        baseTotal += baseItem.getTotal();
                    }

                }
            }

            baseControl = new BaseControl();
            mDataList.add(baseControl);
        }

        statistics();
    }

    private void addSpace(BaseItem baseItem) {

        for (OfferSpaceModel spaceModel : mHouseList) {

            if (spaceModel.getViewType() == RunTimeConfig.ModelConfig.SPACE_ITEM) {

                for (SpaceItemList itemList : ((SpaceItem) spaceModel).getList()) {
                    baseItem.addList(itemList);
                }

            }

        }

        mDataList.add(baseItem);
    }

    private void statistics() {

        mOfferTv.setText(new DecimalFormat("#0.00").format(baseTotal));
        mAreaTv.setText(new DecimalFormat("#0.00").format(totalArea) + "m²");
        mRewardTv.setText(new DecimalFormat("#0.00").format(baseTotal * reward.getValue() / 100));
        mAmountTv.setText(new DecimalFormat("#0.00").format(baseTotal + baseTotal * reward.getValue() / 100));
    }

    private void setRecyclerView() {

        mRecyclerView.setHasFixedSize(false);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(LibApplication.getInstance()));
        mRecyclerView.setOnPauseListenerParams(false, true);

        mAdapter = new OfferBaseAdapter(LibApplication.getInstance(), mDataList, this);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onItemClick(View view, int position, Object... object) {

        int id = view.getId();

        switch (id) {

            //展开
            case R.id.base_open_btn:

                for (int i = position; i >= 0; i--) {

                    if (mDataList.get(i).getViewTpye() == RunTimeConfig.ModelConfig.BASE_ITEM)
                        ((BaseItem) mDataList.get(i)).setShow(!((BaseItem) mDataList.get(i)).isShow());
                    else if (mDataList.get(i).getViewTpye() == RunTimeConfig.ModelConfig.BASE_TITLE)
                        break;

                }

                ((BaseControl) mDataList.get(position)).setShow(!((BaseControl) mDataList.get(position)).isShow());

                mAdapter.notifyDataSetChanged();

                break;

            //添加
            case R.id.base_add_btn:

                mDrawer.openDrawer(Gravity.END);
                mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);

                mTermCountList.clear();
                mTermCountTv.setText(String.valueOf(mTermCountList.size()));

                tempAddId = ((BaseTitle) mDataList.get(position)).getId();

                setClassRecycler(tempAddId);

                if (mChildList.size() > 0)
                    getTerm(mChildList.get(0).getId());

                setTermRecycler();

                break;

            //详情
            case R.id.base_term_rl:

                showBaseItemDialog((BaseItem) mDataList.get(position), position);

                break;

            //删除
            case R.id.base_remove_btn:

                long removePidId = ((BaseItem) mDataList.get(position)).getBaseItemPid();

                mDataList.remove(position);

                if (mAdapter != null)
                    mAdapter.notifyDataSetChanged();

                updateTitleTotal(removePidId);

                break;

            //选择分类
            case R.id.base_class_btn:

                if (mChildList.get(position).isSelect())
                    return;

                for (BaseChild child : mChildList)
                    if (child.isSelect())
                        child.setSelect(false);

                mChildList.get(position).setSelect(true);
                if (mClassAdapter != null)
                    mClassAdapter.notifyDataSetChanged();

                getTerm(mChildList.get(position).getId());

                if (mTermAdapter != null)
                    mTermAdapter.notifyDataSetChanged();

                break;
            //添加基础项
            case R.id.base_term_btn:

                showTermDialog(mTermList.get(position));

                break;
            case R.id.base_search_btn:

                showTermDialog(mSearchList.get(position));

                break;
        }
    }

    //======================================================================

    private void setClassRecycler(long pid) {

        if (mChildList.size() > 0)
            mChildList.clear();

        for (BaseChild child : mChildAllList) {
            if (child.getPid() == pid) {
                child.setSelect(false);
                mChildList.add(child);
            }
        }

        if (mChildList.size() > 0) {
            mChildList.get(0).setSelect(true);
        }

        mClassRecycler.setHasFixedSize(false);

        mClassRecycler.setLayoutManager(new LinearLayoutManager(LibApplication.getInstance()));
        mClassRecycler.setOnPauseListenerParams(false, true);

        mClassAdapter = new BaseClassAdapter(LibApplication.getInstance(), mChildList, this);
        mClassRecycler.setAdapter(mClassAdapter);

    }

    private void getTerm(long childId) {

        if (mTermList.size() > 0)
            mTermList.clear();

        for (BaseTerm term : mAllList) {

            if (term.getBaseItemId() == childId && !term.isDefaultItem())
                mTermList.add(term);
        }

    }

    private void setTermRecycler() {

        mTermRecycler.setHasFixedSize(false);

        mTermRecycler.setLayoutManager(new LinearLayoutManager(LibApplication.getInstance()));
        mTermRecycler.setOnPauseListenerParams(false, true);

        mTermAdapter = new BaseTermAdapter(LibApplication.getInstance(), mTermList, this);
        mTermRecycler.setAdapter(mTermAdapter);
    }

    private void getSearch(String search) {

        if (mSearchList.size() > 0)
            mSearchList.clear();

        for (BaseTerm term : mAllList) {

            if (term.getName().contains(search) && !term.isDefaultItem() && term.getBaseItemPid() == tempAddId) {
                term.setWord(search);
                mSearchList.add(term);
            }
        }

        if (mSearchAdapter != null)
            mSearchAdapter.notifyDataSetChanged();
    }

    private void setSearchRecycler() {

        mSearchRecycler.setHasFixedSize(false);

        mSearchRecycler.setLayoutManager(new LinearLayoutManager(LibApplication.getInstance()));
        mSearchRecycler.setOnPauseListenerParams(false, true);

        mSearchAdapter = new BaseSearchAdapter(LibApplication.getInstance(), mSearchList, this);
        mSearchRecycler.setAdapter(mSearchAdapter);

    }

    private void showTermDialog(final BaseTerm term) {

        if (volumeDialog == null || !volumeDialog.isShowing()) {

            volumeDialog = new TermVolumeDialog(OfferBaseActivity.this,
                    R.style.Dialog, term, new DialogClickCallBack() {
                @Override
                public void onCancelClick(View view) {

                }

                @Override
                public void onSureClick(View view) {

                }

                @Override
                public void onClick(View view, Object... object) {

                    BaseTerm baseTerm = (BaseTerm) object[0];

                    double totalVolume = 0;

                    for (SpaceItemList itemList : baseTerm.getList()) {

                        totalVolume += itemList.getVolume();

                    }

                    boolean isExist = false;

                    for (BaseItem item : mTermCountList) {
                        if (item.getId() == baseTerm.getId()) {
                            item.setVolume(item.getVolume() + totalVolume);

                            for (SpaceItemList termList : baseTerm.getList()) {
                                for (SpaceItemList itemList : item.getList()) {

                                    if (termList.getId() == itemList.getId() &&
                                            termList.getTag() == itemList.getTag()) {

                                        itemList.setVolume(itemList.getVolume() + termList.getVolume());

                                    }

                                }
                            }

                            isExist = true;
                        }

                    }

                    if (!isExist) {

                        BaseItem baseItem = new BaseItem();
                        baseItem.setId(baseTerm.getId());
                        baseItem.setName(baseTerm.getName());
                        baseItem.setBaseItemPid(baseTerm.getBaseItemPid());
                        baseItem.setBaseItemPidName(baseTerm.getBaseItemPidName());
                        baseItem.setBaseItemId(baseTerm.getBaseItemId());
                        baseItem.setBaseItemName(baseTerm.getBaseItemName());
                        baseItem.setCategoryId(baseTerm.getCategoryId());
                        baseItem.setDefaultItem(baseTerm.isDefaultItem());
                        baseItem.setDescription(baseTerm.getDescription());
                        baseItem.setFormula(baseTerm.getFormula());
                        baseItem.setNumber(baseTerm.getNumber());
                        baseItem.setOffer(baseTerm.getOffer());
                        baseItem.setOverallItem(baseTerm.isOverallItem());
                        baseItem.setStatus(baseTerm.getStatus());
                        baseItem.setUnitName(baseTerm.getUnitName());
                        baseItem.setUnitId(baseTerm.getUnitId());
                        baseItem.setWorkType(baseTerm.getWorkType());

                        baseItem.setVolume(totalVolume);
                        baseItem.setTotal(baseItem.getVolume() * baseTerm.getOffer());

                        for (SpaceItemList itemList : baseTerm.getList()) {
                            baseItem.addList(itemList);
                        }

                        mTermCountList.add(baseItem);
                    }

                    mTermCountTv.setText(String.valueOf(mTermCountList.size()));
                }
            });

            volumeDialog.show();
        }

    }

    private void showShoppingCart() {

        if (mTermCountList.size() == 0) {
            ToastUtils.getInstance().showToast("购物车没有商品!");
            return;
        }

        if (shoppingCartDialog == null || !shoppingCartDialog.isShowing()) {

            shoppingCartDialog = new ShoppingCartDialog(OfferBaseActivity.this,
                    R.style.Dialog, mTermCountList, new DialogClickCallBack() {
                @Override
                public void onCancelClick(View view) {

                    if (isBack) {
                        mDrawer.closeDrawer(Gravity.END);
                        mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

                        mDelete.setVisibility(View.GONE);
                        mTermLayout.setVisibility(View.VISIBLE);
                        mTermSearchLayout.setVisibility(View.GONE);
                        mSearchEt.setText("");

                        isBack = false;
                    }

                }

                @Override
                public void onSureClick(View view) {

                    mDrawer.closeDrawer(Gravity.END);
                    mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

                    mDelete.setVisibility(View.GONE);
                    mTermLayout.setVisibility(View.VISIBLE);
                    mTermSearchLayout.setVisibility(View.GONE);
                    mSearchEt.setText("");

                    isBack = false;

                    itemAddData();

                }

                @Override
                public void onClick(View view, Object... object) {

                    mTermCountTv.setText(String.valueOf(mTermCountList.size()));

                }
            });

            shoppingCartDialog.show();
        }

    }

    private void itemAddData() {

        boolean isAdd = false;
        boolean isAddItem = false;

        double classTotal = 0;

        for (int i = mDataList.size() - 1; i >= 0; i--) {

            if (mDataList.get(i).getViewTpye() == RunTimeConfig.ModelConfig.BASE_ITEM &&
                    ((BaseItem) mDataList.get(i)).getBaseItemPid() == tempAddId) {

                int index = i;


                for (BaseItem baseItem : mTermCountList) {

                    index++;

                    baseItem.setShow(((BaseItem) mDataList.get(i)).isShow());

                    classTotal += baseItem.getTotal();

                    mDataList.add(index, baseItem);

                    isAddItem = true;

                }

                break;

            }
        }

        if (isAddItem) {
            for (OfferBaseModel model : mDataList) {

                if (model.getViewTpye() == RunTimeConfig.ModelConfig.BASE_TITLE &&
                        ((BaseTitle) model).getId() == tempAddId) {

                    ((BaseTitle) model).setTotal(((BaseTitle) model).getTotal() + classTotal);

                    isAdd = true;
                }

            }
        }

        if (!isAdd) {
            for (int i = 0; i < mDataList.size(); i++) {

                if (mDataList.get(i).getViewTpye() == RunTimeConfig.ModelConfig.BASE_TITLE &&
                        ((BaseTitle) mDataList.get(i)).getId() == tempAddId) {

                    int index = i;

                    for (BaseItem baseItem : mTermCountList) {

                        index++;

                        baseItem.setShow(false);

                        classTotal += baseItem.getTotal();

                        mDataList.add(index, baseItem);

                    }

                    ((BaseTitle) mDataList.get(i)).setTotal(((BaseTitle) mDataList.get(i)).getTotal() + classTotal);
                }

            }
        }

        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();

        mTermCountList.clear();

        getAmount();
    }

    private void getAmount() {

        double total = 0;

        for (OfferBaseModel model : mDataList) {

            if (model.getViewTpye() == RunTimeConfig.ModelConfig.BASE_TITLE) {

                total += ((BaseTitle) model).getTotal();

            }

        }

        mOfferTv.setText(new DecimalFormat("#0.00").format(total));
        mRewardTv.setText(new DecimalFormat("#0.00").format(total * reward.getValue() / 100));
        mAmountTv.setText(new DecimalFormat("#0.00").format(total + total * reward.getValue() / 100));
    }

    private void showBaseItemDialog(BaseItem item, final int position) {

        if (baseItemDialog == null || !baseItemDialog.isShowing()) {

            final BaseTerm term = new BaseTerm();
            term.setId(item.getId());
            term.setWorkType(item.getWorkType());
            term.setUnitName(item.getUnitName());
            term.setUnitId(item.getUnitId());
            term.setBaseItemId(item.getBaseItemId());
            term.setBaseItemName(item.getBaseItemName());
            term.setBaseItemPid(item.getBaseItemPid());
            term.setBaseItemPidName(item.getBaseItemPidName());
            term.setCategoryId(item.getCategoryId());
            term.setDefaultItem(item.isDefaultItem());
            term.setDescription(item.getDescription());
            term.setFormula(item.getFormula());
            term.setName(item.getName());
            term.setNumber(item.getNumber());
            term.setOffer(item.getOffer());
            term.setOverallItem(item.isOverallItem());
            term.setStatus(item.getStatus());
            term.setWord("");
            SpaceItemList newList;
            for (SpaceItemList itemList : item.getList()) {
                newList = new SpaceItemList();
                newList.setId(itemList.getId());
                newList.setShowName(itemList.isShowName());
                newList.setVolume(itemList.getVolume());
                newList.setName(itemList.getName());
                newList.setTag(itemList.getTag());
                newList.setArea(itemList.getArea());
                newList.setSpaceId(itemList.getSpaceId());
                newList.setType(itemList.getType());
                term.addList(newList);
            }

            baseItemDialog = new TermVolumeDialog(OfferBaseActivity.this,
                    R.style.Dialog, term, totalArea, new DialogClickCallBack() {
                @Override
                public void onCancelClick(View view) {

                }

                @Override
                public void onSureClick(View view) {

                }

                @Override
                public void onClick(View view, Object... object) {

                    refreshData((BaseTerm) object[0], position);

                }
            });
            baseItemDialog.show();
        }

    }

    private void refreshData(BaseTerm term, int position) {

        BaseItem item = (BaseItem) mDataList.get(position);

        double totalVolume = 0;

        for (SpaceItemList itemList : term.getList()) {

            totalVolume += itemList.getVolume();

        }

        item.setVolume(totalVolume);
        item.setTotal(totalVolume * item.getOffer());

        for (SpaceItemList termList : term.getList()) {
            for (SpaceItemList itemList : item.getList()) {

                if (termList.getId() == itemList.getId() &&
                        termList.getTag() == itemList.getTag()) {

                    itemList.setVolume(termList.getVolume());

                }

            }
        }

        if (mAdapter != null)
            mAdapter.notifyItemChanged(position);

        updateTitleTotal(item.getBaseItemPid());

        getAmount();
    }

    private void updateTitleTotal(long basePid) {

        double titleTotal = 0;

        for (OfferBaseModel baseModel : mDataList) {

            if (baseModel.getViewTpye() == RunTimeConfig.ModelConfig.BASE_ITEM &&
                    ((BaseItem) baseModel).getBaseItemPid() == basePid) {

                titleTotal += ((BaseItem) baseModel).getTotal();

            }

        }

        for (int i = 0; i < mDataList.size(); i++) {

            if (mDataList.get(i).getViewTpye() == RunTimeConfig.ModelConfig.BASE_TITLE &&
                    ((BaseTitle) mDataList.get(i)).getId() == basePid) {

                ((BaseTitle) mDataList.get(i)).setTotal(titleTotal);

                if (mAdapter != null)
                    mAdapter.notifyItemChanged(i);

                break;
            }
        }

        getAmount();

    }

    private void submit() {

        if (submitOfferDialog == null || !submitOfferDialog.isShowing()) {

            submitOfferDialog = new SubmitOfferDialog(OfferBaseActivity.this,
                    R.style.Dialog, mOfferTv.getText().toString(), mRewardTv.getText().toString(),
                    mAmountTv.getText().toString(), new DialogClickCallBack() {
                @Override
                public void onCancelClick(View view) {

                }

                @Override
                public void onSureClick(View view) {

                    offerJsonData();

                }

                @Override
                public void onClick(View view, Object... object) {

                }
            });
            submitOfferDialog.show();
        }

    }

    private void offerJsonData() {

        int rooms = 0;
        int halles = 0;
        int cookhouse = 0;
        int washroom = 0;
        int balcony = 0;

        List<SpaceItemList> spaceList = new ArrayList<>();
        List<BaseItem> termList = new ArrayList<>();

        JSONObject jsonData = new JSONObject();
        JSONArray jsonSpaceList = new JSONArray();
        JSONObject jsonSpaceItem;
        JSONArray jsonList;
        JSONObject jsonListItem;

        for (Map<String, Integer> map : mSpaceList) {


            switch (map.get("type")) {

                case RunTimeConfig.HouseConfig.HALL:
                    halles++;
                    break;
                case RunTimeConfig.HouseConfig.ROOM:
                    rooms++;
                    break;
                case RunTimeConfig.HouseConfig.KITCHEN:
                    cookhouse++;
                    break;
                case RunTimeConfig.HouseConfig.TOILET:
                    washroom++;
                    break;
                case RunTimeConfig.HouseConfig.BALCONY:
                    balcony++;
                    break;
            }
        }

        for (OfferSpaceModel spaceModel : mHouseList) {

            if (spaceModel.getViewType() == RunTimeConfig.ModelConfig.SPACE_ITEM) {

                for (SpaceItemList spaceItemList : ((SpaceItem) spaceModel).getList()) {

                    spaceList.add(spaceItemList);

                }

            }
        }

        for (OfferBaseModel baseModel : mDataList) {

            if (baseModel.getViewTpye() == RunTimeConfig.ModelConfig.BASE_ITEM) {

                termList.add((BaseItem) baseModel);

            }

        }
        try {
            for (SpaceItemList itemList : spaceList) {

                jsonSpaceItem = new JSONObject();
                jsonList = new JSONArray();

                for (BaseItem baseItem : termList) {

                    if (!baseItem.isDefaultItem() && !baseItem.isOverallItem()) {

                        for (SpaceItemList baseItemList : baseItem.getList()) {

                            if (itemList.getId() == baseItemList.getId() && itemList.getTag() == baseItemList.getTag()) {

                                if (baseItemList.getVolume() != 0) {

                                    jsonListItem = new JSONObject();

                                    jsonListItem.put("itemId", baseItem.getId());
                                    jsonListItem.put("quantity", baseItemList.getVolume());
                                    jsonListItem.put("step", baseItem.getBaseItemPidName());
                                    jsonList.put(jsonListItem);
                                }
                            }

                        }
                    }
                }
                jsonSpaceItem.put("spaceTypeId", itemList.getId());
                jsonSpaceItem.put("spaceArea", itemList.getArea());
                jsonSpaceItem.put("itemsList", jsonList);
                jsonSpaceList.put(jsonSpaceItem);
            }

            //全屋
            jsonSpaceItem = new JSONObject();
            jsonList = new JSONArray();

            for (BaseItem baseItem : termList) {

                if (baseItem.isDefaultItem() || baseItem.isOverallItem()) {

                    jsonListItem = new JSONObject();

                    jsonListItem.put("itemId", baseItem.getId());
                    jsonListItem.put("quantity", baseItem.getVolume());
                    jsonListItem.put("step", baseItem.getName());
                    jsonList.put(jsonListItem);

                }

            }

            jsonSpaceItem.put("spaceTypeId", houseModel.getId());
            jsonSpaceItem.put("spaceArea", totalArea);
            jsonSpaceItem.put("itemsList", jsonList);
            jsonSpaceList.put(jsonSpaceItem);

            //----

            jsonData.put("projectId", projectId);
            jsonData.put("quote", Double.parseDouble(mOfferTv.getText().toString()));
            jsonData.put("bonus", Double.parseDouble(mRewardTv.getText().toString()));
            jsonData.put("totalAmount", Double.parseDouble(mAmountTv.getText().toString()));
            jsonData.put("branchId", LibApplication.branchId);
            jsonData.put("area", totalArea);
            jsonData.put("rooms", rooms);
            jsonData.put("halles", halles);
            jsonData.put("cookhouse", cookhouse);
            jsonData.put("washroom", washroom);
            jsonData.put("balcony", balcony);
            jsonData.put("spaceList", jsonSpaceList);

            LogUtils.println(" ======== " + jsonData.toString());

            offerSave(jsonData.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void offerSave(String jsonData) {

        RequestUtils.getInstance(Urls.OFFER_SAVE).offerSave(
                RunTimeConfig.RequestConfig.OFFER_SAVE, jsonData, 0, this);

    }

    @Override
    public void onSucceed(String result, int requestId) {

        switch (requestId) {

            case RunTimeConfig.RequestConfig.OFFER_SAVE:

                LogUtils.println(this.getClass().getName() + " request OFFER_SAVE onSucceed " + result);

                JsonHelper.getInstance().jsonString(result, true, new JsonCallBack() {
                    @Override
                    public void onSucceed(Object data) {

                        ToastUtils.getInstance().showToast(R.string.toast_send);

                        ActivityStack.getInstance().finishActivity(OfferSpaceActivity.class);

                        doAction(RunTimeConfig.ActionConfig.REFRESH, Task.PUBLIC, Task.MIN_AUTHORITY, RunTimeConfig.ActionWhatConfig.OFFER_ORDER);

                        finish();
                    }

                    @Override
                    public void onFailure(String error) {

                        LogUtils.println(this.getClass().getName() + " json onFailure OFFER_QUOTATION " + error);

                        ToastUtils.getInstance().showToast(error);

                    }
                });

                break;

        }

    }

    @Override
    public void onFailure(String error, int requestId) {

        switch (requestId) {

            case RunTimeConfig.RequestConfig.OFFER_SAVE:

                LogUtils.println(this.getClass().getName() + " request OFFER_SAVE onFailure " + error);
                ToastUtils.getInstance().showToast(error);

                break;

        }


    }

    @Override
    public void onProgress(long progress, int requestId) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showBackDialog();
        }

        return false;

    }

    private void showBackDialog() {

        if (commonDialog == null || !commonDialog.isShowing()) {

            commonDialog = new CommonDialog(OfferBaseActivity.this, R.style.Dialog,
                    RunTimeConfig.DialogTagConfig.TAG_BACK, new DialogClickCallBack() {
                @Override
                public void onCancelClick(View view) {

                }

                @Override
                public void onSureClick(View view) {

                    finish();

                }

                @Override
                public void onClick(View view, Object... object) {

                }
            });
            commonDialog.show();

        }

    }

}
