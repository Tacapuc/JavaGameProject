package main;

/**
 *
 * @author ilya.bespalov
 */

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import ui.UI;
import main.*;
import combat.*;

public class KeyH implements KeyListener {

    public boolean moveup, movedown, moveleft, moveright, inventory;
    public int keypressedx = 0;
    public int keypressedy = 0;
    MainP gp;

    public KeyH(MainP gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        int keycode = e.getKeyCode();


    }


    @Override
    public void keyPressed(KeyEvent e) {
        int keycode = e.getKeyCode();

        if (keycode == KeyEvent.VK_W || keycode == KeyEvent.VK_UP) {
            moveup = true;

        }
        if (keycode == KeyEvent.VK_S || keycode == KeyEvent.VK_DOWN) {
            movedown = true;

        }
        if (keycode == KeyEvent.VK_A || keycode == KeyEvent.VK_LEFT) {
            moveleft = true;

        }
        if (keycode == KeyEvent.VK_D || keycode == KeyEvent.VK_RIGHT) {
            moveright = true;

        }
        if (keycode == KeyEvent.VK_B) {
            gp.i_UI.toggle();

        }
        if (keycode == KeyEvent.VK_1) {
            if (gp.mouseH.targetedEnt != null) {
                gp.combatLogic.checkFacing(gp.p1, gp.mouseH.targetedEnt, 64);
            }
            

        }
        if (keycode == KeyEvent.VK_C) {
            if (gp.s_UI.visible) {
                gp.s_UI.toggle();
            }
            gp.c_UI.toggle();

        }
        if (keycode == KeyEvent.VK_P) {
            if (gp.c_UI.visible) {
                gp.c_UI.toggle();
            }
            gp.s_UI.toggle();

        }
        if (keycode == KeyEvent.VK_ESCAPE) {
            gp.lW_UI.visible = false;
            gp.ufTarget_UI.visible = false;
            gp.mouseH.clearTarget();
            

        }
    }

        @Override
        public void keyReleased
        (KeyEvent e
        
            ) {
        int keycode = e.getKeyCode();



            if (keycode == KeyEvent.VK_W || keycode == KeyEvent.VK_UP) {
                moveup = false;

            }
            if (keycode == KeyEvent.VK_S || keycode == KeyEvent.VK_DOWN) {
                movedown = false;

            }
            if (keycode == KeyEvent.VK_A || keycode == KeyEvent.VK_LEFT) {
                moveleft = false;

            }
            if (keycode == KeyEvent.VK_D || keycode == KeyEvent.VK_RIGHT) {
                moveright = false;

            }

        }
    }
