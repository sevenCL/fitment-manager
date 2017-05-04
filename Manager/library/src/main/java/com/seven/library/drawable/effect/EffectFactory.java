
package com.seven.library.drawable.effect;

/**
 * This is TouchEffectDrawable draw {@link Effect }'s EffectFactory
 */
public final class EffectFactory {
    public static final int TOUCH_EFFECT_NONE = 0;
    public static final int TOUCH_EFFECT_AUTO = 1;
    public static final int TOUCH_EFFECT_EASE = 2;
    public static final int TOUCH_EFFECT_PRESS = 3;
    public static final int TOUCH_EFFECT_RIPPLE = 4;

    public static Effect creator(int which) {
        if (which == TOUCH_EFFECT_AUTO)
            return (new AutoEffect());
        else if (which == TOUCH_EFFECT_EASE)
            return (new EaseEffect());
        else if (which == TOUCH_EFFECT_PRESS)
            return (new PressEffect());
        else if (which == TOUCH_EFFECT_RIPPLE)
            return (new RippleEffect());
        else
            return null;
    }
}
