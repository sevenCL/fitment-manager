package com.seven.library.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * recycler view holder
 *
 * @author seven
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    private View view;

    public BaseViewHolder(View itemView) {
        super(itemView);
        view = itemView;
    }

    protected <T> T getView(T view, int id) {
        if (null == view) {
            view = (T) this.view.findViewById(id);
        }

        return view;
    }

}

