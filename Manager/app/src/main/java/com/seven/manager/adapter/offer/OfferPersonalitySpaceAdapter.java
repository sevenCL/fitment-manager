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
 * 报价-个性项-空间
 *
 * @author seven
 */

public class OfferPersonalitySpaceAdapter extends RecyclerView.Adapter<OfferPersonalitySpaceAdapter.PersonalitySpace> {

    private Context mContext;

    private List<OfferHouseModel> mList;

    private ListItemCallBack mCallBack;

    private LayoutInflater mInflater;

    public OfferPersonalitySpaceAdapter(Context context, List<OfferHouseModel> list, ListItemCallBack callBack) {

        this.mContext = context;
        this.mList = list;
        this.mCallBack = callBack;

        this.mInflater = LayoutInflater.from(mContext);

    }

    @Override
    public PersonalitySpace onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;

        PersonalitySpace space = null;

        view = mInflater.inflate(R.layout.adapter_offer_personality_space, parent, false);

        space = new PersonalitySpace(view);

        return space;
    }

    @Override
    public void onBindViewHolder(PersonalitySpace holder, int position) {

        holder.mSpace.setText(mList.get(position).getName());

        holder.mLayout.setSelected(mList.get(position).getStatus() == RunTimeConfig.SelectConfig.SELECT_NOT ? false : true);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.mLayout.getLayoutParams();

        params.topMargin = 6;
        params.bottomMargin = 6;

        if (position % 2 == 0) {
            params.rightMargin = 6;
        }
        if (position % 2 == 1) {
            params.leftMargin = 6;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class PersonalitySpace extends BaseViewHolder implements View.OnClickListener {

        private RelativeLayout mLayout;

        private TextView mSpace;

        public PersonalitySpace(View itemView) {
            super(itemView);

            mLayout = getView(mLayout, R.id.personality_space_btn);

            mSpace = getView(mSpace, R.id.personality_space);

            mLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            mCallBack.onItemClick(v, getLayoutPosition());

        }
    }

}
