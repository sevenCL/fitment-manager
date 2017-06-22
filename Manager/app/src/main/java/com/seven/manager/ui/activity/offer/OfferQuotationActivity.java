package com.seven.manager.ui.activity.offer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.frankchen.mvc.aidl.Task;
import com.seven.library.application.LibApplication;
import com.seven.library.base.BaseTitleActivity;
import com.seven.library.callback.HttpRequestCallBack;
import com.seven.library.callback.JsonCallBack;
import com.seven.library.callback.ListItemCallBack;
import com.seven.library.config.RunTimeConfig;
import com.seven.library.http.RequestUtils;
import com.seven.library.http.Urls;
import com.seven.library.json.JsonHelper;
import com.seven.library.task.ActivityStack;
import com.seven.library.utils.LogUtils;
import com.seven.library.utils.ResourceUtils;
import com.seven.library.utils.ToastUtils;
import com.seven.library.view.AutoLoadRecyclerView;
import com.seven.manager.R;
import com.seven.manager.adapter.offer.OfferQuotationAdapter;
import com.seven.manager.db.DbQuotation;
import com.seven.manager.model.offer.OfferQuotationModel;
import com.seven.manager.model.offer.QuotationDate;
import com.seven.manager.model.offer.QuotationModel;
import com.seven.manager.model.offer.QuotationPackage;
import com.seven.manager.model.offer.QuotationPersonality;
import com.seven.manager.model.offer.QuotationTitle;
import com.seven.manager.model.order.OrderModel;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 报价单
 *
 * @author seven
 */

public class OfferQuotationActivity extends BaseTitleActivity implements ListItemCallBack, HttpRequestCallBack {

    private OrderModel mModel;

    private int isCompile;

    private DbManager db;
    private DbQuotation dbQuotation;

    private AutoLoadRecyclerView mRecyclerView;

    private List<OfferQuotationModel> mDataList;

    private OfferQuotationAdapter mAdapter;

    private QuotationTitle quotationTitle;

    private LinearLayout mCompileLayout;

    /**
     * 跳转方法
     *
     * @param isFinishSrcAct 是否关闭启动页面
     */
    public static void start(boolean isFinishSrcAct, Serializable serializable, int isCompile) {
        Bundle param = new Bundle();
        param.putBoolean(RunTimeConfig.IntentCodeConfig.FINISH, isFinishSrcAct);
        param.putSerializable(RunTimeConfig.IntentCodeConfig.SERIALIZABLE, serializable);
        param.putInt(RunTimeConfig.IntentCodeConfig.OFFER_IS_COMPILE, isCompile);
        ActivityStack.getInstance().startActivity(OfferQuotationActivity.class, isFinishSrcAct, param);
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

        return R.layout.activity_offer_quotation;
    }

    @Override
    public void onLeftButtonClicked() {

        finish();

    }

    @Override
    public void onRightButtonClicked() {

    }

    @Override
    public void onRightTextClicked() {

    }

    @Override
    public void initView() {

        mRecyclerView = getView(mRecyclerView, R.id.offer_quotation_recycler);

        mCompileLayout = getView(mCompileLayout, R.id.offer_quotation_bottom);

    }

    @Override
    public void initData(Intent intent) {

        setTitle(ResourceUtils.getInstance().getText(R.string.offer_see));

        if (intent == null)
            intent = getIntent();

        mModel = (OrderModel) intent.getSerializableExtra(RunTimeConfig.IntentCodeConfig.SERIALIZABLE);
        isCompile = intent.getIntExtra(RunTimeConfig.IntentCodeConfig.OFFER_IS_COMPILE, RunTimeConfig.FlowConfig.OFFER_IS_COMPILE);

        mCompileLayout.setVisibility(isCompile == RunTimeConfig.FlowConfig.OFFER_IS_COMPILE ? View.VISIBLE : View.GONE);

        mDataList = new ArrayList<>();

        //数据库
        db = x.getDb(LibApplication.daoConfig);

        try {
            dbQuotation = db.selector(DbQuotation.class).where("projectId", "=", mModel.getProjectId()).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }

    }

    public void btClick(View view) {

        int id = view.getId();

        switch (id) {

            case R.id.offer_quotation_compile:

                finish();

                break;

            case R.id.offer_quotation_send:

                sendOQuotation();

                break;
        }
    }

    private void sendOQuotation() {

        if (dbQuotation == null || TextUtils.isEmpty(dbQuotation.getContent()))
            return;

        // TODO: 2017/5/16 发送报价单

        JsonHelper.getInstance().jsonObject(dbQuotation.getContent(), QuotationModel.class,
                true, null, new JsonCallBack() {
                    @Override
                    public void onSucceed(Object data) {

                        if (data == null)
                            return;

                        QuotationModel model = (QuotationModel) data;

                        RequestUtils.getInstance(Urls.OFFER_QUOTATION).sendQuotation(
                                RunTimeConfig.RequestConfig.OFFER_QUOTATION, LibApplication.branchId,
                                model.getOwnerId(), model.getPlanId(), model.getProjectId(), model.getArea(),
                                model.getHalles(), model.getRooms(), model.getCookhouse(), model.getWashroom(),
                                model.getBalcony(), model.getOthers(), model.getItemsListJson(),
                                model.getAddItemsListJson(), model.getHashCode(), OfferQuotationActivity.this);
                    }

                    @Override
                    public void onFailure(String error) {

                    }
                });

    }

    private void getData() {

        if (dbQuotation == null || TextUtils.isEmpty(dbQuotation.getContent()))
            return;

        JsonHelper.getInstance().jsonObject(dbQuotation.getContent(), QuotationTitle.class,
                true, null, new JsonCallBack() {
                    @Override
                    public void onSucceed(Object data) {

                        if (data == null)
                            return;

                        quotationTitle = (QuotationTitle) data;
                        mDataList.add(quotationTitle);
                    }

                    @Override
                    public void onFailure(String error) {

                    }
                });


        JsonHelper.getInstance().jsonObject(dbQuotation.getContent(), QuotationPackage.class,
                true, null, new JsonCallBack() {
                    @Override
                    public void onSucceed(Object data) {

                        if (data == null)
                            return;

                        QuotationPackage aPackage = (QuotationPackage) data;
                        mDataList.add(aPackage);
                    }

                    @Override
                    public void onFailure(String error) {

                    }
                });

        JsonHelper.getInstance().jsonObject(dbQuotation.getContent(), QuotationPersonality.class,
                true, null, new JsonCallBack() {
                    @Override
                    public void onSucceed(Object data) {

                        if (data == null)
                            return;

                        QuotationPersonality personality = (QuotationPersonality) data;
                        mDataList.add(personality);
                    }

                    @Override
                    public void onFailure(String error) {

                    }
                });


        JsonHelper.getInstance().jsonObject(dbQuotation.getContent(), QuotationDate.class,
                true, null, new JsonCallBack() {
                    @Override
                    public void onSucceed(Object data) {

                        if (data == null)
                            return;

                        QuotationDate personality = (QuotationDate) data;
                        mDataList.add(personality);
                    }

                    @Override
                    public void onFailure(String error) {

                    }
                });

    }

    private void setRecyclerView() {

        mRecyclerView.setHasFixedSize(false);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(LibApplication.getInstance()));
        mRecyclerView.setOnPauseListenerParams(false, true);

        mAdapter = new OfferQuotationAdapter(LibApplication.getInstance(), mDataList, this);
        mRecyclerView.setAdapter(mAdapter);

    }


    @Override
    public void onItemClick(View view, int position, Object... object) {

    }

    @Override
    public void onSucceed(String result, int requestId) {

        switch (requestId) {

            case RunTimeConfig.RequestConfig.OFFER_QUOTATION:

                LogUtils.println(this.getClass().getName() + " request onSucceed OFFER_QUOTATION " + result);

                JsonHelper.getInstance().jsonString(result, true, new JsonCallBack() {
                    @Override
                    public void onSucceed(Object data) {

                        ToastUtils.getInstance().showToast(R.string.toast_send);

                        ActivityStack.getInstance().finishActivity(OfferActivity.class);

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

            case RunTimeConfig.RequestConfig.OFFER_QUOTATION:

                LogUtils.println(this.getClass().getName() + " request onFailure OFFER_QUOTATION " + error);
                ToastUtils.getInstance().showToast(error);
                break;

        }

    }

    @Override
    public void onProgress(long progress, int requestId) {

    }
}
