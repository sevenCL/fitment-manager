package com.seven.manager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.seven.library.base.BaseViewHolder;
import com.seven.library.callback.ListItemCallBack;
import com.seven.library.config.RunTimeConfig;
import com.seven.library.utils.DateFormatUtils;
import com.seven.library.utils.ResourceUtils;
import com.seven.manager.R;
import com.seven.manager.model.http.OrderModel;

import java.util.List;

/**
 * 预约
 *
 * @author seven
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder> {

    private Context mContext;
    private List<OrderModel> mList;
    private ListItemCallBack mCallBack;

    private LayoutInflater mInflater;

    public OrderAdapter(Context context, List<OrderModel> list, ListItemCallBack callBack) {

        this.mContext = context;
        this.mList = list;
        this.mCallBack = callBack;

        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public OrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        OrderHolder holder = null;

        view = mInflater.inflate(R.layout.adapter_order, parent, false);
        holder = new OrderHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(OrderHolder holder, int position) {

        OrderModel model = mList.get(position);

        holder.mStatus.setText(ResourceUtils.getInstance().orderStatus(model.getStatus()));

        holder.mName.setText(model.getOwnerName());

        holder.mArea.setText(model.getArea() + "m²");

        holder.mAddress.setText(model.getAddress());

        holder.mSubmitTime.setText(ResourceUtils.getInstance().getFormatText(R.string.submit_time,
                DateFormatUtils.getInstance().millisecondToDateSecondPoint(model.getStartTime())));

        holder.mOrderNumber.setText(ResourceUtils.getInstance().getFormatText(R.string.order_number, model.getOrderNumber()));

        String hintStr = "";

        int statusId = R.drawable.tv_half_green;

        holder.mOrderNewLayout.setVisibility(View.GONE);
        holder.mOrderOfferLayout.setVisibility(View.GONE);
        holder.mOrderOfferOverLayout.setVisibility(View.GONE);
        holder.mOrderProStartLayout.setVisibility(View.GONE);
        holder.mOrderInvalidLayout.setVisibility(View.GONE);

        switch (model.getStatus()) {

            //新预约
            case RunTimeConfig.StatusConfig.ORDER_STATUS_1:

                hintStr = ResourceUtils.getInstance().getFormatText(R.string.end_time,
                        DateFormatUtils.getInstance().millisecondToDateSecondC(model.getEndTime()));

                holder.mOrderNewLayout.setVisibility(View.VISIBLE);

                break;

            //已取消
            case RunTimeConfig.StatusConfig.ORDER_STATUS_2:

                hintStr = ResourceUtils.getInstance().getText(R.string.order_cancel);

                statusId = R.drawable.tv_half_red;

                break;

            //待报价
            case RunTimeConfig.StatusConfig.ORDER_STATUS_3:

                hintStr = ResourceUtils.getInstance().getFormatText(R.string.order_offer_start,
                        DateFormatUtils.getInstance().millisecondToDateSecondC(model.getEndTime()));

                statusId = R.drawable.tv_half_orange;

                holder.mOrderOfferLayout.setVisibility(View.VISIBLE);

                break;

            //已报价
            case RunTimeConfig.StatusConfig.ORDER_STATUS_4:

                hintStr = ResourceUtils.getInstance().getText(R.string.order_offer_over);

                statusId = R.drawable.tv_half_violet;

                holder.mOrderOfferOverLayout.setVisibility(View.VISIBLE);

                break;

            //待开工
            case RunTimeConfig.StatusConfig.ORDER_STATUS_5:

                hintStr = ResourceUtils.getInstance().getFormatText(R.string.order_project_start,
                        DateFormatUtils.getInstance().millisecondToDateSecondC(model.getEndTime()));

                statusId = R.drawable.tv_half_violet;

                holder.mOrderProStartLayout.setVisibility(View.VISIBLE);

                break;

            //已失效
            case RunTimeConfig.StatusConfig.ORDER_STATUS_6:

                hintStr = ResourceUtils.getInstance().getText(R.string.order_invalid);

                statusId = R.drawable.tv_half_gray;

                holder.mOrderInvalidLayout.setVisibility(View.VISIBLE);

                break;
        }

        holder.mStatus.setBackgroundResource(statusId);
        holder.mHint.setText(hintStr);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class OrderHolder extends BaseViewHolder implements View.OnClickListener {

        //预约单状态
        private TextView mStatus;

        //业主名字
        private TextView mName;

        //面积
        private TextView mArea;

        //地址
        private TextView mAddress;

        //定位
        private ImageView mLocation;

        //提示语句
        private TextView mHint;

        //提交时间
        private TextView mSubmitTime;

        //预约单号
        private TextView mOrderNumber;

        //新预约
        private RelativeLayout mOrderNewLayout;

        //拒绝
        private RelativeLayout mRefuseBtn;

        //接受
        private RelativeLayout mAcceptBtn;

        //待报价
        private RelativeLayout mOrderOfferLayout;

        //已报价
        private RelativeLayout mOrderOfferOverLayout;

        //待开工
        private RelativeLayout mOrderProStartLayout;

        //已失效
        private RelativeLayout mOrderInvalidLayout;

        public OrderHolder(View itemView) {
            super(itemView);

            mStatus = getView(mStatus, R.id.order_status_tv);
            mName = getView(mName, R.id.order_name_tv);
            mArea = getView(mArea, R.id.order_area_tv);
            mAddress = getView(mAddress, R.id.order_address_tv);
            mLocation = getView(mLocation, R.id.order_location_iv);
            mHint = getView(mHint, R.id.order_hint_tv);
            mSubmitTime = getView(mSubmitTime, R.id.order_submit_time_tv);
            mOrderNumber = getView(mOrderNumber, R.id.order_number_tv);

            mOrderNewLayout = getView(mOrderNewLayout, R.id.order_new_rl);
            mRefuseBtn = getView(mRefuseBtn, R.id.order_refuse_btn);
            mAcceptBtn = getView(mAcceptBtn, R.id.order_accept_btn);

            mOrderOfferLayout = getView(mOrderOfferLayout, R.id.order_offer_rl);

            mOrderOfferOverLayout = getView(mOrderOfferOverLayout, R.id.order_offer_over_rl);

            mOrderProStartLayout = getView(mOrderProStartLayout, R.id.order_project_start_rl);

            mOrderInvalidLayout = getView(mOrderInvalidLayout, R.id.order_invalid_rl);

            mRefuseBtn.setOnClickListener(this);
            mAcceptBtn.setOnClickListener(this);
            mLocation.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            mCallBack.onItemClick(v, getLayoutPosition());

        }
    }

}
