/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bropals.gameobject.block;

import bropals.gameobject.GameObject;
import bropals.gameobject.Interactable;
import bropals.level.Area;
import java.util.ArrayList;

/**
 *
 * @author Owner
 */
public class NormalDoor extends Block implements Interactable {
    
    private boolean collidable;
    
    public NormalDoor(float x, float y, float width, float height) {
        super( x, y, width, height);
    }

    @Override
    public void interact(GameObject instance) {
        collidable = !collidable; // toggle the door open or close
    }

    @Override
    public float getInteractDistance() {
        return 150; // random number
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
}
