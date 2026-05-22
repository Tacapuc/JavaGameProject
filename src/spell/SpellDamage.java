/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spell;

import java.io.IOException;
import entity.*;
import main.*;

/**
 *
 * @author ilya.bespalov
 */
public class SpellDamage extends Spell {

    public int cost;
    public int baseDamage;
    public int damageDealt;
    MainP gp;

    public SpellDamage(int spellId, String name, String filePath, MainP gp) throws IOException {
        super(spellId, name, filePath);
        this.gp = gp;
    }

    

}
