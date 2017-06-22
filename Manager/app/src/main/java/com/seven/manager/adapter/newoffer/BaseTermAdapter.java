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

public class BaseTermAdapter extends RecyclerView.Adapter<BaseTermAdapter.Term> {

    private Context mContext;

    private List<BaseTerm> mList;

    private ListItemCallBack mCallBack;

    private LayoutInflater mInflater;

    public BaseTermAdapter(Context mContext, List<BaseTerm> mList, ListItemCallBack mCallBack) {
        this.mContext = mContext;
        this.mList = mList;
        this.mCallBack = mCallBack;

        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public Term onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;

        Term term = null;

        view = mInflater.inflate(R.layout.adapter_base_term, parent, false);

        term = new Term(view);

        return term;
    }

    @Override
    public void onBindViewHolder(Term holder, int position) {

        if (holder instanceof Term) {
            term(holder, position);
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class Term extends BaseViewHolder implements View.OnClickListener {

        private RelativeLayout btn;

        private TextView term;

        public Term(View itemView) {
            super(itemView);

            btn = getView(btn, R.id.base_term_btn);
            btn.setOnClickListener(this);

            term = getView(term, R.id.base_term_tv);
        }

        @Override
        public void onClick(View v) {

            mCallBack.onItemClick(v, getLayoutPosition());

        }
    }

    private void term(Term holder, int position) {

        BaseTerm term = mList.get(position);

        holder.term.setText(term.getName());

    }

}
