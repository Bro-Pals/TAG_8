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
public class TeleportDoor extends Block implements Interactable {
        
    private int targetAreaID;
    private float xPlayerPos, yPlayerPos;
    
    public TeleportDoor(float x, float y, float width, float height, 
        int targetAreaID, float xPlayerPos, float yPlayerPos) {
        super(x, y, width, height);
        this.targetAreaID = targetAreaID;
        this.xPlayerPos = xPlayerPos;
        this.yPlayerPos = yPlayerPos;
    }

    @Override
    public void interact(GameObject instance) {
        // teleport the player to the given location
    }

    @Override
    public float getInteractDistance() {
        return 100; // random number
    }

    // getters and setters
    
    public int getTargetAreaID() {
        return targetAreaID;
    }

    public float getxPlayerPos() {
        return xPlayerPos;
    }

    public float getyPlayerPos() {
        return yPlayerPos;
    }
    
    @Override
    public String toString() {
        String str = "Teleport Door:\n" + 
        "   X: " + getX() + "\n"+
        "   Y: " + getY() + "\n"+
        "   Texture String: " + getTextureString()+ "\n"+
        "   Width: " + getWidth()+ "\n"+
        "   Height: " + getHeight()+ "\n"+
        "";
        return str;
    }
}
