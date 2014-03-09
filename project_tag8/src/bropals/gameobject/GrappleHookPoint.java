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

    public GrappleHookPoint(float x, float y) {
        super(x, y);
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
        return 150; // meh
    }
    
}
