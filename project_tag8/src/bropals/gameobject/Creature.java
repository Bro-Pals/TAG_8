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
    public Creature(Area parent, float x, float y, float width, float height, float speed, Vector2 faceDirection) {
        super(parent, x, y);
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.moveSpeed = speed;
        this.faceDirection = faceDirection;
        this.grappling = false;
        this.moveVector = new Vector2();
    }
    
    @Override
    public BufferedImage getTexture() {
        return ImageLoader.getLoader().getImage("GameIcon");
    }
    
    public void update() {
        if (getParent() == null) // don't update if there is no parent
            return;
        
        float minInteractableDist = 10000*10000;
        boolean collideX = false, collideY = false;
        for (int i=0; i<getParent().getObjects().size(); i++) {
            boolean canTest = true;
            if (grappling && !(getParent().getObjects().get(i) instanceof Wall)) {
                canTest = false;
            }
            if (canTest && getParent().getObjects().get(i) != this && getParent().getObjects().get(i) instanceof Block && 
                    getParent().getObjects().get(i).getParent() == getParent()) {
                Block curBlock = (Block)(getParent().getObjects().get(i));
                if (this.intersectsVelocityPathX(curBlock)) {
                    boolean valid = true;
                    float moveToX = -100000;
                    if (moveVector.getX() > 0) {
                        moveToX = curBlock.getX() - getWidth(); // left of the block
                        for (int g=0; g<getParent().getObjects().size(); g++) {
                            if (getParent().getObjects().get(g) != this && getParent().getObjects().get(g) instanceof Block) {
                                Block localBlock = (Block)(getParent().getObjects().get(g));
                                if (intersects(moveToX, getY(), localBlock)) {
                                    valid = false;
                                    break;
                                }
                            }
                        }
                    } else if (moveVector.getX() < 0) {
                        moveToX = curBlock.getX() + curBlock.getWidth(); // left of the block
                        for (int g=0; g<getParent().getObjects().size(); g++) {
                            if (getParent().getObjects().get(g) != this && getParent().getObjects().get(g) instanceof Block) {
                                Block localBlock = (Block)(getParent().getObjects().get(g));
                                if (intersects(moveToX, getY(), localBlock)) {
                                    valid = false;
                                    break;
                                }
                            }
                        }
                    }
                    if (valid && moveToX != -100000) {
                        collideX = true;
                        setX(moveToX);
                    }
                }
                if (this.intersectsVelocityPathY(curBlock)) {
                    boolean valid = true;
                    float moveToY = -100000;
                    if (moveVector.getY() > 0) {
                        moveToY = curBlock.getY() - getHeight(); // left of the block
                        for (int g=0; g<getParent().getObjects().size(); g++) {
                            if (getParent().getObjects().get(g) != this && getParent().getObjects().get(g) instanceof Block) {
                                Block localBlock = (Block)(getParent().getObjects().get(g));
                                if (intersects(getX(), moveToY, localBlock)) {
                                    valid = false;
                                    break;
                                }
                            }
                        }
                    } else if (moveVector.getY() < 0) {
                        moveToY = curBlock.getY() + curBlock.getHeight(); // left of the block
                        for (int g=0; g<getParent().getObjects().size(); g++) {
                            if (getParent().getObjects().get(g) != this && getParent().getObjects().get(g) instanceof Block) {
                                Block localBlock = (Block)(getParent().getObjects().get(g));
                                if (intersects(getX(), moveToY, localBlock)) {
                                    valid = false;
                                    break;
                                }
                            }
                        }
                    }
                    if (valid && moveToY != -100000) {
                        collideY = true;
                        setY(moveToY);
                    }
                }
            }

            if (getParent().getObjects().get(i) instanceof Interactable) {
                Interactable in = (Interactable)getParent().getObjects().get(i);
                float distanceFrom = 0;
                if (in instanceof Creature) {
                    Creature c = (Creature) in;
                    distanceFrom = (c.getCenterX() - getCenterX())*(c.getCenterX() - getCenterX()) + (c.getCenterY() - getCenterY())*(c.getCenterY() - getCenterY());
                } else if(in instanceof Block) {
                    Block b = (Block) in;
                    distanceFrom = (b.getCenterX() - getCenterX())*(b.getCenterX() - getCenterX()) + (b.getCenterY() - getCenterY())*(b.getCenterY() - getCenterY());
                }
                if (distanceFrom < minInteractableDist && distanceFrom < (in.getInteractDistance())*(in.getInteractDistance())) {
                    selectedInteractable = in;
                    minInteractableDist = distanceFrom;
                }
            }
        }

        if (!collideX) setX((float)(getX() + (moveVector.getX() * moveSpeed)));
        if (!collideY) setY((float)(getY() + (moveVector.getY() * moveSpeed)));
    }
    
    public boolean intersects(float x, float y, Block other) {
        return new Rectangle2D.Double(x, y, getWidth(), getHeight())
                .intersects(other.getX(), other.getY(), other.getWidth(), other.getHeight());
    }
    
    public boolean intersectsVelocityPathY(Block other) {
        if (moveVector.getY() >= 0) {
            return new Rectangle2D.Double(getX(), getY(), getWidth(), getHeight() + moveVector.getY())
                .intersects(other.getX(), other.getY(), other.getWidth(), other.getHeight());
        }
        return new Rectangle2D.Double(getX(), getY() + moveVector.getY(), getWidth(), getHeight() + Math.abs(moveVector.getY()))
                .intersects(other.getX(), other.getY(), other.getWidth(), other.getHeight());
    }
    
    public boolean intersectsVelocityPathX(Block other) {
        if (moveVector.getX() >= 0) {
            return new Rectangle2D.Double(getX(), getY(), getWidth() + moveVector.getX(), getHeight())
                .intersects(other.getX(), other.getY(), other.getWidth(), other.getHeight());
        }
        return new Rectangle2D.Double(getX() + moveVector.getX(), getY(), getWidth() + Math.abs(moveVector.getX()), getHeight())
                .intersects(other.getX(), other.getY(), other.getWidth(), other.getHeight());
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
    
    public float getCenterX() {
        return (getX() - (width/2));
    }
    
    public float getCenterY() {
        return (getY() - (height/2));
    }
}
