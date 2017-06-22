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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.seven.library.base.BaseViewHolder;
import com.seven.library.callback.ListItemCallBack;
import com.seven.library.utils.ScreenUtils;
import com.seven.library.view.DashedLineView;
import com.seven.manager.R;
import com.seven.manager.model.newoffer.SpaceItemList;

import java.text.DecimalFormat;
import java.util.List;

/**
 * @author seven
 */

public class SpaceItemAdapter extends RecyclerView.Adapter<SpaceItemAdapter.ItemHolder> {

    private Context mContext;

    private List<SpaceItemList> mList;

    private int mPosition;

    private ListItemCallBack mCallBack;

    private LayoutInflater mInflater;

    public SpaceItemAdapter(Context context, List<SpaceItemList> list, int parentPosition, ListItemCallBack callBack) {

        this.mContext = context;
        this.mList = list;
        this.mPosition = parentPosition;
        this.mCallBack = callBack;

        this.mInflater = LayoutInflater.from(mContext);

    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;

        ItemHolder holder = null;

        view = mInflater.inflate(R.layout.adapter_offer_space_item_list, parent, false);

        holder = new ItemHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {

        item(holder, position);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ItemHolder extends BaseViewHolder implements View.OnClickListener {

        private RelativeLayout remove;

        private ImageView line;

        private TextView name;

        private EditText area;

        private DashedLineView dashed;

        private RelativeLayout layout;

        public ItemHolder(View itemView) {
            super(itemView);

            remove = getView(remove, R.id.space_item_remove);

            line = getView(line, R.id.space_item_line);
            name = getView(name, R.id.space_item_name);
            area = getView(area, R.id.space_item_area_et);

            dashed = getView(dashed, R.id.space_item_dashed);
            layout = getView(layout, R.id.space_item_layout);

            remove.setOnClickListener(this);

            area.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {

                    if (!s.toString().equals(".")) {
                        (mList.get(getLayoutPosition())).setArea(s.length() > 0 ? Double.parseDouble(s.toString()) : 0);

                        mCallBack.onItemClick(area, getLayoutPosition());
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            mCallBack.onItemClick(v, getLayoutPosition(), mPosition);
        }
    }

    private void item(ItemHolder holder, int position) {

        SpaceItemList itemList = mList.get(position);

        holder.name.setText(itemList.isShowName() ? itemList.getName() : "");

        holder.line.setVisibility(itemList.isShowName() ? View.VISIBLE : View.INVISIBLE);

        holder.area.setText(itemList.getArea() == 0 ? "" : new DecimalFormat("#0.00").format(itemList.getArea()));

        if (position != mList.size() - 1) {
            holder.dashed.setVisibility(View.GONE);
            holder.layout.setPadding(
                    ScreenUtils.getInstance().dip2px(16),ScreenUtils.getInstance().dip2px(16),
                    ScreenUtils.getInstance().dip2px(16),0);
        } else {
            holder.dashed.setVisibility(View.VISIBLE);
            holder.layout.setPadding(
                    ScreenUtils.getInstance().dip2px(16),ScreenUtils.getInstance().dip2px(16),
                    ScreenUtils.getInstance().dip2px(16),ScreenUtils.getInstance().dip2px(16));
        }

    }
}
