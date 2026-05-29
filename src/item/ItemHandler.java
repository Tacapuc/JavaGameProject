package item;

import java.util.ArrayList;
import main.MainP;
import entity.Player;

/**
 *
 * @author ilya.bespalov
 */
public class ItemHandler {

    public ArrayList<Item> items = new ArrayList<>();
    MainP gp;
    Player p;

    public ItemHandler(MainP gp) {
        this.gp = gp;

    }

    public void sortItems(ArrayList<Item> items) {
        for (int i = 0; i < items.size() - 1; i++) {
            int smallestIndex = i;
            for (int j = i + 1; j < items.size(); j++) {
                if (items.get(j).itemSlot < items.get(smallestIndex).itemSlot) {
                    smallestIndex = j;

                }

            }
            Item temp = items.get(smallestIndex);
            items.set(smallestIndex, items.get(i));
            items.set(i, temp);
        }

    }

    public void allItems() {
        for (int i = 0; i < gp.p1.pItems.size(); i++) {
            gp.p1.pItems.removeFirst();

        }
        for (int i = 0; i < items.size(); i++) {
            Item itemToAdd = getItem(i);
            gp.p1.pItems.add(itemToAdd);
        }

    }

    public void destroy() {
        Item itemDestroyed = gp.mouseH.targetedItem;
        gp.p1.pItems.remove(itemDestroyed);
    }

    public void createItems() {

        loadWeapon(0, 5, "Frostmourne", "/resources/icon/Weapons/92.PNG", "Weapon", 17, new Stats(10, 5, 2, 2));
        loadWeapon(1, 3, "Staff of the Azure", "/resources/icon/Weapons/93.PNG", "Weapon", 17, new Stats(5, 10, 10, 0));
        loadArmor(2, 2, "Cloudsong Gloves", "/resources/icon/Armour/86_2.PNG", "Armour", 9, new Stats(0, 0, 5, 10));
        loadArmor(3, 2, "Crown of the Berserker", "/resources/icon/Armour/158.PNG", "Armour", 1, new Stats(0, 0, 10, 5));
        loadArmor(4, 3, "Raging Spaulders", "/resources/icon/Armour/129_1.PNG", "Armour", 3, new Stats(0, 0, 7, 10));
        loadArmor(5, 3, "Conqueror's Legguards", "/resources/icon/Armour/Plate_32.PNG", "Armour", 11, new Stats(10, 0, 3, 3));
        loadArmor(6, 4, "Martin's Fury", "/resources/icon/Armour/Plate_25.PNG", "Armour", 5, new Stats(0, 0, 10, 10));
        loadArmor(7, 4, "Apocalypse's Stride", "/resources/icon/Armour/Plate_12.PNG", "Armour", 12, new Stats(0, 0, 10, 5));
        loadWeapon(8, 5, "Forgotten Relic", "/resources/icon/Weapons/100.PNG", "Instrument", 18, new Stats(10, 10, 0, 0));
        loadWeapon(9, 5, "Glaive of the Archon", "/resources/icon/Weapons/101.PNG", "Instrument", 19, new Stats(10, 10, 0, 0));
        loadWeapon(10, 5, "Worldbreaker's Axe", "/resources/icon/Weapons/102.PNG", "Instrument", 0, new Stats(10, 10, 0, 0));
        loadWeapon(11, 3, "Falyn'Raj", "/resources/icon/Weapons/103.PNG", "Instrument", 0, new Stats(5, 5, 0, 0));

    }

    public void loadArmor(int id, int quality, String name, String filepath, String type, int slot, Stats stats) {
        Armor armor = new Armor(id, quality, name, filepath, type, slot, stats);
        items.add(armor);
    }

    public void loadWeapon(int id, int quality, String name, String filepath, String type, int slot, Stats stats) {
        Weapon weapon = new Weapon(id, quality, name, filepath, type, slot, stats);
        items.add(weapon);
    }

    public void unequipItem(Item i) {
        for (int j = 0; j < gp.p1.pItemsEquipped.size(); j++) {
            if (gp.p1.pItemsEquipped.get(j).itemSlot == i.itemSlot) {
                gp.p1.pItems.add(gp.p1.pItemsEquipped.get(j));
                gp.p1.pItemsEquipped.remove(gp.p1.pItemsEquipped.get(j));

            }
        }
    }

    public void equipItem(Item i) {
        if (i.itemSlot > 0 && i.itemSlot < 20) {
            unequipItem(i);
            gp.p1.pItemsEquipped.add(i);

            gp.p1.pItems.remove(i);
            sortItems(gp.p1.pItemsEquipped);
            gp.p1.stats = new Stats(0, 0, 0, 0);

            for (int j = 0; j < gp.p1.pItemsEquipped.size(); j++) {
               
               gp.p1.stats.addStats(gp.p1.pItemsEquipped.get(j).stats);
            }
            System.out.println(gp.p1.stats);
            gp.p1.updateHealth();
        }

    }

    public void addItem(int id) {
        Item itemToAdd = getItem(id);
        if (itemToAdd != null) {
            gp.p1.pItems.add(itemToAdd);
        } else {
            System.out.println("Item with ID " + id + " not found!");
        }
    }

    public Item getItem(int id) {
        for (Item item : items) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public ArrayList<Item> getItems() {
        return items;
    }
}
