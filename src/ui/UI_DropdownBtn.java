/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

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
public class UI_DropdownBtn extends UI {
    public Color backgroundColor = new Color(0, 0, 0, 0);
    int height = 16;
    public int minwidth = 120;
    int priorityNumber;
    public int x;
    public int y;
    BufferedImage image;
    BufferedImage image1;
    String name;
    Runnable method;
    public boolean buttonHighlight;

    public UI_DropdownBtn(int priorityNumber, String name, Runnable method) {
        this.priorityNumber = priorityNumber;
        this.name = name;
        this.method = method;
        try {
            image = ImageIO.read(getClass().getResource("/resources/ui/UI-Listbox-Highlight.PNG"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    

    @Override
    public void draw(Graphics2D g2, MainP gp) {
        if (buttonHighlight) {
            g2.drawImage(image,x, y , minwidth , height, null);
        }
        
        g2.setColor(Color.white);
        g2.drawString(name, x+4, y+12);
        
    }

    public void run() {
        method.run();
    }
    
}
