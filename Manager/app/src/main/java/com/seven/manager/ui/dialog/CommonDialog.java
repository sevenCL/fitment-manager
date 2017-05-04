package com.seven.manager.ui.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.seven.library.base.BaseDialog;
import com.seven.library.callback.DialogClickCallBack;
import com.seven.library.config.RunTimeConfig;
import com.seven.library.utils.ResourceUtils;
import com.seven.manager.R;

/**
 * 常规弹框
 *
 * @author seven
 */

public class CommonDialog extends BaseDialog {

    //确定展示的内容
    private int tag;

    private TextView mContent;

    private RelativeLayout mCancel;
    private RelativeLayout mSure;

    public CommonDialog(Activity activity, int theme, DialogClickCallBack dialogClickCallBack) {
        super(activity, theme, dialogClickCallBack);
    }

    public CommonDialog(Activity activity, int theme, int tag, DialogClickCallBack dialogClickCallBack) {
        this(activity, theme, dialogClickCallBack);
        this.tag = tag;
    }

    @Override
    public int getRootLayoutId() {

        isTouch = true;

        return R.layout.dialog_common;
    }

    @Override
    public void onInitRootView(Bundle savedInstanceState) {

        initView();

        initData();

    }

    @Override
    public void initView() {

        mContent = getView(mContent, R.id.common_tv);

        mCancel = getView(mCancel, R.id.common_cancel_rl);
        mSure = getView(mSure, R.id.common_sure_rl);
    }

    @Override
    public void initData() {

        mCancel.setOnClickListener(this);
        mSure.setOnClickListener(this);

        setTagContent(tag);

    }

    private void setTagContent(int tag) {

        String content = "";

        switch (tag) {

            case RunTimeConfig.DialogTagConfig.TAG_REFUSE:

                content = ResourceUtils.getInstance().getText(R.string.dialog_refuse);

                break;

            case RunTimeConfig.DialogTagConfig.TAG_ACCEPT:

                content = ResourceUtils.getInstance().getText(R.string.dialog_accept);

                break;

        }

        mContent.setText(content);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id) {

            case R.id.common_sure_rl:

                mCallBack.onSureClick(v);

                break;
        }

        dismiss();

    }
}
