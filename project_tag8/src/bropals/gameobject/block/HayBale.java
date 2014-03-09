/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bropals.gameobject.block;

import bropals.gameobject.GameObject;
import bropals.gameobject.Interactable;
import bropals.gameobject.Player;
import bropals.graphics.ImageLoader;
import bropals.level.Area;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Owner
 */
public class HayBale extends Block implements Interactable {
    
    private float interactDistance;
    private boolean hidingPlayer = false;
    
    public HayBale(float x, float y, float width, float height) {
        super( x, y, width, height);
        interactDistance = 90;
    }

    @Override
    public void interact(GameObject instance) {
        if (instance instanceof Player) {
            ((Player)instance).setHiding(!((Player)instance).isHiding());
            hidingPlayer = ((Player)instance).isHiding();
        }
    }

    @Override
    public BufferedImage getTexture() {
        if (hidingPlayer) {
            return ImageLoader.getLoader().getImage("Blocks", 4);
        } // else
        return ImageLoader.getLoader().getImage("Blocks", 3);
    }
    
    @Override
    public float getInteractDistance() {
        return interactDistance; // some random number
    }
    
    @Override
    public String toString() {
        String str = "Hay Bale:\n" + 
        "   X: " + getX() + "\n"+
        "   Y: " + getY() + "\n"+
        "   Texture String: " + getTextureString()+ "\n"+
        "   Width: " + getWidth()+ "\n"+
        "   Height: " + getHeight()+ "\n"+
        "";
        return str;
    }

    @Override
    public void setInteractDistance(float distance) {
        interactDistance = distance;
    }
}
