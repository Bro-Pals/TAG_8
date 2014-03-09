/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bropals.gameobject.block;

import bropals.gameobject.GameObject;
import bropals.level.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Owner
 */
public class Block extends GameObject {
    
    private float width, height;
    
    public Block(float x, float y, float width, float height) {
        super(x, y);
        this.width = width;
        this.height = height;
    }
    
    @Override
    public BufferedImage getTexture() {
        return null; // no image right now :(
    }
    
    // getters and settesr

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
    
    public Rectangle2D.Float getRectangle2D() {
        return new Rectangle2D.Float(getX(), getY(), getWidth(), getHeight());
    }
    
    public float getCenterX() {
        return (getX() + (getWidth()/2));
    }
    
    public float getCenterY() {
        return (getY() + (getHeight()/2));
    }
    
    @Override
    public String toString() {
        String str = "Block:\n" + 
        "   X: " + getX() + "\n"+
        "   Y: " + getY() + "\n"+
        "   Texture String: " + getTextureString()+ "\n"+
        "   Width: " + getWidth()+ "\n"+
        "   Height: " + getHeight()+ "\n"+
        "";
        return str;
    }
}
