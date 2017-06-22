package com.seven.manager.adapter.offer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seven.library.base.BaseViewHolder;
import com.seven.manager.R;
import com.seven.manager.model.offer.QuotationPackageItem;

import java.util.List;

/**
 * @author seven
 */

public class QuotationPackageAdapter extends RecyclerView.Adapter<QuotationPackageAdapter.PackageHolder> {

    private Context mContext;

    private List<QuotationPackageItem> mList;

    private LayoutInflater mInflater;

    public QuotationPackageAdapter(Context context, List<QuotationPackageItem> list) {

        this.mContext = context;
        this.mList = list;

        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public PackageHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;

        PackageHolder holder = null;

        view = mInflater.inflate(R.layout.adapter_quotation_package_item, parent, false);

        holder = new PackageHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(PackageHolder holder, int position) {

        holder.space.setText(mList.get(position).getName());
        holder.area.setText(String.valueOf(mList.get(position).getSpaceArea())+"m²");
        holder.perimeter.setText(String.valueOf(mList.get(position).getPerimeter())+"m");

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class PackageHolder extends BaseViewHolder {

        private TextView space;

        private TextView area;

        private TextView perimeter;

        public PackageHolder(View itemView) {
            super(itemView);

            space = getView(space, R.id.package_item_space);
            area = getView(area, R.id.package_item_area);
            perimeter = getView(perimeter, R.id.package_item_perimeter);

        }
    }

}
