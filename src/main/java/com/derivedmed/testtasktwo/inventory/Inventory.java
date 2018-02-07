package com.derivedmed.testtasktwo.inventory;

import com.derivedmed.testtasktwo.inventory.cell.Cell;
import com.derivedmed.testtasktwo.inventory.items.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Inventory {
    @Getter
    private List<Cell> inventory;
    @Getter
    private int emptyCellsCount;
    private int inventorySize;
    private int armorCount;
    private int weaponCount;
    private int manaPotCellsCount;
    private int manaPotCellId;
    private int healthPotCellsCount;
    private int healthPotCellId;

    private static Inventory ourInstance = new Inventory();

    public static Inventory getInstance() {
        return ourInstance;
    }

    private Inventory() {
    }

    public void inventoryInitialyze(int size) {
        inventory = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            inventory.add(new Cell(i, true, true));
        }
        inventorySize = size;
        emptyCellsCount = size;
    }

    public void addItem(int cellId, Item item, int count) {
        if (empty()) {
            setAllBlocked();
        }
        if (item instanceof ManaPot) {
            addManaPot(cellId, item, count);
        }
        if (item instanceof HealthPot) {
            addHealthPot(cellId, item, count);
        }
        if (item instanceof Armor) {
            addArmor(cellId, item, count);
        }
        if (item instanceof Weapon) {
            addWeapon(cellId, item, count);
        }
    }

    public void deleteItem(int cellId, int count) {
        if (deletable(cellId)) {
            Cell currentCell = inventory.get(cellId);
            Item itemToBeDeleted = currentCell.getItem();
            if (itemToBeDeleted instanceof Armor) {
                deleteArmor(currentCell, cellId);
            }
            if (itemToBeDeleted instanceof Weapon) {
                deleteWeapon(currentCell, cellId);
            }
            if (itemToBeDeleted instanceof ManaPot) {
                deleteManaPot(currentCell, cellId, count);
            }
            if (itemToBeDeleted instanceof HealthPot){
                deleteHealthPot(currentCell,cellId,count);
            }
        }
        if (empty()){
            setAllUnblocked();
        }
    }

    private void setAllUnblocked() {
        for (Cell cell : inventory){
            cell.setUnblocked();
        }
    }

    private void deleteManaPot(Cell currentCell, int cellId, int count) {
        if (count < inventory.get(cellId).getItemsCount()) {
            inventory.get(cellId).setItemsCount(inventory.get(cellId).getItemsCount() - count);
            return;
        }
        currentCell.setEmpty();
        currentCell.setItem(null);
        manaPotCellsCount = 0;
        emptyCellsCount++;
        currentCell.setItemsCount(0);
        if (isBrake(cellId)){
            fixBrake();
        }
    }
    private void deleteHealthPot(Cell currentCell, int cellId, int count) {
        if (count < inventory.get(cellId).getItemsCount()) {
            inventory.get(cellId).setItemsCount(inventory.get(cellId).getItemsCount() - count);
            return;
        }
        currentCell.setEmpty();
        currentCell.setItem(null);
        healthPotCellsCount = 0;
        emptyCellsCount++;
        currentCell.setItemsCount(0);
        if (isBrake(cellId)){
            fixBrake();
        }
    }

    private void deleteWeapon(Cell currentCell, int cellId) {
        currentCell.setEmpty();
        currentCell.setItem(null);
        currentCell.setItemsCount(0);
        weaponCount = 0;
        emptyCellsCount++;
        if (isBrake(cellId)) {
            fixBrake();
        }
    }

    private void deleteArmor(Cell currentCell, int cellId) {
        currentCell.setEmpty();
        currentCell.setItem(null);
        currentCell.setItemsCount(0);
        armorCount = 0;
        emptyCellsCount++;
        if (isBrake(cellId)) {
            fixBrake();
        }
    }

    private void fixBrake() {
        for (int i = 1; i < inventorySize - 1; i++) {
            Cell previousCell = inventory.get(i - 1);
            Cell nextCell = inventory.get(i + 1);
            if (isBrake(i) && addable(i)) {
                if (previousCell.isEmpty()) {
                    previousCell.setBlocked();

                }
                if (nextCell.isEmpty()) {
                    nextCell.setBlocked();
                }
            }
        }
        autoSort();
    }

    private void autoSort() {
        for (int i = 0; i < inventorySize - 1; i++) {
            Cell currentCell = inventory.get(i);
            Cell nextCell = inventory.get(i + 1);
            if (isBrake(i) && addable(i) && !nextCell.isEmpty()) {
                currentCell.setItem(nextCell.getItem());
                currentCell.setItemsCount(nextCell.getItemsCount());
                currentCell.setFilled();
                nextCell.setEmpty();
                nextCell.setItem(null);
                nextCell.setItemsCount(0);
                fixBrake();
            }
        }
    }

    private boolean isBrake(int cellId) {
        if (cellId == 0 || cellId == inventorySize - 1) {
            return false;
        }
        if (inventory.get(cellId - 1).isUnblocked() && inventory.get(cellId + 1).isUnblocked()) {
            return true;
        }
        return false;
    }

    private boolean deletable(int cellId) {
        return validCellId(cellId) && !inventory.get(cellId).isEmpty();
    }

    private void setAllBlocked() {
        for (Cell cell : inventory) {
            cell.setBlocked();
        }
    }

    private void addArmor(int cellId, Item item, int count) {
        if (armorCount == 0) {
            if (empty() && validCellId(cellId) && count == 1) {
                inventory.get(cellId).setUnblocked();
                add(cellId, item, count);
                armorCount = 1;
                return;
            }
            if (validCellId(cellId) && count == 1 && addable(cellId)) {
                add(cellId, item, count);
                armorCount = 1;
                return;
            }
            if (validCellId(cellId) && count == 1) {
                add(findemptyCell(), item, count);
                armorCount = 1;
            }
        }
    }

    private void addWeapon(int cellId, Item item, int count) {
        if (weaponCount == 0) {
            if (empty() && validCellId(cellId) && count == 1) {
                inventory.get(cellId).setUnblocked();
                add(cellId, item, count);
                weaponCount = 1;
                return;
            }
            if (validCellId(cellId) && count == 1 && addable(cellId)) {
                add(cellId, item, count);
                weaponCount = 1;
                return;
            }
            if (validCellId(cellId) && count == 1) {
                add(findemptyCell(), item, count);
                weaponCount = 1;
            }
        }
    }

    private void addManaPot(int cellId, Item item, int count) {
        if (manaPotCellsCount == 0) {
            addPot(cellId, item, count);
            manaPotCellsCount = 1;
            emptyCellsCount--;
            manaPotCellId = cellId;
            return;
        }
        inventory.get(manaPotCellId).setItemsCount(inventory.get(manaPotCellId).getItemsCount() + count);
    }

    private void addHealthPot(int cellId, Item item, int count) {
        if (healthPotCellsCount == 0) {
            addPot(cellId, item, count);
            healthPotCellsCount = 1;
            emptyCellsCount--;
            healthPotCellId = cellId;
            return;
        }
        inventory.get(healthPotCellId).setItemsCount(inventory.get(healthPotCellId).getItemsCount() + count);
    }

    private void addPot(int cellId, Item item, int count) {
        if (empty()) {
            setCellUnblocked(cellId);
        }
        if (validCellId(cellId) && addable(cellId)) {
            Cell currentCell = inventory.get(cellId);
            currentCell.setItem(item);
            currentCell.setFilled();
            currentCell.setItemsCount(currentCell.getItemsCount() + count);
            setCellsUnblocked(cellId);
            return;
        } else if (validCellId(cellId)) {
            addPot(findemptyCell(), item, count);
        }

    }

    private void setCellUnblocked(int cellId) {
        inventory.get(cellId).setUnblocked();
    }

    private int findemptyCell() {
        int index = 0;
        for (Cell cell : inventory) {
            if (addable((int) cell.getId())) {
                index = (int) cell.getId();
                break;
            }
        }
        return index;
    }

    private void add(int cellId, Item item, int count) {
        Cell currentCell = inventory.get(cellId);
        currentCell.setUnblocked();
        currentCell.setItem(item);
        currentCell.setItemsCount(count);
        currentCell.setFilled();
        setCellsUnblocked(cellId);
        emptyCellsCount--;
    }

    private boolean addable(int cellId) {
        return inventory.get(cellId).isEmpty() && inventory.get(cellId).isUnblocked();
    }

    private void setCellsUnblocked(int cellId) {
        if (cellId == 0) {
            if (!inventory.get(1).isUnblocked())
                inventory.get(1).setUnblocked();
            return;
        }
        if (cellId == inventorySize - 1) {
            if (!inventory.get(inventorySize - 2).isUnblocked())
                inventory.get(inventorySize - 2).setUnblocked();
            return;
        }
        if (!inventory.get(cellId - 1).isUnblocked()) {
            inventory.get(cellId - 1).setUnblocked();
        }
        if (!inventory.get(cellId + 1).isUnblocked()) {
            inventory.get(cellId + 1).setUnblocked();
        }

    }

    private boolean validCellId(int cellId) {
        return cellId >= 0 && cellId < inventorySize;
    }

    private boolean empty() {
        return emptyCellsCount == inventorySize;
    }
}