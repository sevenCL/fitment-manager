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
import com.seven.manager.model.newoffer.BaseChild;

import java.util.List;

/**
 * @author seven
 */

public class BaseClassAdapter extends RecyclerView.Adapter<BaseClassAdapter.ClassItem> {

    private Context mContext;

    private List<BaseChild> mList;

    private ListItemCallBack mCallBack;

    private LayoutInflater mInflater;

    public BaseClassAdapter(Context context, List<BaseChild> list, ListItemCallBack callBack) {

        this.mContext = context;
        this.mList = list;
        this.mCallBack = callBack;

        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ClassItem onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;

        ClassItem classItem = null;

        view = mInflater.inflate(R.layout.adapter_base_class, parent, false);

        classItem = new ClassItem(view);

        return classItem;
    }

    @Override
    public void onBindViewHolder(ClassItem holder, int position) {

        if (holder instanceof ClassItem) {
            classItem(holder, position);
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ClassItem extends BaseViewHolder implements View.OnClickListener {

        private RelativeLayout btn;

        private TextView className;

        public ClassItem(View itemView) {
            super(itemView);

            btn = getView(btn, R.id.base_class_btn);
            btn.setOnClickListener(this);

            className = getView(className, R.id.base_class_tv);
        }

        @Override
        public void onClick(View v) {

            mCallBack.onItemClick(v, getLayoutPosition());

        }
    }

    private void classItem(ClassItem holder, int position) {

        BaseChild child = mList.get(position);

        holder.btn.setSelected(child.isSelect());

        holder.className.setText(child.getName());

    }

}
