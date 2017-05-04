package com.seven.library.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

import com.seven.library.R;


/**
 * 自定义虚线
 *
 * @author seven
 */
public class DashedLineView extends View {

    private Context mContext;

    public DashedLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(mContext.getResources().getColor(R.color.line));//颜色可以自己设置
        Path path = new Path();
        path.moveTo(0, 0);//起始坐标
        path.lineTo(this.getWidth(),0);//终点坐标
        PathEffect effects = new DashPathEffect(new float[]{8,8,8,8},1);//设置虚线的间隔和点的长度
        paint.setPathEffect(effects);
        canvas.drawPath(path, paint);
    }

}
