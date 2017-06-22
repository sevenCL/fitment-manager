package com.seven.manager.adapter.offer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.seven.library.base.BaseViewHolder;
import com.seven.library.callback.ListItemCallBack;
import com.seven.library.config.RunTimeConfig;
import com.seven.library.utils.ResourceUtils;
import com.seven.library.view.DashedLineView;
import com.seven.manager.R;
import com.seven.manager.model.offer.OfferPackageModel;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 报价信息
 *
 * @author seven
 */

public class OfferPackageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    private List<OfferPackageModel> mList;

    private ListItemCallBack mCallBack;

    private LayoutInflater mInflater;

    private PackageTotal packageTotal;

    public OfferPackageAdapter(Context context, List<OfferPackageModel> list, ListItemCallBack callBack) {

        this.mContext = context;
        this.mList = list;
        this.mCallBack = callBack;

        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;

        switch (viewType) {

            case RunTimeConfig.ModelConfig.OFFER_PACKAGE_ITEM:

                PackageItem packageItem = null;

                view = mInflater.inflate(R.layout.adapter_offer_package_item, parent, false);

                packageItem = new PackageItem(view);

                return packageItem;

            case RunTimeConfig.ModelConfig.OFFER_PACKAGE_TOTAL:

                view = mInflater.inflate(R.layout.adapter_offer_package_total, parent, false);

                packageTotal = new PackageTotal(view);

                return packageTotal;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof PackageItem)
            item((PackageItem) holder, position);
        else if (holder instanceof PackageTotal)
            total((PackageTotal) holder, position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getViewType();
    }

    public class PackageItem extends BaseViewHolder implements View.OnClickListener {

        private RelativeLayout mRemove;

        private TextView mHouseName;

        private EditText mArea;

        private EditText mPerimeter;

        private DashedLineView mLine;

        public PackageItem(View itemView) {
            super(itemView);

            mRemove = getView(mRemove, R.id.package_item_remove);

            mHouseName = getView(mHouseName, R.id.package_item_name);
            mArea = getView(mArea, R.id.package_item_area_et);
            mPerimeter = getView(mPerimeter, R.id.package_item_perimeter_et);

            mLine = getView(mLine, R.id.package_item_line);

            mRemove.setOnClickListener(this);

            mArea.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {

                    if (!s.toString().equals(".")) {
                        ((com.seven.manager.model.offer.PackageItem) mList.get(getLayoutPosition())).setArea(s.length() > 0 ? Double.parseDouble(s.toString()) : 0);

                        update();
                    }
                }
            });

            mPerimeter.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!s.toString().equals(".")) {
                        ((com.seven.manager.model.offer.PackageItem) mList.get(getLayoutPosition())).setPerimeter(s.length() > 0 ? Double.parseDouble(s.toString()) : 0);
                    }
                }
            });


        }

        @Override
        public void onClick(View v) {

            mCallBack.onItemClick(v, getLayoutPosition());

        }
    }

    private void item(final PackageItem holder, int position) {

        final com.seven.manager.model.offer.PackageItem item = (com.seven.manager.model.offer.PackageItem) mList.get(position);

        holder.mHouseName.setText(item.getName());

        holder.mLine.setVisibility(position == mList.size() - 2 ? View.GONE : View.VISIBLE);

        holder.mArea.setText(item.getArea() == 0 ? "" : new DecimalFormat("#0.00").format(item.getArea()));
        holder.mPerimeter.setText(item.getPerimeter() == 0 ? "" : new DecimalFormat("#0.00").format(item.getPerimeter()));

    }

    public class PackageTotal extends BaseViewHolder {

        private TextView mHouseInfo;

        private TextView mTotalArea;

        private LinearLayout mMinLayout;

        private TextView mMinArea;

        private TextView mMinAreaTwo;

        private TextView mTotalMoney;

        public PackageTotal(View itemView) {
            super(itemView);

            mHouseInfo = getView(mHouseInfo, R.id.package_total_house);
            mTotalArea = getView(mTotalArea, R.id.package_total_area);
            mMinLayout = getView(mMinLayout, R.id.package_total_min_area_ll);
            mMinArea = getView(mMinArea, R.id.package_total_min_area);
            mMinAreaTwo = getView(mMinAreaTwo, R.id.package_total_min_area_two);
            mTotalMoney = getView(mTotalMoney, R.id.offer_total_money);
        }
    }

    private void total(PackageTotal holder, int position) {

        com.seven.manager.model.offer.PackageTotal total = (com.seven.manager.model.offer.PackageTotal) mList.get(position);

        holder.mHouseInfo.setText(total.getHouseInfo());

        holder.mTotalArea.setText(new DecimalFormat("#0.00").format(total.getTotalArea()));

//        holder.mMinLayout.setVisibility(total.getTotalArea() < total.getMinArea() ? View.VISIBLE : View.GONE);

        holder.mMinArea.setText(ResourceUtils.getInstance().getFormatText(R.string.offer_package_min_area, total.getMinArea()));
        holder.mMinAreaTwo.setText(ResourceUtils.getInstance().getFormatText(R.string.offer_package_min_area_two, total.getMinArea()));

        holder.mTotalMoney.setText(new DecimalFormat("#0.00").format(total.getTotalMoney()));
    }

    private void update() {

        if (packageTotal == null || mList.size() == 0)
            return;

        double totalArea = 0;
        double totalMoney = 0;

        for (OfferPackageModel model : mList) {
            if (model.getViewType() == RunTimeConfig.ModelConfig.OFFER_PACKAGE_ITEM)
                totalArea += ((com.seven.manager.model.offer.PackageItem) model).getArea();
        }

        com.seven.manager.model.offer.PackageTotal total = (com.seven.manager.model.offer.PackageTotal) mList.get(mList.size() - 1);

        if (totalArea < total.getMinArea())
            totalMoney = total.getMinArea() * total.getPrice();
        else
            totalMoney = totalArea * total.getPrice();

        total.setTotalArea(totalArea);
        total.setTotalMoney(totalMoney);

        packageTotal.mTotalArea.setText(new DecimalFormat("#0.00").format(total.getTotalArea()));

//        packageTotal.mMinLayout.setVisibility(total.getTotalArea() < total.getMinArea() ? View.VISIBLE : View.GONE);

        packageTotal.mTotalMoney.setText(new DecimalFormat("#0.00").format(total.getTotalMoney()));

        mCallBack.onItemClick(packageTotal.mTotalArea, mList.size() - 1);
    }

}
