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
public class TeleportDoor extends Block implements Interactable {
        
    private int targetAreaID;
    private float interactDistance;
    private float xPlayerPos, yPlayerPos;
    
    public TeleportDoor(float x, float y, float width, float height, 
        int targetAreaID, float xPlayerPos, float yPlayerPos) {
        super(x, y, width, height);
        this.targetAreaID = targetAreaID;
        this.xPlayerPos = xPlayerPos;
        this.yPlayerPos = yPlayerPos;
        interactDistance = 120;
    }

    @Override
    public void interact(GameObject instance) {
        //
    }

    @Override
    public float getInteractDistance() {
        return interactDistance; // random number
    }

    // getters and setters
    
    public int getTargetAreaID() {
        return targetAreaID;
    }
    
    public void setTargetAreaID(int id) {
        this.targetAreaID = id;
    }

    public float getXPlayerPos() {
        return xPlayerPos;
    }

    public float getYPlayerPos() {
        return yPlayerPos;
    }
    
    public void setXPlayerPos(float xPos) {
        this.xPlayerPos = xPos;
    }
    
    public void setYPlayerPos(float yPos) {
        this.yPlayerPos = yPos;
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

    @Override
    public void setInteractDistance(float distance) {
        interactDistance = distance;
    }
}
