package item;

import java.util.ArrayList;
import main.MainP;
import entity.Player;

public class ItemHandler {

    ArrayList<Item> items = new ArrayList<>();
    MainP gp;
    Player p;

    public ItemHandler(MainP gp, Player p) {
        this.gp = gp;
        this.p = p;
    }
    
    public void sortItems(ArrayList<Item> items) {
        for (int i = 0; i < items.size()-1; i++) {
            int smallestIndex = i;
            for (int j = i+1; j < items.size(); j++) {
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
        for (int i = 0; i < p.pItems.size(); i++) {
            p.pItems.removeFirst();

        }
        for (int i = 0; i < items.size(); i++) {
            Item itemToAdd = getItem(i);
            p.pItems.add(itemToAdd);
        }

    }

    public void destroy() {
        Item itemDestroyed = gp.mouseH.targetedItem;
        p.pItems.remove(itemDestroyed);
    }

    public void createItems() {

        loadItem(0, 1, "SwordPH", "/resources/icon/Weapons/92.PNG", "Weapon",  17);
        loadItem(1, 1, "ThingPH", "/resources/icon/Weapons/93.PNG", "Weapon",  17);
        loadItem(2, 2, "ArmourPH", "/resources/icon/Armour/86_2.PNG", "Armour",  9);
        loadItem(3, 2, "ArmourPH", "/resources/icon/Armour/158.PNG", "Armour",  1);
        loadItem(4, 3, "ArmourPH", "/resources/icon/Armour/129_1.PNG", "Armour",  3);
        loadItem(5, 3, "ArmourPH", "/resources/icon/Armour/Plate_32.PNG", "Armour",  11);
        loadItem(6, 4, "ArmourPH", "/resources/icon/Armour/Plate_25.PNG", "Armour",  5);
        loadItem(7, 4, "ArmourPH", "/resources/icon/Armour/Plate_12.PNG", "Armour",  12);
        loadItem(8, 5, "Iron Pickaxe", "/resources/icon/Weapons/100.PNG", "Instrument",  18);
        loadItem(9, 5, "Iron Shovel", "/resources/icon/Weapons/101.PNG", "Instrument",  19);
        loadItem(10, 5, "Stone Shovel", "/resources/icon/Weapons/102.PNG", "Instrument",  0);
        loadItem(11, 3, "Wood Shovel", "/resources/icon/Weapons/103.PNG", "Instrument",  0);

    }

    private void loadItem(int id, int quality, String name, String filePath, String type, int slot) {
        Item item = new ItemEquipment(id, quality, name, filePath, type, slot);
        items.add(item);
    }
    
    public void unequipItem(Item i) {
        for (int j = 0; j < p.pItemsEquipped.size(); j++) {
                if (p.pItemsEquipped.get(j).itemSlot == i.itemSlot) {
                    p.pItems.add(p.pItemsEquipped.get(j));
                    p.pItemsEquipped.remove(p.pItemsEquipped.get(j));
                    

                }
            }
    }

    public void equipItem(Item i) {
        if (i.itemSlot > 0 && i.itemSlot < 20) {
            unequipItem(i);
            p.pItemsEquipped.add(i);

            p.pItems.remove(i);
            sortItems(p.pItemsEquipped);

            for (int j = 0; j < p.pItemsEquipped.size(); j++) {
                System.out.println(p.pItemsEquipped.get(j));
            }
        }

    }

    public void addItem(int id) {
        Item itemToAdd = getItem(id);
        if (itemToAdd != null) {
            p.pItems.add(itemToAdd);
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
