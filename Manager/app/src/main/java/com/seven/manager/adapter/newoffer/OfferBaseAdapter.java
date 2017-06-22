package com.seven.manager.adapter.newoffer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.seven.library.base.BaseViewHolder;
import com.seven.library.callback.ListItemCallBack;
import com.seven.library.config.RunTimeConfig;
import com.seven.manager.R;
import com.seven.manager.model.newoffer.BaseControl;
import com.seven.manager.model.newoffer.BaseItem;
import com.seven.manager.model.newoffer.BaseTitle;
import com.seven.manager.model.newoffer.OfferBaseModel;

import java.text.DecimalFormat;
import java.util.List;

/**
 * @author seven
 */

public class OfferBaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    private List<OfferBaseModel> mList;

    private ListItemCallBack mCallBack;

    private LayoutInflater mInflater;

    public OfferBaseAdapter(Context context, List<OfferBaseModel> list, ListItemCallBack callBack) {

        this.mContext = context;
        this.mList = list;
        this.mCallBack = callBack;

        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;

        switch (viewType) {

            case RunTimeConfig.ModelConfig.BASE_TITLE:

                Title title = null;

                view = mInflater.inflate(R.layout.adapter_offer_base_title, parent, false);

                title = new Title(view);

                return title;

            case RunTimeConfig.ModelConfig.BASE_ITEM:

                Item item = null;

                view = mInflater.inflate(R.layout.adapter_offer_base_item, parent, false);

                item = new Item(view);

                return item;

            case RunTimeConfig.ModelConfig.BASE_CONTROL:

                Control control = null;

                view = mInflater.inflate(R.layout.adapter_offer_base_control, parent, false);

                control = new Control(view);

                return control;

        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof Title) {
            title((Title) holder, position);
        } else if (holder instanceof Item) {
            item((Item) holder, position);
        } else if (holder instanceof Control) {
            control((Control) holder, position);
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getViewTpye();
    }

    private class Title extends BaseViewHolder implements View.OnClickListener {

        private TextView title;

        private TextView amount;

        private RelativeLayout add;

        public Title(View itemView) {
            super(itemView);

            title = getView(title, R.id.base_title_tv);
            amount = getView(amount, R.id.base_amount_tv);

            add = getView(add, R.id.base_add_btn);
            add.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            mCallBack.onItemClick(v, getLayoutPosition());

        }
    }

    private void title(Title holder, int position) {

        BaseTitle title = (BaseTitle) mList.get(position);

        holder.title.setText(title.getName());

        holder.amount.setText("合计：" + new DecimalFormat("#0.00").format(title.getTotal()));

    }

    private class Item extends BaseViewHolder implements View.OnClickListener {

        private LinearLayout layout;

        private TextView term;

        private TextView volume;

        private TextView total;

        private RelativeLayout remove;

        private ImageView iv;

        public Item(View itemView) {
            super(itemView);

            layout = getView(layout, R.id.base_term_rl);
            layout.setOnClickListener(this);

            term = getView(term, R.id.base_term_tv);
            volume = getView(volume, R.id.base_volume_tv);
            total = getView(total, R.id.base_total_tv);

            remove = getView(remove, R.id.base_remove_btn);
            remove.setOnClickListener(this);

            iv = getView(iv, R.id.base_remove_iv);
        }

        @Override
        public void onClick(View v) {

            mCallBack.onItemClick(v, getLayoutPosition());

        }
    }

    private void item(Item holder, int position) {

        BaseItem item = (BaseItem) mList.get(position);

        holder.layout.setVisibility(item.isShow() ? View.VISIBLE : View.GONE);

        holder.term.setText(item.getName());

        holder.volume.setText(new DecimalFormat("#0.00").format(item.getVolume()) + item.getUnitName());

        holder.total.setText("小计：" + new DecimalFormat("#0.00").format(item.getTotal()));

        holder.iv.setVisibility(item.isDefaultItem() ? View.INVISIBLE : View.VISIBLE);

        holder.remove.setClickable(holder.iv.getVisibility() == View.VISIBLE);
    }

    private class Control extends BaseViewHolder implements View.OnClickListener {

        private RelativeLayout open;

        private ImageView iv;

        public Control(View itemView) {
            super(itemView);

            iv = getView(iv, R.id.base_open_iv);

            open = getView(open, R.id.base_open_btn);
            open.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            mCallBack.onItemClick(v, getLayoutPosition());

        }
    }

    private void control(Control holder, int position) {

        BaseControl control = (BaseControl) mList.get(position);

        holder.iv.setImageResource(control.isShow() ? R.drawable.shouqi : R.drawable.zhankai);

    }

}
