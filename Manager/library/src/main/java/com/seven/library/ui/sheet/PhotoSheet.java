package com.seven.library.ui.sheet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.frankchen.mvc.aidl.Task;
import com.seven.library.R;
import com.seven.library.base.BaseSheetActivity;
import com.seven.library.config.RunTimeConfig;
import com.seven.library.task.ActivityStack;
import com.seven.library.ui.activity.PictureChoiceActivity;
import com.seven.library.ui.activity.PictureCutActivity;
import com.seven.library.utils.LogUtils;
import com.seven.library.utils.ToastUtils;
import com.seven.library.utils.image.ImageUtils;

import java.io.File;

/**
 * @author seven
 */
public class PhotoSheet extends BaseSheetActivity {

    //图片选择方式
    private int model;

    //活动(用于一一对应返回图片集合)
    private int what;

    //拍照图片保存的路径
    private String mCameraPath;

    /**
     * 跳转方法
     *
     * @param isFinishSrcAct 是否关闭启动页面
     */
    public static void start(boolean isFinishSrcAct, int model, int what) {
        Bundle param = new Bundle();
        param.putBoolean(RunTimeConfig.IntentCodeConfig.FINISH, isFinishSrcAct);
        param.putInt(RunTimeConfig.IntentCodeConfig.PICTURE_MODEL, model);
        param.putInt(RunTimeConfig.IntentCodeConfig.ACTION_WHAT, what);
        ActivityStack.getInstance().startActivity(PhotoSheet.class, isFinishSrcAct, param);
    }

    @Override
    public void onInitRootView(Bundle savedInstanceState) {

        initView();

        initData(null);

    }

    @Override
    public int getRootLayoutId() {
        return R.layout.sheet_activity_photo;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(Intent intent) {

        if (intent == null)
            intent = getIntent();

        //图片选择方式 -1 return
        model = intent.getIntExtra(RunTimeConfig.IntentCodeConfig.PICTURE_MODEL, -1);

        //活动id -1 return
        what = intent.getIntExtra(RunTimeConfig.IntentCodeConfig.ACTION_WHAT, -1);
    }

    public void btClick(View view) {

        int id = view.getId();

        if (id == R.id.avatar_root) {//点击操作外区域
            finish();
            animBottomOut();
        } else if (id == R.id.avatar_camera_rl)//调用相机拍照
            camera();
        else if (id == R.id.avatar_photo_rl)//调用自定义相册
            PictureChoiceActivity.start(true, model, what);
    }

    /**
     * 相机
     */
    private void camera() {

        try {

            Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            mCameraPath = ImageUtils.getInstance().getSavePath();

            File file = new File(mCameraPath);
            Uri uri = Uri.fromFile(file);
            camera.putExtra(MediaStore.EXTRA_OUTPUT, uri);

            startActivityForResult(camera, RunTimeConfig.CommonConfig.CAMERA_CODE);

        } catch (Exception e) {
            LogUtils.println("Avatar sheet camera method!" + e);
        }

    }

    /**
     * 图片下一步操作处理
     */
    private void cameraPhoto() {

        if (model == -1 || what == -1)
            return;

        switch (what) {
            case RunTimeConfig.ActionWhatConfig.HEADER://头像

                PictureCutActivity.start(true, mCameraPath, what);

                break;

            case RunTimeConfig.ActionWhatConfig.ID_CARD_FRONT://身份证正面
            case RunTimeConfig.ActionWhatConfig.ID_CARD_REVERSE://身份证反面

                //将图片路径传回
                doAction(RunTimeConfig.ActionConfig.PICTURE_SELECT, Task.PUBLIC, Task.MIN_AUTHORITY, what, mCameraPath);

                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == RunTimeConfig.CommonConfig.CAMERA_CODE) {
            if (!TextUtils.isEmpty(mCameraPath)) {

                cameraPhoto();

                finish();

            } else {
                ToastUtils.getInstance().showToast(R.string.msg_camera);
            }
        }
    }

    /**
     * 监听返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
            animBottomOut();
        }
        return super.onKeyDown(keyCode, event);
    }
}
