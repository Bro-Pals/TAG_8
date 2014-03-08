/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bropals.gameobject;

import bropals.level.Area;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author Owner
 */
public abstract class GameObject {
 
    private float x, y;
    private Area parent;
    
    /**
     * The basic game object
     * @param parent The ArrayList this object will go into
     * @param x The x position of the GameObject
     * @param y The y position of the GameObject
     */
    public GameObject(Area parent, float x, float y) {
        this.x = x;
        this.y = y;
        this.parent = parent;

    }
    
    public abstract BufferedImage getTexture();
    
    /**
     * Used for level editors. 
     * @return The color that represents this GameObject inside a level image
     */
    public static Color getColorID() {
        return new Color(0, 0, 0); // black is the default
    }
    
    // Getters and Setters
    
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Area getParent() {
        return parent;
    }
    
    public void setParent(Area p) {
        this.parent = p;
        parent.getObjects().add(this);
    }
}
