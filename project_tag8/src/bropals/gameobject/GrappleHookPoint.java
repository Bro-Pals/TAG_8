/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bropals.gameobject;

import bropals.graphics.ImageLoader;
import bropals.level.Area;
import java.awt.image.BufferedImage;

/**
 *
 * @author Owner
 */
public class GrappleHookPoint extends GameObject implements MouseInteractable {

    private float interactDistance;
    
    public GrappleHookPoint(float x, float y) {
        super(x, y);
        interactDistance = 50; // from the mouse
    }
    
    @Override
    public BufferedImage getTexture() {
        return ImageLoader.getLoader().getImage("Blocks", 6);
    }

    @Override
    public void interact(GameObject interactee) {
        if (interactee instanceof Creature) {
            ((Creature)interactee).grapple(this);
        }
    }

    @Override
    public float getInteractDistance() {
        return interactDistance; // meh
    }

    @Override
    public void setInteractDistance(float distance) {
        interactDistance = distance;
    }

    @Override
    public float getCreatureInteractDistance() {
        return 200; // how close does the creature have to be to it
    }
    
    public float getCenterX() {
        return getX() + (getTexture().getWidth()/2);
    }
    
    public float getCenterY() {
        return getY() + (getTexture().getHeight()/2);
    }
    
}
