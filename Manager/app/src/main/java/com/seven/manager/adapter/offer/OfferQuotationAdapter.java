package com.seven.manager.adapter.offer;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seven.library.application.LibApplication;
import com.seven.library.base.BaseViewHolder;
import com.seven.library.callback.JsonCallBack;
import com.seven.library.callback.ListItemCallBack;
import com.seven.library.config.RunTimeConfig;
import com.seven.library.json.JsonHelper;
import com.seven.library.utils.CheckUtils;
import com.seven.library.utils.ResourceUtils;
import com.seven.library.view.AutoLoadRecyclerView;
import com.seven.manager.R;
import com.seven.manager.model.offer.OfferQuotationModel;
import com.seven.manager.model.offer.QuotationPackageItem;
import com.seven.manager.model.offer.QuotationPersonalityItem;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 报价单
 *
 * @author seven
 */

public class OfferQuotationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    private List<OfferQuotationModel> mList;

    private ListItemCallBack mCallBack;

    private LayoutInflater mInflater;

    private QuotationPackageAdapter packageAdapter;
    private List<QuotationPackageItem> mPackageList;

    private QuotationPersonalityAdapter personalityAdapter;
    private List<QuotationPersonalityItem> mPersonalityList;

    public OfferQuotationAdapter(Context context, List<OfferQuotationModel> list, ListItemCallBack callBack) {

        this.mContext = context;
        this.mList = list;
        this.mCallBack = callBack;

        this.mInflater = LayoutInflater.from(mContext);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;

        switch (viewType) {

            case RunTimeConfig.ModelConfig.QUOTATION_TITLE:

                QuotationTitle title = null;

                view = mInflater.inflate(R.layout.adapter_quotation_title, parent, false);

                title = new QuotationTitle(view);

                return title;

            case RunTimeConfig.ModelConfig.QUOTATION_PACKAGE:

                QuotationPackage quotationPackage = null;

                view = mInflater.inflate(R.layout.adapter_quotation_package, parent, false);

                quotationPackage = new QuotationPackage(view);

                return quotationPackage;

            case RunTimeConfig.ModelConfig.QUOTATION_PERSONALITY:

                QuotationPersonality personality = null;

                view = mInflater.inflate(R.layout.adapter_quotation_personality, parent, false);

                personality = new QuotationPersonality(view);

                return personality;

            case RunTimeConfig.ModelConfig.QUOTATION_DATE:

                QuotationDate date = null;

                view = mInflater.inflate(R.layout.adapter_quotation_date, parent, false);

                date = new QuotationDate(view);

                return date;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof QuotationTitle)
            title((QuotationTitle) holder, position);
        else if (holder instanceof QuotationPackage)
            packages((QuotationPackage) holder, position);
        else if (holder instanceof QuotationPersonality)
            personality((QuotationPersonality) holder, position);
        else if (holder instanceof QuotationDate)
            date((QuotationDate) holder, position);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getViewType();
    }

    public class QuotationTitle extends BaseViewHolder {

        private TextView name;

        private TextView address;

        private TextView total;

        public QuotationTitle(View itemView) {
            super(itemView);

            name = getView(name, R.id.quotation_name);
            address = getView(address, R.id.quotation_address);
            total = getView(total, R.id.quotation_total);

        }
    }

    private void title(QuotationTitle holder, int position) {

        com.seven.manager.model.offer.QuotationTitle title = (com.seven.manager.model.offer.QuotationTitle) mList.get(position);

        holder.name.setText(title.getOwnerName());
        holder.address.setText(title.getHouseNumber());
        holder.total.setText(String.valueOf(CheckUtils.getInstance().format(title.getTotalAmount())));

    }

    public class QuotationPackage extends BaseViewHolder {

        private AutoLoadRecyclerView recycler;

        private TextView houseInfo;

        private TextView area;

        private TextView money;

        public QuotationPackage(View itemView) {
            super(itemView);

            recycler = getView(recycler, R.id.quotation_package_recycler);
            houseInfo = getView(houseInfo, R.id.quotation_package_house);
            area = getView(area, R.id.quotation_package_area);
            money = getView(money, R.id.offer_quotation_package_money);

        }
    }

    private void packages(QuotationPackage holder, int position) {

        com.seven.manager.model.offer.QuotationPackage packages = (com.seven.manager.model.offer.QuotationPackage) mList.get(position);

        holder.houseInfo.setText(packages.getHouseInfo());
        holder.area.setText(String.valueOf(CheckUtils.getInstance().format(packages.getArea())));
        holder.money.setText(String.valueOf(CheckUtils.getInstance().format(packages.getPackageMoney())));

        mPackageList = new ArrayList<>();

        JsonHelper.getInstance().jsonArraySingle(packages.getItemsListJson(), QuotationPackageItem.class,
                true, new JsonCallBack() {
                    @Override
                    public void onSucceed(Object data) {

                        if (data == null)
                            return;

                        for (QuotationPackageItem item : (List<QuotationPackageItem>) data)
                            mPackageList.add(item);

                    }

                    @Override
                    public void onFailure(String error) {

                    }
                });

        holder.recycler.setHasFixedSize(false);

        holder.recycler.setLayoutManager(new LinearLayoutManager(LibApplication.getInstance()));
        holder.recycler.setOnPauseListenerParams(false, true);

        packageAdapter = new QuotationPackageAdapter(LibApplication.getInstance(), mPackageList);
        holder.recycler.setAdapter(packageAdapter);
    }

    public class QuotationPersonality extends BaseViewHolder {

        private AutoLoadRecyclerView recycler;

        private TextView count;

        private TextView money;

        public QuotationPersonality(View itemView) {
            super(itemView);

            recycler = getView(recycler, R.id.quotation_personality_recycler);
            count = getView(count, R.id.quotation_personality_count);
            money = getView(money, R.id.offer_quotation_personality_money);

        }
    }

    private void personality(QuotationPersonality holder, int position) {

        com.seven.manager.model.offer.QuotationPersonality personality = (com.seven.manager.model.offer.QuotationPersonality) mList.get(position);

        holder.count.setText(ResourceUtils.getInstance().getFormatText(R.string.offer_personality_count, personality.getPersonalityCount()));
        holder.money.setText(String.valueOf(CheckUtils.getInstance().format(personality.getPersonalityMoney())));

        mPersonalityList = new ArrayList<>();

        JsonHelper.getInstance().jsonArraySingle(personality.getAddItemsListJson(), QuotationPersonalityItem.class,
                true, new JsonCallBack() {
                    @Override
                    public void onSucceed(Object data) {

                        if (data == null)
                            return;

                        for (QuotationPersonalityItem item : (List<QuotationPersonalityItem>) data) {

                            boolean isAdd = true;

                            for (QuotationPersonalityItem newItem : mPersonalityList) {

                                if (item.getName().equals(newItem.getName()) && item.getPrice() / item.getQuantity() == newItem.getPrice() / newItem.getQuantity()) {
                                    newItem.setQuantity(newItem.getQuantity() + item.getQuantity());
                                    newItem.setPrice(newItem.getPrice() + item.getPrice());
                                    isAdd = false;
                                }
                            }
                            if (isAdd)
                                mPersonalityList.add(item);
                        }

                    }

                    @Override
                    public void onFailure(String error) {

                    }
                });

        holder.recycler.setHasFixedSize(false);

        holder.recycler.setLayoutManager(new LinearLayoutManager(LibApplication.getInstance()));
        holder.recycler.setOnPauseListenerParams(false, true);

        personalityAdapter = new QuotationPersonalityAdapter(LibApplication.getInstance(), mPersonalityList);
        holder.recycler.setAdapter(personalityAdapter);

    }

    public class QuotationDate extends BaseViewHolder {

        private TextView area;

        private TextView total;

        public QuotationDate(View itemView) {
            super(itemView);

            area = getView(area, R.id.date_area);
            total = getView(total, R.id.date_money_total);

        }
    }

    private void date(QuotationDate holder, int position) {

        OfferQuotationModel date = mList.get(position);

        holder.area.setText(ResourceUtils.getInstance().getFormatText(R.string.offer_quotation_date_area, date.getArea()));

        holder.total.setText(String.valueOf(CheckUtils.getInstance().format(date.getTotalAmount())));
    }
}
