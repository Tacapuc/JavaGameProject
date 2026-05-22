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

public class UI_Spellbook extends UI {

    BufferedImage image;
    int x;
    int y;
    int width = (int) (512 * scale);
    int height = (int) (512 * scale);
    MainP gp;
    Player p;

    public UI_Spellbook(MainP gp, Player p) {
        this.gp = gp;
        this.p = p;
        try {
            image = ImageIO.read(getClass().getResource("/resources/ui/spellbook.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g2, MainP gp) {

        if (!visible) {
            return;
        }

        g2.drawImage(image, x, y, width, height, null);
      

        


    }
}
