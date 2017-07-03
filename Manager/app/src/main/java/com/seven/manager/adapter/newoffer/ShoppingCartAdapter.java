package com.seven.manager.adapter.newoffer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.seven.library.base.BaseViewHolder;
import com.seven.library.callback.ListItemCallBack;
import com.seven.library.utils.CheckUtils;
import com.seven.manager.R;
import com.seven.manager.model.newoffer.BaseItem;

import java.text.DecimalFormat;
import java.util.List;

/**
 * @author seven
 */

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ShoppingCart> {

    private Context context;

    private List<BaseItem> list;

    private ListItemCallBack callBack;

    private LayoutInflater inflater;

    public ShoppingCartAdapter(Context context, List<BaseItem> list, ListItemCallBack callBack) {
        this.context = context;
        this.list = list;
        this.callBack = callBack;

        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ShoppingCart onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;

        ShoppingCart shoppingCart = null;

        view = inflater.inflate(R.layout.adapter_shopping_cart, parent, false);

        shoppingCart = new ShoppingCart(view);

        return shoppingCart;
    }

    @Override
    public void onBindViewHolder(ShoppingCart holder, int position) {

        if (holder instanceof ShoppingCart)
            shopping(holder, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ShoppingCart extends BaseViewHolder implements View.OnClickListener {

        private RelativeLayout remove;

        private TextView volume;

        private TextView name;

        public ShoppingCart(View itemView) {
            super(itemView);

            remove = getView(remove, R.id.shopping_remove_btn);
            remove.setOnClickListener(this);

            volume = getView(volume, R.id.shopping_volume_tv);
            name = getView(name, R.id.shopping_name_tv);
        }

        @Override
        public void onClick(View v) {

            callBack.onItemClick(v, getLayoutPosition());

        }
    }

    private void shopping(ShoppingCart holder, int position) {

        BaseItem item = list.get(position);

        holder.name.setText(item.getName());
        holder.volume.setText(CheckUtils.getInstance().format(item.getVolume()) + item.getUnitName());

    }

}
