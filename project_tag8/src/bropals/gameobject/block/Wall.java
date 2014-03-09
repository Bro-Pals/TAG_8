/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bropals.gameobject.block;

import bropals.level.Area;

/**
 * Exactly like a Block only it can't be grappled over.
 * @author Owner
 */
public class Wall extends Block {
    public Wall(float x, float y, float width, float height) {
        super(x, y, width, height);
    }
    
    @Override
    public String toString() {
        String str = "Wall:\n" + 
        "   X: " + getX() + "\n"+
        "   Y: " + getY() + "\n"+
        "   Texture String: " + getTextureString()+ "\n"+
        "   Width: " + getWidth()+ "\n"+
        "   Height: " + getHeight()+ "\n"+
        "";
        return str;
    }
}
