package com.seven.library.drawable.effect;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Ease Draw Effect
 */
public class EaseEffect extends Effect {
    static final int ALPHA_EASE_MAX_DEFAULT = 256;
    protected int mAlpha = 0;
    protected int mMaxAlpha;

    public EaseEffect() {
        mMaxAlpha = ALPHA_EASE_MAX_DEFAULT;
    }

    public EaseEffect(int maxEaseAlpha) {
        mMaxAlpha = maxEaseAlpha;
    }

    public void setMaxEaseAlpha(int alpha) {
        this.mMaxAlpha = alpha;
    }

    public int getMaxEaseAlpha() {
        return mMaxAlpha;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        if (mAlpha > 0) {
            setPaintAlpha(paint, mAlpha);
            canvas.drawColor(paint.getColor());
        }
    }

    @Override
    public void animationEnter(float factor) {
        mAlpha = (int) (factor * mMaxAlpha);
    }

    @Override
    public void animationExit(float factor) {
        mAlpha = mMaxAlpha - (int) (mMaxAlpha * factor);
    }
}
