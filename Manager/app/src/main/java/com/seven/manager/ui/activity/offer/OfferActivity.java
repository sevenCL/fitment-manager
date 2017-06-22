package com.seven.manager.ui.activity.offer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frankchen.mvc.aidl.Task;
import com.seven.library.application.LibApplication;
import com.seven.library.base.BaseTitleActivity;
import com.seven.library.callback.DialogClickCallBack;
import com.seven.library.callback.HttpRequestCallBack;
import com.seven.library.callback.JsonCallBack;
import com.seven.library.config.RunTimeConfig;
import com.seven.library.db.res.ResPersonality;
import com.seven.library.http.RequestUtils;
import com.seven.library.http.Urls;
import com.seven.library.json.JsonHelper;
import com.seven.library.task.ActivityStack;
import com.seven.library.utils.LogUtils;
import com.seven.library.utils.ResourceUtils;
import com.seven.library.utils.ToastUtils;
import com.seven.manager.R;
import com.seven.manager.db.DbOfferHouse;
import com.seven.manager.db.DbOfferPackage;
import com.seven.manager.db.DbQuotation;
import com.seven.manager.model.offer.OfferPackageModel;
import com.seven.manager.model.offer.OfferPersonalityModel;
import com.seven.manager.model.offer.PackageItem;
import com.seven.manager.model.offer.PackageTotal;
import com.seven.manager.model.offer.PersonalityItem;
import com.seven.manager.model.offer.PersonalityTotal;
import com.seven.manager.model.order.OrderModel;
import com.seven.manager.ui.dialog.CommonDialog;
import com.seven.manager.ui.fragment.offer.DateFragment;
import com.seven.manager.ui.fragment.offer.PackageFragment;
import com.seven.manager.ui.fragment.offer.PersonalityFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

/**
 * 报价
 *
 * @author seven
 */

public class OfferActivity extends BaseTitleActivity implements HttpRequestCallBack {

    private OrderModel mModel;

    //tab 套餐、个性项、工期
    private RelativeLayout mTabPackage;
    private RelativeLayout mTabPersonality;
    private RelativeLayout mTabDate;

    //套餐、个性项、工期
    private PackageFragment mPackageFg;
    private PersonalityFragment mPersonalityFg;
    private DateFragment mDateFg;

    private Fragment mFrom;

    private DbManager db;
    private DbOfferHouse dbOfferHouse;
    private DbOfferPackage dbOfferPackage;
    private ResPersonality resPersonality;
    private DbQuotation dbQuotation;

    private int personalityHash;

    //-------fragment tag
    private int tag;

    private double packageMoney;
    private double personalityMoney;

    private TextView mPackageMoney;

    private TextView mPersonalityMoney;

    private TextView mTotalMoney;

    private CommonDialog mDialog;

    /**
     * 跳转方法
     *
     * @param isFinishSrcAct 是否关闭启动页面
     */
    public static void start(boolean isFinishSrcAct, Serializable serializable) {
        Bundle param = new Bundle();
        param.putBoolean(RunTimeConfig.IntentCodeConfig.FINISH, isFinishSrcAct);
        param.putSerializable(RunTimeConfig.IntentCodeConfig.SERIALIZABLE, serializable);
        ActivityStack.getInstance().startActivity(OfferActivity.class, isFinishSrcAct, param);
    }

    @Override
    public void onInitContentView(Bundle savedInstanceState) {

        initView();

        initData(null);

    }

    @Override
    public int getContentLayoutId() {

        isLeftBtn = true;

        return R.layout.activity_offer;
    }

    @Override
    public void onLeftButtonClicked() {

        finish();

    }

    @Override
    public void onRightButtonClicked() {

        switch (tag) {

            case RunTimeConfig.OfferFGTagConfig.TAG_PACKAGE:

                if (mPackageFg == null)
                    mPackageFg = new PackageFragment();

                mPackageFg.showHouseDialog();

                break;

            case RunTimeConfig.OfferFGTagConfig.TAG_PERSONALITY:

                OfferPersonalitySpaceActivity.start(false);

                break;
        }

    }

    @Override
    public void onRightTextClicked() {

    }

    @Override
    public void onReceiveNotification(Task task) {
        super.onReceiveNotification(task);

        switch (task.getWhat()) {

            case RunTimeConfig.ActionWhatConfig.OFFER_ADD:

                int visible = (int) task.getParamObjects()[0];
                setRightButtonVisible(visible);
                tag = (int) task.getParamObjects()[1];

                if (tag == RunTimeConfig.OfferFGTagConfig.TAG_PACKAGE) {

                    double money = (double) task.getParamObjects()[2];
                    packageMoney = money;

                    mPackageMoney.setText(ResourceUtils.getInstance().getFormatText(
                            R.string.offer_total_package, new DecimalFormat("#0.00").format(money)));
                } else if (tag == RunTimeConfig.OfferFGTagConfig.TAG_PERSONALITY) {

                    double money = (double) task.getParamObjects()[2];
                    personalityMoney = money;

                    mPersonalityMoney.setText(ResourceUtils.getInstance().getFormatText(
                            R.string.offer_total_personality, new DecimalFormat("#0.00").format(money)));
                }

                mTotalMoney.setText(ResourceUtils.getInstance().getFormatText(
                        R.string.offer_total_count, new DecimalFormat("#0.00").format(packageMoney + personalityMoney)));

                break;

            case RunTimeConfig.ActionWhatConfig.PERSONNALITY:

                if (mPersonalityFg == null)
                    mPersonalityFg = new PersonalityFragment();

                mPersonalityFg.onReceiveNotification(task);

                break;

        }

    }

    @Override
    public void initView() {

        mTabPackage = getView(mTabPackage, R.id.offer_package_btn);
        mTabPersonality = getView(mTabPersonality, R.id.offer_personality_btn);
        mTabDate = getView(mTabDate, R.id.offer_date_btn);

        mPackageMoney = getView(mPackageMoney, R.id.offer_packager_tv);
        mPersonalityMoney = getView(mPersonalityMoney, R.id.offer_personality_tv);
        mTotalMoney = getView(mTotalMoney, R.id.offer_total_tv);
    }

    @Override
    public void initData(Intent intent) {

        setTitle(ResourceUtils.getInstance().getText(R.string.offer));

        setRightButtonBackground(R.drawable.tianjia);

        if (intent == null)
            intent = getIntent();

        mModel = (OrderModel) intent.getSerializableExtra(RunTimeConfig.IntentCodeConfig.SERIALIZABLE);

        //默认选中套餐
        mPackageFg = new PackageFragment();
        changeTabSelected(mTabPackage, null, mPackageFg);

        //数据库
        db = x.getDb(LibApplication.daoConfig);

        try {
            dbOfferHouse = db.selector(DbOfferHouse.class).findFirst();
            dbOfferPackage = db.selector(DbOfferPackage.class).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }

        getOfferHouse();
    }

    public void btClick(View view) {

        int id = view.getId();

        RelativeLayout tabLayout = null;
        Fragment fragment = null;

        boolean isChangeFg = true;

        switch (id) {

            //套餐
            case R.id.offer_package_btn:

                if (mPackageFg == null)
                    mPackageFg = new PackageFragment();
                tabLayout = mTabPackage;
                fragment = mPackageFg;

                mPackageFg.updateView();
                isChangeFg = true;

                break;

            //个性项
            case R.id.offer_personality_btn:

                if (mPersonalityFg == null)
                    mPersonalityFg = new PersonalityFragment();
                tabLayout = mTabPersonality;
                fragment = mPersonalityFg;

                mPersonalityFg.updateView();
                isChangeFg = true;

                break;

            //工期
            case R.id.offer_date_btn:

                if (mDateFg == null)
                    mDateFg = new DateFragment();
                tabLayout = mTabDate;
                fragment = mDateFg;

                setRightButtonVisible(View.GONE);
                isChangeFg = true;

                break;

            //保存报价
            case R.id.offer_save_btn:

                showDialog();

                break;
        }

        if (isChangeFg) {
            if (tabLayout != null && fragment != null)
                changeTabSelected(tabLayout, mFrom, fragment);
        }
    }

    /**
     * 改变tab状态
     *
     * @param layout
     */
    private void changeTabSelected(RelativeLayout layout, Fragment from, Fragment to) {
        if (!layout.isSelected()) {
            mTabPackage.setSelected(layout == mTabPackage);
            mTabPersonality.setSelected(layout == mTabPersonality);
            mTabDate.setSelected(layout == mTabDate);
//            replaceFragment(R.id.offer_container_fl, fragment);
            switchFragment(R.id.offer_container_fl, from, to);
            mFrom = to;
        }
    }

    private void showDialog() {

        if (mDialog == null || !mDialog.isShowing()) {

            mDialog = new CommonDialog(this, R.style.Dialog, RunTimeConfig.DialogTagConfig.TAG_QUOTATION, new DialogClickCallBack() {
                @Override
                public void onCancelClick(View view) {

                }

                @Override
                public void onSureClick(View view) {

                    saveData();
                }

                @Override
                public void onClick(View view, Object... object) {

                }
            });
            mDialog.show();
        }

    }

    /**
     * 获取房间资源
     */
    private void getOfferHouse() {

        // TODO: 2017/5/8 获取报价套餐房间  这里的套餐id写死 固定399

        RequestUtils.getInstance(Urls.OFFER_HOUSE).offerHouse(
                RunTimeConfig.RequestConfig.OFFER_HOUSE, 1, 1, 1000, this);

        // TODO: 2017/5/11 获取套餐信息 这里的套餐id写死 固定399

        int hashCode = 0;

        if (dbOfferPackage != null)
            hashCode = dbOfferPackage.getHashCode();

        RequestUtils.getInstance(Urls.OFFER_PACKAGE).offerPackage(
                RunTimeConfig.RequestConfig.OFFER_PACKAGE, 1, LibApplication.branchId, hashCode, this);
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

            //获取报价套餐信息
            case RunTimeConfig.RequestConfig.OFFER_PACKAGE:

                try {

                    LogUtils.println(this.getClass().getName() + " onSucceed OFFER_PACKAGE request " + result);

                    if (TextUtils.isEmpty(new JSONObject(result).getJSONObject("data").toString()))
                        return;

                    dbOfferPackage = db.selector(DbOfferPackage.class).findFirst();

                    if (dbOfferPackage == null) {//第一次创建表并添加数据

                        if (TextUtils.isEmpty(result))
                            return;

                        dbOfferPackage = new DbOfferPackage();
                        dbOfferPackage.setId(1);
                        dbOfferPackage.setProjectId(1);
                        dbOfferPackage.setHashCode(JsonHelper.getInstance().jsonStringHash(result));
                        dbOfferPackage.setParams(Urls.OFFER_PACKAGE);
                        dbOfferPackage.setContent(result);
                        dbOfferPackage.setCreateTime(System.currentTimeMillis());

                        db.save(dbOfferPackage);

                    } else {//更新

                        if (new JSONObject(result).getJSONObject("data").getInt("hashCode") == dbOfferPackage.getHashCode())
                            return;

                        dbOfferPackage.setHashCode(JsonHelper.getInstance().jsonStringHash(result));
                        dbOfferPackage.setContent(result);
                        dbOfferPackage.setCreateTime(System.currentTimeMillis());

                        db.saveOrUpdate(dbOfferPackage);
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;

        }
    }

    @Override
    public void onFailure(String error, int requestId) {

    }

    @Override
    public void onProgress(long progress, int requestId) {

    }

    private void saveData() {

        try {
            dbQuotation = db.selector(DbQuotation.class).where("projectId", "=", mModel.getProjectId()).findFirst();
            resPersonality = db.selector(ResPersonality.class).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }

        //套餐
        if (mPackageFg == null)
            return;

        List<OfferPackageModel> packageList = mPackageFg.getPackageList();

        if (packageList == null || packageList.size() == 0) {
            ToastUtils.getInstance().showToast(R.string.toast_offer_save);
            return;
        }

        List<OfferPersonalityModel> personalityList = null;

        if (mPersonalityFg != null) {
            personalityList = mPersonalityFg.getPersonalityList();
        }

        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();

        JSONArray packageArray = new JSONArray();
        JSONObject packageObject = null;

        JSONArray personality = new JSONArray();
        JSONObject personalityObject = null;

        int personalityCount = 0;
        double personalityMoney = 0;

        double packageMoney = 0;
        double area = 0;
        String houseInfo = "";

        int halles = 0;
        int rooms = 0;
        int cookhouse = 0;
        int washroom = 0;
        int balcony = 0;
        int others = 0;

        double totalAmount = 0;

        if (resPersonality != null && !TextUtils.isEmpty(resPersonality.getContent()))
            JsonHelper.getInstance().jsonStringSingle(resPersonality.getContent(),
                    true, "hashCode", new JsonCallBack() {
                        @Override
                        public void onSucceed(Object data) {

                            personalityHash = (int) data;

                        }

                        @Override
                        public void onFailure(String error) {

                        }
                    });

        try {
            if (personalityList != null) {
                for (OfferPersonalityModel model : personalityList) {

                    if (model.getViewType() == RunTimeConfig.ModelConfig.OFFER_PERSONALITY_ITEM) {

                        personalityObject = new JSONObject();

                        personalityObject.put("planSpaceId", ((PersonalityItem) model).getSpaceId());
                        personalityObject.put("itemId", ((PersonalityItem) model).getTermId());
                        personalityObject.put("quantity", ((PersonalityItem) model).getVolume());
                        personalityObject.put("name", ((PersonalityItem) model).getTerm());
                        personalityObject.put("price", ((PersonalityItem) model).getSmallTotal());
                        personalityObject.put("unit", ((PersonalityItem) model).getUnit());

                        personality.put(personalityObject);

                    } else {

                        personalityCount = ((PersonalityTotal) model).getCount();
                        personalityMoney = ((PersonalityTotal) model).getTotal();
                    }

                }
            }

            for (OfferPackageModel model : packageList) {

                if (model.getViewType() == RunTimeConfig.ModelConfig.OFFER_PACKAGE_ITEM) {

                    packageObject = new JSONObject();

                    packageObject.put("planSpaceId", ((PackageItem) model).getSpaceId());
                    packageObject.put("spaceType", ((PackageItem) model).getType());
                    packageObject.put("spaceArea", ((PackageItem) model).getArea());
                    packageObject.put("perimeter", ((PackageItem) model).getPerimeter());
                    packageObject.put("name", ((PackageItem) model).getName());

                    packageArray.put(packageObject);

                } else {

                    packageMoney = ((PackageTotal) model).getTotalMoney();
                    area = ((PackageTotal) model).getTotalArea();
                    houseInfo = ((PackageTotal) model).getHouseInfo();

                }

            }

            for (OfferPackageModel model : packageList) {

                if (model.getViewType() == RunTimeConfig.ModelConfig.OFFER_PACKAGE_ITEM) {

                    switch (((PackageItem) model).getType()) {

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
                        case RunTimeConfig.HouseConfig.OTHER:
                            others++;
                            break;

                    }

                }
            }

            totalAmount = packageMoney + personalityMoney;

            data.put("ownerId", mModel.getOwnerId());
            data.put("planId", "1");
            data.put("ownerName", mModel.getOwnerName());
            data.put("projectId", mModel.getProjectId());
            data.put("houseNumber", mModel.getHouseNumber());
            data.put("totalAmount", totalAmount);
            data.put("area", area);
            data.put("halles", halles);
            data.put("rooms", rooms);
            data.put("cookhouse", cookhouse);
            data.put("washroom", washroom);
            data.put("balcony", balcony);
            data.put("others", others);
            data.put("houseInfo", houseInfo);
            data.put("packageMoney", packageMoney);
            data.put("personalityCount", personalityCount);
            data.put("personalityMoney", personalityMoney);
            data.put("hashCode", personalityHash);
            data.put("itemsListJson", packageArray);
            data.put("addItemsListJson", personality);

            jsonObject.put("status", 1);
            jsonObject.put("code", 0);
            jsonObject.put("msg", "查询成功");
            jsonObject.put("data", data);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if (dbQuotation == null) {
                dbQuotation = new DbQuotation();
                dbQuotation.setProjectId(mModel.getProjectId());
                dbQuotation.setContent(jsonObject.toString());
                db.save(dbQuotation);
            }else {
                dbQuotation.setContent(jsonObject.toString());
                db.saveOrUpdate(dbQuotation);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

        OfferQuotationActivity.start(false, mModel,RunTimeConfig.FlowConfig.OFFER_IS_COMPILE);

    }
/*
 *{
	"status":1,
	"code":0,
	"msg":"查询成功",
	"data":
	{
		"ownerId":1,
		"ownerName":"seven",
		"projectId":1,
		"houseNumber":"东大街芷泉1588号",
		"totalAmount":1555.00,
		"area":120.00,
		"halls":2,
		"rooms":2,
		"cookhouse":1,
		"washroom":1,
		"balcony":1,
		"others":1,
		"houseInfo":"1室2厅",
		"packageMoney":1522.00,
		"personalityCount":2,
		"personalityMoney":144.00,
		"hashCode":111,
		"itemsListJson":
		[
			{
				"planSpaceId":1,
				"spaceType":1,
				"spaceArea":10,
				"perimeter":10
			},
			...
		],
		"addItemsListJson":
		[
			{
				"itemId":60,
				"quantity":11,
				"planSpaceId":1
			},
			...
		]
	}
}
 */
}
