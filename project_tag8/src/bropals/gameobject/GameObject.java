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
    private String texture;
    
    /**
     * The basic game object
     * @param x The x position of the GameObject
     * @param y The y position of the GameObject
     */
    public GameObject(float x, float y) {
        this.x = x;
        this.y = y;
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
        if (parent != null || p==null) parent.getObjects().remove(this);
        this.parent = p;
        if (parent!= null) parent.getObjects().add(this);
    }

    public String getTextureString() {
        return texture;
    }

    public void setTextureString(String texture) {
        this.texture = texture;
    }
    
    @Override
    public String toString() {
        String str = "GameObject:\n" + 
        "   X: " + getX() + "\n"+
        "   Y: " + getY() + "\n"+
        "   Texture String: " + getTextureString()+ "\n"+
        "";
        return str;
    }
}
