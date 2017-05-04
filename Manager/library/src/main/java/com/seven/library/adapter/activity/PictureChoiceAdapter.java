package com.seven.library.adapter.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.seven.library.R;
import com.seven.library.base.BaseViewHolder;
import com.seven.library.callback.ListItemCallBack;
import com.seven.library.config.RunTimeConfig;
import com.seven.library.utils.image.ImageLoadProxy;

import java.util.List;
import java.util.Map;

/**
 * 选择图片的适配器
 *
 * @author seven
 */
public class PictureChoiceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Map<String, Object>> mList;
    private ListItemCallBack mCallBack;
    private int mScreenWidth;

    private DisplayImageOptions options;
    private LayoutInflater mInflater;

    public PictureChoiceAdapter(Context context, List<Map<String, Object>> list,
                                ListItemCallBack callBack, int screenWidth) {

        this.mContext = context;
        this.mList = list;
        this.mCallBack = callBack;
        this.mScreenWidth = screenWidth;

        options = ImageLoadProxy.getOptions4PictureList(R.drawable.ic_loading_small, R.drawable.ic_loading_failure);
        mInflater = LayoutInflater.from(mContext);

    }

    @Override
    public int getItemViewType(int position) {
        return (int) mList.get(position).get("type");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;

        switch (viewType) {

            case RunTimeConfig.PictureConfig.CAMERA:

                PictureCameraViewHolder cameraViewHolder = null;

                view = mInflater.inflate(R.layout.adapter_picture_camera, parent, false);

                cameraViewHolder = new PictureCameraViewHolder(view);

                return cameraViewHolder;

            case RunTimeConfig.PictureConfig.PICTURE:

                PictureChoiceViewHolder choiceViewHolder = null;

                view = mInflater.inflate(R.layout.adapter_picture_choice, parent, false);

                choiceViewHolder = new PictureChoiceViewHolder(view);

                return choiceViewHolder;

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof PictureCameraViewHolder)
            camera((PictureCameraViewHolder) holder, position);
        else
            picture((PictureChoiceViewHolder) holder, position);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class PictureCameraViewHolder extends BaseViewHolder implements View.OnClickListener {

        private RelativeLayout mLayout;

        private ImageView mCamera;

        public PictureCameraViewHolder(View itemView) {
            super(itemView);

            mLayout = getView(mLayout, R.id.camera_rl);
            mCamera = getView(mCamera, R.id.camera_iv);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            mCallBack.onItemClick(v, getLayoutPosition());

        }
    }

    public class PictureChoiceViewHolder extends BaseViewHolder implements View.OnClickListener {

        private RelativeLayout mLayout;

        private ImageView mPicture;

        private ImageView mState;

        public PictureChoiceViewHolder(View itemView) {
            super(itemView);

            mLayout = getView(mLayout, R.id.picture_rl);
            mPicture = getView(mPicture, R.id.picture_iv);
            mState = getView(mState, R.id.picture_state_iv);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            mCallBack.onItemClick(v, getLayoutPosition());

        }
    }

    /**
     * 相机
     *
     * @param holder
     * @param position
     */

    private void camera(PictureCameraViewHolder holder, int position) {
        ViewGroup.LayoutParams params = holder.mLayout.getLayoutParams();
        params.height = mScreenWidth / 4;
        holder.mLayout.setLayoutParams(params);
        holder.mCamera.setImageResource(R.drawable.camera);
    }

    /**
     * 相册
     *
     * @param holder
     * @param position
     */
    private void picture(PictureChoiceViewHolder holder, int position) {
        ViewGroup.LayoutParams params = holder.mLayout.getLayoutParams();
        params.height = mScreenWidth / 4;
        holder.mLayout.setLayoutParams(params);

        String url = "file:/" + mList.get(position).get("path");

        ImageLoadProxy.displayImage(url, holder.mPicture, options);

        int state = (int) mList.get(position).get("state");

        if (state == RunTimeConfig.PictureConfig.OUT)
            holder.mState.setImageResource(R.drawable.cb_out);
        else
            holder.mState.setImageResource(R.drawable.cb_in);
    }

}
