package com.seven.library.view.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.seven.library.R;
import com.seven.library.drawable.CircleCheckDrawable;
import com.seven.library.view.Ui;

/**
 * This is CheckBox widget
 * The widget extend view widget
 */
public class CheckBox extends android.widget.CheckBox {
    private CircleCheckDrawable mMarkDrawable;

    public CheckBox(Context context) {
        super(context);
        init(null, R.attr.gCheckBoxStyle, R.style.Genius_Widget_CompoundButton_CheckBox);
    }

    public CheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, R.attr.gCheckBoxStyle, R.style.Genius_Widget_CompoundButton_CheckBox);
    }

    public CheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr, R.style.Genius_Widget_CompoundButton_CheckBox);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CheckBox(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(CheckBox.class.getName());
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(CheckBox.class.getName());
    }

    private void init(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        final Context context = getContext();
        final Resources resource = getResources();
        final float density = resource.getDisplayMetrics().density;
        final int baseSize = (int) (density * 2);

        if (attrs == null) {
            mMarkDrawable = new CircleCheckDrawable(resource.getColorStateList(R.color.color_primary));
            setButtonDrawable(mMarkDrawable);
            return;
        }

        // Load attributes
        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.CheckBox, defStyleAttr, defStyleRes);

        int borderSize = a.getDimensionPixelOffset(R.styleable.CheckBox_gBorderSize, baseSize);
        int intervalSize = a.getDimensionPixelOffset(R.styleable.CheckBox_gIntervalSize, baseSize);
        int markSize = a.getDimensionPixelOffset(R.styleable.CheckBox_gMarkSize, -1);
        ColorStateList color = a.getColorStateList(R.styleable.CheckBox_gMarkColor);
        String fontFile = a.getString(R.styleable.CheckBox_gFont);

        a.recycle();

        if (color == null)
            color = resource.getColorStateList(R.color.color_primary);

        boolean isCustom = true;

        if (markSize < 0) {
            markSize = (int) (density * 22);
            isCustom = false;
        }

        mMarkDrawable = new CircleCheckDrawable(color);
        mMarkDrawable.setBorderSize(borderSize);
        mMarkDrawable.setIntervalSize(intervalSize);
        mMarkDrawable.setMarkSize(markSize, isCustom);
        mMarkDrawable.inEditMode(isInEditMode());
        setButtonDrawable(mMarkDrawable);

        // Check for IDE preview render
        if (!this.isInEditMode()) {
            // Font
            if (fontFile != null && fontFile.length() > 0) {
                Typeface typeface = Ui.getFont(getContext(), fontFile);
                if (typeface != null) setTypeface(typeface);
            }
        }
    }

    public void setBorderSize(int size) {
        mMarkDrawable.setBorderSize(size);
    }

    public void setIntervalSize(int size) {
        mMarkDrawable.setIntervalSize(size);
    }

    public void setMarkSize(int size) {
        mMarkDrawable.setMarkSize(size, true);
    }

    public int getBorderSize() {
        return mMarkDrawable.getBorderSize();
    }

    public int getIntervalSize() {
        return mMarkDrawable.getIntervalSize();
    }

    public int getMarkSize() {
        return mMarkDrawable.getMarkSize();
    }

    public void setMarkColor(int color) {
        mMarkDrawable.setColor(color);
    }

    public void setMarkColor(ColorStateList colorList) {
        mMarkDrawable.setColorStateList(colorList);
    }

    public ColorStateList getMarkColor() {
        return mMarkDrawable.getColorStateList();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // Refresh display with current params
        refreshDrawableState();
    }
}
