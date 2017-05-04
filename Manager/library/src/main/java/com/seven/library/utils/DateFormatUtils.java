package com.seven.library.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;

/**
 * 日期转换工具辅助类
 *
 * @author seven
 */
public class DateFormatUtils {

    private static volatile DateFormatUtils dateFormatUtil;

    private DateFormatUtils() {

    }

    public static DateFormatUtils getInstance() {

        if (dateFormatUtil == null) {
            synchronized (DateFormatUtils.class) {

                dateFormatUtil = new DateFormatUtils();

            }

        }

        return dateFormatUtil;
    }

    /**
     * 获取指定日期过后的n天的日期
     *
     * @param startDate
     * @param days
     * @param isWeekendInclud 是否包含周末
     * @return
     */
    public String getEndDate(Date startDate, int days, Integer isWeekendInclud) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (1 == isWeekendInclud.intValue()) {
            Calendar cld = Calendar.getInstance();
            cld.setTime(startDate);
            cld.add(Calendar.DAY_OF_YEAR, days);
            return simpleDateFormat.format(cld.getTime());
        } else {
            int num = 0;
            Calendar cld = Calendar.getInstance();
            while (num < days) {
                if (!isWeekend(startDate)) {
                    num++;
                }
                cld.setTime(startDate);
                cld.add(Calendar.DAY_OF_YEAR, 1);
                startDate = cld.getTime();

            }
            return simpleDateFormat.format(startDate);
        }
    }

    /**
     * 判断是否为周末
     *
     * @param date
     * @return
     */
    public boolean isWeekend(Date date) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(date);
        int week = cld.get(Calendar.DAY_OF_WEEK);
        if (week == 1 || week == 7) {
            return true;
        } else {

            return false;
        }
    }


    /**
     * 毫秒转日期（精确到天）
     *
     * @param millisecond
     * @return
     */
    public String millisecondToDateSecond(long millisecond) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(millisecond);

        return simpleDateFormat.format(date);
    }

    /**
     * 毫秒转日期（精确到天）
     *
     * @param millisecond
     * @return
     */
    public String millisecondToDateSecondPoint(long millisecond) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        Date date = new Date(millisecond);

        return simpleDateFormat.format(date);
    }

    /**
     * 毫秒转日期（精确到天）
     *
     * @param millisecond
     * @return
     */
    public String millisecondToDateSecondC(long millisecond) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd HH:mm");
        Date date = new Date(millisecond);

        return simpleDateFormat.format(date);
    }

    /**
     * 毫秒转日期（精确到天）
     *
     * @param millisecond
     * @return
     */
    public String millisecondToDateDay(long millisecond) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(millisecond);

        return simpleDateFormat.format(date);
    }

    /**
     * 毫秒转日期（精确到年）
     *
     * @param millisecond
     * @return
     */
    public String millisecondToDateYear(long millisecond) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date(millisecond);

        return simpleDateFormat.format(date);
    }

    /**
     * 日期转时间戳
     *
     * @param time
     * @return
     */
    public long dataToMillisecondMinute(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm",
                Locale.CHINA);
        Date date;
        long times = 0;
        try {
            date = sdr.parse(time);
            times = date.getTime();
//            String stf = String.valueOf(l);
//            times = stf.substring(0, 10);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return times;
    }

    /**
     * 日期转时间戳
     *
     * @param time
     * @return
     */
    public long dataToMillisecondMinuteC(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分",
                Locale.CHINA);
        Date date;
        long times = 0;
        try {
            date = sdr.parse(time);
            times = date.getTime();
//            String stf = String.valueOf(l);
//            times = stf.substring(0, 10);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return times;
    }

    /**
     * 日期转时间戳
     *
     * @param time
     * @return
     */
    public long dataToMillisecondDay(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd",
                Locale.CHINA);
        Date date;
        long times = 0;
        try {
            date = sdr.parse(time);
            times = date.getTime();
//            String stf = String.valueOf(l);
//            times = stf.substring(0, 10);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return times;
    }

    /**
     * 日期转时间戳
     *
     * @param time
     * @return
     */
    public long dataToMillisecondMonth(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM",
                Locale.CHINA);
        Date date;
        long times = 0;
        try {
            date = sdr.parse(time);
            times = date.getTime();
//            String stf = String.valueOf(l);
//            times = stf.substring(0, 10);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return times;
    }

    /**
     * 日期转时间戳
     *
     * @param time
     * @return
     */
    public long dataToMillisecondMonthC(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月",
                Locale.CHINA);
        Date date;
        long times = 0;
        try {
            date = sdr.parse(time);
            times = date.getTime();
//            String stf = String.valueOf(l);
//            times = stf.substring(0, 10);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return times;
    }

    /**
     * 日期转时间戳
     *
     * @param time
     * @return
     */
    public long dataToMillisecondDayC(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日",
                Locale.CHINA);
        Date date;
        long times = 0;
        try {
            date = sdr.parse(time);
            times = date.getTime();
//            String stf = String.valueOf(l);
//            times = stf.substring(0, 10);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return times;
    }

    /**
     * 获取现在时间
     *
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
    public Date getNowDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }

    /**
     * 获取现在时间
     *
     * @return返回短时间格式 yyyy-MM-dd
     */
    public Date getNowDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }

    /**
     * 获取现在时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    public String getStringDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取时间 小时:分;秒 HH:mm:ss
     *
     * @return
     */
    public String getTimeShort() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date currentTime = new Date();
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate
     * @return
     */
    public Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss  *   * @param dateDate  * @return
     */
    public String dateToStrLong(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 将短时间格式时间转换为字符串 yyyy-MM-dd
     *
     * @param dateDate
     * @return
     */
    public String dateToStr(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     *
     * @param strDate
     * @return
     */
    public Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 得到现在时间
     *
     * @return
     */
    public Date getNow() {
        Date currentTime = new Date();
        return currentTime;
    }

    /**
     * 提取一个月中的最后一天
     *
     * @param day
     * @return
     */
    public Date getLastDate(long day) {
        Date date = new Date();
        long date_3_hm = date.getTime() - 3600000 * 34 * day;
        Date date_3_hm_date = new Date(date_3_hm);
        return date_3_hm_date;
    }

    /**
     * 得到现在时间
     *
     * @return 字符串 yyyyMMdd HHmmss
     */
    public String getStringToday() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 得到现在小时
     */
    public String getHour() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        String hour;
        hour = dateString.substring(11, 13);
        return hour;
    }

    /**
     * 得到现在分钟
     *
     * @return
     */
    public String getTime() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        String min;
        min = dateString.substring(14, 16);
        return min;
    }

    /**
     * 根据用户传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
     *
     * @param sformat yyyyMMddhhmmss
     * @return
     */
    public String getUserDate(String sformat) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(sformat);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 二个小时时间间的差值,必须保证二个时间都是"HH:MM"的格式，返回字符型的分钟
     */
    public String getTwoHour(String st1, String st2) {
        String[] kk = null;
        String[] jj = null;
        kk = st1.split(":");
        jj = st2.split(":");
        if (Integer.parseInt(kk[0]) < Integer.parseInt(jj[0]))
            return "0";
        else {
            double y = Double.parseDouble(kk[0]) + Double.parseDouble(kk[1]) / 60;
            double u = Double.parseDouble(jj[0]) + Double.parseDouble(jj[1]) / 60;
            if ((y - u) > 0)
                return y - u + "";
            else
                return "0";
        }
    }

    /**
     * 得到二个日期间的间隔天数
     */
    public String getTwoDay(String sj1, String sj2) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        long day = 0;
        try {
            Date date = myFormatter.parse(sj1);
            Date mydate = myFormatter.parse(sj2);
            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return "";
        }
        return day + "";
    }

    /**
     * 时间前推或后推分钟,其中JJ表示分钟.
     */
    public String getPreTime(String sj1, String jj) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String mydate1 = "";
        try {
            Date date1 = format.parse(sj1);
            long Time = (date1.getTime() / 1000) + Integer.parseInt(jj) * 60;
            date1.setTime(Time * 1000);
            mydate1 = format.format(date1);
        } catch (Exception e) {
        }
        return mydate1;
    }

    /**
     * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
     */
    public String getNextDay(String nowdate, String delay) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String mdate = "";
            Date d = strToDate(nowdate);
            long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24 * 60 * 60;
            d.setTime(myTime * 1000);
            mdate = format.format(d);
            return mdate;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 判断是否润年
     *
     * @param ddate
     * @return
     */
    public boolean isLeapYear(String ddate) {

        /**
         * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
         * 3.能被4整除同时能被100整除则不是闰年
         */
        Date d = strToDate(ddate);
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(d);
        int year = gc.get(Calendar.YEAR);
        if ((year % 400) == 0)
            return true;
        else if ((year % 4) == 0) {
            if ((year % 100) == 0)
                return false;
            else
                return true;
        } else
            return false;
    }


    /**
     * 获取该月天数
     */
    public int getCurrentMonthDay(long millSec) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millSec);
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);
        int dateCount = calendar.get(Calendar.DATE);
        return dateCount;
    }

    /**
     * 获取上一月/下一月 正数为+ 负数为-
     */
    public long getLastOrNextMonth(long millSec, int count) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millSec);
        calendar.add(Calendar.MONTH, count);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取当月第一天
     */
    public long getFirOfMonth(long millSec) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millSec);
        calendar.set(Calendar.DATE, 1);
        return calendar.getTimeInMillis();
    }

    /**
     * 获得星期在月中的index
     * @param week
     * @return
     */
    public int getWeekForCalendarIndex(String week) {

        if (week.equals("星期日"))
            return 0;
        else if (week.equals("星期一"))
            return 1;
        else if (week.equals("星期二"))
            return 2;
        else if (week.equals("星期三"))
            return 3;
        else if (week.equals("星期四"))
            return 4;
        else if (week.equals("星期五"))
            return 5;
        else if (week.equals("星期六"))
            return 6;

        return -1;
    }

    /**
     * 返回美国时间格式 26 Apr 2006
     *
     * @param str
     * @return
     */
    public String getEDate(String str) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(str, pos);
        String j = strtodate.toString();
        String[] k = j.split(" ");
        return k[2] + k[1].toUpperCase() + k[5].substring(2, 4);
    }

    /**
     * 获取一个月的最后一天
     *
     * @param dat
     * @return
     */
    public String getEndDateOfMonth(String dat) {// yyyy-MM-dd
        String str = dat.substring(0, 8);
        String month = dat.substring(5, 7);
        int mon = Integer.parseInt(month);
        if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12) {
            str += "31";
        } else if (mon == 4 || mon == 6 || mon == 9 || mon == 11) {
            str += "30";
        } else {
            if (isLeapYear(dat)) {
                str += "29";
            } else {
                str += "28";
            }
        }
        return str;
    }

    /**
     * 判断二个时间是否在同一个周
     *
     * @param date1
     * @param date2
     * @return
     */
    public boolean isSameWeekDates(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        if (0 == subYear) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        } else if (1 == subYear && 11 == cal2.get(Calendar.MONTH)) {
            // 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        } else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH)) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        }
        return false;
    }

    /**
     * 产生周序列,即得到当前时间所在的年度是第几周
     *
     * @return
     */
    public String getSeqWeek() {
        Calendar c = Calendar.getInstance(Locale.CHINA);
        String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
        if (week.length() == 1)
            week = "0" + week;
        String year = Integer.toString(c.get(Calendar.YEAR));
        return year + week;
    }

    /**
     * 获得一个日期所在的周的星期几的日期，如要找出2002年2月3日所在周的星期一是几号
     *
     * @param sdate
     * @param num
     * @return
     */
    public String getWeek(String sdate, String num) {
        // 再转换为时间
        Date dd = strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(dd);
        if (num.equals("1")) // 返回星期一所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        else if (num.equals("2")) // 返回星期二所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        else if (num.equals("3")) // 返回星期三所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        else if (num.equals("4")) // 返回星期四所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        else if (num.equals("5")) // 返回星期五所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        else if (num.equals("6")) // 返回星期六所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        else if (num.equals("0")) // 返回星期日所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    }

    /**
     * 根据一个日期，返回是星期几的字符串
     *
     * @param sdate
     * @return
     */
    public String getWeek(String sdate) {
        // 再转换为时间
        Date date = strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // int hour=c.get(Calendar.DAY_OF_WEEK);
        // hour中存的就是星期几了，其范围 1~7
        // 1=星期日 7=星期六，其他类推
        return new SimpleDateFormat("EEEE").format(c.getTime());
    }

    public String getWeekStr(String sdate) {
        String str = "";
        str = getWeek(sdate);
        if ("1".equals(str)) {
            str = "星期日";
        } else if ("2".equals(str)) {
            str = "星期一";
        } else if ("3".equals(str)) {
            str = "星期二";
        } else if ("4".equals(str)) {
            str = "星期三";
        } else if ("5".equals(str)) {
            str = "星期四";
        } else if ("6".equals(str)) {
            str = "星期五";
        } else if ("7".equals(str)) {
            str = "星期六";
        }
        return str;
    }

    /**
     * 两个时间之间的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public long getDays(String date1, String date2) {
        if (date1 == null || date1.equals(""))
            return 0;
        if (date2 == null || date2.equals(""))
            return 0;
        // 转换为标准时间
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        Date mydate = null;
        try {
            date = myFormatter.parse(date1);
            mydate = myFormatter.parse(date2);
        } catch (Exception e) {
        }
        long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        return day;
    }

    /**
     * 形成如下的日历 ， 根据传入的一个时间返回一个结构 星期日 星期一 星期二 星期三 星期四 星期五 星期六 下面是当月的各个时间
     * 此函数返回该日历第一行星期日所在的日期
     *
     * @param calendar
     * @return
     */
    public String getNowMonth(String calendar) {
        // 取该时间所在月的一号
        calendar = calendar.substring(0, 8) + "01";

        // 得到这个月的1号是星期几
        Date date = strToDate(calendar);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int u = c.get(Calendar.DAY_OF_WEEK);
        String newday = getNextDay(calendar, (1 - u) + "");
        return newday;
    }

    /**
     * 取得数据库主键 生成格式为yyyymmddhhmmss+k位随机数
     *
     * @param k 表示是取几位随机数，可以自己定
     */

    public String getNo(int k) {

        return getUserDate("yyyyMMddhhmmss") + getRandom(k);
    }

    /**
     * 返回一个随机数
     *
     * @param i
     * @return
     */
    public String getRandom(int i) {
        Random jjj = new Random();
        // int suiJiShu = jjj.nextInt(9);
        if (i == 0)
            return "";
        String jj = "";
        for (int k = 0; k < i; k++) {
            jj = jj + jjj.nextInt(9);
        }
        return jj;
    }

    /***************************************************************************
     * //nd=1表示返回的值中包含年度 //yf=1表示返回的值中包含月份 //rq=1表示返回的值中包含日期 //format表示返回的格式 1
     * 以年月日中文返回 2 以横线-返回 // 3 以斜线/返回 4 以缩写不带其它符号形式返回 // 5 以点号.返回
     **************************************************************************/
//    public String getStringDateMonth(String sdate, String nd, String yf, String rq, String format) {
//        Date currentTime = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        String dateString = formatter.format(currentTime);
//        String s_nd = dateString.substring(0, 4); // 年份
//        String s_yf = dateString.substring(5, 7); // 月份
//        String s_rq = dateString.substring(8, 10); // 日期
//        String sreturn = "";
//        MyChar mc = new MyChar();
//        if (sdate == null || sdate.equals("") || !mc.Isdate(sdate)) { // 处理空值情况
//            if (nd.equals("1")) {
//                sreturn = s_nd;
//                // 处理间隔符
//                if (format.equals("1"))
//                    sreturn = sreturn + "年";
//                else if (format.equals("2"))
//                    sreturn = sreturn + "-";
//                else if (format.equals("3"))
//                    sreturn = sreturn + "/";
//                else if (format.equals("5"))
//                    sreturn = sreturn + ".";
//            }
//            // 处理月份
//            if (yf.equals("1")) {
//                sreturn = sreturn + s_yf;
//                if (format.equals("1"))
//                    sreturn = sreturn + "月";
//                else if (format.equals("2"))
//                    sreturn = sreturn + "-";
//                else if (format.equals("3"))
//                    sreturn = sreturn + "/";
//                else if (format.equals("5"))
//                    sreturn = sreturn + ".";
//            }
//            // 处理日期
//            if (rq.equals("1")) {
//                sreturn = sreturn + s_rq;
//                if (format.equals("1"))
//                    sreturn = sreturn + "日";
//            }
//        } else {
//            // 不是空值，也是一个合法的日期值，则先将其转换为标准的时间格式
//            sdate = roc.util.RocDate.getOKDate(sdate);
//            s_nd = sdate.substring(0, 4); // 年份
//            s_yf = sdate.substring(5, 7); // 月份
//            s_rq = sdate.substring(8, 10); // 日期
//            if (nd.equals("1")) {
//                sreturn = s_nd;
//                // 处理间隔符
//                if (format.equals("1"))
//                    sreturn = sreturn + "年";
//                else if (format.equals("2"))
//                    sreturn = sreturn + "-";
//                else if (format.equals("3"))
//                    sreturn = sreturn + "/";
//                else if (format.equals("5"))
//                    sreturn = sreturn + ".";
//            }
//            // 处理月份
//            if (yf.equals("1")) {
//                sreturn = sreturn + s_yf;
//                if (format.equals("1"))
//                    sreturn = sreturn + "月";
//                else if (format.equals("2"))
//                    sreturn = sreturn + "-";
//                else if (format.equals("3"))
//                    sreturn = sreturn + "/";
//                else if (format.equals("5"))
//                    sreturn = sreturn + ".";
//            }
//            // 处理日期
//            if (rq.equals("1")) {
//                sreturn = sreturn + s_rq;
//                if (format.equals("1"))
//                    sreturn = sreturn + "日";
//            }
//        }
//        return sreturn;
//    }

}
