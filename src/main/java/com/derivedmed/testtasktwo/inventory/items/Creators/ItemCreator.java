package com.derivedmed.testtasktwo.inventory.items.Creators;

import com.derivedmed.testtasktwo.inventory.items.Item;

public abstract class ItemCreator {
    Item item = createItem();
    public abstract Item createItem();
}
