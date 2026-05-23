/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;
import combat.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Random;
import main.MainP;

/**
 *
 * @author ilya.bespalov
 */
public class UI_DamageNumber {
    public float x;
    public float y;

    public String text;

    public Color color;

    public int life = 60; // livstid av texten i hur många frames/"uppdateringar" den ska leva
    public int maxLife = 60; //ursprungliga livstiden

    public float ySpeed = -0.5f; //hur stort varje steg är.

    public UI_DamageNumber(float x, float y, String text, Color color) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.color = color;
    }

    public void update() {
        y += ySpeed;
        life--;
    }

    public boolean isDead() {
        return life <= 0;
    }
    
}
