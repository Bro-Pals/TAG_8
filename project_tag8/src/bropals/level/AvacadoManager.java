/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bropals.level;

import bropals.debug.Debugger;
import bropals.gameobject.block.Avacado;
import java.util.ArrayList;

/**
 *
 * @author Owner
 */
public class AvacadoManager {
    
    private static final AvacadoManager avacadoManager = new AvacadoManager();
    
    private ArrayList<Avacado> avacadosInWorld; // avacados to be collected
    private ArrayList<Avacado> avacadosCollected; // avacados already collected
    private ArrayList<Avacado> avacadoPouch; // avacados the player has on him
    
    public AvacadoManager() {
        avacadosCollected = new ArrayList<>();
        avacadosInWorld = new ArrayList<>();
        avacadoPouch = new ArrayList<>();
    }

    public void addAvacadoToWorld(Avacado a) {
        avacadosInWorld.add(a);
    }
    
    public int getAvacadosInPouchNum() {
        return avacadoPouch.size();
    }
    
    public int getAvacadosInWorldNum() {
        return avacadosInWorld.size();
    }
    
    public int getAvacadosCollectedNum() {
        return avacadosCollected.size();
    }
    
    /**
     * Moves all the avacados back from the pouch into the world.
     */
    public void losePouch() {
        if (avacadoPouch.isEmpty()) return;
        
        for (Avacado ava : avacadoPouch) {
            avacadosInWorld.add(ava);
            ava.setCollected(false);
        }
        avacadoPouch.clear();
    }
    
    /**
     * Empties all the avacaods from the pouch pile to the collected pile
     */
    public void depositAvacadoPouch() {
        if (avacadoPouch.isEmpty()) return;
        
        for (Avacado ava : avacadoPouch) {
            avacadosCollected.add(ava);
        }
        avacadoPouch.clear();
    }
    
    /**
     * Picks up an Avacado, removing it from the world and adding it in the pouch.
     * @param a The avacado being picked up
     */
    public void pickUpAvacado(Avacado a) {
        for (Avacado find : avacadosInWorld) {
            if (find == a) {
                Debugger.print("We have picked up an avacado", Debugger.INFO);
                avacadosInWorld.remove(a);
                avacadoPouch.add(a);
                a.setParent(null); // remove from world
                a.setCollected(true);
            }
        }
    }
    
    /**
     * Get the single copy of AvacadoManager
     * @return The single copy of Avacado Manager
     */
    public static AvacadoManager get() {
        return avacadoManager;
    }
}
