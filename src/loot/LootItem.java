package loot;
import item.*;

public class LootItem {
    public Item item;
    public double chance;

    public LootItem(Item item, double chance) {
        this.item = item;
        this.chance = chance;
    }
}
