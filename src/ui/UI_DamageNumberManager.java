/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import main.*;

/**
 *
 * @author ilya.bespalov
 */
public class UI_DamageNumberManager {
    MainP gp;

    public UI_DamageNumberManager(MainP gp) {
        this.gp = gp;
    }
    

    ArrayList<UI_DamageNumber> numbers = new ArrayList<>();



    public void add(float x, float y, int damage, Color color) {
        numbers.add(new UI_DamageNumber(x, y, String.valueOf(damage), color));
    }

    public void update() {
        for (int i = 0; i < numbers.size(); i++) {
            UI_DamageNumber dn = numbers.get(i);
            dn.update();
            if (dn.isDead()) {
                numbers.remove(i);
                i--;
            }
        }
    }

    public void draw(Graphics2D g2) {
        g2.setFont(gp.font.deriveFont(30f));
        for (UI_DamageNumber dn : numbers) {
            float alpha = (float) dn.life / dn.maxLife;
            Color c = new Color(dn.color.getRed(), dn.color.getGreen(), dn.color.getBlue(), (int) (alpha * 255));
            //den svarta skuggan
            g2.setColor(new Color(0, 0, 0, alpha));
            int screenX = (int) dn.x - gp.cameraX;
            int screenY = (int) dn.y - gp.cameraY;
            g2.drawString(dn.text, screenX+2, screenY+2);
            //huvud texten
            g2.setColor(c);
            g2.drawString(dn.text, screenX, screenY);
        }
    }
}
