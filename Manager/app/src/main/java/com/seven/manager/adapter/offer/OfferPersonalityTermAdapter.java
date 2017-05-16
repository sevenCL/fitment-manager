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
import com.seven.manager.R;
import com.seven.manager.model.offer.OfferPersonalityTermModel;

import java.util.List;

/**
 * 报价-个性项-选项
 *
 * @author seven
 */

public class OfferPersonalityTermAdapter extends RecyclerView.Adapter<OfferPersonalityTermAdapter.PersonalityTerm> {

    private Context mContext;

    private List<OfferPersonalityTermModel> mList;

    private ListItemCallBack mCallBack;

    private LayoutInflater mInflater;

    public OfferPersonalityTermAdapter(Context context, List<OfferPersonalityTermModel> list, ListItemCallBack callBack) {

        this.mContext = context;
        this.mList = list;
        this.mCallBack = callBack;

        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public PersonalityTerm onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;

        PersonalityTerm term = null;

        view = mInflater.inflate(R.layout.adapter_offer_personality_term, parent, false);

        term = new PersonalityTerm(view);

        return term;
    }

    @Override
    public void onBindViewHolder(PersonalityTerm holder, int position) {

        holder.mName.setText(mList.get(position).getName());

        holder.mNumber.setText(mList.get(position).getNumber());

        holder.mPrice.setText(mList.get(position).getOffer()+"/"+mList.get(position).getUnitName());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class PersonalityTerm extends BaseViewHolder implements View.OnClickListener {

        private TextView mName;

        private TextView mNumber;

        private TextView mPrice;

        private RelativeLayout mSelectBtn;

        public PersonalityTerm(View itemView) {
            super(itemView);

            mName = getView(mName, R.id.personality_term_name);
            mNumber = getView(mNumber, R.id.personality_term_number);
            mPrice = getView(mPrice, R.id.personality_term_price);

            mSelectBtn = getView(mSelectBtn, R.id.personality_term_select_btn);
            mSelectBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            mCallBack.onItemClick(v, getLayoutPosition());

        }
    }

}
