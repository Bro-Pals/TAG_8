/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bropals.gameobject.block;

import bropals.gameobject.GameObject;
import bropals.gameobject.Interactable;
import bropals.level.Area;
import java.util.ArrayList;

/**
 *
 * @author Owner
 */
public class HayBale extends Block implements Interactable {
    
    public HayBale(Area parent, float x, float y, float width, float height) {
        super(parent, x, y, width, height);
    }

    @Override
    public void interact(GameObject instance) {
        // player hides in this hay bale
    }

    @Override
    public float getInteractDistance() {
        return 100; // some random number
    }
    
}