package entity;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author ilya.bespalov
 */
import item.Item;
import main.MainP;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.*;
import java.util.ArrayList;

public class Player extends Entity {


    public ArrayList<Item> pItems = new ArrayList<>();
    public ArrayList<Item> pItemsEquipped = new ArrayList<>();

    public Player(String name, int health, int resource, String model, MainP gp) {
        super(name, health, resource, model, gp);
    }

}
