package com.seven.manager.adapter.offer;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seven.library.application.LibApplication;
import com.seven.library.base.BaseViewHolder;
import com.seven.library.callback.ListItemCallBack;
import com.seven.library.utils.ResourceUtils;
import com.seven.library.view.AutoLoadRecyclerView;
import com.seven.manager.R;
import com.seven.manager.model.offer.OfferHouse;

import java.util.List;

/**
 * 报价-添加房间
 *
 * @author seven
 */

public class OfferHouseAdapter extends RecyclerView.Adapter<OfferHouseAdapter.HouseHolder> {

    private Context mContext;

    private List<OfferHouse> mList;

    private ListItemCallBack mCallBack;

    private LayoutInflater mInflater;

    private OfferHouseContentAdapter mAdapter;

    public OfferHouseAdapter(Context context, List<OfferHouse> list, ListItemCallBack callBack) {

        this.mContext = context;
        this.mList = list;
        this.mCallBack = callBack;

        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public HouseHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;

        HouseHolder houseHolder = null;

        view = mInflater.inflate(R.layout.adapter_house, parent, false);

        houseHolder = new HouseHolder(view);

        return houseHolder;
    }

    @Override
    public void onBindViewHolder(HouseHolder holder, int position) {

        holder.mTitle.setText(ResourceUtils.getInstance().houseType(mList.get(position).getType()));

        holder.mRecyclerView.setHasFixedSize(false);

        holder.mRecyclerView.setLayoutManager(new GridLayoutManager(LibApplication.getInstance(), 3));
        holder.mRecyclerView.setOnPauseListenerParams(false, true);

        mAdapter = new OfferHouseContentAdapter(LibApplication.getInstance(),
                mList.get(position).getList(), position, mCallBack);
        holder.mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class HouseHolder extends BaseViewHolder {

        private TextView mTitle;

        private AutoLoadRecyclerView mRecyclerView;

        public HouseHolder(View itemView) {
            super(itemView);

            mTitle = getView(mTitle, R.id.house_title);

            mRecyclerView = getView(mRecyclerView, R.id.house_recycler_view);

        }
    }


}
