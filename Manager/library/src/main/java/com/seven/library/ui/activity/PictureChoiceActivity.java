package com.seven.library.ui.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.frankchen.mvc.aidl.Task;
import com.seven.library.R;
import com.seven.library.adapter.activity.PictureChoiceAdapter;
import com.seven.library.application.LibApplication;
import com.seven.library.base.BaseTitleActivity;
import com.seven.library.callback.ListItemCallBack;
import com.seven.library.config.RunTimeConfig;
import com.seven.library.task.ActivityStack;
import com.seven.library.utils.LogUtils;
import com.seven.library.utils.ResourceUtils;
import com.seven.library.utils.ToastUtils;
import com.seven.library.utils.image.ImageUtils;
import com.seven.library.view.AutoLoadRecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 相册
 *
 * @author seven
 */
public class PictureChoiceActivity extends BaseTitleActivity implements ListItemCallBack {

    //图片选择方式
    private int model;

    //活动(用于一一对应返回图片集合)
    private int what;

    //自定义recycler
    private AutoLoadRecyclerView mRecyclerView;

    //集合
    private List<Map<String, Object>> mDataList;
    private Map<String, Object> mDataMap;

    //适配器
    private PictureChoiceAdapter mAdapter;

    //相片保存路径
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
        ActivityStack.getInstance().startActivity(PictureChoiceActivity.class, isFinishSrcAct, param);
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
        isRightTextBtn = true;

        return R.layout.activity_picture_choice;
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
        photoSelect();
    }

    @Override
    public void initView() {

        mRecyclerView = getView(mRecyclerView, R.id.picture_recycler_view);

    }

    @Override
    public void initData(Intent intent) {

        setTitle(ResourceUtils.getInstance().getText(R.string.choice));
        setRightText(ResourceUtils.getInstance().getText(R.string.sure));

        if (intent == null)
            intent = getIntent();

        //图片选择方式 -1 return
        model = intent.getIntExtra(RunTimeConfig.IntentCodeConfig.PICTURE_MODEL, -1);

        //活动id -1 return
        what = intent.getIntExtra(RunTimeConfig.IntentCodeConfig.ACTION_WHAT, -1);

        mDataList = new ArrayList<>();
    }

    /**
     * 获取数据
     */
    private void getData() {

        try {

            //判断sd卡
            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
                return;

            Uri imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            ContentResolver mContentResolver = LibApplication.getInstance().getContentResolver();

            // 取出条件为jpg或者png
            Cursor cursor = mContentResolver.query(
                    imageUri,
                    null,
                    MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?",
                    new String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED
            );

            //从媒体数据库取出图片路径
            if (cursor != null) {
                /**
                 * 第一个位置是否加入照相机功能(类似微信)
                 */
                mDataMap = new HashMap<>();
                mDataMap.put("type", RunTimeConfig.PictureConfig.CAMERA);
                mDataList.add(mDataMap);

                String path;

                if (cursor.moveToLast()) {

                    path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));

                    if (new File(path).length() > 0) {

                        mDataMap = new HashMap<>();
                        mDataMap.put("type", RunTimeConfig.PictureConfig.PICTURE);
                        mDataMap.put("path", path);
                        mDataMap.put("state", RunTimeConfig.PictureConfig.OUT);

                        mDataList.add(mDataMap);
                    }

                    while (cursor.moveToPrevious()) {

                        path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));

                        if (new File(path).length() > 0) {

                            mDataMap = new HashMap<>();
                            mDataMap.put("type", RunTimeConfig.PictureConfig.PICTURE);
                            mDataMap.put("path", path);
                            mDataMap.put("state", RunTimeConfig.PictureConfig.OUT);

                            mDataList.add(mDataMap);
                        }
                    }

                }

                cursor.close();
            }
        } catch (Exception e) {

            ToastUtils.getInstance().showToast(R.string.msg_permission);

        }
    }

    /**
     * 添加条目
     */
    private void setRecyclerView() {

        if (mDataList == null && mDataList.size() == 0)
            return;

        mRecyclerView.setHasFixedSize(false);

        mRecyclerView.setLayoutManager(new GridLayoutManager(LibApplication.getInstance(), 4));
        mRecyclerView.setOnPauseListenerParams(false, true);

        mAdapter = new PictureChoiceAdapter(LibApplication.getInstance(), mDataList, this, screenWidth);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onItemClick(View view, int position, Object... object) {

        int type = (int) mDataList.get(position).get("type");

        switch (type) {

            case RunTimeConfig.PictureConfig.CAMERA:
                camera();
                break;
            case RunTimeConfig.PictureConfig.PICTURE:
                photo(position);
                break;
        }
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
     * 相册
     */
    private void photo(int position) {

        switch (model) {

            case RunTimeConfig.PictureConfig.SINGLE:

                for (int i = 1; i < mDataList.size(); i++) {
                    if ((int) mDataList.get(i).get("state") == RunTimeConfig.PictureConfig.IN) {
                        mDataList.get(i).put("state", RunTimeConfig.PictureConfig.OUT);
                        mAdapter.notifyItemChanged(i);
                    }
                }
                mDataList.get(position).put("state", RunTimeConfig.PictureConfig.IN);
                mAdapter.notifyItemChanged(position);

                break;

            case RunTimeConfig.PictureConfig.MULTI:

                if ((int) mDataList.get(position).get("state") == RunTimeConfig.PictureConfig.OUT) {

                    int count = 0;
                    for (int i = 1; i < mDataList.size(); i++) {
                        if ((int) mDataList.get(i).get("state") == RunTimeConfig.PictureConfig.IN)
                            count++;

                    }

                    if (count == 9) {
                        ToastUtils.getInstance().showToast(R.string.msg_picture_max);
                        return;
                    }

                    mDataList.get(position).put("state", RunTimeConfig.PictureConfig.IN);
                } else {
                    mDataList.get(position).put("state", RunTimeConfig.PictureConfig.OUT);
                }
                mAdapter.notifyItemChanged(position);

                break;
        }
    }

    /**
     * 图片下一步操作处理
     */
    private void cameraPhoto() {

        if (model == -1 || what == -1)
            return;

        switch (what) {
            //头像
            case RunTimeConfig.ActionWhatConfig.HEADER:

                PictureCutActivity.start(true, mCameraPath, what);

                break;
        }

    }

    /**
     * 选择图片的结果
     */
    private void photoSelect() {

        switch (what) {
            //头像
            case RunTimeConfig.ActionWhatConfig.HEADER:

                PictureCutActivity.start(true, photoPath(), what);

                break;
            default:
                doAction(RunTimeConfig.ActionConfig.PICTURE_SELECT, Task.PUBLIC,
                        Task.MIN_AUTHORITY, what, photoPath());
                finish();
                break;

        }

    }

    /**
     * 获得所有图片路径
     *
     * @return
     */
    private String photoPath() {

        switch (model) {
            //单张
            case RunTimeConfig.PictureConfig.SINGLE:
                for (Map<String, Object> map : mDataList) {
                    if ((int) map.get("type") == RunTimeConfig.PictureConfig.PICTURE) {
                        if ((int) map.get("state") == RunTimeConfig.PictureConfig.IN)
                            return (String) map.get("path");
                    }
                }
                break;
            //多张
            case RunTimeConfig.PictureConfig.MULTI:

                StringBuffer buffer = new StringBuffer();

                for (Map<String, Object> map : mDataList) {
                    if ((int) map.get("type") == RunTimeConfig.PictureConfig.PICTURE) {
                        if ((int) map.get("state") == RunTimeConfig.PictureConfig.IN) {
                            if (TextUtils.isEmpty(buffer.toString()))
                                buffer.append(map.get("path"));
                            else
                                buffer.append("," + map.get("path"));
                        }
                    }
                }

                return buffer.toString();
        }

        return "";
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

}
