/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bropals.gameobject.block;

import bropals.gameobject.GameObject;
import bropals.gameobject.Interactable;
import bropals.gameobject.Player;
import bropals.level.Area;
import java.util.ArrayList;

/**
 *
 * @author Owner
 */
public class HayBale extends Block implements Interactable {
    
    public HayBale(float x, float y, float width, float height) {
        super( x, y, width, height);
    }

    @Override
    public void interact(GameObject instance) {
        if (instance instanceof Player) {
            ((Player)instance).setHiding(!((Player)instance).isHiding());
        }
    }

    @Override
    public float getInteractDistance() {
        return 100; // some random number
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
}
