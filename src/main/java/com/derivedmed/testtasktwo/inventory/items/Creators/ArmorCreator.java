package com.derivedmed.testtasktwo.inventory.items.Creators;

import com.derivedmed.testtasktwo.inventory.items.Armor;
import com.derivedmed.testtasktwo.inventory.items.Item;

public class ArmorCreator extends ItemCreator {
    @Override
    public Item createItem() {
        return new Armor();
    }
}
