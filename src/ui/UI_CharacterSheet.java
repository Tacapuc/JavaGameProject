package ui;

/**
 *
 * @author ilya.bespalov
 */

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import main.MainP;
import entity.Player;
import java.util.ArrayList;

public class UI_CharacterSheet extends UI {

    BufferedImage image;
    int x;
    int y;
    int width = (int) (512 * scale);
    int height = (int) (512 * scale);
    MainP gp;
    Player p;

    public UI_CharacterSheet(MainP gp, Player p) {
        this.gp = gp;
        this.p = p;
        try {
            image = ImageIO.read(getClass().getResource("/resources/ui/C_Frame.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g2, MainP gp) {

        if (!visible) {
            return;
        }


        //med avseende på vilken slot ett item har så ritas alla saker i en lista på sin fast plats



        g2.drawImage(image, x, y, width, height, null);
        for (int i = 0; i < p.pItemsEquipped.size(); i++) {
            //vänster sida
            if (p.pItemsEquipped.get(i).itemSlot < 8) {
                p.pItemsEquipped.get(i).x = x + 24;
                p.pItemsEquipped.get(i).y = y + 77 + 41 * (p.pItemsEquipped.get(i).itemSlot - 1);
                g2.drawImage(p.pItemsEquipped.get(i).image, p.pItemsEquipped.get(i).x, p.pItemsEquipped.get(i).y , 32, 32, null);
            } //höger sida
            else if (p.pItemsEquipped.get(i).itemSlot > 8 && p.pItemsEquipped.get(i).itemSlot < 17) {
                p.pItemsEquipped.get(i).x = x + 308;
                p.pItemsEquipped.get(i).y = y + 77 + 41 * (p.pItemsEquipped.get(i).itemSlot - 9);
                g2.drawImage(p.pItemsEquipped.get(i).image, p.pItemsEquipped.get(i).x, p.pItemsEquipped.get(i).y , 32, 32, null);
            }
            //vapen och sånt där nere
            else if (p.pItemsEquipped.get(i).itemSlot >= 17) {
                p.pItemsEquipped.get(i).x = x + 124 + 41 * (p.pItemsEquipped.get(i).itemSlot - 17) ;
                p.pItemsEquipped.get(i).y = y + 387;
                g2.drawImage(p.pItemsEquipped.get(i).image, p.pItemsEquipped.get(i).x, p.pItemsEquipped.get(i).y , 32, 32, null);
            }

        }
        for (int i = 0; i < gp.p1.stats.guiGetStats().size(); i++) {
            g2.drawString(gp.p1.stats.guiGetStats().get(i), x+72, y+i*15+96);
            
        }
        
        g2.drawImage(p.down, x+60, y+128, 256, 256, null);


    }
}
