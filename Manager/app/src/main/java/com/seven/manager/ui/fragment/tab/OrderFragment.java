package com.seven.manager.ui.fragment.tab;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.frankchen.mvc.aidl.Task;
import com.seven.library.application.LibApplication;
import com.seven.library.base.BaseFragment;
import com.seven.library.callback.DialogClickCallBack;
import com.seven.library.callback.HttpRequestCallBack;
import com.seven.library.callback.JsonCallBack;
import com.seven.library.callback.ListItemCallBack;
import com.seven.library.callback.LoadMoreListener;
import com.seven.library.config.RunTimeConfig;
import com.seven.library.http.RequestUtils;
import com.seven.library.http.Urls;
import com.seven.library.json.JsonHelper;
import com.seven.library.task.ActivityStack;
import com.seven.library.utils.LogUtils;
import com.seven.library.utils.ResourceUtils;
import com.seven.library.utils.ToastUtils;
import com.seven.library.view.AutoLoadRecyclerView;
import com.seven.library.view.SwipeToRefreshLayout;
import com.seven.manager.R;
import com.seven.manager.adapter.OrderAdapter;
import com.seven.manager.db.DbOrderList;
import com.seven.manager.model.order.OrderModel;
import com.seven.manager.ui.activity.HomeActivity;
import com.seven.manager.ui.activity.MapActivity;
import com.seven.manager.ui.activity.newoffer.OfferSpaceActivity;
import com.seven.manager.ui.activity.offer.OfferActivity;
import com.seven.manager.ui.activity.offer.OfferQuotationActivity;
import com.seven.manager.ui.dialog.CommonDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 预约
 *
 * @author seven
 */
@SuppressLint("ValidFragment")
public class OrderFragment extends BaseFragment implements HttpRequestCallBack, ListItemCallBack {

    //刷新
    private SwipeToRefreshLayout mSwipeRefresh;

    //自动加载的recycler
    private AutoLoadRecyclerView mRecyclerView;

    //无数据提示图
    private LinearLayout mNullLayout;

    private OrderAdapter mAdapter;
    private List<OrderModel> mDataList;

    private DbManager db;
    private DbOrderList dbOderList;

    private CommonDialog mCommonDialog;

    public OrderFragment() {
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_order;
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

        mSwipeRefresh = getView(mSwipeRefresh, R.id.order_swipe_refresh);

        mRecyclerView = getView(mRecyclerView, R.id.order_recycler_view);

        mNullLayout = getView(mNullLayout, R.id.order_null_ll);

    }

    @Override
    public void initData() {

        mDataList = new ArrayList<>();

        //数据库

        db = x.getDb(LibApplication.daoConfig);


        try {
            dbOderList = db.selector(DbOrderList.class).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }

        onRefresh();

    }

    @Override
    public void onReceiveNotification(Task task) {
        super.onReceiveNotification(task);

        switch (task.getWhat()) {

            case RunTimeConfig.ActionWhatConfig.OFFER_ORDER:

                onRefresh();

                break;

        }
    }

    private void onRefresh() {

//        int hashCode = 0;
//
//        if (dbOderList != null)
//            hashCode = dbOderList.getHashCode();

        // TODO: 2017/4/22 预约 订单
        RequestUtils.getInstance(Urls.ORDER_LIST).orderList(RunTimeConfig.RequestConfig.ORDER_LIST, this);

    }

    @Override
    public void onClick(View v) {

    }

    private void getData() {

        //数据库

        db = x.getDb(LibApplication.daoConfig);

        try {
            dbOderList = db.selector(DbOrderList.class).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }

        if (dbOderList == null || TextUtils.isEmpty(dbOderList.getContent())) {
            mNullLayout.setVisibility(View.VISIBLE);
        } else {
            JsonHelper.getInstance().jsonArray(dbOderList.getContent(),
                    OrderModel.class, true, new JsonCallBack() {
                        @Override
                        public void onSucceed(Object data) {

                            LogUtils.println(this.getClass().getName() + " json ORDER_LIST onSucceed " + data);

                            if (data == null)
                                return;

                            mDataList.clear();

                            for (OrderModel model : (List<OrderModel>) data)
                                mDataList.add(model);

                            mNullLayout.setVisibility(mDataList.size() > 0 ? View.GONE : View.VISIBLE);

                            if (mAdapter != null)
                                mAdapter.notifyDataSetChanged();

                            if (mSwipeRefresh.isRefreshing())
                                mSwipeRefresh.setRefreshing(false);

                        }

                        @Override
                        public void onFailure(String error) {
                            LogUtils.println(this.getClass().getName() + " json ORDER_LIST onFailure " + error);
                        }
                    });
        }
    }

    private void setRecyclerView() {

        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLoadMoreListener(new LoadMoreListener() {
            @Override
            public void loadMore() {

            }
        });

        mSwipeRefresh.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                // TODO: 2016/6/25  重新请求数据

                OrderFragment.this.onRefresh();
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(LibApplication.getInstance()));
        mRecyclerView.setOnPauseListenerParams(false, true);

        mAdapter = new OrderAdapter(LibApplication.getInstance(), mDataList, this);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onSucceed(String result, int requestId) {

        switch (requestId) {

            //预约订单
            case RunTimeConfig.RequestConfig.ORDER_LIST:

                try {

                    LogUtils.println(this.getClass().getName() + " onSucceed ORDER_LIST request " + result);

                    if (TextUtils.isEmpty(new JSONObject(result).getJSONArray("data").toString()))
                        return;

                    dbOderList = db.selector(DbOrderList.class).findFirst();

                    if (dbOderList == null) {//第一次创建表并添加数据

                        if (TextUtils.isEmpty(result))
                            return;

                        dbOderList = new DbOrderList();
                        dbOderList.setId(1);
                        dbOderList.setProjectId(1);
//                        dbOderList.setHashCode(JsonHelper.getInstance().jsonStringHash(result));
                        dbOderList.setParams(Urls.ORDER_LIST);
                        dbOderList.setContent(result);
                        dbOderList.setCreateTime(System.currentTimeMillis());

                        db.save(dbOderList);

                    } else {//更新

//                        if (new JSONObject(result).getJSONObject("data").getInt("hashCode") == dbOderList.getHashCode())
//                            return;

//                        dbOderList.setHashCode(JsonHelper.getInstance().jsonStringHash(result));
                        dbOderList.setContent(result);
                        dbOderList.setCreateTime(System.currentTimeMillis());

                        db.saveOrUpdate(dbOderList);
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getData();

                break;

            case RunTimeConfig.RequestConfig.ORDER_REFUSE:

                JsonHelper.getInstance().jsonString(result, true, new JsonCallBack() {
                    @Override
                    public void onSucceed(Object data) {

                        ToastUtils.getInstance().showToast(ResourceUtils.getInstance().getText(R.string.hint_refuse_success));

                        onRefresh();

                    }

                    @Override
                    public void onFailure(String error) {

                        LogUtils.println(this.getClass().getName() + " onFailure ORDER_REFUSE json " + error);

                    }
                });

                break;

            case RunTimeConfig.RequestConfig.ORDER_ACCEPT:

                JsonHelper.getInstance().jsonString(result, true, new JsonCallBack() {
                    @Override
                    public void onSucceed(Object data) {

                        ToastUtils.getInstance().showToast(ResourceUtils.getInstance().getText(R.string.hint_accept_success));

                        onRefresh();

                    }

                    @Override
                    public void onFailure(String error) {

                        LogUtils.println(this.getClass().getName() + " onFailure ORDER_ACCEPT json " + error);

                    }
                });

                break;

        }
    }

    @Override
    public void onFailure(String error, int requestId) {

        switch (requestId) {

            case RunTimeConfig.RequestConfig.ORDER_LIST:

                LogUtils.println(this.getClass().getName() + " onFailure ORDER_LIST request " + error);

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

            //拒绝
            case R.id.order_refuse_btn:

//                RequestUtils.getInstance().orderStatus(RunTimeConfig.RequestConfig.ORDER_REFUSE,
//                        Urls.ORDER_STATUS, mDataList.get(position).getOrderNumber(),
//                        RunTimeConfig.StatusConfig.ORDER_REFUSE, this);

                showDialog(RunTimeConfig.StatusConfig.ORDER_REFUSE, position, RunTimeConfig.DialogTagConfig.TAG_REFUSE);

                break;

            //接受
            case R.id.order_accept_btn:

//                RequestUtils.getInstance().orderStatus(RunTimeConfig.RequestConfig.ORDER_ACCEPT,
//                        Urls.ORDER_STATUS, mDataList.get(position).getOrderNumber(),
//                        RunTimeConfig.StatusConfig.ORDER_ACCEPT, this);

                showDialog(RunTimeConfig.StatusConfig.ORDER_ACCEPT, position, RunTimeConfig.DialogTagConfig.TAG_ACCEPT);

                break;

            //电话
            case R.id.order_phone_iv:

                try {

                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mDataList.get(position).getOwnerPhone()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                } catch (Exception e) {

                    LogUtils.println(this.getClass().getName() + e);

                }

                break;

            //地图
            case R.id.order_location_iv:

                MapActivity.start(false, mDataList.get(position));

                break;

            //报价
            case R.id.order_offer_btn:

//                OfferActivity.start(false, mDataList.get(position));

                OfferSpaceActivity.start(false, mDataList.get(position));

                break;

            //查看报价
            case R.id.order_offer_see_btn:

                OfferQuotationActivity.start(false, mDataList.get(position), RunTimeConfig.FlowConfig.OFFER_IS_COMPILE_NOT);

                break;


        }

    }

    /**
     * 二次确认
     *
     * @param status
     * @param position
     * @param tag
     */
    private void showDialog(final int status, final int position, int tag) {

        if (mCommonDialog == null || !mCommonDialog.isShowing()) {

            Activity activity = ActivityStack.getInstance().findActivityByClass(HomeActivity.class);

            if (activity == null)
                return;

            mCommonDialog = new CommonDialog(activity, R.style.Dialog, tag, new DialogClickCallBack() {
                @Override
                public void onCancelClick(View view) {

                }

                @Override
                public void onSureClick(View view) {

                    switch (status) {

                        case RunTimeConfig.StatusConfig.ORDER_REFUSE:

                            RequestUtils.getInstance(Urls.ORDER_STATUS).orderStatus(RunTimeConfig.RequestConfig.ORDER_REFUSE,
                                    mDataList.get(position).getOrderNumber(), RunTimeConfig.StatusConfig.ORDER_REFUSE, OrderFragment.this);

                            break;

                        case RunTimeConfig.StatusConfig.ORDER_ACCEPT:

                            RequestUtils.getInstance(Urls.ORDER_STATUS).orderStatus(RunTimeConfig.RequestConfig.ORDER_ACCEPT,
                                    mDataList.get(position).getOrderNumber(), RunTimeConfig.StatusConfig.ORDER_ACCEPT, OrderFragment.this);

                            break;

                    }

                }

                @Override
                public void onClick(View view, Object... object) {

                }
            });

            mCommonDialog.show();
        }
    }

}
