/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bropals.gameobject;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Owner
 */
public abstract class GameObject {
 
    private float x, y;
    private ArrayList<GameObject> parent;
    
    /**
     * The basic game object
     * @param parent The ArrayList this object will go into
     * @param x The x position of the GameObject
     * @param y The y position of the GameObject
     */
    public GameObject(ArrayList<GameObject> parent, float x, float y) {
        this.x = x;
        this.y = y;
        this.parent = parent;
        
        parent.add(this);
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

    public ArrayList<GameObject> getParent() {
        return parent;
    }
}
