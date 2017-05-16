package com.seven.manager.model.offer;

import com.seven.library.config.RunTimeConfig;

/**
 * 报价-个性项-item
 *
 * @author seven
 */

public class PersonalityItem extends OfferPersonalityModel {

    public PersonalityItem() {
        setViewType(RunTimeConfig.ModelConfig.OFFER_PERSONALITY_ITEM);
    }

    /**
     * 空间id
     */
    private long spaceId;

    /**
     * 空间
     */
    private String space;

    /**
     * 施工项id
     */
    private long termId;

    /**
     * 施工项
     */
    private String term;

    /**
     * 方量
     */
    private double volume;

    /**
     * 单价
     */
    private double price;

    /**
     * 单位
     */
    private String unit;

    /**
     * 小计
     */
    private double smallTotal;


    public long getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(long spaceId) {
        this.spaceId = spaceId;
    }

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
    }

    public long getTermId() {
        return termId;
    }

    public void setTermId(long termId) {
        this.termId = termId;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getSmallTotal() {
        return smallTotal;
    }

    public void setSmallTotal(double smallTotal) {
        this.smallTotal = smallTotal;
    }

    @Override
    public String toString() {
        return "PersonalityItem{" +
                "spaceId=" + spaceId +
                ", space='" + space + '\'' +
                ", termId=" + termId +
                ", term='" + term + '\'' +
                ", volume=" + volume +
                ", price=" + price +
                ", unit='" + unit + '\'' +
                ", smallTotal=" + smallTotal +
                '}';
    }
}
