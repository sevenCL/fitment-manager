package com.seven.manager.ui.dialog.newoffer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.seven.library.base.BaseDialog;
import com.seven.library.callback.DialogClickCallBack;
import com.seven.manager.R;

/**
 * @author seven
 */

public class SubmitOfferDialog extends BaseDialog {

    private String total;
    private String reward;
    private String amount;

    private RelativeLayout mCancel;
    private RelativeLayout mSure;

    private TextView mTotal;
    private TextView mReward;
    private TextView mAmount;

    public SubmitOfferDialog(Activity activity, int theme, DialogClickCallBack dialogClickCallBack) {
        super(activity, theme, dialogClickCallBack);
    }

    public SubmitOfferDialog(Activity activity, int theme, String total, String reward, String amount, DialogClickCallBack dialogClickCallBack) {
        super(activity, theme, dialogClickCallBack);

        this.total = total;
        this.reward = reward;
        this.amount = amount;

    }

    @Override
    public int getRootLayoutId() {

        isTouch = true;

        return R.layout.dialog_submit_offer;
    }

    @Override
    public void onInitRootView(Bundle savedInstanceState) {

        initView();

        initData();

    }

    @Override
    public void initView() {

        mCancel = getView(mCancel, R.id.submit_cancel_rl);
        mSure = getView(mSure, R.id.submit_sure_rl);

        mTotal = getView(mTotal, R.id.submit_total_tv);
        mReward = getView(mReward, R.id.submit_reward_tv);
        mAmount = getView(mAmount, R.id.submit_amount_tv);
    }

    @Override
    public void initData() {
        mCancel.setOnClickListener(this);
        mSure.setOnClickListener(this);

        mTotal.setText(total);
        mReward.setText(reward);
        mAmount.setText(amount);

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id) {

            case R.id.submit_sure_rl:

                mCallBack.onSureClick(v);

                break;
        }

        dismiss();

    }
}
