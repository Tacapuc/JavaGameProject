/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import entity.Player;
import item.ItemHandler;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import main.MainP;

/**
 *
 * @author ilya.bespalov
 */
public class UI_DropdownMenu extends UI {

    public ArrayList<UI_DropdownBtn> btns = new ArrayList<>();
    public ArrayList<UI_DropdownBtn> menu_itemUnit = new ArrayList<>();
    public ArrayList<UI_DropdownBtn> menu_self = new ArrayList<>();
    public ArrayList<UI_DropdownBtn> menu_visible = new ArrayList<>();
    public int x;
    public int y;
    int height = 45;
    int width = 128;
    MainP gp;
    public Color shownColor = new Color (255, 255, 255);
    
    public String name = "Undefined";

    public UI_DropdownMenu(MainP gp) {
        this.gp = gp;

    }

    public void loadAllMenus() {
        loadButtons_Item();
        loadButtons_Player();
    }

    public void loadButtons_Item() {
        createButton("Equip", 0, () -> {
            gp.iH.equipItem(gp.mouseH.targetedItem);
        }, menu_itemUnit);
        createButton("Unequip", 1, () -> {
            gp.iH.unequipItem(gp.mouseH.targetedItem);
        }, menu_itemUnit);
        createButton("Use", 2, () -> {
            System.out.println("Cancelled!");
        }, menu_itemUnit);
        createButton("Destroy", 3, () -> {
            gp.iH.destroy();
        }, menu_itemUnit);
        createButton("Cancel", 4, () -> {
            System.out.println("Cancelled!");
        }, menu_itemUnit);

    }
    
    public void loadButtons_Player() {
        createButton("Inventory", 0, () -> {
            gp.i_UI.toggle();
        }, menu_self);
        createButton("Character Sheet", 1, () -> {
            gp.c_UI.toggle();
        }, menu_self);
        createButton("Move Frame", 2, () -> {
            gp.ufOwn_UI.moveFrame();
        }, menu_self);
        createButton("Reset Position", 2, () -> {
            gp.ufOwn_UI.resetPosition();
        }, menu_self);
        createButton("Cancel", 3, () -> {
            System.out.println("Cancelled!");
        }, menu_self);

    }

    public void createButton(String name, int prio, Runnable method, ArrayList menu) {
        UI_DropdownBtn btn = new UI_DropdownBtn(prio, name, method);
        menu.add(btn);
    }

    public void callMenu(ArrayList menu) {
        menu_visible = menu;
    }

    @Override
    public void draw(Graphics2D g2, MainP gp) {
        if (!visible) {
            return;
        }
        
        height = menu_visible.size() * 16 + 8 + 16;
         if (x < 0) {
            x = 0;
        } else if (y < 0) {
            y = 0;
        } else if (x + width > 1024) {
            x = 1024-width;
        } else if (y + height > 768) {
            y = 768-height;
        }
        g2.drawImage(gp.t_UI.image, x, y, width, height, null);
        g2.setColor(shownColor);
        if (name.length() > 15) {
            name = name.substring(0, 15) + "...";
        }
        g2.drawString(name, x + 4 + 2, y + 16);
        g2.setColor(Color.white);
        for (int i = 0; i < menu_visible.size(); i++) {
            menu_visible.get(i).y = y + i * 16 + 4 + 16;
            menu_visible.get(i).x = x + 4;
            menu_visible.get(i).draw(g2, gp);

        }
    }

}
