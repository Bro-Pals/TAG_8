/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bropals.gameobject;

import bropals.gameobject.block.Block;
import bropals.level.Area;
import bropals.util.Vector2;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Owner
 */
public abstract class Creature extends GameObject {
    
    private float radius;
    private float speed;
    private Vector2 faceDirection;
    private float fieldOfView;
    
    /**
     * A creature that can move around and collide into things
     * @param parent The Area that this creature is inside
     * @param x The creature's x position
     * @param y The creature's y position
     * @param radius The creature's radius (All creatures are circles)
     * @param speed How fast this creature moves
     * @param faceDirection A 2D vector representing where the creature faces
     */
    public Creature(Area parent, float x, float y, float radius, float speed, Vector2 faceDirection) {
        super(parent, x, y);
        this.radius = radius;
        this.speed = speed;
        this.faceDirection = faceDirection;
    }
    
    public void update() {
        for (GameObject obj: getParent().getObjects()) {
            if (obj instanceof Block) {
                Block block = (Block) obj;
                float leftBound = block.getX();
                float rightBound = block.getX() + block.getWidth();
                float topBound = block.getY();
                float bottomBound = block.getY() + block.getHeight();
                
                float velX = (float)(faceDirection.getX() * speed);
                float velY = (float)(faceDirection.getY() * speed);
                if ((getY() + velY + radius > topBound && getY() + velY - radius < bottomBound && // circle collides on rectangle
                        getX() + velX + radius > leftBound && getX() + velX - radius < rightBound) ||
                        (getX() + velX > leftBound && getX() + velX < rightBound &&  // circle center is inside rectangle
                        getY() + velY > topBound && getY() + velY < bottomBound)) {
                    this.speed = 0; // stop moving if you collide!
                }
            }
        }
        
        setX(getX() + (float)(faceDirection.getX() * speed));
        setY(getY() + (float)(faceDirection.getY() * speed));
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
    
    // getters and setters
    public float getRadius() {
        return radius;
    }

    public float getSpeed() {
        return speed;
    }

    public Vector2 getFaceDirection() {
        return faceDirection;
    }

    public void setFaceDirection(Vector2 faceDirection) {
        this.faceDirection = faceDirection;
    }

    public float getFieldOfView() {
        return fieldOfView;
    }

    public void setFieldOfView(float fieldOfView) {
        this.fieldOfView = fieldOfView;
    }
}
