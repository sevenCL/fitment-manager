package com.seven.manager.ui.activity.offer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.frankchen.mvc.aidl.Task;
import com.seven.library.base.BaseTitleActivity;
import com.seven.library.config.RunTimeConfig;
import com.seven.library.task.ActivityStack;
import com.seven.library.utils.ResourceUtils;
import com.seven.library.utils.ToastUtils;
import com.seven.manager.R;
import com.seven.manager.model.offer.OfferHouseModel;
import com.seven.manager.model.offer.OfferPersonalityTermModel;

import java.io.Serializable;

/**
 * 报价-个性项-方量
 *
 * @author seven
 */

public class OfferPersonalityVolumeActivity extends BaseTitleActivity {

    private OfferHouseModel houseModel;

    private OfferPersonalityTermModel termModel;

    private TextView mSpace;
    private TextView mTerm;
    private TextView mTermHint;
    private TextView mUnit;

    private EditText mVolumeEt;

    /**
     * 跳转方法
     *
     * @param isFinishSrcAct 是否关闭启动页面
     */
    public static void start(boolean isFinishSrcAct, Serializable serializable, Serializable term) {
        Bundle param = new Bundle();
        param.putBoolean(RunTimeConfig.IntentCodeConfig.FINISH, isFinishSrcAct);
        param.putSerializable(RunTimeConfig.IntentCodeConfig.SERIALIZABLE, serializable);
        param.putSerializable(RunTimeConfig.IntentCodeConfig.TERM, term);
        ActivityStack.getInstance().startActivity(OfferPersonalityVolumeActivity.class, isFinishSrcAct, param);
    }

    @Override
    public void onInitContentView(Bundle savedInstanceState) {

        initView();

        initData(null);

    }

    @Override
    public int getContentLayoutId() {

        isLeftBtn = true;

        return R.layout.activity_personality_volume;
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

        mSpace = getView(mSpace, R.id.personality_volume_space);
        mTerm = getView(mTerm, R.id.personality_volume_price);
        mTermHint = getView(mTermHint, R.id.personality_volume_term_name);
        mUnit = getView(mUnit, R.id.personality_volume_unit);

        mVolumeEt = getView(mVolumeEt, R.id.personality_volume_et);
    }

    @Override
    public void initData(Intent intent) {

        setTitle(ResourceUtils.getInstance().getText(R.string.offer_personality_add));

        if (intent == null)
            intent = getIntent();

        houseModel = (OfferHouseModel) intent.getSerializableExtra(RunTimeConfig.IntentCodeConfig.SERIALIZABLE);
        termModel = (OfferPersonalityTermModel) intent.getSerializableExtra(RunTimeConfig.IntentCodeConfig.TERM);

        if (houseModel == null || termModel == null)
            return;

        mSpace.setText(houseModel.getName());
        mTerm.setText(termModel.getName() + "/" + termModel.getUnitName());
        mTermHint.setText(ResourceUtils.getInstance().getFormatText(R.string.offer_personality_term_volume, termModel.getName()));
        mUnit.setText(termModel.getUnitName());

    }

    public void btClick(View view) {

        int id = view.getId();

        switch (id) {

            case R.id.personality_volume_sure:

                if (TextUtils.isEmpty(mVolumeEt.getText())) {
                    ToastUtils.getInstance().showToast(R.string.toast_volume);
                    return;
                }

                termModel.setVolume(Double.parseDouble(mVolumeEt.getText().toString()));

                doAction(RunTimeConfig.ActionConfig.OFFER, Task.PUBLIC, Task.MIN_AUTHORITY,
                        RunTimeConfig.ActionWhatConfig.PERSONNALITY, houseModel, termModel);

                ActivityStack.getInstance().finishActivity(OfferPersonalityVolumeActivity.class);
                ActivityStack.getInstance().finishActivity(OfferPersonalityTermActivity.class);
                ActivityStack.getInstance().finishActivity(OfferPersonalitySpaceActivity.class);

                break;

        }
    }

}
