/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bropals.gameobject;

import bropals.debug.Debugger;
import bropals.gameobject.block.Block;
import bropals.gameobject.block.NormalDoor;
import bropals.gameobject.block.Wall;
import bropals.graphics.ImageLoader;
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
    private final float moveSpeed;
    private Vector2 faceDirection;
    private Vector2 moveVector;
    private float fieldOfView;
    private Interactable selectedInteractable;
    
    private boolean grappling;
    private boolean hiding;
    private float grappleDistanceLeft;
    float grappleSpeed = 15; // random number
    
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
        this.moveSpeed = speed;
        this.faceDirection = faceDirection;
        this.grappling = false;
    }
    
    @Override
    public BufferedImage getTexture() {
        return ImageLoader.getLoader().getImage("GameIcon");
    }
    
    public void update() {
        
    }
    
    public boolean intersects(Block block, float centerX, float centerY) {
        float leftBound = block.getX();
        float rightBound = block.getX() + block.getWidth();
        float topBound = block.getY();
        float bottomBound = block.getY() + block.getHeight();

        float velX = (float)(faceDirection.getX() * moveSpeed);
        float velY = (float)(faceDirection.getY() * moveSpeed);
        return ((centerY + velY + radius > topBound && centerY + velY - radius < bottomBound && // circle collides on rectangle
                        centerX + velX + radius > leftBound && centerX + velX - radius < rightBound) ||
                        (centerX + velX > leftBound && centerX + velX < rightBound &&  // circle center is inside rectangle
                        centerY + velY > topBound && centerY + velY < bottomBound));
    }
    
        public boolean intersectsX(Block block, float centerX, float centerY) {
        float leftBound = block.getX();
        float rightBound = block.getX() + block.getWidth();
        float topBound = block.getY();
        float bottomBound = block.getY() + block.getHeight();

        float velX = (float)(faceDirection.getX() * moveSpeed);
        float velY = 0;
        return ((centerY + velY + radius > topBound && centerY + velY - radius < bottomBound && // circle collides on rectangle
                        centerX + velX + radius > leftBound && centerX + velX - radius < rightBound) ||
                        (centerX + velX > leftBound && centerX + velX < rightBound &&  // circle center is inside rectangle
                        centerY + velY > topBound && centerY + velY < bottomBound));
    }
        
    public boolean intersectsY(Block block, float centerX, float centerY) {
        float leftBound = block.getX();
        float rightBound = block.getX() + block.getWidth();
        float topBound = block.getY();
        float bottomBound = block.getY() + block.getHeight();

        float velX = 0;
        float velY = (float)(faceDirection.getY() * moveSpeed);
        return ((centerY + velY + radius > topBound && centerY + velY - radius < bottomBound && // circle collides on rectangle
                        centerX + velX + radius > leftBound && centerX + velX - radius < rightBound) ||
                        (centerX + velX > leftBound && centerX + velX < rightBound &&  // circle center is inside rectangle
                        centerY + velY > topBound && centerY + velY < bottomBound));
    }
    
    public void grapple(GrappleHookPoint ghp) {
        Vector2 grappleVector = new Vector2(ghp.getX() - getX(), ghp.getY() - getY());
        faceDirection = grappleVector.getUnitVector();
        grappleDistanceLeft = (float)(2 * grappleVector.getMagnitude());
        grappling = true;
    }
    
    /**
     * Returns the direction it's facing in radians. Based on the unit circle
     * @return The direction it's facing in radians.
     */
    public float getAngleFacing() {
        float angle = 0;
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

    public Interactable getSelectedInteractable() {
        return selectedInteractable;
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

    public boolean isHiding() {
        return hiding;
    }

    public void setHiding(boolean hiding) {
        this.hiding = hiding;
    }
    
    public void setSpeed(float s) {
        this.speed = s;
    }
    
    public float getMoveSpeed() {
        return moveSpeed;
    }
}
