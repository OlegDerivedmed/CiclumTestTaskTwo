package com.derivedmed.testtasktwo.inventory;

import com.derivedmed.testtasktwo.inventory.items.Armor;
import com.derivedmed.testtasktwo.inventory.items.HealthPot;
import com.derivedmed.testtasktwo.inventory.items.ManaPot;
import com.derivedmed.testtasktwo.inventory.items.Weapon;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class InventoryTest {

    private Inventory inventory = Inventory.getInstance();

    private final int inventorySize = 10;

    @Test
    public void inventoryInitialyzer() throws Exception {
        initialyze();
        long expectedEmpty = inventory.getInventory().stream().filter(cell -> cell.isEmpty()).count();
        int expectedSize = inventory.getInventory().size();
        assertTrue(expectedSize == inventorySize);
        assertTrue(expectedEmpty == inventory.getInventory().size());
    }

    @Test
    public void addItem() throws Exception {
        initialyze();
        inventory.addItem(0, Optional.of(new Weapon()));
        inventory.addItem(8, Optional.of(new HealthPot()));
        inventory.addItem(6, Optional.of(new HealthPot()));
        inventory.addItem(4, Optional.of(new HealthPot()));
        inventory.addItem(7, Optional.of(new ManaPot()));
        inventory.addItem(7, Optional.of(new ManaPot()));
        inventory.addItem(6,Optional.of(new Armor()));
        inventory.addItem(6,Optional.of(new Armor()));
        inventory.addItem(6,Optional.of(new Armor()));
        inventory.addItem(6,Optional.of(new Armor()));
        inventory.addItem(6,Optional.of(new Armor()));
        inventory.addItem(6,Optional.of(new Armor()));
        inventory.addItem(6,Optional.of(new Armor()));
        inventory.addItem(6,Optional.of(new Armor()));
        inventory.addItem(6,Optional.of(new Armor()));
        long expectedEmpty = inventory.getInventory().stream().filter(cell -> cell.isEmpty()).count();
        System.out.println(expectedEmpty);
        assertTrue(expectedEmpty == 0);
    }

    @Test
    public void swapItems() throws Exception {
        initialyze();
        inventory.addItem(5, Optional.of(new Armor()));
        inventory.addItem(5, Optional.of(new Armor()));
        inventory.swapItems(5,4);
        inventory.swapItems(5,6);
        inventory.swapItems(5,10);
        long expectedEmpty = inventory.getInventory().stream().filter(cell -> cell.isEmpty()).count();
        assertTrue(expectedEmpty == 8);
    }

    @Test
    public void deleteItem() throws Exception {
        initialyze();
        inventory.addItem(3, Optional.of(new Weapon()));
        inventory.addItem(8, Optional.of(new HealthPot()));
        inventory.addItem(6, Optional.of(new HealthPot()));
        inventory.addItem(3, Optional.of(new HealthPot()));
        inventory.addItem(7, Optional.of(new ManaPot()));
        inventory.addItem(7, Optional.of(new ManaPot()));
        inventory.addItem(6, Optional.of(new ManaPot()));
        inventory.deleteItem(5);
        inventory.deleteItem(5);
        inventory.deleteItem(8);
        inventory.deleteItem(3);
        long expectedEmpty = inventory.getInventory().stream().filter(cell -> cell.isEmpty()).count();
        System.out.println(expectedEmpty);
        assertTrue(expectedEmpty == 8);
    }

    @Test
    public void transferItem() throws Exception {
        initialyze();
        inventory.addItem(2,Optional.of(new HealthPot()));
        inventory.addItem(3, Optional.of(new ManaPot()));
        inventory.addItem(7,Optional.of(new Armor()));
        inventory.transferItem(5,1);
        inventory.transferItem(1,5);
        inventory.transferItem(2,5);
        inventory.transferItem(4,1);
        inventory.transferItem(11,12);
        long expectedEmpty = inventory.getInventory().stream().filter(cell -> cell.isEmpty()).count();
        assertTrue(expectedEmpty == 7);
    }

    private void initialyze() {
        inventory.inventoryInitialyzer(inventorySize);
    }
}