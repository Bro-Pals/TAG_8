/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bropals.gameobject;

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
        interactDistance = 200;
    }
    
    @Override
    public BufferedImage getTexture() {
        return null;
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
    
}
