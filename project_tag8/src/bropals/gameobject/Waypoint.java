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

    private float xPosition, yPosition;
    private Vector2 faceDirection;
    
    public Waypoint(Area parent, float x, float y) {
        super(parent, x, y);
    }

    @Override
    public BufferedImage getTexture() {
        return null;
    }

    public float getXPosition() {
        return xPosition;
    }

    public void setXPosition(float xPosition) {
        this.xPosition = xPosition;
    }

    public float getYPosition() {
        return yPosition;
    }

    public void setYPosition(float yPosition) {
        this.yPosition = yPosition;
    }

    public Vector2 getFaceDirection() {
        return faceDirection;
    }

    public void setFaceDirection(Vector2 faceDirection) {
        this.faceDirection = faceDirection;
    }
    
}
