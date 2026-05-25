package ui;

import main.MainP;

import java.awt.*;
import java.util.ArrayList;

public class UI_KeyBindGuide extends UI {

    ArrayList<String> keyBindList = new ArrayList<>();


    public UI_KeyBindGuide(){
        addKeybind("Toggle keybind guide", "F1");
        addKeybind("Open Bags", "B");
        addKeybind("Open Character Sheet", "C");
        addKeybind("Open Spellbook (WIP)", "p");
        addKeybind("Interact with entity/item/frame", "RMB");
        addKeybind("Start Attack", "RMB Enemy");
        addKeybind("Movement", "WASD / Arrows");
        addKeybind("Close/Exit target", "ESC");


    }
    public void addKeybind(String action, String keybind) {
        String newKeybind = keybind + " - " + action;
        keyBindList.add(newKeybind);
    }



    @Override
    public void draw(Graphics2D g2, MainP gp) {
        if (!visible) {
            return;

        }
        int bgHeight = 30*keyBindList.size()+10;
        g2.drawImage(gp.t_UI.image, 0,0,325, bgHeight, null);
        g2.setColor(new Color(0, 0, 0));
        for (int i =0; i<keyBindList.size(); i++) {
            g2.drawString(keyBindList.get(i), 17, 22+i*30 );

        }
        g2.setColor(new Color(255, 255, 255));
        for (int i =0; i<keyBindList.size(); i++) {
            g2.drawString(keyBindList.get(i), 15, 20+i*30 );

        }

    }
}
