/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bropals.gameobject.block;

import bropals.gameobject.GameObject;
import bropals.gameobject.Interactable;
import bropals.level.Area;
import bropals.level.AvacadoManager;
import java.util.ArrayList;

/**
 *
 * @author Owner
 */
public class AvacadoBin extends Block implements Interactable {
    
    public AvacadoBin(float x, float y, float width, float height) {
        super( x, y, width, height);
    }

    @Override
    public void interact(GameObject instance) {
        AvacadoManager.get().collectAvacados();
    }

    @Override
    public float getInteractDistance() {
        return 100; // some random number
    }
}
