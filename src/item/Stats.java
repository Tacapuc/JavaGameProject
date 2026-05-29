/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package item;

import java.util.ArrayList;

/**
 *
 * @author ilya.bespalov
 */
public class Stats {
    public int attackPower;
    public int critChance;
    public int stamina;
    public int armor;
    
    
    public Stats (int attackPower, int critChance, int stamina, int armor) {
        this.attackPower = attackPower;
        this.critChance = critChance;
        this.stamina = stamina;
        this.armor = armor;
        
    }
    public ArrayList<String> guiGetStats() {
        ArrayList<String> strings = new ArrayList<>();
        String crit = "Critical Strike Rating: " + critChance;
        String ap = "Attack Power: " + attackPower;
        String hp = "Stamina: " + stamina;
        String armour = "Armor: " + armor;
        strings.add(ap);
        strings.add(crit);
        strings.add(armour);
        strings.add(hp);
        
        return strings;
        
        
    }
    
    public void addStats(Stats stats) {
        if (stats == null) {
            System.out.println("invalid stats");
            return;
            
        }
        this.stamina += stats.stamina;
        this.critChance += stats.critChance;
        this.attackPower += stats.attackPower;
        this.armor += stats.armor;
    }

    @Override
    public String toString() {
        return "Stats{" + "attackPower=" + attackPower + ", critChance=" + critChance + ", stamina=" + stamina + ", armor=" + armor + '}';
    }
    
    
}
