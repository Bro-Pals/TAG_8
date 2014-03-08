/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bropals.gameobject;

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
    private Vector2 faceDirection;
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
        this.faceDirection = faceDirection;
        this.grappling = false;
    }
    
    @Override
    public BufferedImage getTexture() {
        return ImageLoader.getLoader().getImage("GameIcon");
    }
    
    public void update() {
        selectedInteractable = null;
        
        float moveSpeed = speed;
        if (grappling && grappleDistanceLeft > 0) {
            grappleDistanceLeft = grappleDistanceLeft - grappleSpeed;
            moveSpeed = grappleSpeed; // set the speed to the grappleSpeed if grappling
        }
        
        float minInteractDistance = 10000*10000; // some random large number
        
        for (GameObject obj: getParent().getObjects()) {
            if (obj instanceof Block) {
                Block block = (Block) obj;
                if (grappling && !(block instanceof Wall)) {
                    continue; // if it's not a wall the player can go over it while grappling
                } else if (block instanceof NormalDoor) {
                    if (((NormalDoor)block).isOpen()) {
                        continue; // open doors aren't collidable
                    }
                }
                float leftBound = block.getX();
                float rightBound = block.getX() + block.getWidth();
                float topBound = block.getY();
                float bottomBound = block.getY() + block.getHeight();
                
                float velX = (float)(faceDirection.getX() * moveSpeed);
                float velY = (float)(faceDirection.getY() * moveSpeed);
                if ((getY() + velY + radius > topBound && getY() + velY - radius < bottomBound && // circle collides on rectangle
                        getX() + velX + radius > leftBound && getX() + velX - radius < rightBound) ||
                        (getX() + velX > leftBound && getX() + velX < rightBound &&  // circle center is inside rectangle
                        getY() + velY > topBound && getY() + velY < bottomBound)) {
                    this.speed = 0; // stop moving if you collide!
                    // stop grappling if possible
                    grappling = false;
                    grappleDistanceLeft = 0;
                    //if mostly moving horizontally then clip it horizontally
                    if (faceDirection.getX() > faceDirection.getY() && getX() - block.getCenterX() > 0) {
                        setX(rightBound + radius);
                    } else if (faceDirection.getX() > faceDirection.getY() && getX() - block.getCenterX() <= 0) {
                        setX(leftBound - radius);
                    }
                    
                    //if mostly moving vertically then clip it vertically
                    if (faceDirection.getX() <= faceDirection.getY() && getY() - block.getCenterY() > 0) {
                        setY(bottomBound + radius);
                    } else if (faceDirection.getX() <= faceDirection.getY() && getY() - block.getCenterY() <= 0) {
                        setY(topBound - radius);
                    }
                }
            }
            
            if (obj instanceof Interactable) {
                float interactDist = (((Interactable)obj).getInteractDistance());
                float distanceFrom;
                if (obj instanceof Block) {
                    Block b = (Block) obj;
                    distanceFrom = ((b.getCenterX() - getX())*(b.getCenterX() - getX())) + 
                            ((b.getCenterY() - getY())*(b.getCenterY() - getY()));
                } else {
                    distanceFrom = ((obj.getX() - getX())*(obj.getX() - getX())) + ((obj.getY() - getY())*(obj.getY() - getY()));
                }
                // if the player is close enoough to the object and it's the smallest interact distance
                if (interactDist * interactDist < distanceFrom && minInteractDistance > distanceFrom) {
                    selectedInteractable = (Interactable) obj; // make it the selected interactable
                    minInteractDistance = distanceFrom;
                }
            }
        }
        
        setX(getX() + (float)(faceDirection.getX() * moveSpeed));
        setY(getY() + (float)(faceDirection.getY() * moveSpeed));
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
        float angle = (float)Math.atan2((double)(faceDirection.getY()), -(double)(faceDirection.getX())); // opp/adj
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
}
