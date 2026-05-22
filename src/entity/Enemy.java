/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;
import main.*;
import loot.LootItem;
import loot.LootTable;

/**
 *
 * @author ilya.bespalov
 */
public class Enemy extends Entity {


    public Enemy(String name, int health, int resource, String model, MainP gp) {
        super(name, health, resource, model, gp);
        lootTable = new LootTable();


    }
    
}
