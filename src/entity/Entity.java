/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import item.Item;
import main.MainP;
import loot.*;

/**
 *
 * @author ilya.bespalov
 */
public abstract class Entity {

    public String name;
    public int health;
    public int resource;
    public double x;
    public double y;
    public int currentHealth = 100;
    public int currentResource = 100;
    private BufferedImage image;
    public int tileSize = MainP.tileSize;
    public String direction = "down";
    public String model;
    public BufferedImage up;
    public BufferedImage down;
    public boolean attackable = true;
    BufferedImage left, right, highlight, deadEnt;
    public boolean dead = false;
    public LootTable lootTable;
    public ArrayList<Item> drops = new ArrayList<>();

    
    public Rectangle solidArea;
    public Rectangle hitBox;
    public int screenX;
    public int screenY;
    public boolean targeted = false;
    MainP gp;

    public Entity(String name, int health, int resource, String model, MainP gp) {
        this.name = name;
        this.health = health;
        this.resource = resource;
        this.gp = gp;
        solidArea = new Rectangle(8, 8, tileSize - 16, tileSize - 16);

        try {
            up = ImageIO.read(getClass().getResource("/resources/entity/" + model + "_up.png"));
            down = ImageIO.read(getClass().getResource("/resources/entity/" + model + "_down.png"));
            left = ImageIO.read(getClass().getResource("/resources/entity/" + model + "_left.png"));
            right = ImageIO.read(getClass().getResource("/resources/entity/" + model + "_right.png"));
            highlight = ImageIO.read(getClass().getResource("/resources/ui/TargetHighlight.png"));
            deadEnt = ImageIO.read(getClass().getResource("/resources/entity/deadEnt.png"));
            
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String toString() {
        return "Entity{" + "name=" + name + ", health=" + health + ", resource=" + resource + ", x=" + screenX + ", y=" + screenY + ", currentHealth=" + currentHealth + ", currentResource=" + currentResource + ", model=" + model + '}' + "Dead = " + dead;
    }

    public ArrayList<Item> dropLoot() {
        if (lootTable == null) {
            return new ArrayList<>();
        }
        return lootTable.dropLoot();
    }



    
    public void die() {
        attackable = false;
        currentHealth = 0;
        image = deadEnt;
        dead = true;
        drops = dropLoot();
        for (Item item: drops) {
            System.out.println(item);
        }
    }

    public void drawEntity(Graphics2D g2, MainP gp) {
        if (currentHealth != 0) {
            switch (direction) {
            case "up":
                image = up;
                break;
            case "down":
                image = down;
                break;
            case "left":
                image = left;
                break;
            case "right":
                image = right;
                break;
        }
            
        }
        
        screenX = (int) x - gp.cameraX;
        screenY = (int) y - gp.cameraY;
        if (targeted) {
            g2.setColor(Color.red);
            g2.drawImage(highlight, screenX-6, screenY-6, tileSize+12, tileSize+12, null);
            g2.setColor(Color.white);
        }
        hitBox = new Rectangle(screenX, screenY, tileSize, tileSize);

        g2.drawImage(image, screenX, screenY, tileSize, tileSize, null);

    }
}
