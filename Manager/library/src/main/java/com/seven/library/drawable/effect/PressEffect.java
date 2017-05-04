package com.seven.library.drawable.effect;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Press Draw Effect
 */
public class PressEffect extends EaseEffect {
    protected float mMinRadius;
    protected float mMaxRadius;
    protected float mRadius;

    protected float mCenterX;
    protected float mCenterY;

    protected float mMinRadiusFactor;
    protected float mMaxRadiusFactor;

    public PressEffect() {
        this(0.68f, 0.98f);
    }

    public PressEffect(float minRadiusFactor, float maxRadiusFactor) {
        this.mMinRadiusFactor = minRadiusFactor;
        this.mMaxRadiusFactor = maxRadiusFactor;
    }

    public void setMaxRadiusFactor(float factor) {
        this.mMaxRadiusFactor = factor;
    }

    public void setMinRadiusFactor(float factor) {
        this.mMinRadiusFactor = factor;
    }

    public float getMaxRadiusFactor() {
        return mMaxRadiusFactor;
    }

    public float getMinRadiusFactor() {
        return mMinRadiusFactor;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        if (mRadius > 0 && mAlpha > 0) {
            setPaintAlpha(paint, mAlpha);
            canvas.drawCircle(mCenterX, mCenterY, mRadius, paint);
        }
    }

    @Override
    public void animationEnter(float factor) {
        super.animationEnter(factor);
        mRadius = mMinRadius + (mMaxRadius - mMinRadius) * factor;
    }

    @Override
    public void animationExit(float factor) {
        super.animationExit(factor);
        mRadius = mMaxRadius + (mMinRadius - mMaxRadius) * factor;
    }

    @Override
    protected void onResize(float width, float height) {
        mCenterX = width / 2;
        mCenterY = height / 2;

        final float radius = Math.max(mCenterX, mCenterY);
        setMaxRadius(radius);
    }

    protected void setMaxRadius(float radius) {
        mMinRadius = radius * mMinRadiusFactor;
        mMaxRadius = radius * mMaxRadiusFactor;
    }
}
