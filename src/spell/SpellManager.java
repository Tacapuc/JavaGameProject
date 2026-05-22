/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spell;

import java.util.ArrayList;

/**
 *
 * @author ilya.bespalov
 */
public class SpellManager {
    ArrayList<Spell> spells = new ArrayList<>();
    public void add(Spell spell){
        spells.add(spell);
    }
}
