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
import com.seven.manager.R;
import com.seven.manager.model.newoffer.BaseTerm;

import java.util.List;

/**
 * @author seven
 */

public class BaseSearchAdapter extends RecyclerView.Adapter<BaseSearchAdapter.Search> {

    private Context context;

    private List<BaseTerm> list;

    private ListItemCallBack callBack;

    private LayoutInflater inflater;

    public BaseSearchAdapter(Context context, List<BaseTerm> list, ListItemCallBack callBack) {
        this.context = context;
        this.list = list;
        this.callBack = callBack;

        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public Search onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;

        Search search = null;

        view = inflater.inflate(R.layout.adapter_base_search, parent, false);

        search = new Search(view);

        return search;
    }

    @Override
    public void onBindViewHolder(Search holder, int position) {

        if (holder instanceof Search)
            search(holder, position);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Search extends BaseViewHolder implements View.OnClickListener {

        private RelativeLayout btn;

        private TextView termLeft;
        private TextView termCenter;
        private TextView termRight;

        public Search(View itemView) {
            super(itemView);

            btn = getView(btn, R.id.base_search_btn);
            btn.setOnClickListener(this);

            termLeft = getView(termLeft, R.id.base_search_left_tv);
            termCenter = getView(termCenter, R.id.base_search_center_tv);
            termRight = getView(termRight, R.id.base_search_right_tv);

        }

        @Override
        public void onClick(View v) {

            callBack.onItemClick(v, getLayoutPosition());

        }
    }

    private void search(Search holder, int position) {

        BaseTerm term = list.get(position);

        int index = term.getName().indexOf(term.getWord());

        String left = term.getName().substring(0, index);

        holder.termLeft.setText(left);

        holder.termCenter.setText(term.getWord());

        String right = term.getName().substring(index+1, term.getName().length());

        holder.termRight.setText(right);
    }

}
