package ui;

/**
 *
 * @author ilya.bespalov
 */

import java.awt.Graphics2D;
import java.util.ArrayList;
import main.MainP;

public class UIManager {

    public ArrayList<UI> elements = new ArrayList<>();

    public void add(UI element){
        elements.add(element);
    }

    public void draw(Graphics2D g2, MainP gp){

        for(UI e : elements){

            if(e.visible){
                e.draw(g2, gp);
            }

        }
    }
}