package com.derivedmed.testtasktwo.inventory.cell;

import com.derivedmed.testtasktwo.inventory.items.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
public class Cell {
    @Setter
    private long id;
    @Setter
    private Optional<Item> item;
    @Setter
    private int itemsCount;
    private boolean isEmpty;
    private boolean isUnblocked;

    public void setEmpty() {
        isEmpty = true;
    }

    public void setFilled() {
        isEmpty = false;
    }

    public void setUnblocked() {
        isUnblocked = true;
    }

    public void setBlocked() {
        isUnblocked = false;
    }
}
