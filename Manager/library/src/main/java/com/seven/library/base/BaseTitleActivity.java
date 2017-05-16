package com.seven.library.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.seven.library.R;

/**
 * 统一标题的基类
 *
 * @author seven
 */
public abstract class BaseTitleActivity extends BaseActivity {

    //标题底层布局
    private RelativeLayout rootLayout;

    //标题左侧按钮
    private RelativeLayout leftBtn;
    private ImageView leftImg;

    //标题右侧按钮
    private RelativeLayout rightBtn;
    private ImageView rightImg;

    //标题右侧文字按钮
    private RelativeLayout rightTextBtn;
    private TextView rightText;

    //标题(居中)
    private TextView title;

    //控制是否显示
    public boolean isLeftBtn;
    public boolean isRightBtn;
    public boolean isRightTextBtn;

    //这个类独有的点击事件(请不要实现全局点击事件,继承类需要使用点击事件会冲突)
    private BaseTitleOnClick clickListener;

    @Override
    public int getRootLayoutId() {
        return R.layout.base_activity_title;
    }

    @Override
    public void onInitRootView(Bundle savedInstanceState) {

        //初始化布局
        leftBtn = getView(leftBtn, R.id.base_left_rl);
        leftImg = getView(leftImg, R.id.base_left_img);

        rightBtn = getView(rightBtn, R.id.base_right_rl);
        rightImg = getView(rightImg, R.id.base_right_img);

        rightTextBtn = getView(rightTextBtn, R.id.base_right_text_rl);
        rightText = getView(rightText, R.id.base_right_text_tv);

        title = getView(title, R.id.base_title_tv);

        //初始化事件
        clickListener = new BaseTitleOnClick();
        leftBtn.setOnClickListener(clickListener);
        rightBtn.setOnClickListener(clickListener);
        rightTextBtn.setOnClickListener(clickListener);

        //添加title到底层布局
        View contentView = LayoutInflater.from(this).inflate(getContentLayoutId(), null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        params.topMargin = (int) getResources().getDimension(R.dimen.title);
        contentView.setLayoutParams(params);

        rootLayout = getView(rootLayout, R.id.root_layout);
        rootLayout.addView(contentView);

        onInitContentView(savedInstanceState);

        //根据状态显示所需的图标
        leftBtn.setVisibility(isLeftBtn ? View.VISIBLE : View.GONE);
        rightBtn.setVisibility(isRightBtn ? View.VISIBLE : View.GONE);
        rightTextBtn.setVisibility(isRightTextBtn ? View.VISIBLE : View.GONE);
    }

    /**
     * 当初始化view后回调，仅次于onCreate之后调用
     *
     * @param savedInstanceState
     */
    public abstract void onInitContentView(Bundle savedInstanceState);

    /**
     * 初始化布局xml
     *
     * @return
     */
    public abstract int getContentLayoutId();

    /**
     * 标题"左"按钮点击
     */
    public abstract void onLeftButtonClicked();

    /**
     * 标题"右"按钮点击
     */
    public abstract void onRightButtonClicked();

    /**
     * 标题"左"文本按钮点击
     */
    public abstract void onRightTextClicked();

    /**
     * 设置标题
     *
     * @param titleName
     */
    protected void setTitle(String titleName) {
        title.setText(titleName);
    }

    /**
     * 设置"左"按钮的背景
     *
     * @param id
     */
    protected void setLeftButtonBackground(int id) {
        leftImg.setBackgroundResource(id);
    }

    /**
     * 设置"右"按钮的背景
     *
     * @param id
     */
    protected void setRightButtonBackground(int id) {
        rightImg.setBackgroundResource(id);
    }

    /**
     *
     * @param visible
     */
    protected void setRightButtonVisible(int visible) {
        rightBtn.setVisibility(visible);
    }

    /**
     * 设置"右"按钮的文本
     *
     * @param text
     */
    protected void setRightText(String text) {
        rightText.setText(text);
    }

    private class BaseTitleOnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            int id = v.getId();

            if (R.id.base_left_rl == id) {
                onLeftButtonClicked();
            } else if (R.id.base_right_rl == id) {
                onRightButtonClicked();
            } else if (R.id.base_right_text_rl == id) {
                onRightTextClicked();
            }

           /* 这里不能使用switch-case的原因:
            1.android studio library中生成的R文件不是final的;
            2.在API14之后建议使用if-else替代(感觉好low);
            3.在这种情况下"注解"也将不能使用,所以有些框架慎用
            */

           /* switch (id){

                case R.id.base_left_rl:

                    onLeftButtonClicked();

                    break;
                case R.id.base_right_rl:

                    onRightButtonClicked();

                    break;

                case R.id.base_right_text_rl:

                    onRightTextClicked();

                    break;

            }*/
        }
    }
}
