package com.seven.manager.model.offer;

import com.seven.library.config.RunTimeConfig;

/**
 * 报价单-个性项
 *
 * @author seven
 */

public class QuotationPersonality extends OfferQuotationModel {

    public QuotationPersonality() {

        setViewType(RunTimeConfig.ModelConfig.QUOTATION_PERSONALITY);

    }

    //个性项数量
    private int personalityCount;

    //个性项总价
    private double personalityMoney;

    //个性项集合
    private String addItemsListJson;

    public int getPersonalityCount() {
        return personalityCount;
    }

    public void setPersonalityCount(int personalityCount) {
        this.personalityCount = personalityCount;
    }

    public double getPersonalityMoney() {
        return personalityMoney;
    }

    public void setPersonalityMoney(double personalityMoney) {
        this.personalityMoney = personalityMoney;
    }

    public String getAddItemsListJson() {
        return addItemsListJson;
    }

    public void setAddItemsListJson(String addItemsListJson) {
        this.addItemsListJson = addItemsListJson;
    }

    @Override
    public String toString() {
        return "QuotationPersonality{" +
                "personalityCount=" + personalityCount +
                ", personalityMoney=" + personalityMoney +
                ", addItemsListJson='" + addItemsListJson + '\'' +
                '}';
    }
}
