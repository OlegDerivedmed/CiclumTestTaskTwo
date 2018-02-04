package com.derivedmed.testtasktwo.inventory;

import com.derivedmed.testtasktwo.inventory.cell.Cell;
import com.derivedmed.testtasktwo.inventory.items.HealthPot;
import com.derivedmed.testtasktwo.inventory.items.Item;
import com.derivedmed.testtasktwo.inventory.items.ManaPot;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Optional;

public class Inventory {
    @Getter
    private ArrayList<Cell> inventory;
    private int filledCellsCount;
    private boolean hasManaPot;
    @Getter
    private int manaPotIndex;
    private boolean hasHealthPot;
    @Getter
    private int healthPotIndex;

    private static Inventory ourInstance = new Inventory();

    public static Inventory getInstance() {
        return ourInstance;
    }

    private Inventory() {
    }

    public void inventoryInitialyzer(int size) {
        inventory = new ArrayList<Cell>(size);
        Cell cell = new Cell();
        for (int i = 0; i < size; i++) {
            Cell currentCell = cell.copy();
            currentCell.setId(i);
            currentCell.setEmpty();
            currentCell.setUnblocked();
            inventory.add(currentCell);
        }
    }

    public void addItem(int index, Optional<Item> item) {
        if (filledCellsCount != inventory.size()) {
            Cell currentCell = inventory.get(index);
            if (inventory.stream().filter((c) -> c.isEmpty()).count() == inventory.size()) {
                for (Cell cell :
                        inventory) {
                    cell.setBlocked();
                }
                currentCell.setUnblocked();
                currentCell.setItem(item);
                currentCell.setFilled();
                setCellsUnblocked(index);
                if (isManaPot(item)) {
                    hasManaPot = true;
                    manaPotIndex = index;
                    currentCell.setItemsCount(currentCell.getItemsCount() + 1);
                    filledCellsCount++;
                    return;
                }
                if (isHealthPot(item)) {
                    hasHealthPot = true;
                    healthPotIndex = index;
                    currentCell.setItemsCount(currentCell.getItemsCount() + 1);
                    filledCellsCount++;
                    return;
                }
                filledCellsCount++;
                return;
            }
            if (isHealthPot(item)) {
                if (!hasHealthPot && !addable(index)) {
                    filledCellsCount++;
                }
                if (!hasHealthPot && addable(index)) {
                    currentCell.setItem(item);
                    currentCell.setFilled();
                    hasHealthPot = true;
                    healthPotIndex = index;
                    setCellsUnblocked(index);
                    currentCell.setItemsCount(currentCell.getItemsCount() + 1);
                } else if (!hasHealthPot && !addable(index)) {
                    addItem(findUnblockedCell(), item);
                } else {
                    inventory.get(healthPotIndex).setItemsCount(inventory.get(healthPotIndex).getItemsCount() + 1);
                }
                return;
            }
            if (isManaPot(item)) {
                if (!hasManaPot && addable(index)) {
                    filledCellsCount++;
                }
                if (!hasManaPot && addable(index)) {
                    currentCell.setItem(item);
                    currentCell.setFilled();
                    hasManaPot = true;
                    manaPotIndex = index;
                    setCellsUnblocked(index);
                    currentCell.setItemsCount(currentCell.getItemsCount() + 1);
                } else if (!hasManaPot && !addable(index)) {
                    addItem(findUnblockedCell(), item);
                } else {
                    inventory.get(manaPotIndex).setItemsCount(inventory.get(manaPotIndex).getItemsCount() + 1);
                }
                return;
            }

            if (addable(index)) {
                currentCell.setItem(item);
                currentCell.setFilled();
                setCellsUnblocked(index);
                filledCellsCount++;
            } else {
                addItem(findUnblockedCell(), item);
            }
        }
    }

    public void swapItems(int index1, int index2) {
        if (index1 <= inventory.size() - 1 && index2 <= inventory.size() - 1) {
            Cell cellOne = inventory.get(index1);
            Cell cellTwo = inventory.get(index2);
            if (!(cellOne.isEmpty() && cellTwo.isEmpty())) {
                Optional<Item> item;
                item = cellOne.getItem();
                cellOne.setItem(cellTwo.getItem());
                cellTwo.setItem(item);
            }
        }
    }

    public void deleteItem(int index) {
        if (filledCellsCount >= 0) {
            Cell currentCell = inventory.get(index);
            if (!currentCell.isEmpty()) {
                if (isManaPot(currentCell.getItem())) {
                    currentCell.setItemsCount(currentCell.getItemsCount() - 1);
                    if (currentCell.getItemsCount() > 0) {
                        filledCellsCount--;
                        return;
                    }
                }
                if (isHealthPot(currentCell.getItem())) {
                    currentCell.setItemsCount(currentCell.getItemsCount() - 1);
                    if (currentCell.getItemsCount() > 0) {
                        filledCellsCount--;
                        return;
                    }
                }
                currentCell.setEmpty();
                currentCell.setItem(Optional.empty());
                filledCellsCount--;
            }
            if (isBrake(index)) {
                transferItem(index + 1, index);
            }
        }
    }

    public void transferItem(int index, int destinitionCell) {
        Cell current = inventory.get(index);
        Cell target = inventory.get(destinitionCell);
        if ( !current.isEmpty()  && addable(destinitionCell)){
            target.setItem(current.getItem());
            target.setFilled();
            current.setEmpty();
            current.setItem(Optional.empty());
        }
        if (isBrake(index)) {
            transferItem(index + 1, index);
        }
    }

    private int findUnblockedCell() {
        long index = 0;
        for (Cell cell :
                inventory) {
            if (cell.isEmpty() && cell.isUnblocked()) {
                index = cell.getId();
            }

        }
        return (int) index;
    }

    private boolean isBrake(int index) {
        if (index != 0 && index != inventory.size() - 1) {
            if (inventory.get(index).isEmpty() && !inventory.get(index - 1).isEmpty() && !inventory.get(index + 1).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private boolean addable(int index) {
        Cell currentCell = inventory.get(index);
        return currentCell.isUnblocked() && currentCell.isEmpty();
    }

    private void setCellsUnblocked(int index) {
        if (index == 0) {
            inventory.get(index + 1).setUnblocked();
        } else if (index == inventory.size() - 1) {
            inventory.get(index - 1).setUnblocked();
        } else {
            inventory.get(index - 1).setUnblocked();
            inventory.get(index + 1).setUnblocked();
        }
    }

    private boolean isManaPot(Optional<Item> item) {
        return item.get().getClass().getSimpleName().equals(ManaPot.class.getSimpleName());
    }

    private boolean isHealthPot(Optional<Item> item) {
        return item.get().getClass().getSimpleName().equals(HealthPot.class.getSimpleName());
    }

}
