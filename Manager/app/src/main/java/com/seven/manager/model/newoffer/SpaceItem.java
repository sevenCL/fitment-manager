package com.seven.manager.model.newoffer;

import com.seven.library.config.RunTimeConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @author seven
 */

public class SpaceItem extends OfferSpaceModel {

    public SpaceItem() {
        setViewType(RunTimeConfig.ModelConfig.SPACE_ITEM);

        list = new ArrayList<>();
    }

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
}
