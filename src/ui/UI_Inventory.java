package ui;

/**
 *
 * @author ilya.bespalo
 * 
 */
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import main.MainP;
import entity.Player;

public class UI_Inventory extends UI {

    Player p;
    MainP gp;

    BufferedImage image;
    int height = (int) (256 * scale);
    int width = (int) (256 * scale);

    public UI_Inventory(Player p, MainP gp) {
        this.gp = gp;
        this.p = p;
        try {
            image = ImageIO.read(getClass().getResource("/resources/ui/I_Frame.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g2, MainP gp) {

        if (!visible) {
            return;
        }

        g2.drawImage(image, gp.scWidth - width - 20, gp.scHeight - height - 20, width, height, null);
        loadInventory(g2);

    }

    public void loadInventory(Graphics2D g2) {
        //rita ut sakerna som spelaren äger och inte har på sig med jämna mellan rum och max 4 per rad
        for (int i = 0; i < p.pItems.size(); i++) {
            p.pItems.get(i).x = gp.scWidth - width + 64 + 42 * (i % 4);
            p.pItems.get(i).y = gp.scHeight - height + 47 + (36 + 5) * (i / 4);
            g2.drawImage(p.pItems.get(i).image, p.pItems.get(i).x, p.pItems.get(i).y, 32, 32, null);
        }

    }
}
