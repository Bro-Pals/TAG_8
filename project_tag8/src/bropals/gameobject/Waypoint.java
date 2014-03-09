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
    
    public Waypoint( float x, float y) {
        super(x, y);
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
    
}
