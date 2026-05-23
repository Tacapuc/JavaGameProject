/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spell;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import entity.*;

/**
 *
 * @author ilya.bespalov
 */


//den här grejen blev inte klar
public abstract class Spell {
    public int spellId;
    String name;
    String filePath;
    int cost;
    public BufferedImage image;
    public Spell (int spellId, String name, String filePath) throws IOException {
        this.spellId = spellId;
        this.name = name;
        this.filePath = filePath;
        this.cost = cost;
        try {
            image = ImageIO.read(getClass().getResource(filePath));
        } catch (IOException e) {
            image = ImageIO.read(getClass().getResource("/resources/icons/Miscellaneous/QuestionMark.png"));
        }
    }
    

    
}
