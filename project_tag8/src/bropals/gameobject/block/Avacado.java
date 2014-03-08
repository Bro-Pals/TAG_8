/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bropals.gameobject.block;

import bropals.gameobject.GameObject;
import bropals.gameobject.Interactable;
import bropals.level.Area;

/**
 *
 * @author Owner
 */
public class Avacado extends Block implements Interactable {

    public Avacado(Area parent, float x, float y, float width, float height) {
        super(parent, x, y, width, height);
    }

    @Override
    public void interact(GameObject instance) {
        // player pics up te avacado
    }

    @Override
    public float getInteractDistance() {
        return 100; // some random number
    }
}
