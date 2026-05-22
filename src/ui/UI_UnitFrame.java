/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import entity.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.MainP;

/**
 *
 * @author ilya.bespalov
 */
public class UI_UnitFrame extends UI {

    MainP gp;
    public int height = 128;
    public int width = 256;
    public int x = 15;
    public int y = 15;
    int healthWidth;
    double hpPercent;
    int resourceWidth;
    double resourcePercent;
    Entity ent = null;
    Player p;
    String health;
    BufferedImage image, image2, image3;
    public boolean moving = false;

    public void moveFrame() {
        gp.ufOwn_UI.moving = true;
        x = gp.mouseH.mouseX;
        y = gp.mouseH.mouseY;

    }
    public void resetPosition() {
        x = 15;
        y = 15;
    }

    public UI_UnitFrame(MainP gp, Entity ent) {
        this.ent = ent;
        this.gp = gp;
        try {
            image = ImageIO.read(getClass().getResource("/resources/ui/unitFrame/DIFrameSkin.png"));
            image2 = ImageIO.read(getClass().getResource("/resources/ui/unitFrame/health.png"));
            image3 = ImageIO.read(getClass().getResource("/resources/ui/unitFrame/damage.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setEnt(Entity ent) {
        this.ent = ent;
    }
    

    @Override
    public void draw(Graphics2D g2, MainP gp) {
        int portraitX = x+35;
        int portraitY = y+22;
        int portraitDim = 70;
        health = ent.currentHealth + "/" + ent.health;
        hpPercent = (double) ent.currentHealth / ent.health;
        healthWidth = (int) ((width / 2 + 12) * hpPercent);
        resourcePercent = (double) ent.currentResource / ent.resource;
        resourceWidth = (int) ((width / 2 + 12) * resourcePercent);
        g2.setColor(Color.black);
        g2.fillOval(portraitX, portraitY, portraitDim, portraitDim);
        g2.drawImage(ent.down, portraitX, portraitY, portraitDim, portraitDim, null);
        g2.drawImage(image2, x + 110, y + 29, healthWidth, height / 6, null);
        g2.drawImage(image3, x + 110, y + 55, resourceWidth, height / 12, null);
        g2.drawImage(image, x, y, width, height, null);
        g2.setColor(Color.white);
        g2.drawString(ent.name, x + 120, y + 42);
        g2.drawString(health, x + 120 + 85, y + 42);

    }

}
