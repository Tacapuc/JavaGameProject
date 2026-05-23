package main;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import ui.*;
import main.MainP;
import entity.*;
import item.Item;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.SwingUtilities;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author ilya.bespalov
 */
public class MouseH implements MouseMotionListener, MouseListener {

    MainP gp;
    Player p;
    MainF mf;
    Entity entHovered;
    boolean hoveringEnt = false;
    boolean hoveringItem = false;
    public Item hoveredItem = null;
    boolean hoveringFrame = false;
    boolean hoveringUI;
    public Item targetedItem = null;
    boolean hoveringBtn = false;
    public UI_DropdownBtn hoveredBtn = null;
    boolean hoveringEnemy;
    public int mouseX;
    public int mouseY;
    public Entity targetedEnt;
    public Color color = new Color(255, 255, 255);

    public MouseH(MainP gp, Player p, MainF mf) {
        this.gp = gp;
        this.p = p;
        this.mf = mf;

    }

    //kollar om jag hoverar över UnitFrame

    public void hoverFrame() {
        if ((mouseX <= gp.ufOwn_UI.x + gp.ufOwn_UI.width) && (mouseX >= gp.ufOwn_UI.x) && ((mouseY <= gp.ufOwn_UI.y + gp.ufOwn_UI.height) && (mouseY >= gp.ufOwn_UI.y))) {
            hoveringFrame = true;
        } else {
            hoveringFrame = false;
        }
    }


    //Rensar min valda target

    public void clearTarget() {
        targetedEnt = null;
        for (int i = 0; i < gp.entManager.entities.size(); i++) {
            if (gp.entManager.entities.get(i).targeted) {
                gp.entManager.entities.get(i).targeted = false;
            }

        }
    }

    //kollar hover över fiende

    public void hoverEnemy() {
        if (hoveringEnt && entHovered instanceof Enemy && !entHovered.dead) {
            gp.setCursor(mf.attackCursor);
            hoveringEnemy = true;
        }
        
        else if (hoveringEnt && entHovered.dead) {
            gp.setCursor(gp.mf.lootCursor);
        }
        
        else {
            gp.setCursor(mf.blankCursor);
            hoveringEnemy = false;
        }
    }

    //kollar hover över vilken ent som helst

    public void hoverEnt() {
        hoveringEnt = false;
        entHovered = null;
        for (int i = 0; i < gp.entManager.entities.size(); i++) {
            if ((mouseX <= gp.entManager.entities.get(i).screenX + 64) && (mouseX >= gp.entManager.entities.get(i).screenX) && ((mouseY <= gp.entManager.entities.get(i).screenY + 64) && (mouseY >= gp.entManager.entities.get(i).screenY))) {
                hoveringEnt = true;

                entHovered = gp.entManager.entities.get(i);
                break;
            }

        }
    }

    //koden för att spawna tooltips som tar in vilka items den ska skanna efter på skärmen

    public void tooltip(ArrayList<Item> items) {

        hoveringItem = false;
        hoveredItem = null;
        for (int i = 0; i < items.size(); i++) {
            if ((mouseX <= items.get(i).x + 32) && (mouseX >= items.get(i).x)) {
                if ((mouseY <= items.get(i).y + 32) && (mouseY >= items.get(i).y)) {
                    //sätter in informationen i tooltipen
                    ArrayList<String> dStrings = new ArrayList<>();
                    hoveringItem = true;
                    hoveredItem = items.get(i);
                    color = items.get(i).getColor();
                    gp.t_UI.x = items.get(i).x;
                    gp.t_UI.y = items.get(i).y;
                    dStrings.add(items.get(i).name);
                    dStrings.add(items.get(i).type);
                    dStrings.add(items.get(i).getQuality());
                    gp.t_UI.dStrings = dStrings;
                    int longestString = 0;
                    //försök på anpassade tooltips efter längden i tecken
                    for (int j = 0; j < dStrings.size(); j++) {

                        int comparedString = dStrings.get(j).length();
                        if (longestString < comparedString) {
                            longestString = comparedString;
                        }

                    }
                    gp.t_UI.longestString = longestString;
                    gp.t_UI.shownColor = color;
                }
            }

        }
        gp.t_UI.visible = hoveringItem;


    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        hoverEnt();
        hoverEnemy();

        //flytta på ramen av spelarens liv om man vill
        if (gp.ufOwn_UI.moving) {
            gp.ufOwn_UI.x = mouseX;
            gp.ufOwn_UI.y = mouseY;

        }


        //tooltips för olika menyer


        if (gp.c_UI.visible) {
            tooltip(p.pItemsEquipped);

        } else if (gp.i_UI.visible) {
            tooltip(p.pItems);

        } else if (gp.lW_UI.visible) {
            tooltip(gp.lW_UI.npc.drops);

        } else {
            gp.t_UI.visible = false;
            hoveringItem = false;
            hoveredItem = null;
        }

        //skannar efter vilken button vi hoverar över om vi har en dropdownmenu öppen

        if (gp.d_UI.visible) {

            hoveringBtn = false;
            hoveredBtn = null;
            for (int i = 0; i < gp.d_UI.menu_visible.size(); i++) {
                var btn = gp.d_UI.menu_visible.get(i);
                if ((mouseX <= btn.x + 128) && (mouseX >= btn.x) && ((mouseY <= btn.y + 16) && (mouseY >= btn.y))) {
                    hoveringBtn = true;
                    hoveredBtn = btn;
                    btn.buttonHighlight = true;
                } else {
                    btn.buttonHighlight = false;
                }
            }

        }
    }



    
    public void openLootFrame() {
        if (hoveringEnt && entHovered.dead) {
            gp.lW_UI.npc = entHovered;
            gp.lW_UI.x = mouseX;
            gp.lW_UI.y = mouseY;
            gp.lW_UI.toggle();
            
            
        }
    }


    //tar in items, oom listan inte är tom och vi hoverar över itemet så ska det skickas tillvårt inventory.

    public void lootItem(ArrayList<Item> items) {
        if (!items.isEmpty()) {
            for (int i = items.size() - 1; i >= 0; i--) {
                Item item = items.get(i);
                if (mouseX <= item.x + 32 && mouseX >= item.x) {
                    if (mouseY <= item.y + 32 && mouseY >= item.y) {
                        gp.iH.addItem(item.id);
                        items.remove(i);
                        break;
                    }
                }
            }
        }
    }

    public void mousePressed(MouseEvent e) {
        hoverFrame();
        if (SwingUtilities.isRightMouseButton(e)) {
            openLootFrame();


            //logiken för att calla efter dropdown menu, borde egentligen göras om till metoder men det här duger så länge
            if (hoveringItem) {
                gp.d_UI.shownColor = color;
                gp.d_UI.callMenu(gp.d_UI.menu_itemUnit);
                targetedItem = hoveredItem;
                gp.d_UI.x = e.getX();
                gp.d_UI.y = e.getY();
                gp.d_UI.name = targetedItem.name;
                gp.d_UI.toggle();

            } else if (hoveringFrame) {
                gp.d_UI.shownColor = gp.titleColor;
                gp.d_UI.callMenu(gp.d_UI.menu_self);
                gp.d_UI.x = e.getX();
                gp.d_UI.y = e.getY();
                gp.d_UI.name = p.name;
                gp.d_UI.toggle();
            }

            //börja attackera valda ent varannan sekund om den är enemy

            if (targetedEnt != null && hoveringEnemy) {
                gp.combatLogic.startAttack(2);
                
                
            }
        }

        //sluta flytta på framen om vi så gör

        if (gp.ufOwn_UI.moving == true) {
            gp.ufOwn_UI.moving = false;

        }
        //loota grej

        if (gp.lW_UI.visible) {
            lootItem(gp.lW_UI.npc.drops);
        }


        //targetta en entity


        if (hoveringEnt) {
            clearTarget();
            entHovered.targeted = true;
            targetedEnt = entHovered;
            gp.ufTarget_UI.setEnt(entHovered);

            if (!gp.ufTarget_UI.visible) {
                gp.ufTarget_UI.toggle();
            }

            gp.ufTarget_UI.x = 15 + 270;

        }
        if (hoveringBtn) {
            hoveredBtn.run();
            hoveredBtn.buttonHighlight = false;
            gp.d_UI.toggle();
            hoveringBtn = false;

        }
        if (!hoveringItem && !hoveringFrame) {
            gp.d_UI.visible = false;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
