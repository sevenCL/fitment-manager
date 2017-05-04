package com.seven.library.ui.sheet;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.seven.library.R;
import com.seven.library.base.BaseSheet;
import com.seven.library.callback.DialogClickCallBack;
import com.seven.library.config.RunTimeConfig;
import com.seven.library.utils.DateFormatUtils;
import com.seven.library.view.widget.PickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 自定义 IOS 风格时间选择器
 *
 * @author seven
 */
public class IOSDateSheet extends BaseSheet implements PickerView.onSelectListener {

    //DATE_Y DATE_YMD DATE_YMDHS
    private int model;

    //取消、确定
    private RelativeLayout mCancel, mSure;

    //年、月、日、时、分 控件
    private PickerView mYearPv, mMonthPv, mDayPv, mHourPv, mMinutePv;
    private TextView mYearTv, mMonthTv, mDayTv, mHourTv, mMinuteTv;

    //是否循环滚动
    private boolean isLoop;

    //存放日期的集合
    private List<String> mYearList, mMonthList, mDayList, mHourList, mMinuteList;

    //年、月、日、时、分 及 坐标
    private int year, month, day, hour, minute;
    private int yearIndex, monthIndex, dayIndex, hourIndex, minuteIndex;

    //是否是闰年
    private boolean isLeap;

    //开始时间、结束时间
    private int startYear, startMonth, startDay, startHour, startMinute;
    private int endYear, endMonth, endDay, endHour, endMinute;

    //日历、日期
    private Calendar calendar;
    private Calendar selectCalendar;
    private Date date;

    private boolean isFirst;

    public IOSDateSheet(Activity activity, int theme, DialogClickCallBack dialogClickCallBack) {
        super(activity, theme, dialogClickCallBack);
    }

    public IOSDateSheet(Activity activity, int model, int theme, DialogClickCallBack dialogClickCallBack) {
        this(activity, theme, dialogClickCallBack);

        this.model = model;

        calendar = Calendar.getInstance();
        selectCalendar = Calendar.getInstance();
        date = new Date();

        isFirst = true;
    }

    @Override
    public int getRootLayoutId() {

        isTouch = true;

        return R.layout.sheet_ios_date;
    }

    @Override
    public void onInitRootView(Bundle savedInstanceState) {

        initView();

        initData();

    }

    @Override
    public void initView() {

        mCancel = getView(mCancel, R.id.date_cancel_rl);
        mSure = getView(mSure, R.id.date_sure_rl);
        mCancel.setOnClickListener(this);
        mSure.setOnClickListener(this);

        mYearPv = getView(mYearPv, R.id.year_picker_view);
        mYearPv.setOnSelectListener(this);

        if (model == RunTimeConfig.CommonConfig.DATE_YMD) {
            mMonthPv = getView(mMonthPv, R.id.month_picker_view);
            mMonthTv = getView(mMonthTv, R.id.month_picker_tv);
            mDayPv = getView(mDayPv, R.id.day_picker_view);
            mDayTv = getView(mDayTv, R.id.day_picker_tv);
            mMonthPv.setVisibility(View.VISIBLE);
            mMonthTv.setVisibility(View.VISIBLE);
            mDayPv.setVisibility(View.VISIBLE);
            mDayTv.setVisibility(View.VISIBLE);
            mMonthPv.setOnSelectListener(this);
            mDayPv.setOnSelectListener(this);
        }

        if (model == RunTimeConfig.CommonConfig.DATE_YMDHS) {
            mHourPv = getView(mHourPv, R.id.hour_picker_view);
            mHourTv = getView(mHourTv, R.id.hour_picker_tv);
            mMinutePv = getView(mMinutePv, R.id.minute_picker_view);
            mMinuteTv = getView(mMinuteTv, R.id.minute_picker_tv);
            mHourPv.setVisibility(View.VISIBLE);
            mHourTv.setVisibility(View.VISIBLE);
            mMinutePv.setVisibility(View.VISIBLE);
            mMinuteTv.setVisibility(View.VISIBLE);
            mHourPv.setOnSelectListener(this);
            mMinutePv.setOnSelectListener(this);
        }
    }

    @Override
    public void initData() {

        //是否循环滚动
        mYearPv.setIsLoop(isLoop);
        if (model == RunTimeConfig.CommonConfig.DATE_YMD) {
            mMonthPv.setIsLoop(isLoop);
            mDayPv.setIsLoop(isLoop);
        }
        if (model == RunTimeConfig.CommonConfig.DATE_YMDHS) {
            mHourPv.setIsLoop(isLoop);
            mMinutePv.setIsLoop(isLoop);
        }

        //根据需求初始化所需集合
        mYearList = new ArrayList<>();
        if (model == RunTimeConfig.CommonConfig.DATE_YMD) {
            mMonthList = new ArrayList<>();
            mDayList = new ArrayList<>();
        }
        if (model == RunTimeConfig.CommonConfig.DATE_YMDHS) {
            mHourList = new ArrayList<>();
            mMinuteList = new ArrayList<>();
        }

        //当前时间
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        //今年是否是闰年
        isLeap = year % 4 == 0 && year % 100 != 0 || year % 400 == 0;

        //加入年
        for (int i = startYear; i <= endYear; i++) {
            mYearList.add(String.valueOf(i));
            if (year == i)
                yearIndex = mYearList.size() - 1;
        }
        mYearPv.setData(mYearList);
        mYearPv.setSelected(yearIndex);
    }

    /**
     * 是否循环 在show之前调用
     *
     * @param isLoop
     */
    public void setIsLoop(boolean isLoop) {
        this.isLoop = isLoop;
    }

    /**
     * 设置时间区间(yyyy-MM-dd HH:mm)
     */
    public void setDateSection(String start, String end) {

        long startTime = DateFormatUtils.getInstance().dataToMillisecondMinute(start);
        long endTime = DateFormatUtils.getInstance().dataToMillisecondMinute(end);

        if (startTime == 0 || endTime == 0 || startTime >= endTime)
            return;

        //检查当前时间是否在区间内 0 开始  1 结束
        startTime = checkDateSection(0, startTime);
        endTime = checkDateSection(1, endTime);

        //开始时间
        date.setTime(startTime);
        calendar.setTime(date);

        startYear = calendar.get(Calendar.YEAR);
        startMonth = calendar.get(Calendar.MONTH) + 1;
        startDay = calendar.get(Calendar.DAY_OF_MONTH);
        startHour = calendar.get(Calendar.HOUR_OF_DAY);
        startMinute = calendar.get(Calendar.MINUTE);

        //结束时间
        date.setTime(endTime);
        calendar.setTime(date);

        endYear = calendar.get(Calendar.YEAR);
        endMonth = calendar.get(Calendar.MONTH) + 1;
        endDay = calendar.get(Calendar.DAY_OF_MONTH);
        endHour = calendar.get(Calendar.HOUR_OF_DAY);
        endMinute = calendar.get(Calendar.MINUTE);

        //使用后清空
        calendar = Calendar.getInstance();
    }

    /**
     * 检查当前时间是否在区间内 0 开始  1 结束
     *
     * @param model
     * @param time
     * @return
     */
    private long checkDateSection(int model, long time) {

        long now = System.currentTimeMillis();

        if (model == 0)
            return now < time ? now : time;
        else
            return now > time ? now : time;
    }

    /**
     * 选中年--改变月份
     *
     * @param text 年
     */
    private void monthChange(String text) {

        if (mMonthList.size() > 0)
            mMonthList.clear();

        monthIndex = 0;

        int minMonth = 1;
        int maxMonth = 12;

        int selectYear = Integer.parseInt(text);

        //当前年份
        if (selectYear == year) {
            if (selectYear == startYear) //当前日期是起始时间
                minMonth = month;
            else if (selectYear == endYear) {//当前日期是结束时间
                maxMonth = month;
                if (isFirst)
                    monthIndex = maxMonth - 1;
            } else {
                if (isFirst)
                    monthIndex = month - 1;
            }
        } else {
            if (selectYear == startYear) //当前日期是起始时间
                minMonth = startMonth;
            else if (selectYear == endYear) //当前日期是结束时间
                maxMonth = endMonth;
        }
        for (int i = minMonth; i <= maxMonth; i++)
            mMonthList.add(String.valueOf(i));

        mMonthPv.setData(mMonthList);
        mMonthPv.setSelected(monthIndex);
        animator(200, mMonthPv);
    }

    /**
     * 选中年、月--改变天
     *
     * @param text 月份
     */
    private void dayChange(String text) {

        if (mDayList.size() > 0)
            mDayList.clear();

        dayIndex = 0;

        int minDay = 1;
        int maxDay = 31;

        int selectYear = selectCalendar.get(Calendar.YEAR);
        int selectMonth = Integer.parseInt(text);
        boolean leap = selectYear % 4 == 0 && selectYear % 100 != 0 || selectYear % 400 == 0;

        if (selectMonth == 2) {
            //选中年份是闰年
            if (leap)
                maxDay = 29;
            else
                maxDay = 28;

            //选中年份是否是当前年份
            if (selectYear == year) {
                if (selectYear == startYear && selectMonth == startMonth) //当前日期是起始时间
                    minDay = day;
                else if (selectYear == endYear && selectMonth == endMonth) {//当前日期是结束时间
                    maxDay = day;
                    if (isFirst)
                        dayIndex = maxDay - 1;
                } else {
                    if (isFirst)
                        dayIndex = day - 1;
                }
            } else {
                if (selectYear == startYear && selectMonth == startMonth) //当前日期是起始时间
                    minDay = startDay;
                else if (selectYear == endYear && selectMonth == endMonth) //当前日期是结束时间
                    maxDay = endDay;
            }
        } else if (selectMonth == 1 || selectMonth == 3 || selectMonth == 5 ||
                selectMonth == 7 || selectMonth == 8 || selectMonth == 10 || selectMonth == 12) {
            //选中年份是否是当前年份
            if (selectYear == year) {
                if (selectYear == startYear && selectMonth == startMonth) //当前日期是起始时间
                    minDay = day;
                else if (selectYear == endYear && selectMonth == endMonth) {//当前日期是结束时间
                    maxDay = day;
                    if (isFirst)
                        dayIndex = maxDay - 1;
                } else {
                    if (isFirst)
                        dayIndex = day - 1;
                }
            } else {
                if (selectYear == startYear && selectMonth == startMonth) //当前日期是起始时间
                    minDay = startDay;
                else if (selectYear == endYear && selectMonth == endMonth) //当前日期是结束时间
                    maxDay = endDay;
            }
        } else {
            //选中年份是否是当前年份
            if (selectYear == year) {
                if (selectYear == startYear && selectMonth == startMonth) //当前日期是起始时间
                    minDay = day;
                else if (selectYear == endYear && selectMonth == endMonth) {//当前日期是结束时间
                    maxDay = day;
                    if (isFirst)
                        dayIndex = maxDay - 1;
                } else {
                    if (isFirst)
                        dayIndex = day - 1;
                }
            } else {
                if (selectYear == startYear && selectMonth == startMonth) //当前日期是起始时间
                    minDay = startDay;
                else if (selectYear == endYear && selectMonth == endMonth) //当前日期是结束时间
                    maxDay = endDay;
            }
        }

        for (int i = minDay; i <= maxDay; i++)
            mDayList.add(String.valueOf(i));

        mDayPv.setData(mDayList);
        mDayPv.setSelected(dayIndex);
        animator(200, mDayPv);

        if (model == RunTimeConfig.CommonConfig.DATE_YMD)
            isFirst = false;
    }

    /**
     * 选中年、月、日--改变时
     *
     * @param text 天
     */
    private void hourChange(String text) {

        if (mHourList.size() > 0)
            mHourList.clear();

        hourIndex = 0;

        int minHour = 0;
        int maxHour = 23;

        int selectYear = selectCalendar.get(Calendar.YEAR);
        int selectMonth = selectCalendar.get(Calendar.MONTH) + 1;
        int selectDay = Integer.parseInt(text);

        //选中年份是否是当前年份
        if (selectYear == year) {
            if (selectYear == startYear && selectMonth == startMonth && selectDay == startDay) //当前日期是起始时间
                minHour = hour;
            else if (selectYear == endYear && selectMonth == endMonth && selectDay == endDay) {//当前日期是结束时间
                maxHour = hour;
                if (isFirst)
                    hourIndex = maxHour;
            } else {
                if (isFirst)
                    hourIndex = hour;
            }
        } else {
            if (selectYear == startYear && selectMonth == startMonth && selectDay == startDay) //当前日期是起始时间
                minHour = startDay;
            else if (selectYear == endYear && selectMonth == endMonth && selectDay == endDay) //当前日期是结束时间
                maxHour = endDay;
        }

        for (int i = minHour; i <= maxHour; i++)
            mHourList.add(String.valueOf(i));

        mHourPv.setData(mHourList);
        mHourPv.setSelected(hourIndex);
        animator(200, mHourPv);
    }

    /**
     * 选中年、月、日、时--改变分
     *
     * @param text 时
     */
    private void minuteChange(String text) {

        if (mMinuteList.size() > 0)
            mMinuteList.clear();

        minuteIndex = 0;

        int minMinute = 0;
        int maxMinute = 59;

        int selectYear = selectCalendar.get(Calendar.YEAR);
        int selectMonth = selectCalendar.get(Calendar.MONTH) + 1;
        int selectDay = selectCalendar.get(Calendar.DAY_OF_MONTH);
        int selectHour = Integer.parseInt(text);

        //选中年份是否是当前年份
        if (selectYear == year) {
            if (selectYear == startYear && selectMonth == startMonth && selectDay == startDay && selectHour == startHour) //当前日期是起始时间
                minMinute = minute;
            else if (selectYear == endYear && selectMonth == endMonth && selectDay == endDay && selectHour == endHour) {//当前日期是结束时间
                maxMinute = minute;
                if (isFirst)
                    minuteIndex = maxMinute;
            } else {
                if (isFirst)
                    minuteIndex = minute;
            }
        } else {
            if (selectYear == startYear && selectMonth == startMonth && selectDay == startDay && selectHour == startHour) //当前日期是起始时间
                minMinute = startMinute;
            else if (selectYear == endYear && selectMonth == endMonth && selectDay == endDay && selectHour == endHour) //当前日期是结束时间
                maxMinute = endMinute;
        }

        for (int i = minMinute; i <= maxMinute; i++)
            mMinuteList.add(String.valueOf(i));

        mMinutePv.setData(mMinuteList);
        mMinutePv.setSelected(minuteIndex);
        animator(200, mMinutePv);

        isFirst = false;
    }

    /**
     * 动画
     *
     * @param duration
     * @param view
     */
    private void animator(long duration, View view) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f,
                0f, 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f,
                1.3f, 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f,
                1.3f, 1f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ).setDuration(duration).start();
    }

    @Override
    public void onSelect(View v, String text) {

        int id = v.getId();

        if (id == R.id.year_picker_view) {//年
            selectCalendar.set(Calendar.YEAR, Integer.parseInt(text));
            if (model == RunTimeConfig.CommonConfig.DATE_YMD)
                monthChange(text);
        } else if (id == R.id.month_picker_view) {//月
            selectCalendar.set(Calendar.MONTH, Integer.parseInt(text) - 1);
            dayChange(text);
        } else if (id == R.id.day_picker_view) {//日
            selectCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(text));
            if (model == RunTimeConfig.CommonConfig.DATE_YMDHS)
                hourChange(text);
        } else if (id == R.id.hour_picker_view) {//时
            selectCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(text));
            minuteChange(text);
        } else {
            selectCalendar.set(Calendar.MINUTE, Integer.parseInt(text));
        }
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.date_sure_rl) //确定选择时间
            mCallBack.onClick(v, selectCalendar.getTimeInMillis());

        dismiss();
    }


}
