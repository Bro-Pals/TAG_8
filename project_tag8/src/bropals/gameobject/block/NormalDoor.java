/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bropals.gameobject.block;

import bropals.gameobject.Creature;
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
public class NormalDoor extends Block implements Interactable {
    
    private float interactDistance;
    private boolean collidable;
    private Object GameObject;
    
    public void setOpened(boolean b) { this.collidable = b; }
    private BufferedImage openImage;
    public NormalDoor(float x, float y, float width, float height) {
        super( x, y, width, height);
        interactDistance = 150;
        collidable = true;
    }

    @Override
    public void interact(GameObject instance) {
        if (instance instanceof Creature) {
            if (!((Creature)instance).intersects(this)) {
                collidable = !collidable; // toggle the door open or close
            }
        }
    }

    @Override
    public BufferedImage getTexture() {
        return (collidable) ? getDrawImage() : openImage;
    }
    
    @Override
    public void updateDrawImage() {
        openImage = ImageLoader.getLoader().getRepeatingTexture(ImageLoader.getLoader().getImage("Blocks", 2), (int)getWidth(), (int)getHeight());
        setDrawImage(ImageLoader.getLoader().getRepeatingTexture(ImageLoader.getLoader().getImage("Blocks", 1), (int)getWidth(), (int)getHeight()));
    }
    
    @Override
    public float getInteractDistance() {
        return interactDistance; // random number
    }

    public boolean isOpen() {
        return !collidable;
    }
    
    @Override
    public String toString() {
        String str = "Block:\n" + 
        "   X: " + getX() + "\n"+
        "   Y: " + getY() + "\n"+
        "   Texture String: " + getTextureString()+ "\n"+
        "   Width: " + getWidth()+ "\n"+
        "   Height: " + getHeight()+ "\n"+
        "   Open: " + isOpen()+ "\n"+
        "";
        return str;
    }

    @Override
    public void setInteractDistance(float distance) {
        interactDistance = distance;
    }
}
