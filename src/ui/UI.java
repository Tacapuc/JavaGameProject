package ui;

/**
 *
 * @author ilya.bespalov
 */

import java.awt.Graphics2D;
import main.MainP;

public abstract class UI {

    public boolean visible = false;
    public double scale = 1;

    public void show() {
        visible = true;
    }

    public void hide() {
        visible = false;
    }

    public void toggle() {
        visible = !visible;
    }

    public abstract void draw(Graphics2D g2, MainP gp);
}