/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package combat;

import main.*;
import entity.*;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.Random;

/**
 *
 * @author ilya.bespalov
 */

public class CombatLogic {


    MainP gp;
    boolean attacking;
    public int attackInterval;
    public int attackCooldown;
    Random rn = new Random();
    public int damageDone;


    public CombatLogic(MainP gp) {
        this.gp = gp;
    }

    public void startAttack(int interval) {
        attackInterval = interval;
        if (!attacking) {
            attacking = true;
            gp.p1.attacking = true;
        } else {
            return;
        }
    }

    public void attack() {
//om inget ska hända returnar den
    if (!attacking) return;

    if (gp.mouseH.targetedEnt == null) {
        attacking = false;
        gp.p1.attacking = false;
        gp.p1.playingAttackAnim = false;
        return;
    }

    //inte gå vidare om ingen attack ska ske

    if (attackCooldown > 0) {
        if (attackCooldown>=60) {
            gp.p1.playingAttackAnim = true;

        } else gp.p1.playingAttackAnim = false;
        attackCooldown--;
        return;
    }




    Entity target = gp.mouseH.targetedEnt;

    if (checkFacing(gp.p1, target, 64) && target.attackable) {

        damageDone = rn.nextInt(20, 25);


        target.currentHealth -= damageDone;
        gp.p1.currentResource += 0.8*damageDone;
        int damageX = (int) target.x;
        int damageY = (int) target.y;
        if (gp.p1.currentResource > gp.p1.maxResource) {
            gp.p1.currentResource = gp.p1.maxResource;
        }



        gp.damageNumberManager.add(damageX, damageY, damageDone, Color.YELLOW);

        if (target.currentHealth <= 0) {
            target.die();
        }

        attackCooldown = attackInterval * 60;
    
}
    }

    //om mainEnt kollar på checkedEnt i intervallet av range meter ska den returnera true

    public boolean checkFacing(Entity mainEnt, Entity checkedEnt, int range) {

        int x = mainEnt.screenX;
        int y = mainEnt.screenY;

        int boxX = x;
        int boxY = y;

        switch (mainEnt.direction) {
            case "up":
                boxY -= range;
                break;

            case "down":
                boxY += range;
                break;

            case "left":
                boxX -= range;
                break;

            case "right":
                boxX += range;
                break;

            default:
                return false;
        }

        Rectangle hitBox = new Rectangle(boxX, boxY, range, range);

        return checkedEnt.hitBox.intersects(hitBox);
    }

}
