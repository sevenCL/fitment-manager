package com.seven.manager.adapter.offer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seven.library.base.BaseViewHolder;
import com.seven.manager.R;
import com.seven.manager.model.offer.QuotationPersonalityItem;

import java.text.DecimalFormat;
import java.util.List;

/**
 * @author seven
 */

public class QuotationPersonalityAdapter extends RecyclerView.Adapter<QuotationPersonalityAdapter.PersonalityHolder> {

    private Context mContext;

    private List<QuotationPersonalityItem> mList;

    private LayoutInflater mInflater;

    public QuotationPersonalityAdapter(Context context, List<QuotationPersonalityItem> list) {

        this.mContext = context;
        this.mList = list;

        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public PersonalityHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;

        PersonalityHolder holder = null;

        view = mInflater.inflate(R.layout.adapter_quotation_personality_item, parent, false);

        holder = new PersonalityHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(PersonalityHolder holder, int position) {

        holder.term.setText(mList.get(position).getName());
        holder.volume.setText(mList.get(position).getQuantity()+mList.get(position).getUnit());
        holder.price.setText(new DecimalFormat("#0.00").format(mList.get(position).getPrice()));

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class PersonalityHolder extends BaseViewHolder {

        private TextView term;

        private TextView volume;

        private TextView price;

        public PersonalityHolder(View itemView) {
            super(itemView);

            term=getView(term,R.id.personality_item_term);
            volume=getView(volume,R.id.package_item_volume);
            price=getView(price,R.id.package_item_price);

        }
    }

}
