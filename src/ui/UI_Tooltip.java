/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.MainP;
import java.util.ArrayList;

/**
 *
 * @author ilya.bespalov
 */
public class UI_Tooltip extends UI {
    public BufferedImage image;
    public int x = 0;
    public int y = 0;
    int dHeight = (int) (256 * scale);
    int dWidth = (int) (256 * scale);
    public int longestString;
    public ArrayList<String> dStrings = new ArrayList<>();
    public Color shownColor = new Color(255, 255, 255);
    public UI_Tooltip() {
        
        try {
            image = ImageIO.read(getClass().getResource("/resources/ui/Tooltip_Frame.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public void draw(Graphics2D g2, MainP gp) { 
        

        if(!visible) return;
        FontMetrics fm = g2.getFontMetrics();
        dWidth = fm.charWidth('A')*longestString;
        dHeight = 64;
        int xPre =  x-dWidth+20;
        int yPre = y-dHeight+10;
        if (xPre < 0) {
            xPre = 0;
        } else if (yPre < 0) {
            yPre = 0;
        } else if (xPre+dWidth > 1024) {
            xPre = 1024-dWidth;
        } else if (yPre+dHeight > 768) {
            yPre = 768-dHeight;
        }

        g2.drawImage(image,xPre, yPre , dWidth , dHeight, null);
        g2.setColor(shownColor);
         g2.drawString(dStrings.get(0), xPre+5, yPre+15);
         g2.setColor(Color.white);
        for (int i = 1; i < 3; i++) {
            g2.drawString(dStrings.get(i),  xPre+5, yPre+15+10*i);
            
            
        }
        
    }
    
    
}
