/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;
import entity.Player;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.MainP;
/**
 *
 * @author ilya.bespalov
 */
public class UI_ActionBar extends UI {
    public BufferedImage image;
    public int height = 49;
    public int width = 552;
    public int x= 236;
    public int y = 768-height;
    
    
    public UI_ActionBar () {
        closeOnEsc = false;
        try {
            image = ImageIO.read(getClass().getResource("/resources/ui/spellbar.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public void draw(Graphics2D g2, MainP gp) {
        g2.drawImage(image,x, y , width , height, null);
    }
    
}
