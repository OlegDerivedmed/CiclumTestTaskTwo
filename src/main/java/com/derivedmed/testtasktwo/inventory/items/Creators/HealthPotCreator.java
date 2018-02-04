package com.derivedmed.testtasktwo.inventory.items.Creators;

import com.derivedmed.testtasktwo.inventory.items.HealthPot;
import com.derivedmed.testtasktwo.inventory.items.Item;

public class HealthPotCreator extends ItemCreator {
    @Override
    public Item createItem() {
        return new HealthPot();
    }
}
