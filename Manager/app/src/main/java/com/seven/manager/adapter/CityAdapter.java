package com.seven.manager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seven.library.base.BaseViewHolder;
import com.seven.library.callback.ListItemCallBack;
import com.seven.manager.R;
import com.seven.manager.model.res.CityModel;

import java.util.List;

/**
 * @author seven
 */
public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityHolder> {

    private Context mContext;
    private List<CityModel> mList;
    private ListItemCallBack mCallBack;

    private LayoutInflater mInflater;

    public CityAdapter(Context context, List<CityModel> list, ListItemCallBack callBack) {

        this.mContext = context;
        this.mList = list;
        this.mCallBack = callBack;

        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public CityHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;

        CityHolder holder;

        view = mInflater.inflate(R.layout.adapter_city, parent, false);

        holder = new CityHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(CityHolder holder, int position) {

        city(holder, position);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class CityHolder extends BaseViewHolder implements View.OnClickListener {

        private TextView cityName;

        public CityHolder(View itemView) {
            super(itemView);

            cityName = getView(cityName, R.id.city_name_tv);
            cityName.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mCallBack.onItemClick(v, getLayoutPosition());
        }
    }

    private void city(CityHolder holder, int position) {

        CityModel model = mList.get(position);

        holder.cityName.setText(model.getRegionName());

        holder.cityName.setBackgroundColor(model.isSelect() ?
                mContext.getResources().getColor(R.color.color_primary) : mContext.getResources().getColor(R.color.white));
        holder.cityName.setTextColor(model.isSelect() ?
                mContext.getResources().getColor(R.color.white) : mContext.getResources().getColor(R.color.content));
    }
}
