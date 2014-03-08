/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bropals.level;

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

    public ArrayList<Avacado> getAvacadosInWorld() {
        return avacadosInWorld;
    }

    public ArrayList<Avacado> getAvacadosCollected() {
        return avacadosCollected;
    }

    /**
     * Return how many avacados the player has collected. Collected avacados are
     *        not lost when the player dies
     * @return The number of collectedAvacados
     */
    public int getAvacadosCollectedCount() {
        return avacadosCollected.size();
    }
    
    /**
     * Return how many avacado objects are being kept track of total
     * @return The total number of avacados
     */
    public int getTotalAvacadoCount() {
        return (avacadosCollected.size() + avacadosInWorld.size() + avacadoPouch.size());
    }
    
    /**
     * Add all the avacados in the pouch to the collected pile. Will clear
     * the avacadoPouch and remove.
     */
    public void collectAvacados() {
        for (Avacado a : avacadoPouch) {
            for (int i=0; i<avacadosInWorld.size(); i++) {
                if (a == avacadosInWorld.get(i)) {
                    avacadosInWorld.remove(i); // remove from the world
                    avacadosCollected.add(a); // add to the collected pile
                }
            }
        }
        avacadoPouch.clear();
    }

    /**
     * Adds an avacado to the avacadoPouch and removes it from the world
     * @param thisOne The avacado being collected
     */
    public void pickUpAvacado(Avacado thisOne) {
        for (int i=0; i<avacadosInWorld.size(); i++) {
            if (avacadosInWorld.get(i) == thisOne) {
                avacadosInWorld.remove(i);
                avacadoPouch.add(thisOne);
                break; // added the avacado so you can break
            }
        }
    }
    
    /**
     * All avacados in the avacadoPouch is moved to the world and returned to
     * their locations. clears the avacadoPouch.
     */
    public void loseAvacadosInPouch() {
        for (Avacado a : avacadoPouch) {
            a.returnToSpawn();
            if (!avacadosInWorld.contains(a)) {
                avacadosInWorld.add(a);
            } // add if it does not contain the avacado yet
        }
        avacadoPouch.clear();
    }
    
    /**
     * Get the single copy of AvacadoManager
     * @return The single copy of Avacado Manager
     */
    public static AvacadoManager get() {
        return avacadoManager;
    }
}
