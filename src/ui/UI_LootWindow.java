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
public class UI_LootWindow extends UI {

    BufferedImage image;
    public int x;
    public int y;
    int width = (int) (256 * scale);
    int height = (int) (256 * scale);
    MainP gp;
    public Entity npc;

    public UI_LootWindow(MainP gp) {
        this.gp = gp;
        try {
            image = ImageIO.read(getClass().getResource("/resources/ui/UI-LootPanel.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g2, MainP gp) {
        int portraitX = x+11;
        int portraitY = y+9;
        int portraitDim = 56;
        if (!visible) {
            return;
        }

        g2.setColor(Color.black);
        g2.fillOval(portraitX, portraitY, portraitDim, portraitDim);
        g2.drawImage(npc.down, portraitX, portraitY, portraitDim, portraitDim, null);

        g2.drawImage(image, x, y, width, height, null);
    }

}
