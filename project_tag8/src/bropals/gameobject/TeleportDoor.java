/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bropals.gameobject;

import java.util.ArrayList;

/**
 *
 * @author Owner
 */
public class TeleportDoor extends Block implements Interactable {
        
    private int targetAreaID;
    private float xPlayerPos, yPlayerPos;
    
    public TeleportDoor(ArrayList<GameObject> parent, float x, float y, float width, float height, 
            int targetAreaID, float xPlayerPos, float yPlayerPos) {
        super(parent, x, y, width, height);
        this.targetAreaID = targetAreaID;
        this.xPlayerPos = xPlayerPos;
        this.yPlayerPos = yPlayerPos;
    }

    @Override
    public void interact() {
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
}
