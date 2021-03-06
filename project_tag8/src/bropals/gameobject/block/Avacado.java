/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bropals.gameobject.block;

import bropals.gameobject.GameObject;
import bropals.gameobject.Interactable;
import bropals.graphics.ImageLoader;
import bropals.level.Area;
import bropals.level.AvacadoManager;
import java.awt.image.BufferedImage;

/**
 *
 * @author Owner
 */
public class Avacado extends Block implements Interactable {
    
    public static int avacadosMade = 0;
    
    // keep track of where it was
    private int id;
    private int roomId;
    private boolean collected;
    private float interactDistance;
    
    public Avacado(float x, float y, float width, float height, int roomId) {
        super(x, y, width, height);
        avacadosMade++;
        this.id = avacadosMade;
        this.roomId = roomId;
        interactDistance = 80;
        AvacadoManager.get().addAvacadoToWorld(this);
        // add this avacado to the world
        collected = false;
    }
    
    @Override
    public BufferedImage getTexture() {
        return ImageLoader.getLoader().getImage("Blocks", 5);
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
        return interactDistance; // some random number
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

    @Override
    public void setInteractDistance(float distance) {
        interactDistance = distance;
    }
    
    public void setCollected(boolean c) {
        collected = c;
    }
    
    public boolean isCollected() {
        return collected;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}
