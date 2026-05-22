package loot;

import item.Item;

import java.util.ArrayList;
import java.util.Random;

public class LootTable {
    ArrayList <LootItem> items = new ArrayList<>();
    Random rn = new Random();
    public void addLootItem(LootItem loot) {
        items.add(loot);
    }

    public ArrayList<Item> dropLoot() {
        ArrayList<Item> drops = new ArrayList<>();
        for (LootItem item : items) {
            if (rn.nextDouble() <= item.chance) {
                drops.add(item.item);
            }
        }
        return drops;
    }
}
