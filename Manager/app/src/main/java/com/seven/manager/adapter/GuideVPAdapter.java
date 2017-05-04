package com.seven.manager.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.seven.library.callback.ListItemCallBack;
import com.seven.manager.R;

import java.util.List;

/**
 * 适配器
 *
 * @author seven
 */
public class GuideVPAdapter extends PagerAdapter {

    private Context mContext;

    private List<View> mList;

    private ListItemCallBack mCallBack;

    public GuideVPAdapter(Context mContext, List<View> mList, ListItemCallBack callBack) {
        this.mContext = mContext;
        this.mList = mList;
        this.mCallBack=callBack;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View v = mList.get(position);
        container.addView(v);

        ImageView img = (ImageView) v.findViewById(R.id.guide_iv);

        int imgId = R.drawable.tour_1;

        switch (position) {

            case 0:
                imgId = R.drawable.tour_1;
                break;
            case 1:
                imgId = R.drawable.tour_2;
                break;
            case 2:
                imgId = R.drawable.tour_3;
                break;
            case 3:
                imgId = R.drawable.tour_4;
                break;
        }

        img.setImageResource(imgId);

        if (position == 3) {
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mCallBack.onItemClick(v,position);

                }
            });
        }
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mList.get(position));
    }
}
