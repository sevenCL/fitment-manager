package com.seven.library.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.seven.library.R;
import com.seven.library.callback.HttpRequestCallBack;
import com.seven.library.callback.JsonCallBack;
import com.seven.library.callback.UploadCallBack;
import com.seven.library.http.RequestUtils;
import com.seven.library.http.Urls;
import com.seven.library.json.JsonHelper;
import com.seven.library.ui.dialog.UploadingDialog;
import com.seven.library.utils.LogUtils;
import com.seven.library.utils.ToastUtils;
import com.seven.library.utils.image.ImageUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 上传图片
 *
 * @author seven
 */
public class UploadPictureTask extends AsyncTask<Void, Integer, Integer> implements HttpRequestCallBack {

    //环境
    private Activity mActivity;
    //请求id
    private int mRequestId;
    //图片类型
    private int mType;

    //图片地址集合
    private List<String> mPathList;
    //手机宽、高--用于压缩
    private int mHeight;
    private int mWidth;
    //结果返回
    private UploadCallBack mCallBack;


    //进度条弹窗
    private UploadingDialog mDialog;
    //文件总长度
    private long mCountLength;
    //压缩后图片地址
    private List<String> mNewPathList;
    //结果
    private List<String> mResultList;

    public UploadPictureTask(Activity activity, int requestId, int type, List<String> list,
                             int height, int width, UploadCallBack callBack) {

        this.mActivity = activity;
        this.mRequestId = requestId;
        this.mType = type;

        this.mPathList = list;
        this.mHeight = height;
        this.mWidth = width;
        this.mCallBack = callBack;

        mNewPathList = new ArrayList<>();
        mResultList = new ArrayList<>();

        for (String path : mPathList)
            mCountLength += new File(path).length();

        showDialog();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(Void... params) {

        String savePath;

        for (String path : mPathList) {
            savePath = ImageUtils.getInstance().getSavePath();
            //压缩图片
            boolean isSave = ImageUtils.getInstance().setImage(savePath, path, mHeight, mWidth);
            if (isSave) {
                mNewPathList.add(savePath);
                mDialog.refreshProgress((int) (50f * ((float) mNewPathList.size() / (float) mPathList.size())));
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Integer integer) {

        RequestUtils.getInstance().upload(mRequestId, Urls.IMG_UPLOAD, mNewPathList, mType, this);

    }

    /**
     * 显示进度条
     */
    private void showDialog() {

        if (mDialog == null || !mDialog.isShowing()) {

            mDialog = new UploadingDialog(mActivity, R.style.Dialog, null);
            mDialog.show();
        }
    }

    @Override
    public void onSucceed(String result, int requestId) {

        LogUtils.println(" UploadPictureTask onSucceed IMG request " + result);

        if (TextUtils.isEmpty(result)) {
            ToastUtils.getInstance().showToast(R.string.msg_upload_failure);
            mDialog.dismiss();
            return;
        }

        JsonHelper.getInstance().jsonList(result, String.class, true, "pathList", new JsonCallBack() {
            @Override
            public void onSucceed(Object data) {

                if (data == null) {
                    ToastUtils.getInstance().showToast(R.string.msg_upload_failure);
                    mDialog.dismiss();
                    return;
                }

                for (String path : (List<String>) data)
                    mResultList.add(path);

                mCallBack.uploadCallBack(mRequestId,mResultList);

                ToastUtils.getInstance().showToast(R.string.msg_upload_success);
                mDialog.refreshProgress(100);//progress==100时会延迟1s后dismiss
            }

            @Override
            public void onFailure(String error) {
                ToastUtils.getInstance().showToast(error);
                mDialog.dismiss();
            }
        });
    }

    @Override
    public void onFailure(String error, int requestId) {
        ToastUtils.getInstance().showToast(error);
        mDialog.dismiss();
    }

    @Override
    public void onProgress(long progress, int requestId) {

        mDialog.refreshProgress(50 + (int) (50F * ((float) progress / (float) mCountLength)));
    }
}
