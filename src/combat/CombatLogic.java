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

    Rectangle hitBox;
    MainP gp;
    boolean attacking;
    public int attackInterval;
    public int attackCooldown;
    Random rn = new Random();
    public int damageDone;

    public CombatLogic(MainP gp) {
        this.gp = gp;
    }

    public void startAttack(int interval, Entity target) {
        attackInterval = interval;
        if (!attacking) {
            attacking = true;
        } else {
            return;
        }
    }

    public void attack() {

    if (!attacking) return;

    if (gp.mouseH.targetedEnt == null) {
        attacking = false;
        return;
    }

    if (attackCooldown > 0) {
        attackCooldown--;
        return;
    }

    Entity target = gp.mouseH.targetedEnt;

    if (checkFacing(gp.p1, target, 64) && target.attackable) {

        damageDone = rn.nextInt(20, 25);

        target.currentHealth -= damageDone;
        int damageX = (int) target.x;
        int damageY = (int) target.y;

        gp.damageNumberManager.add(
            damageX,
            damageY,
            damageDone,
            Color.YELLOW
        );

        if (target.currentHealth <= 0) {
            target.die();
        }

        attackCooldown = attackInterval * 60;
    
}
    }

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
