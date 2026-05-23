package item;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Color;
import main.MainP;
/**
 *
 * @author ilya.bespalov
 */
public abstract class Item {

    public int id;
    public int quality;
    public String name;
    public String filePath;
    public String type;
    public boolean equipped;
    public BufferedImage image;
    public int itemSlot;
    public int x;
    public int y;
    MainP gp;

    public Item(int id, int quality, String name, String filePath, String type, int itemSlot) {
        this.id = id;
        this.quality = quality;
        this.name = name;
        this.filePath = filePath;
        this.type = type;
        this.itemSlot = itemSlot;
        try {
            image = ImageIO.read(getClass().getResource(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Item{" + "id=" + id + ", quality=" + quality + ", name=" + name + ", filePath=" + filePath + ", type=" + type + '}';
    }

    public int getId() {
        return id;
    }

    public String getQuality() {
        String stringQuality = "Undef";
        switch (quality) {
            case 1:
                stringQuality = "Common";
                break;
            case 2:
                stringQuality = "Uncommon";
                break;
            case 3:
                stringQuality = "Rare";
                break;
            case 4:
                stringQuality = "Epic";
                break;
            case 5:
                stringQuality = "Legendary";
                break;

        }
        return stringQuality;
    }
        

    public Color getColor() {
        Color itemColor = new Color(255, 255, 255);
        switch (quality) {
            case 1:
                itemColor = new Color(200, 200, 200);
                break;
            case 2:
                itemColor = new Color(30, 255, 0);
                break;
            case 3:
                itemColor = new Color(0, 112, 221);
                break;
            case 4:
                itemColor = new Color(163, 53, 238);
                break;
            case 5:
                itemColor = new Color(255, 128, 0);
                break;

        }
        return itemColor;
        

    }

}
