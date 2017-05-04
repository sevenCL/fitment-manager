
package com.seven.library.callback;

/**
 * Interface that drawables supporting animations should implement.
 */
public interface Animatable extends android.graphics.drawable.Animatable {
    /**
     * This is drawable animation frame duration
     */
    public static final int FRAME_DURATION = 16;

    /**
     * This is drawable animation duration
     */
    public static final int ANIMATION_DURATION = 250;
}
