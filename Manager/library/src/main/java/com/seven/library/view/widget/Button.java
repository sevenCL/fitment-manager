package com.seven.library.view.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.seven.library.R;
import com.seven.library.drawable.TouchEffectDrawable;
import com.seven.library.drawable.effect.EffectFactory;
import com.seven.library.drawable.factory.ClipFilletFactory;
import com.seven.library.view.Ui;


/**
 * This is touch effect button
 * Include 'Auto' 'Ease' 'Press' 'Ripple' effect to touch
 * And supper custom font
 */
public class Button extends android.widget.Button implements TouchEffectDrawable.PerformClicker {
    private TouchEffectDrawable mTouchDrawable;
    private int mTouchColor;

    public Button(Context context) {
        super(context);
    }

    public Button(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, R.attr.gButtonStyle, R.style.Genius_Widget_Button);
    }

    public Button(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr, R.style.Genius_Widget_Button);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Button(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(Button.class.getName());
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(Button.class.getName());
    }

    private void init(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        if (attrs == null)
            return;

        final Context context = getContext();
        final Resources resources = getResources();

        // Load attributes
        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.Button, defStyleAttr, defStyleRes);

        String fontFile = a.getString(R.styleable.Button_gFont);
        int touchEffect = a.getInt(R.styleable.Button_gTouchEffect, EffectFactory.TOUCH_EFFECT_NONE);
        int touchColor = a.getColor(R.styleable.Button_gTouchColor, Ui.TOUCH_PRESS_COLOR);

        // Load clip touch corner radius
        int touchRadius = a.getDimensionPixelOffset(R.styleable.Button_gTouchCornerRadius, resources.getDimensionPixelOffset(R.dimen.g_button_touch_corners_radius));
        int touchRadiusTL = a.getDimensionPixelOffset(R.styleable.Button_gTouchCornerRadiusTL, touchRadius);
        int touchRadiusTR = a.getDimensionPixelOffset(R.styleable.Button_gTouchCornerRadiusTR, touchRadius);
        int touchRadiusBL = a.getDimensionPixelOffset(R.styleable.Button_gTouchCornerRadiusBL, touchRadius);
        int touchRadiusBR = a.getDimensionPixelOffset(R.styleable.Button_gTouchCornerRadiusBR, touchRadius);
        float[] radius = new float[]{touchRadiusTL, touchRadiusTL, touchRadiusTR, touchRadiusTR,
                touchRadiusBR, touchRadiusBR, touchRadiusBL, touchRadiusBL};
        ClipFilletFactory touchFactory = new ClipFilletFactory(radius);
        float touchDurationRate = a.getFloat(R.styleable.Button_gTouchDurationRate, 1.0f);

        a.recycle();

        // set background on user not set background
        if (!Ui.isHaveAttribute(attrs, "background")) {
            // Set Background
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                //noinspection deprecation
                Drawable drawable = getResources().getDrawable(R.drawable.g_button_background);
                //noinspection deprecation
                setBackgroundDrawable(drawable);
            } else
                setBackgroundResource(R.drawable.g_button_background);
        }

        // the lollipop new attrs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !this.isInEditMode()) {
            // outlineProvider
            if (!Ui.isHaveAttribute(attrs, "outlineProvider")) {
                setOutlineProvider(null);
            }
            // elevation
            if (!Ui.isHaveAttribute(attrs, "elevation")) {
                setElevation(0);

            }
        }

        // SetTouch
        setTouchEffect(touchEffect);
        setTouchColor(touchColor);
        setTouchDuration(touchDurationRate);

        // Check for IDE preview render
        if (!this.isInEditMode()) {
            // Touch factory
            setTouchClipFactory(touchFactory);

            // Font
            if (fontFile != null && fontFile.length() > 0) {
                Typeface typeface = Ui.getFont(getContext(), fontFile);
                if (typeface != null) setTypeface(typeface);
            }
        }
    }

    @Override
    public void setLayerType(int layerType, Paint paint) {
        // In this, to support Canvas.clipPath(),
        // must set layerType is View.LAYER_TYPE_SOFTWARE
        // on your need touch draw
        if (mTouchDrawable != null)
            layerType = View.LAYER_TYPE_SOFTWARE;
        super.setLayerType(layerType, paint);
    }

    /**
     * Set the touch draw type
     * This type include:
     * TOUCH_EFFECT_NONE
     * TOUCH_EFFECT_AUTO
     * TOUCH_EFFECT_EASE
     * TOUCH_EFFECT_PRESS
     * TOUCH_EFFECT_RIPPLE
     *
     * @param touchEffect Touch effect type
     */
    public void setTouchEffect(int touchEffect) {
        if (touchEffect == 0)
            mTouchDrawable = null;
        else {
            if (mTouchDrawable == null) {
                mTouchDrawable = new TouchEffectDrawable();
                mTouchDrawable.getPaint().setColor(mTouchColor);
                mTouchDrawable.setCallback(this);
            }

            mTouchDrawable.setEffect(EffectFactory.creator(touchEffect));

            // We must set layer type is View.LAYER_TYPE_SOFTWARE,
            // to support Canvas.clipPath()
            // on Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB
            if (getLayerType() != View.LAYER_TYPE_SOFTWARE)
                setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        }
    }

    public void setTouchColor(int touchColor) {
        if (mTouchDrawable != null && touchColor != -1 && touchColor != mTouchColor) {
            mTouchColor = touchColor;
            mTouchDrawable.setColor(touchColor);
            invalidate();
        }
    }

    public void setTouchClipFactory(TouchEffectDrawable.ClipFactory factory) {
        if (mTouchDrawable != null) {
            mTouchDrawable.setClipFactory(factory);
        }
    }

    /**
     * Set the touch animation duration.
     * This setting about enter animation
     * and exit animation.
     * <p/>
     * Default:
     * EnterDuration: 280ms
     * ExitDuration: 160ms
     * FactorRate: 1.0
     * <p/>
     * This set will calculation: factor * duration
     * This factor need > 0
     *
     * @param factor Touch duration rate
     */
    public void setTouchDuration(float factor) {
        if (mTouchDrawable != null) {
            mTouchDrawable.setEnterDuration(factor);
            mTouchDrawable.setExitDuration(factor);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        TouchEffectDrawable drawable = mTouchDrawable;
        if (drawable != null) {
            /*
            Rect padding = new Rect();
            if (drawable.getPadding(padding) && (padding.left > 0
                    || padding.top > 0 || padding.right > 0 || padding.bottom > 0)) {
                drawable.setBounds(padding.left, padding.top, getWidth() - padding.right, getHeight() - padding.bottom);
            } else
            */
            drawable.setBounds(0, 0, getWidth(), getHeight());
        }
    }

    @Override
    protected boolean verifyDrawable(Drawable who) {
        Drawable drawable = mTouchDrawable;
        return (drawable != null && who == drawable) || super.verifyDrawable(who);
    }

    @Override
    public boolean performClick() {
        final TouchEffectDrawable d = mTouchDrawable;

        if (d != null) {
            return d.performClick(this) && super.performClick();
        } else
            return super.performClick();
    }

    @Override
    public void postPerformClick() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                performClick();
            }
        };

        if (!this.post(runnable)) {
            performClick();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //return super.onTouchEvent(event);
        final boolean ret = super.onTouchEvent(event);

        // send to touch drawable
        final TouchEffectDrawable d = mTouchDrawable;
        if (ret && d != null && isEnabled()) {
            d.onTouch(event);
        }

        return ret;
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {

        final TouchEffectDrawable d = mTouchDrawable;
        if (d != null) {
            d.draw(canvas);
        }

        super.onDraw(canvas);
    }

    @Override
    public boolean performLongClick() {
        return super.performLongClick();
    }
}
