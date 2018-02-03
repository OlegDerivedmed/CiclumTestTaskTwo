package com.derivedmed.testtasktwo.inventory.cell;

import com.derivedmed.testtasktwo.inventory.items.Item;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Cell implements Copyable {
    @Setter
    private long id;
    @Setter
    private Item item;
    @Setter
    private int itemsCount;
    private boolean isEmpty;
    private boolean isUnblocked;

    public Cell copy() {
        return new Cell();
    }

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
