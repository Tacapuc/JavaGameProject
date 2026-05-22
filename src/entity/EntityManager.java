/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.ArrayList;

/**
 *
 * @author ilya.bespalov
 */
public class EntityManager {
    public ArrayList<Entity> entities = new ArrayList();
    
    public void add(Entity entity){
        entities.add(entity);
    }
}
