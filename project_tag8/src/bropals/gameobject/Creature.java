/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bropals.gameobject;

import bropals.level.Area;
import bropals.util.Vector2;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Owner
 */
public abstract class Creature extends GameObject {
    
    private float size;
    private float speed;
    private Vector2 faceDirection;
    private float fieldOfView;
    
    /**
     * A creature that can move around and collide into things
     * @param parent The Area that this creature is inside
     * @param x The creature's x position
     * @param y The creature's y position
     * @param size The creature's radius (All creatures are circles)
     * @param speed How fast this creature moves
     * @param faceDirection A 2D vector representing where the creature faces
     */
    public Creature(Area parent, float x, float y, float size, float speed, Vector2 faceDirection) {
        super(parent, x, y);
        this.size = size;
        this.speed = speed;
        this.faceDirection = faceDirection;
    }
    
    /**
     * Returns the direction it's facing in radians. Based on the unit circle
     * @return The direction it's facing in radians.
     */
    public float getAngleFacing() {
        float angle = (float)Math.atan((double)(faceDirection.getY() / faceDirection.getX())); // opp/adj
        Vector2 uv = faceDirection.getUnitVector();
        if (uv.getX() < 0) {
            angle = (float)(Math.PI - angle); // reflect over y axis
        }
        return angle;
    }
    
}
