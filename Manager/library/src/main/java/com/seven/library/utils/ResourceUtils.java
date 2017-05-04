package com.seven.library.utils;

import com.seven.library.R;
import com.seven.library.application.LibApplication;
import com.seven.library.config.RunTimeConfig;

import java.text.DecimalFormat;

/**
 * 字符串资源辅助类
 *
 * @author seven
 */
public class ResourceUtils {

    private static volatile ResourceUtils resourceUtil;

    private ResourceUtils() {
    }


    public static ResourceUtils getInstance() {

        if (resourceUtil == null) {

            synchronized (ResourceUtils.class) {
                resourceUtil = new ResourceUtils();
            }
        }

        return resourceUtil;

    }

    /**
     * 获得string
     *
     * @param id
     * @return
     */
    public String getText(int id) {

        return LibApplication.getInstance().getResources().getString(id);

    }

    /**
     * 获得带值的string
     *
     * @param id
     * @param args
     * @return
     */
    public String getFormatText(int id, Object... args) {
        return String.format(LibApplication.getInstance().getResources().getString(id), args);
    }

    /**
     * 格式化金钱
     *
     * @param money
     * @return
     */
    public String getMoneyFormat(double money) {

        if (money == 0)
            return "";

        if (money > 9999) {
            return new DecimalFormat("#0.00").format(money / 10000) + "万";

        } else {
            return new DecimalFormat("#0.00").format(money);
        }
    }

    /**
     * 获得周几
     *
     * @param index
     * @return
     */
    public String getWeek(int index) {

        switch (index) {

            case 0:
                return getText(R.string.weekday);
            case 1:
                return getText(R.string.monday);
            case 2:
                return getText(R.string.tuesday);
            case 3:
                return getText(R.string.wednesday);
            case 4:
                return getText(R.string.thursday);
            case 5:
                return getText(R.string.friday);
            case 6:
                return getText(R.string.saturday);

        }

        return "";
    }

    /**
     * 获取性别
     *
     * @param sex 1男 2女
     * @return
     */
    public String getSex(int sex) {

        switch (sex) {
            case 1:
                return getText(R.string.sex_man);
            case 2:
                return getText(R.string.sex_woman);
            default:
                return "";
        }
    }

    //----------------------------------------------------------------------------------------------

    /**
     * 获取资料状态
     *
     * @return
     */
    public String dataStatus(int status) {

        switch (status) {
            case RunTimeConfig.StatusConfig.UNAUTHERIZED:
                return getText(R.string.data_status_1);
            case RunTimeConfig.StatusConfig.AUTHERIZED_NOPASS:
                return getText(R.string.data_status_2);
            case RunTimeConfig.StatusConfig.AUTHERIZED_PASS:
                return getText(R.string.data_status_3);
            case RunTimeConfig.StatusConfig.AUTHERIZED:
                return getText(R.string.data_status_5);
            default:
                return "";
        }

    }

    /**
     * 机构关联状态
     *
     * @return
     */
    public String companyStatus(int status) {

        switch (status) {

            case RunTimeConfig.StatusConfig.NOT_RELEVANCE:
                return getText(R.string.company_status_0);
            case RunTimeConfig.StatusConfig.RELEVANCE:
                return "";
            default:
                return "";

        }
    }

    /**
     * 预约单的状态
     *
     * @param status
     * @return
     */
    public String orderStatus(int status) {

        switch (status) {

            case RunTimeConfig.StatusConfig.ORDER_STATUS_1:
                return getText(R.string.order_status_1);
            case RunTimeConfig.StatusConfig.ORDER_STATUS_2:
                return getText(R.string.order_status_2);
            case RunTimeConfig.StatusConfig.ORDER_STATUS_3:
                return getText(R.string.order_status_3);
            case RunTimeConfig.StatusConfig.ORDER_STATUS_4:
                return getText(R.string.order_status_4);
            case RunTimeConfig.StatusConfig.ORDER_STATUS_5:
                return getText(R.string.order_status_5);
            case RunTimeConfig.StatusConfig.ORDER_STATUS_6:
                return getText(R.string.order_status_6);
            default:
                return "";
        }

    }
}
