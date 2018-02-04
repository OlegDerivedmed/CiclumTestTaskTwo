package com.derivedmed.testtasktwo.inventory.items.Creators;

import com.derivedmed.testtasktwo.inventory.items.Item;
import com.derivedmed.testtasktwo.inventory.items.ManaPot;

public class ManaPotCreator extends ItemCreator {
    @Override
    public Item createItem() {
        return new ManaPot();
    }
}
