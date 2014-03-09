/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bropals.gameobject.block;

import bropals.gameobject.GameObject;
import bropals.gameobject.Interactable;
import bropals.level.Area;
import bropals.level.AvacadoManager;

/**
 *
 * @author Owner
 */
public class Avacado extends Block implements Interactable {

    // keep track of where it was
    private int id;
    private int roomId;
    
    public Avacado(float x, float y, float width, float height, int roomId) {
        super(x, y, width, height);
        this.id = AvacadoManager.get().getAvacadosCollected().size() + AvacadoManager.get().getAvacadosInWorld().size();
        this.roomId = roomId;
        // add this avacado to the world
        AvacadoManager.get().getAvacadosInWorld().add(this);
    }
    
    public int getId() {
        return id;
    }
    
    @Override
    public void interact(GameObject instance) {
        AvacadoManager.get().pickUpAvacado(this);
    }

    @Override
    public float getInteractDistance() {
        return 100; // some random number
    }
    
    public void returnToSpawn() {
        // return to where it's spawn location is
    }
    
    @Override
    public String toString() {
        String str = "Avacado:\n" + 
        "   X: " + getX() + "\n"+
        "   Y: " + getY() + "\n"+
        "   Texture String: " + getTextureString()+ "\n"+
        "   Width: " + getWidth()+ "\n"+
        "   Height: " + getHeight()+ "\n"+
        "";
        return str;
    }
}
