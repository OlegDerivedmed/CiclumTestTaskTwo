package com.derivedmed.testtasktwo;

import com.derivedmed.testtasktwo.inventory.Inventory;
import com.derivedmed.testtasktwo.inventory.items.*;
import com.sun.xml.internal.ws.addressing.ProblemHeaderQName;

import java.util.Optional;

public class Test {
    public static void main(String[] args) {
        Inventory inventory = Inventory.getInstance();
        inventory.inventoryInitialyzer(20);
        inventory.addItem(0,Optional.of(new ManaPot()));
        inventory.addItem(5,Optional.of(new ManaPot()));
//        inventory.addItem(1,new ManaPot());
//        inventory.addItem(2,new ManaPot());
//        inventory.addItem(1,new HealthPot());
//        inventory.addItem(2,new HealthPot());
//        inventory.addItem(2,new HealthPot());
//        inventory.addItem(2,new Weapon());
//        inventory.addItem(5,new Armor());
        System.out.println(inventory.getInventory().stream().filter(cell -> cell.isEmpty()).count());
        System.out.println(inventory.getInventory().get(inventory.getManaPotIndex()).getItemsCount());
    }
}
