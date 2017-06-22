package com.seven.manager.adapter.newoffer;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seven.library.application.LibApplication;
import com.seven.library.base.BaseViewHolder;
import com.seven.library.callback.ListItemCallBack;
import com.seven.library.config.RunTimeConfig;
import com.seven.library.view.AutoLoadRecyclerView;
import com.seven.library.view.DashedLineView;
import com.seven.manager.R;
import com.seven.manager.model.newoffer.OfferSpaceModel;
import com.seven.manager.model.newoffer.SpaceItem;
import com.seven.manager.model.newoffer.SpaceTitle;

import java.util.List;

/**
 * @author seven
 */

public class OfferSpaceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ListItemCallBack {

    private Context mContext;

    private List<OfferSpaceModel> mList;

    private ListItemCallBack mCallBack;

    private LayoutInflater mInflater;

    private SpaceItemAdapter mAdapter;

    public OfferSpaceAdapter(Context context, List<OfferSpaceModel> list, ListItemCallBack callBack) {

        this.mContext = context;
        this.mList = list;
        this.mCallBack = callBack;

        this.mInflater = LayoutInflater.from(mContext);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;

        switch (viewType) {

            case RunTimeConfig.ModelConfig.SPACE_TITLE:

                TitleHolder titleHolder = null;

                view = mInflater.inflate(R.layout.adapter_offer_space_title, parent, false);

                titleHolder = new TitleHolder(view);

                return titleHolder;

            case RunTimeConfig.ModelConfig.SPACE_ITEM:

                ItemHolder itemHolder = null;

                view = mInflater.inflate(R.layout.adapter_offer_space_item, parent, false);

                itemHolder = new ItemHolder(view);

                return itemHolder;

        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof TitleHolder) {
            title((TitleHolder) holder, position);
        } else if (holder instanceof ItemHolder) {
            item((ItemHolder) holder, position);
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getViewType();
    }

    @Override
    public void onItemClick(View view, int position, Object... object) {

        int id = view.getId();

        switch (id) {

            case R.id.space_item_area_et:

                mCallBack.onItemClick(view, position);

                break;

            case R.id.space_item_remove:

                mCallBack.onItemClick(view,position,object[0]);

                break;

        }

    }

    private class TitleHolder extends BaseViewHolder {

        private TextView address;

        private TextView name;

        public TitleHolder(View itemView) {
            super(itemView);

            address = getView(address, R.id.space_address_tv);
            name = getView(name, R.id.space_name_tv);

        }
    }

    private void title(TitleHolder holder, int position) {

        SpaceTitle spaceTitle = (SpaceTitle) mList.get(position);

        holder.address.setText(spaceTitle.getHouse());
        holder.name.setText(spaceTitle.getOwnerName());

    }

    private class ItemHolder extends BaseViewHolder {

        private AutoLoadRecyclerView recycler;


        public ItemHolder(View itemView) {
            super(itemView);

            recycler = getView(recycler, R.id.space_item_recycler);
        }
    }

    private void item(ItemHolder holder, int position) {

        SpaceItem spaceItem = (SpaceItem) mList.get(position);

        holder.recycler.setHasFixedSize(false);

        holder.recycler.setLayoutManager(new LinearLayoutManager(LibApplication.getInstance()));
        holder.recycler.setOnPauseListenerParams(false, true);

        mAdapter = new SpaceItemAdapter(LibApplication.getInstance(), spaceItem.getList(),position, this);
        holder.recycler.setAdapter(mAdapter);

    }

}
