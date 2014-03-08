/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bropals.gameobject.block;

import bropals.gameobject.GameObject;
import bropals.level.Area;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Owner
 */
public class Block extends GameObject {
    
    private float width, height;
    
    public Block(Area parent, float x, float y, float width, float height) {
        super(parent, x, y);
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
    
}
