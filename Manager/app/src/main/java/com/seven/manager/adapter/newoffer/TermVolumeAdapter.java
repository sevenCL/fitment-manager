package com.seven.manager.adapter.newoffer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.seven.library.base.BaseViewHolder;
import com.seven.library.callback.ListItemCallBack;
import com.seven.library.config.RunTimeConfig;
import com.seven.manager.R;
import com.seven.manager.model.newoffer.TermVolumeItem;
import com.seven.manager.model.newoffer.TermVolumeModel;
import com.seven.manager.model.newoffer.TermVolumeTitle;
import com.seven.manager.model.newoffer.TermVolumeTotal;

import java.text.DecimalFormat;
import java.util.List;

/**
 * @author seven
 */

public class TermVolumeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    private List<TermVolumeModel> list;

    private ListItemCallBack callBack;

    private LayoutInflater inflater;

    public TermVolumeAdapter(Context context, List<TermVolumeModel> list, ListItemCallBack callBack) {
        this.context = context;
        this.list = list;
        this.callBack = callBack;

        this.inflater = LayoutInflater.from(context);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;

        switch (viewType) {

            case RunTimeConfig.ModelConfig.TERM_VOLUME_TITLE:

                Title title = null;

                view = inflater.inflate(R.layout.adapter_term_volume_title, parent, false);

                title = new Title(view);

                return title;

            case RunTimeConfig.ModelConfig.TERM_VOLUME_ITEM:

                Item item = null;

                view = inflater.inflate(R.layout.adapter_term_volume_item, parent, false);

                item = new Item(view);

                return item;

            case RunTimeConfig.ModelConfig.TERM_VOLUME_TOTAL:

                Total total = null;

                view = inflater.inflate(R.layout.adapter_term_volume_total, parent, false);

                total = new Total(view);

                return total;

        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof Title) {
            title((Title) holder, position);
        } else if (holder instanceof Item) {
            item((Item) holder, position);
        } else if (holder instanceof Total) {
            total((Total) holder, position);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getViewType();
    }

    public class Title extends BaseViewHolder {

        private TextView name;

        private TextView price;

        private TextView describe;

        private TextView unit;

        public Title(View itemView) {
            super(itemView);

            name = getView(name, R.id.volume_name_tv);
            price = getView(price, R.id.volume_price_tv);
            describe = getView(describe, R.id.volume_describe_tv);
            unit = getView(unit, R.id.volume_unit_tv);

        }
    }

    private void title(Title holder, int position) {

        TermVolumeTitle title = (TermVolumeTitle) list.get(position);

        holder.name.setText(title.getName());
        holder.price.setText(title.getPrice() + title.getUnit());
        holder.describe.setText(title.getDescribe());
        holder.unit.setText(title.getUnit());

    }

    public class Item extends BaseViewHolder implements View.OnClickListener {

        private TextView name;

        private EditText volume;

        private RelativeLayout delete;

        public Item(View itemView) {
            super(itemView);

            name = getView(name, R.id.volume_name_tv);
            volume = getView(volume, R.id.volume_et);

            delete = getView(delete, R.id.volume_delete_btn);
            delete.setOnClickListener(this);

            volume.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!s.toString().equals(".")) {
                        ((TermVolumeItem) list.get(getLayoutPosition())).setVolume(s.length() > 0 ? Double.parseDouble(s.toString()) : 0);

                        delete.setVisibility(s.toString().length() > 0 ? View.VISIBLE : View.INVISIBLE);

                        callBack.onItemClick(volume, getLayoutPosition(), s.toString());
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

        @Override
        public void onClick(View v) {
            callBack.onItemClick(v, getLayoutPosition());
        }
    }

    private void item(Item holder, int position) {

        TermVolumeItem item = (TermVolumeItem) list.get(position);

        holder.name.setVisibility(item.isShowName() ? View.VISIBLE : View.INVISIBLE);

        holder.name.setText(item.getName());
        holder.volume.setText(item.getVolume() == 0 ? "" : new DecimalFormat("#0.00").format(item.getVolume()));

        holder.delete.setVisibility(item.getVolume() > 0 ? View.VISIBLE : View.INVISIBLE);

        if (item.isDefaultItem()) {
            holder.delete.setVisibility(View.INVISIBLE);
            holder.volume.setKeyListener(null);
        }

    }

    public class Total extends BaseViewHolder {

        private TextView unit;

        private TextView volume;

        public Total(View itemView) {
            super(itemView);

            unit = getView(unit, R.id.volume_unit_tv);
            volume = getView(volume, R.id.volume_tv);

        }
    }

    private void total(Total holder, int position) {

        TermVolumeTotal total = (TermVolumeTotal) list.get(position);

        holder.unit.setText(total.getUnit());
        holder.volume.setText(new DecimalFormat("#0.00").format(total.getTotal()));

    }

}
