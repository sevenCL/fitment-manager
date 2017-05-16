package com.seven.manager.model.offer;

import com.seven.library.config.RunTimeConfig;

/**
 * 报价-个性项-total
 *
 * @author seven
 */

public class PersonalityTotal extends OfferPersonalityModel {

    public PersonalityTotal() {
        setViewType(RunTimeConfig.ModelConfig.OFFER_PERSONALITY_TOTAL);
    }

    /**
     * 个性项数量
     */
    private int count;

    /**
     * 总价
     */
    private double total;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "PersonalityTotal{" +
                "count=" + count +
                ", total=" + total +
                '}';
    }
}
