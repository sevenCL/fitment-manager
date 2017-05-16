package com.seven.manager.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.seven.library.application.LibApplication;
import com.seven.library.base.BaseTitleActivity;
import com.seven.library.callback.HttpRequestCallBack;
import com.seven.library.callback.JsonCallBack;
import com.seven.library.callback.ListItemCallBack;
import com.seven.library.config.RunTimeConfig;
import com.seven.library.db.res.ResCity;
import com.seven.library.http.RequestUtils;
import com.seven.library.http.Urls;
import com.seven.library.json.JsonField;
import com.seven.library.json.JsonHelper;
import com.seven.library.task.ActivityStack;
import com.seven.library.utils.LogUtils;
import com.seven.library.utils.ResourceUtils;
import com.seven.library.view.AutoLoadRecyclerView;
import com.seven.manager.R;
import com.seven.manager.adapter.CityAdapter;
import com.seven.manager.model.res.CityModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择城市
 *
 * @author seven
 */
public class CityActivity extends BaseTitleActivity implements HttpRequestCallBack, ListItemCallBack {

    //流程 REGISTER 注册 FORGET_PASSWORD 忘记密码
    private int flow;

    //城市id
    private long cityId;

    //数据库
    private DbManager db;
    private ResCity resCity;

    private AutoLoadRecyclerView mRecyclerView;
    private List<CityModel> mDataList;
    private CityAdapter mAdapter;

    //下一步
    private Button mNextBtn;

    /**
     * 跳转方法
     *
     * @param isFinishSrcAct 是否关闭启动页面
     */
    public static void start(boolean isFinishSrcAct, int flow) {
        Bundle param = new Bundle();
        param.putBoolean(RunTimeConfig.IntentCodeConfig.FINISH, isFinishSrcAct);
        param.putInt(RunTimeConfig.IntentCodeConfig.FLOW, flow);
        ActivityStack.getInstance().startActivity(CityActivity.class, isFinishSrcAct, param);
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

        return R.layout.activity_city;
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

        mRecyclerView = getView(mRecyclerView, R.id.city_recycler_view);

        mNextBtn = getView(mNextBtn, R.id.city_next_btn);
        mNextBtn.setClickable(false);
        mNextBtn.setBackgroundColor(getResources().getColor(R.color.hint));
    }

    @Override
    public void initData(Intent intent) {

        //设置标题
        setTitle(ResourceUtils.getInstance().getText(R.string.register));

        mDataList = new ArrayList<>();

        if (intent == null)
            intent = getIntent();

        //默认是注册流程
        flow = intent.getIntExtra(RunTimeConfig.IntentCodeConfig.FLOW, RunTimeConfig.FlowConfig.REGISTER);

        //数据库
        db = x.getDb(LibApplication.daoConfig);

        try {
            resCity = db.selector(ResCity.class).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }

        if (resCity == null || TextUtils.isEmpty(resCity.getContent()))
            RequestUtils.getInstance(Urls.RES_CITY).resource(RunTimeConfig.RequestConfig.RES_CITY, 0, this);

    }

    public void btClick(View view) {

        int id = view.getId();

        switch (id) {

            case R.id.city_next_btn:

                SmsActivity.start(false, flow, cityId);

                break;

        }

    }

    /**
     * 获取数据
     */
    private void getData() {

        if (resCity == null || TextUtils.isEmpty(resCity.getContent()))
            return;

        if (mDataList.size() > 0)
            mDataList.clear();

//        JsonHelper.getInstance().jsonList(resCity.getContent(),
//                CityModel.class, true, JsonField.LISTS, new JsonCallBack() {
//                    @Override
//                    public void onSucceed(Object data) {
//
//                        for (CityModel model : (List<CityModel>) data)
//                            mDataList.add(model);
//
//                        if (mAdapter != null)
//                            mAdapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onFailure(String error) {
//
//                    }
//                });

        JsonHelper.getInstance().jsonArray(resCity.getContent(),
                CityModel.class, true, new JsonCallBack() {
                    @Override
                    public void onSucceed(Object data) {

                        for (CityModel model : (List<CityModel>) data)
                            mDataList.add(model);

                        if (mAdapter != null)
                            mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(String error) {

                    }
                });
    }

    /**
     * 添加数据到适配器
     */
    private void setRecyclerView() {

        mRecyclerView.setHasFixedSize(false);

        mRecyclerView.setLayoutManager(new GridLayoutManager(LibApplication.getInstance(), 3));
        mRecyclerView.setOnPauseListenerParams(false, true);

        mAdapter = new CityAdapter(LibApplication.getInstance(), mDataList, this);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onSucceed(String result, int requestId) {

        switch (requestId) {

            //服务城市
            case RunTimeConfig.RequestConfig.RES_CITY:

                LogUtils.println(this.getClass().getName() + " onSucceed RES_CITY request ");

                try {
                    resCity = db.selector(ResCity.class).findFirst();

                    if (resCity == null) {//第一次创建表并添加数据

                        if (TextUtils.isEmpty(result))
                            return;

                        resCity = new ResCity();
                        resCity.setId(1);
                        resCity.setHashCode(JsonHelper.getInstance().jsonStringHash(result));
                        resCity.setParams(Urls.RES_CITY);
                        resCity.setContent(result);
                        resCity.setCreateTime(System.currentTimeMillis());

                        db.save(resCity);

                    } else {//更新

                        try {
                            if (new JSONObject(result).getJSONObject(JsonField.DATA).getInt(
                                    JsonField.HASHCODE) == resCity.getHashCode())
                                return;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        resCity.setHashCode(JsonHelper.getInstance().jsonStringHash(result));
                        resCity.setContent(result);
                        resCity.setCreateTime(System.currentTimeMillis());

                        db.saveOrUpdate(resCity);
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                }

                getData();

                break;
        }
    }

    @Override
    public void onFailure(String error, int requestId) {

    }

    @Override
    public void onProgress(long progress, int requestId) {

    }

    @Override
    public void onItemClick(View view, int position, Object... object) {

        if (!mDataList.get(position).isSelect()) {

            for (int i = 0; i < mDataList.size(); i++) {
                if (mDataList.get(i).isSelect()) {
                    mDataList.get(i).setSelect(false);
                    mAdapter.notifyItemChanged(i);
                }
            }

            mDataList.get(position).setSelect(true);
            mAdapter.notifyItemChanged(position);

            cityId = mDataList.get(position).getRegionId();
        }

        if (!mNextBtn.isClickable()) {
            mNextBtn.setClickable(true);
            mNextBtn.setBackgroundColor(ContextCompat.getColor(LibApplication.getInstance(), R.color.color_primary));
        }
    }
}
