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
        for (int i = 0; i < size; i++) {
            Cell currentCell = new Cell();
            currentCell.setId(i);
            currentCell.setEmpty();
            currentCell.setUnblocked();
            inventory.add(currentCell);
        }
    }

    public void addItem(int index, Optional<Item> item) {
        if (filledCellsCount != inventory.size()) {
            Cell currentCell = inventory.get(index);
            //check if inventory is empty
            if (inventory.stream().filter((c) -> c.isEmpty()).count() == inventory.size()) {
                //make all cells blocked
                for (Cell cell :
                        inventory) {
                    cell.setBlocked();
                }
                //adding item
                currentCell.setUnblocked();
                currentCell.setItem(item);
                currentCell.setFilled();
                setCellsUnblocked(index);
                //if item is Mana potion we need to remember in which cell we putting it to add all potions in one cell in future
                if (item.get() instanceof ManaPot) {
                    hasManaPot = true;
                    manaPotIndex = index;
                    currentCell.setItemsCount(currentCell.getItemsCount() + 1);
                    filledCellsCount++;
                    return;
                }
                //same with mana potion
                if (item.get() instanceof HealthPot) {
                    hasHealthPot = true;
                    healthPotIndex = index;
                    currentCell.setItemsCount(currentCell.getItemsCount() + 1);
                    filledCellsCount++;
                    return;
                }
                filledCellsCount++;
                return;
            }
            /*  If inventory not empty:
                If item is health potion we need to chek if health potions already in inventory.
                If it true we just incrementing item count in health potion cell.
                If inventory has no health potion we just adding it.
                We checking every cell if it addable, if it not we finding the addable cell and putting item in it.
                Same with mana potion.

             */
            if (item.get() instanceof HealthPot) {
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
            if (item.get() instanceof ManaPot) {
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
            /*
                If item is not mana or health potion we just need to chek if cell is addable and add item. If it not we finding addable cell.
             */
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

    /*
        First we need to check that cells where we want to swap items is not empty.
        If it true we just swapping items.
     */
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

    /*
        We can delete only if inventory is not empty and if cell is not empty.
        If we must delete mana or health potion first we need to check if it`s count > 1. If it true
        we just decrementing itemCount in current cell. if it < 1 we deleting item from cell (setting optional empty).
        Same with not potion items.
     */
    public void deleteItem(int index) {
        if (filledCellsCount >= 0) {
            Cell currentCell = inventory.get(index);
            if (!currentCell.isEmpty()) {
                if (currentCell.getItem().get() instanceof ManaPot && currentCell.getItemsCount() > 1) {
                    currentCell.setItemsCount(currentCell.getItemsCount() - 1);
                    filledCellsCount--;
                    if (isBreak(index)) {
                        transferItem(index + 1, index);
                    }
                    return;
                }
                if (currentCell.getItem().get() instanceof HealthPot && currentCell.getItemsCount() > 1) {
                    currentCell.setItemsCount(currentCell.getItemsCount() - 1);
                    filledCellsCount--;
                    if (isBreak(index)) {
                        transferItem(index + 1, index);
                    }
                    return;
                }
                currentCell.setEmpty();
                currentCell.setItem(Optional.empty());
                filledCellsCount--;
                if (isBreak(index)) {
                    transferItem(index + 1, index);
                }
            }
        }
    }
    /*
        We can transfer item only in addable cell. If it will form break items will be sorted automatically.
     */
    public void transferItem(int index, int destinitionCell) {
        if (index >= 0 && index <= inventory.size() - 1 && destinitionCell >= 0 && destinitionCell <= inventory.size() - 1) {
            Cell current = inventory.get(index);
            Cell target = inventory.get(destinitionCell);
            if (!current.isEmpty() && addable(destinitionCell)) {
                target.setItem(current.getItem());
                target.setFilled();
                current.setEmpty();
                current.setItem(Optional.empty());
            }
            if (isBreak(index)) {
                transferItem(index + 1, index);
            }
        }
    }

    private int findUnblockedCell() {
        long index = 0;
        for (Cell cell :
                inventory) {
            if (cell.isEmpty() && cell.isUnblocked()) {
                index = cell.getId();
                return (int) index;
            }
        }
        return (int) index;
    }

    private boolean isBreak(int index) {
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
}
