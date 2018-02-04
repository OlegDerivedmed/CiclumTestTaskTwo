package com.derivedmed.testtasktwo.inventory.items.Creators;

import com.derivedmed.testtasktwo.inventory.items.Item;
import com.derivedmed.testtasktwo.inventory.items.Weapon;

public class WeapotCreator extends ItemCreator {
    @Override
    public Item createItem() {
        return new Weapon();
    }
}
