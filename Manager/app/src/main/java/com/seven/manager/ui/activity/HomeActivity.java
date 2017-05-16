package com.seven.manager.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;

import com.frankchen.mvc.aidl.Task;
import com.seven.library.base.BaseTitleActivity;
import com.seven.library.config.RunTimeConfig;
import com.seven.library.task.ActivityStack;
import com.seven.library.utils.PermissionUtils;
import com.seven.library.utils.ResourceUtils;
import com.seven.manager.R;
import com.seven.manager.ui.fragment.tab.IncomeFragment;
import com.seven.manager.ui.fragment.tab.OrderFragment;
import com.seven.manager.ui.fragment.tab.ProjectFragment;
import com.seven.manager.ui.fragment.tab.UserFragment;

/**
 * 主页
 *
 * @author seven
 */

public class HomeActivity extends BaseTitleActivity {

    //底部标签
    private LinearLayout mOrderLl;
    private LinearLayout mProjectLl;
    private LinearLayout mIncomeLl;
    private LinearLayout mUserLl;

    //模块
    private OrderFragment mOrderFg;
    private ProjectFragment mProjectFg;
    private IncomeFragment mIncomeFg;
    private UserFragment mUserFg;

    /**
     * 跳转方法
     *
     * @param isFinishSrcAct 是否关闭启动页面
     */
    public static void start(boolean isFinishSrcAct) {
        Bundle param = new Bundle();
        param.putBoolean(RunTimeConfig.IntentCodeConfig.FINISH, isFinishSrcAct);
        ActivityStack.getInstance().startActivity(HomeActivity.class, isFinishSrcAct, param);
    }

    @Override
    public void onInitContentView(Bundle savedInstanceState) {

        initView();

        initData(null);

    }

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void onLeftButtonClicked() {

    }

    @Override
    public void onRightButtonClicked() {

    }

    @Override
    public void onRightTextClicked() {

    }

    @Override
    public void onReceiveNotification(Task task) {
        super.onReceiveNotification(task);

        switch (task.getWhat()) {

            case RunTimeConfig.ActionWhatConfig.QUOTATION_ORDER:

                if (mOrderFg == null)
                    mOrderFg = new OrderFragment();

                mOrderFg.onReceiveNotification(task);
                break;

        }
    }

    @Override
    public void initView() {

        mOrderLl = getView(mOrderLl, R.id.tab_order_ll);
        mProjectLl = getView(mProjectLl, R.id.tab_project_ll);
        mIncomeLl = getView(mIncomeLl, R.id.tab_income_ll);
        mUserLl = getView(mUserLl, R.id.tab_user_ll);

    }

    @Override
    public void initData(Intent intent) {

        //存储调用、手机状态权限
        PermissionUtils.getInstance(getPackageManager()).sdcardPermission(this);

        //默认选中预约
        mOrderFg = new OrderFragment();
        changeTabSelected(mOrderLl, mOrderFg, R.string.bottom_tab_order);
    }

    public void btClick(View view) {

        int id = view.getId();

        LinearLayout tabLayout = null;
        Fragment fragment = null;
        int title = 0;

        switch (id) {

            //预约
            case R.id.tab_order_ll:

                mOrderFg = new OrderFragment();
                tabLayout = mOrderLl;
                fragment = mOrderFg;
                title = R.string.bottom_tab_order;

                break;

            //工程
            case R.id.tab_project_ll:

                mProjectFg = new ProjectFragment();
                tabLayout = mProjectLl;
                fragment = mProjectFg;
                title = R.string.bottom_tab_project;

                break;

            //收益
            case R.id.tab_income_ll:

                mIncomeFg = new IncomeFragment();
                fragment = mIncomeFg;
                tabLayout = mIncomeLl;
                title = R.string.bottom_tab_income;

                break;

            //我的
            case R.id.tab_user_ll:

                mUserFg = new UserFragment();
                fragment = mUserFg;
                tabLayout = mUserLl;
                title = R.string.bottom_tab_user;

                break;
        }

        if (tabLayout != null && fragment != null)
            changeTabSelected(tabLayout, fragment, title);
    }

    /**
     * 改变tab状态
     *
     * @param layout
     */
    private void changeTabSelected(LinearLayout layout, Fragment fragment, int title) {
        if (!layout.isSelected()) {
            mOrderLl.setSelected(layout == mOrderLl);
            mProjectLl.setSelected(layout == mProjectLl);
            mIncomeLl.setSelected(layout == mIncomeLl);
            mUserLl.setSelected(layout == mUserLl);
            replaceFragment(R.id.home_container_fl, fragment);
            setTitle(ResourceUtils.getInstance().getText(title));
        }
    }

}
