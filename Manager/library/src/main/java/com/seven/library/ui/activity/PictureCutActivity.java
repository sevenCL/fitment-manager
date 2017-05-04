package com.seven.library.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.frankchen.mvc.aidl.Task;
import com.seven.library.R;
import com.seven.library.base.BaseTitleActivity;
import com.seven.library.config.RunTimeConfig;
import com.seven.library.task.ActivityStack;
import com.seven.library.utils.ResourceUtils;
import com.seven.library.utils.image.ImageUtils;
import com.seven.library.view.cut.ClipViewLayout;

/**
 * 图片剪裁
 *
 * @author seven
 */
public class PictureCutActivity extends BaseTitleActivity {

    //图片路径
    private String path;

    //活动(用于一一对应返回图片集合)
    private int what;

    private ClipViewLayout mClipLl;

    /**
     * 跳转方法
     *
     * @param isFinishSrcAct 是否关闭启动页面
     */
    public static void start(boolean isFinishSrcAct, String path, int what) {
        Bundle param = new Bundle();
        param.putBoolean(RunTimeConfig.IntentCodeConfig.FINISH, isFinishSrcAct);
        param.putString(RunTimeConfig.IntentCodeConfig.PATH, path);
        param.putInt(RunTimeConfig.IntentCodeConfig.ACTION_WHAT, what);
        ActivityStack.getInstance().startActivity(PictureCutActivity.class, isFinishSrcAct, param);
    }

    @Override
    public void onInitContentView(Bundle savedInstanceState) {

        initView();

        initData(null);

    }

    @Override
    public int getContentLayoutId() {

        isLeftBtn = true;
        isRightTextBtn = true;

        return R.layout.activity_picture_cut;
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

        String path = ImageUtils.getInstance().getSavePath();

        ImageUtils.getInstance().savePicture(path, mClipLl.clip());

        //将图片路径传回
        doAction(RunTimeConfig.ActionConfig.PICTURE_SELECT, Task.PUBLIC,
                Task.MIN_AUTHORITY, RunTimeConfig.ActionWhatConfig.HEADER, path);

        finish();
    }

    @Override
    public void initView() {

        mClipLl = getView(mClipLl, R.id.cut_ll);

    }

    @Override
    public void initData(Intent intent) {

        setTitle(ResourceUtils.getInstance().getText(R.string.cut));
        setRightText(ResourceUtils.getInstance().getText(R.string.sure));

        if (intent == null)
            intent = getIntent();

        //获得路径
        path = intent.getStringExtra(RunTimeConfig.IntentCodeConfig.PATH);

        if (TextUtils.isEmpty(path))
            return;

        //活动id -1 return
        what = intent.getIntExtra(RunTimeConfig.IntentCodeConfig.ACTION_WHAT, -1);

        //加载剪裁视图
        mClipLl.setImageSrc(path);
    }
}
