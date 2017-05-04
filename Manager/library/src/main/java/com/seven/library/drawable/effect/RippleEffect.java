package com.seven.library.drawable.effect;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Ripple Draw Effect
 */
public class RippleEffect extends PressEffect {
    protected float mPaintX;
    protected float mPaintY;

    public RippleEffect() {
        mMinRadiusFactor = 0;
        mMaxRadiusFactor = 1;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        if (mRadius > 0) {
            canvas.drawCircle(mPaintX, mPaintY, mRadius, paint);
        } else if (mAlpha > 0) {
            setPaintAlpha(paint, mAlpha);
            canvas.drawColor(paint.getColor());
        }
    }


    @Override
    public void animationExit(float factor) {
        super.animationExit(factor);
        mRadius = 0;
    }

    @Override
    public void touchDown(float dx, float dy) {
        mPaintX = dx;
        mPaintY = dy;

        float x = dx < mCenterX ? getWidth() : 0;
        float y = dy < mCenterY ? getHeight() : 0;
        float radius = (float) Math.sqrt((x - dx) * (x - dx) + (y - dy) * (y - dy));
        setMaxRadius(radius);
    }
}
