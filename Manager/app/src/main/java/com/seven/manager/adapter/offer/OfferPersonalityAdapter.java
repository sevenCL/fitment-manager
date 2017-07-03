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
import com.seven.library.utils.CheckUtils;
import com.seven.library.utils.ResourceUtils;
import com.seven.library.view.DashedLineView;
import com.seven.manager.R;
import com.seven.manager.model.offer.OfferPersonalityModel;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 报价-个性项
 *
 * @author seven
 */

public class OfferPersonalityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    private List<OfferPersonalityModel> mList;

    private ListItemCallBack mCallBack;

    private LayoutInflater mInflater;

    public OfferPersonalityAdapter(Context context, List<OfferPersonalityModel> list, ListItemCallBack callBack) {

        this.mContext = context;
        this.mList = list;
        this.mCallBack = callBack;

        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;

        switch (viewType) {

            case RunTimeConfig.ModelConfig.OFFER_PERSONALITY_ITEM:

                PersonalityItem item = null;

                view = mInflater.inflate(R.layout.adapter_offer_personality_item, parent, false);

                item = new PersonalityItem(view);

                return item;

            case RunTimeConfig.ModelConfig.OFFER_PERSONALITY_TOTAL:

                PersonalityTotal total = null;

                view = mInflater.inflate(R.layout.adapter_offer_personality_total, parent, false);

                total = new PersonalityTotal(view);

                return total;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof PersonalityItem)
            item((PersonalityItem) holder, position);
        else if (holder instanceof PersonalityTotal)
            total((PersonalityTotal) holder, position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getViewType();
    }

    public class PersonalityItem extends BaseViewHolder implements View.OnClickListener {

        private RelativeLayout mRemove;

        private TextView mName;

        private TextView mPrice;

        private TextView mVolume;

        private TextView mSubTotal;

        private DashedLineView mLine;

        public PersonalityItem(View itemView) {
            super(itemView);

            mRemove = getView(mRemove, R.id.personality_item_remove);

            mName = getView(mName, R.id.personality_item_name);

            mPrice = getView(mPrice, R.id.personality_item_price);
            mVolume = getView(mVolume, R.id.personality_item_volume);
            mSubTotal = getView(mSubTotal, R.id.personality_item_subtotal);

            mLine = getView(mLine, R.id.personality_item_line);

            mRemove.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            mCallBack.onItemClick(v, getLayoutPosition());

        }
    }

    private void item(PersonalityItem holder, int position) {

        com.seven.manager.model.offer.PersonalityItem item = (com.seven.manager.model.offer.PersonalityItem) mList.get(position);

        holder.mName.setText(item.getSpace() + "-" + item.getTerm());

        holder.mPrice.setText(ResourceUtils.getInstance().getFormatText(
                R.string.offer_personality_price,String.valueOf(CheckUtils.getInstance().format(item.getPrice())) + "/" + item.getUnit()));

        holder.mVolume.setText(ResourceUtils.getInstance().getFormatText(
                R.string.offer_personality_volume, String.valueOf(CheckUtils.getInstance().format(item.getVolume()) + item.getUnit())));

        holder.mSubTotal.setText(ResourceUtils.getInstance().getFormatText(
                R.string.offer_personality_small_total, String.valueOf(CheckUtils.getInstance().format(item.getSmallTotal()))));

        holder.mLine.setVisibility(position == mList.size() - 2 ? View.GONE : View.VISIBLE);
    }

    public class PersonalityTotal extends BaseViewHolder {

        private TextView mCount;

        private TextView mTotal;

        public PersonalityTotal(View itemView) {
            super(itemView);

            mCount = getView(mCount, R.id.personality_total);

            mTotal = getView(mTotal, R.id.personality_total_money);

        }
    }

    private void total(PersonalityTotal holder, int position) {

        com.seven.manager.model.offer.PersonalityTotal total = (com.seven.manager.model.offer.PersonalityTotal) mList.get(position);

        holder.mCount.setText(ResourceUtils.getInstance().getFormatText(R.string.offer_personality_count, total.getCount()));

        holder.mTotal.setText(String.valueOf(CheckUtils.getInstance().format(total.getTotal())));
    }

}
