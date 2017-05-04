package com.seven.library.task;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.seven.library.R;
import com.seven.library.utils.LogUtils;
import com.seven.library.utils.ResourceUtils;
import com.seven.library.utils.ToastUtils;

import java.util.Iterator;
import java.util.Stack;

/**
 * activity栈
 *
 * @author seven
 */
public class ActivityStack {
    private static ActivityStack mSingleInstance;
    private Stack<Activity> mActivityStack;
    private boolean isAnimationEnable = false;
    private int animationIn, animationOut;

    private ActivityStack() {
        mActivityStack = new Stack<Activity>();
    }

    public static ActivityStack getInstance() {
        if (null == mSingleInstance) {
            mSingleInstance = new ActivityStack();
        }
        return mSingleInstance;
    }

    public void setAnimationEnable(boolean isEnable) {
        isAnimationEnable = isEnable;
    }

    public void setAnimation(int inAnim, int outAnim) {
        animationIn = inAnim;
        animationOut = outAnim;
    }

    /**
     * 带返回的activity跳转
     *
     * @param srcAct       activity源
     * @param desActCls    需要启动的activity类
     * @param isFinishSelf 是否要finish启动者
     * @param bundle       要传递的参数
     * @param requestCode  请求 码
     * @param flags        intent标志
     */
    public void startActivityForResult(Activity srcAct,
                                       Class<? extends Activity> desActCls, boolean isFinishSelf, Bundle bundle, int requestCode,
                                       int... flags) {
        if (null == srcAct || null == desActCls) {
            return;
        }
        Intent intent = new Intent(srcAct, desActCls);
        if (null != flags) {
            for (int flag : flags) {
                intent.addFlags(flag);
            }
        }
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        srcAct.startActivityForResult(intent, requestCode);
        if (isFinishSelf) {
            srcAct.finish();
        }
    }

    /**
     * 带返回的activity跳转
     *
     * @param srcActCls    activity源类
     * @param desActCls    需要启动的activity类
     * @param isFinishSelf 是否要finish启动者
     * @param bundle       要传递的参数
     * @param requestCode  请求 码
     * @param flags        intent标志
     */
    public void startActivityForResult(Class<? extends Activity> srcActCls,
                                       Class<? extends Activity> desActCls, boolean isFinishSelf, Bundle bundle, int requestCode,
                                       int... flags) {
        Activity srcAct = findActivityByClass(srcActCls);
        startActivityForResult(srcAct, desActCls, isFinishSelf, bundle, requestCode, flags);
    }

    /**
     * 带返回的activity跳转
     *
     * @param desActCls    需要启动的activity类
     * @param isFinishSelf 是否要finish启动者
     * @param bundle       要传递的参数
     * @param requestCode  请求 码
     * @param flags        intent标志
     */
    public void startActivityForResult(Class<? extends Activity> desActCls, boolean isFinishSelf, Bundle bundle, int requestCode,
                                       int... flags) {
        Activity srcAct = peekActivity();
        if (null != srcAct) {
            startActivityForResult(srcAct, desActCls, isFinishSelf, bundle, requestCode, flags);
        }
    }

    /**
     * 启动一个activity，默认启动者为activity栈定顶元素
     *
     * @param desActCls      需要启动的activity类
     * @param isFinishSrcAct 是否要finish启动者
     * @param flags          启动intent的flag
     */
    public void startActivity(Class<? extends Activity> desActCls,
                              boolean isFinishSrcAct, int... flags) {
        int size = mActivityStack.size();
        if (1 <= size) {
            Activity srcAct = mActivityStack.get(size - 1);
            Intent intent = new Intent(srcAct, desActCls);
            if (null != flags) {
                for (int flag : flags) {
                    intent.addFlags(flag);
                }
            }
            srcAct.startActivity(intent);
            if (isAnimationEnable) {
                srcAct.overridePendingTransition(animationIn,
                        animationOut);
            }
            if (isFinishSrcAct) {
                srcAct.finish();
            }
        }

    }

    /**
     * 启动一个activity，默认启动者为activity栈定顶元素（带参数）
     *
     * @param desActCls      需要启动的activity类
     * @param isFinishSrcAct 是否要finish启动者
     * @param bundle         需要传递的参数
     * @param flags          启动intent的flag
     */
    public void startActivity(Class<? extends Activity> desActCls,
                              boolean isFinishSrcAct, Bundle bundle, int... flags) {
        int size = mActivityStack.size();
        if (1 <= size) {
            Activity srcAct = mActivityStack.get(size - 1);
            startActivity(srcAct, desActCls, isFinishSrcAct, bundle, flags);
        }

    }

    /**
     * 通过包名启动一个activity，默认启动者为activity栈定顶元素（带参数）
     *
     * @param packageName    需要启动的activity类带包
     * @param isFinishSrcAct 是否要finish启动者
     * @param bundle         需要传递的参数
     * @param flags          启动intent的flag
     */
    public void startPackageActivity(String packageName, boolean isFinishSrcAct, Bundle bundle, int... flags) {

        int size = mActivityStack.size();
        if (1 <= size) {
            Activity srcAct = mActivityStack.get(size - 1);
            startPackageActivity(srcAct, packageName, isFinishSrcAct, bundle, flags);
        }
    }

    /**
     * 启动一个activity，手动指定启动者实例
     *
     * @param srcAct       启动者activity
     * @param desActCls    需要启动的activity类
     * @param isFinishSelf 是否要finish启动者
     * @param flags        启动intent的flag
     */
    public void startActivity(Activity srcAct,
                              Class<? extends Activity> desActCls, boolean isFinishSelf,
                              int... flags) {
        Intent intent = new Intent(srcAct, desActCls);
        if (null != flags) {
            for (int flag : flags) {
                intent.addFlags(flag);
            }
        }
        srcAct.startActivity(intent);

        if (isFinishSelf) {
            srcAct.finish();
        }
    }

    /**
     * 启动一个activity，手动指定启动者实例
     *
     * @param srcAct       启动者activity
     * @param desActCls    需要启动的activity类
     * @param isFinishSelf 是否要finish启动者
     * @param bundle       传递的bundle
     * @param flags        启动intent的flag
     */
    public void startActivity(Activity srcAct,
                              Class<? extends Activity> desActCls, boolean isFinishSelf, Bundle bundle,
                              int... flags) {
        Intent intent = new Intent(srcAct, desActCls);
        if (null != flags) {
            for (int flag : flags) {
                intent.addFlags(flag);
            }
        }
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        srcAct.startActivity(intent);

        if (isFinishSelf) {
            srcAct.finish();
        }
    }

    /**
     * 通过包名启动一个activity，手动指定启动者实例
     *
     * @param srcAct       启动者activity
     * @param packageName  需要启动的activity类带包名
     * @param isFinishSelf 是否要finish启动者
     * @param bundle       传递的bundle
     * @param flags        启动intent的flag
     */
    public void startPackageActivity(Activity srcAct, String packageName, boolean isFinishSelf, Bundle bundle, int... flags) {

        if (TextUtils.isEmpty(packageName))
            return;

        Intent intent = new Intent();
        if (null != flags) {
            for (int flag : flags)
                intent.addFlags(flag);
        }

        if (null != bundle) {
            intent.putExtras(bundle);
        }
        intent.setClassName(srcAct, packageName);

        try {
            srcAct.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            ToastUtils.getInstance().showToast(packageName + ResourceUtils.getInstance().getText(R.string.msg_not_find));
            LogUtils.println(packageName + ResourceUtils.getInstance().getText(R.string.msg_not_find));
        }

        if (isFinishSelf) {
            srcAct.finish();
        }
    }

    /**
     * 启动一个activity,手动指定启动者类
     *
     * @param srcActCls      启动者activity类
     * @param desActCls      需要启动的activity类
     * @param isFinishSrcAct 是否要finish启动者
     * @param flags          启动intent的flag
     */
    public void startActivity(Class<? extends Activity> srcActCls,
                              Class<? extends Activity> desActCls, boolean isFinishSrcAct,
                              int... flags) {
        Activity srcAct = findActivityByClass(srcActCls);
        if (null != srcAct) {
            Intent intent = new Intent(srcAct, desActCls);
            if (null != flags) {
                for (int flag : flags) {
                    intent.addFlags(flag);
                }
            }
            srcAct.startActivity(intent);

            if (isFinishSrcAct) {
                srcAct.finish();
            }
        }

    }

    /**
     * 返回到上一个activity
     *
     * @param isFinishSelf 是否需要finish自己
     */
    public void backToPreActivity(boolean isFinishSelf) {
        int size = mActivityStack.size();
        if (2 <= size) {
            Activity topAct = mActivityStack.get(size - 1);
            Activity preAct = mActivityStack.get(size - 2);
            startActivity(topAct, preAct.getClass(), isFinishSelf);
        }
    }

    /**
     * 返回上一层的activity
     *
     * @param preActCls    需要返回的activity类
     * @param isFinishSelf 是否需要finish自己
     */
    public void backToActivity(Class<? extends Activity> preActCls,
                               boolean isFinishSelf) {
        int size = mActivityStack.size();
        if (2 <= size) {
            Activity topAct = mActivityStack.get(size - 1);
            Activity preAct = findActivityByClass(preActCls);
            if (null != topAct && null != preAct) {
                startActivity(topAct, preAct.getClass(), isFinishSelf);
            }

        }
    }

    /**
     * 找到指定的activity
     *
     * @param actCls 需要寻找的activity的类
     * @return activity_amount_report 实例
     */
    public Activity findActivityByClass(Class<? extends Activity> actCls) {
        Activity aActivity = null;
        Iterator<Activity> itr = mActivityStack.iterator();
        while (itr.hasNext()) {
            aActivity = itr.next();
            if (null != aActivity && aActivity.getClass().getName().equals(actCls.getName()) && !aActivity.isFinishing()) {
                break;
            }
            aActivity = null;
        }
        return aActivity;
    }

    /**
     * finish指定的activity类
     *
     * @param actCls 需要finish的类
     * @return 是否成功调用finish
     */
    public boolean finishActivity(Class<? extends Activity> actCls) {
        Activity act = findActivityByClass(actCls);
        if (null != act && !act.isFinishing()) {
            act.finish();
            return true;
        }
        return false;
    }

    /**
     * finish指定的activity类之上的所有activity。
     *
     * @param actCls        需要finish的类
     * @param isIncludeSelf 是否包含自己
     * @return 是否成功调用finish
     */
    public boolean finishToActivity(Class<? extends Activity> actCls, boolean isIncludeSelf) {
        Activity actFloor = findActivityByClass(actCls);
        if (null != actFloor && !actFloor.isFinishing()) {
            Activity act = popActivity();
            while (act != null && act != actFloor) {
                act.finish();
                act = popActivity();
            }
            if (isIncludeSelf) {
                actFloor.finish();
            } else {
                pushActivity(actFloor);
            }
            return true;
        }
        return false;
    }

    /**
     * 结束掉当前activity
     */
    public void finishCurrentActivity() {
        peekActivity().finish();
    }

    public void finishAllActivity() {
        Activity activity;
        while (!mActivityStack.empty()) {
            activity = mActivityStack.pop();
            if (activity != null)
                activity.finish();
        }
    }

    public void pushActivity(Activity aActivity) {
        mActivityStack.push(aActivity);
    }


    /**
     * 获取ActivityStack顶部的Activity并移除,<b>注意：在需要Activity上下文时使用，如果只需要使用应用上下文请使用Application.getInstance()
     *
     * @return Activity
     */
    public Activity popActivity() {
        if (mActivityStack.isEmpty()) {
            return null;
        }
        Activity act = mActivityStack.pop();
        while (act == null || act.isFinishing()) {
            if (mActivityStack.isEmpty()) {
                return null;
            }
            act = mActivityStack.pop();
        }
        return act;
    }

    /**
     * 获取ActivityStack顶部的Activity,<b>注意：在需要Activity上下文时使用，如果只需要使用应用上下文请使用Application.getInstance()
     *
     * @return Activity
     */
    public Activity peekActivity() {
        if (mActivityStack.isEmpty()) {
            return null;
        }
        Activity act = mActivityStack.peek();
        while (act == null || act.isFinishing()) {
            mActivityStack.pop();
            if (mActivityStack.isEmpty()) {
                return null;
            }
            act = mActivityStack.peek();
        }
        return act;
    }

    public void removeActivity(Activity aActivity) {
        Iterator<Activity> itr = mActivityStack.iterator();
        while (itr.hasNext()) {
            if (itr.next() == aActivity) {
                itr.remove();
                break;
            }
        }
    }
}
