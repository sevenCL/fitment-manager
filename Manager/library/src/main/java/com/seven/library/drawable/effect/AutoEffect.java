package com.seven.library.drawable.effect;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Move Draw Effect
 */
public class AutoEffect extends PressEffect {
    protected float mDownX;
    protected float mDownY;

    protected float mPaintX;
    protected float mPaintY;

    private int mCircleAlpha;

    public AutoEffect() {
        mMaxAlpha = 172;
        mMinRadiusFactor = 0;
        mMaxRadiusFactor = 0.78f;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        // Draw background
        int preAlpha = setPaintAlpha(paint, mAlpha);
        if (paint.getAlpha() > 0) {
            canvas.drawColor(paint.getColor());
        }

        if (mRadius > 0) {
            // Get double trans color
            if (preAlpha < 255) {
                preAlpha = getCircleAlpha(preAlpha, paint.getAlpha());
            }
            // Draw circle
            paint.setAlpha(preAlpha);
            setPaintAlpha(paint, mCircleAlpha);
            if (paint.getAlpha() > 0) {
                canvas.drawCircle(mPaintX, mPaintY, mRadius, paint);
            }
        }
    }

    @Override
    public void animationEnter(float factor) {
        super.animationEnter(factor);
        mPaintX = mDownX + (mCenterX - mDownX) * factor;
        mPaintY = mDownY + (mCenterY - mDownY) * factor;
    }

    @Override
    public void animationExit(float factor) {
        super.animationExit(factor);
        mRadius = mMaxRadius;
        mCircleAlpha = 255 - (int) (255 * factor);
    }

    @Override
    public void touchDown(float dx, float dy) {
        mPaintX = mDownX = dx;
        mPaintY = mDownY = dy;
        mCircleAlpha = 255;
    }

    private int getCircleAlpha(int preAlpha, int nowAlpha) {
        if (nowAlpha > preAlpha)
            return 0;
        int dAlpha = preAlpha - nowAlpha;
        return (255 * dAlpha) / (255 - nowAlpha);
    }

}
