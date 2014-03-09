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
import bropals.util.Direction;
import bropals.util.Vector2;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Owner
 */
public abstract class Creature extends GameObject {
    
    private float width, height;
    private float speed;
    private final float MOVE_SPEED;
    private Vector2 faceDirection;
    private Vector2 moveVector;
    private float fieldOfView;
    private Interactable selectedInteractable;
    
    private boolean grappling;
    private boolean hiding;
    private float grappleDistanceLeft;
    private GrappleHookPoint hookUsing;
    private Vector2 grappleVector;
    float GRAPPLE_SPEED = 25; // random number
    
    /**
     * A creature that can move around and collide into things
     * @param parent The Area that this creature is inside
     * @param x The creature's x position
     * @param y The creature's y position
     * @param radius The creature's radius (All creatures are circles)
     * @param speed How fast this creature moves
     * @param faceDirection A 2D vector representing where the creature faces
     */
    public Creature(float x, float y, float width, float height, float speed, Vector2 faceDirection) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.MOVE_SPEED = speed;
        this.faceDirection = faceDirection;
        this.grappling = false;
        this.moveVector = new Vector2();
        this.hookUsing = null;
    }
    
    @Override
    public BufferedImage getTexture() {
        return ImageLoader.getLoader().getImage("GameIcon");
    }
    
    public void update() {
        if (getParent() == null) // don't update if there is no parent
            return;
        
        selectedInteractable = null;
        
        float minInteractableDist = 10000*10000;
        boolean collide = false;
        
        if (grappling && grappleVector!= null && grappleDistanceLeft > 0) {
            moveVector = grappleVector.clone();
            speed = GRAPPLE_SPEED;
            grappleDistanceLeft = grappleDistanceLeft - GRAPPLE_SPEED;
        } else {
            speed = MOVE_SPEED;
            grappling = false;
            grappleDistanceLeft = 0;
        }
        
        if (!hiding) { // you don't move when you're hiding
            setX((float)(getX() + (moveVector.getX() * speed)));
            setY((float)(getY() + (moveVector.getY() * speed)));
        }
        
        for (int i=0; i<getParent().getObjects().size(); i++) {
            boolean canTest = true;
            if (grappling && !(getParent().getObjects().get(i) instanceof Wall)) {
                canTest = false;
            } else if(getParent().getObjects().get(i) instanceof NormalDoor &&
                    ((NormalDoor)getParent().getObjects().get(i)).isOpen()) {
                canTest = false;
            }
            if (canTest && getParent().getObjects().get(i) != this && getParent().getObjects().get(i) instanceof Block && 
                    getParent().getObjects().get(i).getParent() == getParent()) {
                Block curBlock = (Block)(getParent().getObjects().get(i));
                
                //x axis
                float xMax1 = getX() + getWidth();
                float xMin1 = getX();
                float xMax2 = curBlock.getX() + curBlock.getWidth();
                float xMin2 = curBlock.getX();
                
                float smallestMaxX = xMax1 < xMax2 ? xMax1 : xMax2;
                float largestMinX = xMin1 > xMin2 ? xMin1 : xMin2;
                
                //y axis
                float yMax1 = getY() + getHeight();
                float yMin1 = getY();
                float yMax2 = curBlock.getY() + curBlock.getHeight();
                float yMin2 = curBlock.getY();
                
                float smallestMaxY = yMax1 < yMax2 ? yMax1 : yMax2;
                float largestMinY = yMin1 > yMin2 ? yMin1 : yMin2;
                
                float xPenetration = smallestMaxX - largestMinX;
                float yPenetration = smallestMaxY - largestMinY;
                if (xPenetration > 0 && yPenetration > 0) {
                    collide = true;
                    if (Math.abs(xPenetration) < Math.abs(yPenetration)) {
                        if (getCenterX() > curBlock.getX() + curBlock.getWidth()) {
                            setX(curBlock.getX() + curBlock.getWidth());
                        } else {
                            setX(curBlock.getX() - getWidth());   
                        }
                        moveVector.setX(0);
                    } else {
                        if (getCenterY() > curBlock.getY() + curBlock.getHeight()) {
                            setY(curBlock.getY() + curBlock.getHeight());
                        } else {
                            setY(curBlock.getY() - getHeight());   
                        }
                        moveVector.setY(0);
                    }
                }
            }

            if (getParent().getObjects().get(i) instanceof Interactable) {
                Interactable in = (Interactable)getParent().getObjects().get(i);
                float distanceFrom = 1020101010;
                if (in instanceof Creature) {
                    Creature c = (Creature) in;
                    float xDiff = c.getCenterX() - getCenterX();
                    float yDiff = c.getCenterY() - getCenterY();
                    distanceFrom = (xDiff*xDiff) + (yDiff*yDiff);
                } else if(in instanceof Block) {
                    Block b = (Block) in;
                    float xDiff = b.getCenterX() - getCenterX();
                    float yDiff = b.getCenterY() - getCenterY();
                    distanceFrom = (xDiff*xDiff) + (yDiff*yDiff);
                }
                if (distanceFrom < minInteractableDist && distanceFrom < (in.getInteractDistance())*(in.getInteractDistance())) {
                    selectedInteractable = in;
                    minInteractableDist = distanceFrom;
                }
            }
        }
    }
    
    public boolean intersects(Block other) {
        float xMax1 = getX() + getWidth();
        float xMin1 = getX();
        float xMax2 = other.getX() + other.getWidth();
        float xMin2 = other.getX();

        float smallestMaxX = xMax1 < xMax2 ? xMax1 : xMax2;
        float largestMinX = xMin1 > xMin2 ? xMin1 : xMin2;

        //y axis
        float yMax1 = getY() + getHeight();
        float yMin1 = getY();
        float yMax2 = other.getY() + other.getHeight();
        float yMin2 = other.getY();

        float smallestMaxY = yMax1 < yMax2 ? yMax1 : yMax2;
        float largestMinY = yMin1 > yMin2 ? yMin1 : yMin2;

        float xPenetration = smallestMaxX - largestMinX;
        float yPenetration = smallestMaxY - largestMinY;
        return (xPenetration > 0 && yPenetration > 0);
    }

    public void moveTowardsPoint(Waypoint point) {
        moveTowardsPoint(point.getX(), point.getY());
    }
    
    public void moveTowardsPoint(float x, float y) {
        Vector2 mVect = new Vector2(x - getX(), y - getY());
        faceDirection = mVect.getUnitVector();
        moveVector = faceDirection.clone();
    }
    
    public void grapple(GrappleHookPoint ghp) {
        grappleVector = new Vector2(ghp.getX() - getX(), ghp.getY() - getY());
        faceDirection = grappleVector.getUnitVector();
        grappleDistanceLeft = (float)(2 * grappleVector.getMagnitude());
        grappling = true;
        hookUsing = ghp;
        grappleVector = grappleVector.getUnitVector();
        Debugger.print("We are now grappling!", Debugger.INFO);
    }
    
    /**
     * Returns the direction it's facing in radians. Based on the unit circle
     * @return The direction it's facing in radians.
     */
    public float getAngleFacing() {
        float angle = (float)Math.atan2(faceDirection.getX(), -faceDirection.getY());
        return angle;
    }
    
    // getters and setters

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
        return MOVE_SPEED;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setMoveVector(Vector2 v) {
        this.moveVector = v;
    }
    
    public Vector2 getMoveVector() {
        return moveVector;
    }
    
    public float getCenterX() {
        return (getX() + (width/2));
    }
    
    public float getCenterY() {
        return (getY() + (height/2));
    }
    
    public void setSelectedInteractable(Interactable in) {
        this.selectedInteractable = in;
    }
    
    public GrappleHookPoint getHookUsing() {
        return hookUsing;
    }
    
    public boolean isGrappling() {
        return grappling;
    }
}
