package com.derivedmed.testtasktwo.inventory.cell;

import com.derivedmed.testtasktwo.inventory.items.Item;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Cell {

    private long id;
    @Setter
    private Item item;
    @Setter
    private int itemsCount;
    private boolean isEmpty;
    private boolean isUnblocked;

    public Cell(long id, boolean isUnblocked, boolean isEmpty) {
        this.id = id;
        this.isEmpty = isEmpty;
        this.isUnblocked = isUnblocked;
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

    @Override
    public String toString() {
        return "Cell{" +
                "id=" + id +
                ", item=" + item +
                ", itemsCount=" + itemsCount +
                ", isEmpty=" + isEmpty +
                ", isUnblocked=" + isUnblocked +
                '}';
    }
}
