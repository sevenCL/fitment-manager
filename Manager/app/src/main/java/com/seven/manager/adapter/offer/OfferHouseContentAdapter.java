package com.seven.manager.adapter.offer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.seven.library.base.BaseViewHolder;
import com.seven.library.callback.ListItemCallBack;
import com.seven.library.config.RunTimeConfig;
import com.seven.manager.R;
import com.seven.manager.model.offer.OfferHouseModel;

import java.util.List;

/**
 * 报价-房间-选项
 *
 * @author seven
 */

public class OfferHouseContentAdapter extends RecyclerView.Adapter<OfferHouseContentAdapter.HouseContent> {

    private Context mContext;

    private List<OfferHouseModel> mList;

    private int mParentPosition;

    private ListItemCallBack mCallBack;

    private LayoutInflater mInflater;

    public OfferHouseContentAdapter(Context context, List<OfferHouseModel> list, int position, ListItemCallBack callBack) {

        this.mContext = context;
        this.mList = list;
        this.mParentPosition = position;
        this.mCallBack = callBack;

        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public HouseContent onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;

        HouseContent houseContent = null;

        view = mInflater.inflate(R.layout.adapter_house_content, parent, false);

        houseContent = new HouseContent(view);

        return houseContent;
    }

    @Override
    public void onBindViewHolder(HouseContent holder, int position) {

        holder.mContent.setText(mList.get(position).getName());

        holder.mContentBtn.setSelected(mList.get(position).getStatus() == RunTimeConfig.SelectConfig.SELECT_NOT ? false : true);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.mContentBtn.getLayoutParams();

        params.topMargin = 6;
        params.bottomMargin = 6;

        if (position % 3 == 0) {
            params.rightMargin = 7;
        }
        if (position % 3 == 1) {
            params.rightMargin = 4;
            params.leftMargin = 5;
        }
        if (position % 3 == 2) {
            params.leftMargin = 7;
        }

        holder.mContentBtn.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class HouseContent extends BaseViewHolder implements View.OnClickListener {

        private RelativeLayout mContentBtn;

        private TextView mContent;

        public HouseContent(View itemView) {
            super(itemView);

            mContentBtn = getView(mContentBtn, R.id.house_content_btn);

            mContent = getView(mContent, R.id.house_content);

            mContentBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mCallBack.onItemClick(v, mParentPosition, getLayoutPosition());
        }
    }
}
