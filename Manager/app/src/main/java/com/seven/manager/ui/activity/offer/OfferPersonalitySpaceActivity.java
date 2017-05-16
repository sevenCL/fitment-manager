package com.seven.manager.ui.activity.offer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.seven.library.application.LibApplication;
import com.seven.library.base.BaseTitleActivity;
import com.seven.library.callback.JsonCallBack;
import com.seven.library.callback.ListItemCallBack;
import com.seven.library.config.RunTimeConfig;
import com.seven.library.json.JsonHelper;
import com.seven.library.task.ActivityStack;
import com.seven.library.utils.ResourceUtils;
import com.seven.library.view.AutoLoadRecyclerView;
import com.seven.manager.R;
import com.seven.manager.adapter.offer.OfferPersonalitySpaceAdapter;
import com.seven.manager.db.DbOfferHouse;
import com.seven.manager.model.offer.OfferHouse;
import com.seven.manager.model.offer.OfferHouseModel;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 报价-个性项-添加
 *
 * @author seven
 */

public class OfferPersonalitySpaceActivity extends BaseTitleActivity implements ListItemCallBack {

    private AutoLoadRecyclerView mRecyclerView;

    private List<OfferHouseModel> mDataList;

    private OfferPersonalitySpaceAdapter mAdapter;

    //房间数据
    private DbManager db;
    private DbOfferHouse dbOfferHouse;

    /**
     * 跳转方法
     *
     * @param isFinishSrcAct 是否关闭启动页面
     */
    public static void start(boolean isFinishSrcAct) {
        Bundle param = new Bundle();
        param.putBoolean(RunTimeConfig.IntentCodeConfig.FINISH, isFinishSrcAct);
        ActivityStack.getInstance().startActivity(OfferPersonalitySpaceActivity.class, isFinishSrcAct, param);
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

        return R.layout.activity_offer_personality_space;
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

        mRecyclerView = getView(mRecyclerView, R.id.offer_personality_space_recycler);

    }

    @Override
    public void initData(Intent intent) {

        setTitle(ResourceUtils.getInstance().getText(R.string.offer_personality_add));

        mDataList = new ArrayList<>();

        //数据库

        db = x.getDb(LibApplication.daoConfig);

        try {
            dbOfferHouse = db.selector(DbOfferHouse.class).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }

    }

    public void btClick(View view) {

        int id = view.getId();

        switch (id) {

            //下一步
            case R.id.offer_personality_space_next:

                for (OfferHouseModel model : mDataList) {
                    if (model.getStatus() == RunTimeConfig.SelectConfig.SELECTED)
                        OfferPersonalityTermActivity.start(false, model);
                }

                break;

        }
    }

    private void getData() {

        if (dbOfferHouse == null || TextUtils.isEmpty(dbOfferHouse.getContent()))
            return;

        JsonHelper.getInstance().jsonArray(dbOfferHouse.getContent(),
                OfferHouseModel.class, true, new JsonCallBack() {
                    @Override
                    public void onSucceed(Object data) {

                        for (OfferHouseModel model : (List<OfferHouseModel>) data)
                            mDataList.add(model);

                    }

                    @Override
                    public void onFailure(String error) {

                    }
                });

    }

    private void setRecyclerView() {

        mRecyclerView.setHasFixedSize(false);

        mRecyclerView.setLayoutManager(new GridLayoutManager(LibApplication.getInstance(), 2));
        mRecyclerView.setOnPauseListenerParams(false, true);

        mAdapter = new OfferPersonalitySpaceAdapter(LibApplication.getInstance(), mDataList, this);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onItemClick(View view, int position, Object... object) {

        for (int i = 0; i < mDataList.size(); i++) {
            if (mDataList.get(i).getStatus() == RunTimeConfig.SelectConfig.SELECTED) {
                mDataList.get(i).setStatus(RunTimeConfig.SelectConfig.SELECT_NOT);
                mAdapter.notifyItemChanged(i);
            }
        }

        mDataList.get(position).setStatus(RunTimeConfig.SelectConfig.SELECTED);

        mAdapter.notifyItemChanged(position);

    }
}
