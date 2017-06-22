package com.seven.manager.ui.activity.offer;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.seven.library.application.LibApplication;
import com.seven.library.base.BaseTitleActivity;
import com.seven.library.callback.JsonCallBack;
import com.seven.library.callback.ListItemCallBack;
import com.seven.library.config.RunTimeConfig;
import com.seven.library.db.res.ResPersonality;
import com.seven.library.json.JsonField;
import com.seven.library.json.JsonHelper;
import com.seven.library.task.ActivityStack;
import com.seven.library.utils.ResourceUtils;
import com.seven.library.utils.ScreenUtils;
import com.seven.library.view.AutoLoadRecyclerView;
import com.seven.manager.R;
import com.seven.manager.adapter.offer.OfferPersonalityTermAdapter;
import com.seven.manager.model.offer.OfferHouseModel;
import com.seven.manager.model.offer.OfferPersonalityTermModel;
import com.seven.manager.model.offer.PersonalityChild;
import com.seven.manager.model.offer.PersonalityParent;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 报价-个性项-细项
 *
 * @author seven
 */

public class OfferPersonalityTermActivity extends BaseTitleActivity implements ListItemCallBack {

    private OfferHouseModel mSpace;

    private DbManager db;
    private ResPersonality resPersonality;

    private EditText mSearchEt;

    private TextView mTermSpace;

    private LinearLayout mRelationLayout;

    private Spinner mWorkSp;
    private List<String> mWorkList;
    private ArrayAdapter<String> mWorkAdapter;

    private Spinner mSmallSp;
    private List<String> mSmallList;
    private ArrayAdapter<String> mSmallAdapter;

    private List<OfferPersonalityTermModel> mAllList;

    private AutoLoadRecyclerView mRecyclerView;
    private List<OfferPersonalityTermModel> mDataList;

    private OfferPersonalityTermAdapter mAdapter;

    /**
     * 跳转方法
     *
     * @param isFinishSrcAct 是否关闭启动页面
     */
    public static void start(boolean isFinishSrcAct, Serializable serializable) {
        Bundle param = new Bundle();
        param.putBoolean(RunTimeConfig.IntentCodeConfig.FINISH, isFinishSrcAct);
        param.putSerializable(RunTimeConfig.IntentCodeConfig.SERIALIZABLE, serializable);
        ActivityStack.getInstance().startActivity(OfferPersonalityTermActivity.class, isFinishSrcAct, param);
    }

    @Override
    public void onInitContentView(Bundle savedInstanceState) {

        initView();

        initData(null);

        setListener();

        setSpinner();

        setSpinnerSelected();

        setRecyclerView();

    }

    @Override
    public int getContentLayoutId() {

        isLeftBtn = true;

        return R.layout.activity_offer_personality_term;
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

        mSearchEt = getView(mSearchEt, R.id.personality_term_search_et);

        mTermSpace = getView(mTermSpace, R.id.personality_term_space);

        mRelationLayout = getView(mRelationLayout, R.id.personality_term_relation);
        mWorkSp = getView(mWorkSp, R.id.personality_term_work);
        mSmallSp = getView(mSmallSp, R.id.personality_term_small);

        mRecyclerView = getView(mRecyclerView, R.id.personality_term_recycler);

    }

    @Override
    public void initData(Intent intent) {

        setTitle(ResourceUtils.getInstance().getText(R.string.offer_personality_add));

        if (intent == null)
            intent = getIntent();

        //空间
        mSpace = (OfferHouseModel) intent.getSerializableExtra(RunTimeConfig.IntentCodeConfig.SERIALIZABLE);

        if (mSpace != null)
            mTermSpace.setText(mSpace.getName());

        mDataList = new ArrayList<>();
        mAllList = new ArrayList<>();

        mWorkList = new ArrayList<>();
        mSmallList = new ArrayList<>();

        //数据库
        db = x.getDb(LibApplication.daoConfig);

        try {
            resPersonality = db.selector(ResPersonality.class).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }

        getParentData();

    }

    private void getParentData() {

        if (resPersonality == null || TextUtils.isEmpty(resPersonality.getContent()))
            return;

        JsonHelper.getInstance().jsonList(resPersonality.getContent(),
                PersonalityParent.class, true, JsonField.LISTS, new JsonCallBack() {
                    @Override
                    public void onSucceed(Object data) {

                        if (data == null)
                            return;

                        for (PersonalityParent parent : (List<PersonalityParent>) data)
                            getChildData(parent);

                    }

                    @Override
                    public void onFailure(String error) {

                    }
                });
    }

    private void getChildData(final PersonalityParent parent) {

        JsonHelper.getInstance().jsonArraySingle(parent.getItemCategoryList(),
                PersonalityChild.class, true, new JsonCallBack() {
                    @Override
                    public void onSucceed(Object data) {

                        if (data == null)
                            return;

                        for (PersonalityChild child : (List<PersonalityChild>) data) {
                            child.setpName(parent.getName());
                            getTermData(child);
                        }

                    }

                    @Override
                    public void onFailure(String error) {

                    }
                });

    }

    private void getTermData(final PersonalityChild child) {

        JsonHelper.getInstance().jsonArraySingle(child.getContractItemsList(),
                OfferPersonalityTermModel.class, true, new JsonCallBack() {
                    @Override
                    public void onSucceed(Object data) {

                        if (data == null)
                            return;

                        for (OfferPersonalityTermModel model : (List<OfferPersonalityTermModel>) data) {

                            model.setBaseItemPid(child.getPid());
                            model.setBaseItemPidName(child.getpName());
                            model.setBaseItemId(child.getId());
                            model.setBaseItemName(child.getName());

                            mAllList.add(model);
                        }

                    }

                    @Override
                    public void onFailure(String error) {

                    }
                });

    }

    private void setListener() {
        mSearchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                mRelationLayout.setVisibility(s.toString().length() > 0 ? View.GONE : View.VISIBLE);
                getData(s.toString().length() > 0 ? true : false);
            }
        });
    }

    private void setSpinner() {

        for (OfferPersonalityTermModel model : mAllList) {

            boolean isAdd = true;

            for (String parent : mWorkList) {

                if (model.getBaseItemPidName().equals(parent))
                    isAdd = false;

            }

            if (isAdd)
                mWorkList.add(model.getBaseItemPidName());

        }

        mWorkAdapter = new ArrayAdapter<>(
                LibApplication.getInstance(), R.layout.spinner_item_loan, mWorkList);
        mWorkSp.setAdapter(mWorkAdapter);

        setChildSpinner((String) mWorkSp.getSelectedItem());
    }

    private void setChildSpinner(String parent) {

        mSmallList=new ArrayList<>();

        for (OfferPersonalityTermModel model : mAllList) {

            boolean isAdd = true;

            if (model.getBaseItemPidName().equals(parent)) {

                for (String child : mSmallList) {

                    if (model.getBaseItemName().equals(child))
                        isAdd = false;

                }

                if (isAdd)
                    mSmallList.add(model.getBaseItemName());

            }
        }

        mSmallAdapter = new ArrayAdapter<>(
                LibApplication.getInstance(), R.layout.spinner_item_loan, mSmallList);
        mSmallSp.setAdapter(mSmallAdapter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mWorkSp.setDropDownVerticalOffset(ScreenUtils.getInstance().dip2px(40));
            mSmallSp.setDropDownVerticalOffset(ScreenUtils.getInstance().dip2px(40));
        }
    }

    private void setSpinnerSelected() {

        mWorkSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                setChildSpinner(mWorkList.get(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSmallSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getData(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void getData(boolean isSearch) {

        if (mDataList.size() > 0)
            mDataList.clear();

        if (isSearch) {
            for (OfferPersonalityTermModel model : mAllList) {

                if (model.getName().contains(mSearchEt.getText()) || model.getNumber().contains(mSearchEt.getText()))
                    mDataList.add(model);
            }
        } else {
            for (OfferPersonalityTermModel model : mAllList) {

                if (model.getBaseItemPidName().equals(mWorkSp.getSelectedItem()) &&
                        model.getBaseItemName().equals(mSmallSp.getSelectedItem()))
                    mDataList.add(model);

            }
        }

        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();

    }

    private void setRecyclerView() {

        mRecyclerView.setHasFixedSize(false);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(LibApplication.getInstance()));
        mRecyclerView.setOnPauseListenerParams(false, true);

        mAdapter = new OfferPersonalityTermAdapter(LibApplication.getInstance(), mDataList, this);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onItemClick(View view, int position, Object... object) {

        int id = view.getId();

        switch (id) {

            case R.id.personality_term_select_btn:

                OfferPersonalityVolumeActivity.start(false,mSpace,mDataList.get(position));

                break;
        }
    }
}
