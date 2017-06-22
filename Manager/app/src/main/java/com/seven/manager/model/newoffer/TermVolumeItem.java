package com.seven.manager.model.newoffer;

import com.seven.library.config.RunTimeConfig;

/**
 * @author seven
 */

public class TermVolumeItem extends TermVolumeModel {

    public TermVolumeItem() {

        setViewType(RunTimeConfig.ModelConfig.TERM_VOLUME_ITEM);

    }

    private String name;

    private long id;

    private long spaceId;

    private int type;

    private double area;

    private double volume;

    private boolean isShowName;

    private int tag;

    private boolean isDefaultItem;

    public boolean isDefaultItem() {
        return isDefaultItem;
    }

    public void setDefaultItem(boolean defaultItem) {
        isDefaultItem = defaultItem;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public boolean isShowName() {
        return isShowName;
    }

    public void setShowName(boolean showName) {
        isShowName = showName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(long spaceId) {
        this.spaceId = spaceId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }
}
