package com.derivedmed.testtasktwo;


import com.derivedmed.testtasktwo.inventory.Inventory;
import com.derivedmed.testtasktwo.inventory.cell.Cell;
import com.derivedmed.testtasktwo.inventory.items.Armor;
import com.derivedmed.testtasktwo.inventory.items.HealthPot;
import com.derivedmed.testtasktwo.inventory.items.ManaPot;
import com.derivedmed.testtasktwo.inventory.items.Weapon;

public class Test {
    public static void main(String[] args) {
        Inventory inventory = Inventory.getInstance();
        inventory.inventoryInitialyze(10);
        inventory.addItem(6, new HealthPot(), 20);
        inventory.addItem(5, new ManaPot(), 20);
        inventory.addItem(10, new Armor(), 1);
        inventory.addItem(10, new Weapon(), 1);
        inventory.addItem(4, new Weapon(), 1);
        inventory.addItem(3, new Armor(), 1);
        inventory.addItem(1, new Weapon(), 1);
        inventory.addItem(7, new HealthPot(), 20);
        System.out.println(inventory.getEmptyCellsCount());
        for (Cell cell : inventory.getInventory()) {
            System.out.println(cell.toString());
        }
        inventory.transferItems(5,10);
        System.out.println(inventory.getEmptyCellsCount());
        for (Cell cell : inventory.getInventory()) {
            System.out.println(cell.toString());
        }
        System.out.println();
        inventory.deleteItem(4, 1);
        System.out.println(inventory.getEmptyCellsCount());
        for (Cell cell : inventory.getInventory()) {
            System.out.println(cell.toString());
        }
        System.out.println();
        inventory.deleteItem(4, 21);
        inventory.deleteItem(3, 1);
        System.out.println(inventory.getEmptyCellsCount());
        for (Cell cell : inventory.getInventory()) {
            System.out.println(cell.toString());
        }
        System.out.println();
        inventory.deleteItem(4, 40);
        System.out.println(inventory.getEmptyCellsCount());
        for (Cell cell : inventory.getInventory()) {
            System.out.println(cell.toString());
        }
        System.out.println();

    }
}
