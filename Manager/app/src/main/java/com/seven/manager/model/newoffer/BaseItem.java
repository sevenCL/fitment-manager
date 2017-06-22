package com.seven.manager.model.newoffer;

import com.seven.library.config.RunTimeConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @author seven
 */

public class BaseItem extends OfferBaseModel {

    public BaseItem() {

        setViewTpye(RunTimeConfig.ModelConfig.BASE_ITEM);

        list = new ArrayList<>();
    }

    /**
     * 阶段id
     */
    private long baseItemPid;

    /**
     * 阶段名称
     */
    private String baseItemPidName;

    /**
     * 类id
     */
    private long baseItemId;

    /**
     * 类名称
     */
    private String baseItemName;

    /**
     * 施工项id
     */
    private long id;

    /**
     * 施工项编号
     */
    private String number;

    /**
     * 类id
     */
    private long categoryId;

    /**
     * 施工名称
     */
    private String name;

    /**
     * 单位id
     */
    private String unitId;

    /**
     * 单位名称
     */
    private String unitName;

    /**
     * 施工项描述
     */
    private String description;

    /**
     * 工种
     */
    private int workType;

    /**
     * 状态
     */
    private int status;

    /**
     * 是否是默认项
     */
    private boolean defaultItem;

    /**
     * 默认项目 系数
     */
    private String formula;

    /**
     * 是否是全屋项目
     */
    private boolean overallItem;

    /**
     * 单价
     */
    private double offer;

    //------------------------------------自定义字段

    /**
     * 方量
     */
    private double volume;

    /**
     * 小计
     */
    private double total;

    //--------------------------------------------------------空间

    private List<SpaceItemList> list;

    public List<SpaceItemList> getList() {
        return list;
    }

    public void addList(SpaceItemList item) {
        this.list.add(item);
    }

    public void clearList() {
        this.list.clear();
    }

    //-------------------------------------------------自定义

    /**
     * 是否展开
     */
    private boolean isShow;

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public long getBaseItemPid() {
        return baseItemPid;
    }

    public void setBaseItemPid(long baseItemPid) {
        this.baseItemPid = baseItemPid;
    }

    public String getBaseItemPidName() {
        return baseItemPidName;
    }

    public void setBaseItemPidName(String baseItemPidName) {
        this.baseItemPidName = baseItemPidName;
    }

    public long getBaseItemId() {
        return baseItemId;
    }

    public void setBaseItemId(long baseItemId) {
        this.baseItemId = baseItemId;
    }

    public String getBaseItemName() {
        return baseItemName;
    }

    public void setBaseItemName(String baseItemName) {
        this.baseItemName = baseItemName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getWorkType() {
        return workType;
    }

    public void setWorkType(int workType) {
        this.workType = workType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isDefaultItem() {
        return defaultItem;
    }

    public void setDefaultItem(boolean defaultItem) {
        this.defaultItem = defaultItem;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public boolean isOverallItem() {
        return overallItem;
    }

    public void setOverallItem(boolean overallItem) {
        this.overallItem = overallItem;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getOffer() {
        return offer;
    }

    public void setOffer(double offer) {
        this.offer = offer;
    }

    @Override
    public String toString() {
        return "BaseItem{" +
                "baseItemPid=" + baseItemPid +
                ", baseItemPidName='" + baseItemPidName + '\'' +
                ", baseItemId=" + baseItemId +
                ", baseItemName='" + baseItemName + '\'' +
                ", id=" + id +
                ", number='" + number + '\'' +
                ", categoryId=" + categoryId +
                ", name='" + name + '\'' +
                ", unitId='" + unitId + '\'' +
                ", unitName='" + unitName + '\'' +
                ", description='" + description + '\'' +
                ", workType=" + workType +
                ", status=" + status +
                ", defaultItem=" + defaultItem +
                ", formula=" + formula +
                ", overallItem=" + overallItem +
                ", offer='" + offer + '\'' +
                ", volume=" + volume +
                ", total=" + total +
                '}';
    }
}
