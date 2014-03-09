/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals.gameobject;

import bropals.level.Area;
import bropals.util.Vector2;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Jonathon
 */
public class Waypoint extends GameObject {

    private Vector2 faceDirection;
    private int delay;
    
    public Waypoint(float x, float y) {
        super(x, y);
        this.delay = 5; // default
    }

    public Waypoint(float x, float y, int delay) {
        super(x, y);
        this.delay = delay;
    }
    
    @Override
    public BufferedImage getTexture() {
        return null;
    }

    public Vector2 getFaceDirection() {
        return faceDirection;
    }

    public void setFaceDirection(Vector2 faceDirection) {
        this.faceDirection = faceDirection;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
    
    @Override
    public String toString() {
        return "Waypoint:\n" +
        "   X: " + getX() + "\n"+
        "   Y: " + getY() + "\n"+
        "   Wait time: " + getDelay() + "";
    }
}
