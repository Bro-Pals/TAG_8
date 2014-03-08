/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals.level;

import bropals.gameobject.GameObject;
import bropals.gameobject.Player;
import java.util.ArrayList;

/**
 *
 * @author Jonathon
 */
public class AreaRunner {
    
    private final Player player;
    private final AreaFactory areaFactory;

    public Player getPlayer() {
        return player;
    }

    public AreaFactory getAreaFactory() {
        return areaFactory;
    }
            
    public AreaRunner() {
        areaFactory = new AreaFactory();
        player = new Player(areaFactory.getArea(), 0, 0);
        //Initial area
        setCurrentArea(-1);
    }
    
    public Area getCurrentArea() {
        return areaFactory.getArea();
    }

    public void setCurrentArea(int areaId) {
        areaFactory.setArea(areaId);
    }
    
    public void iterateThroughObjectsInCurrentArea() {
        ArrayList<GameObject> objects = areaFactory.getArea().getObjects();
        for (int i=0; i<objects.size(); i++) {
            
        }
    }
    
    public void reactToMouseInput(boolean pressed, int button, int screenX, int screenY) {
        
    }
    
    public void reactToKeyInput(boolean pressed, int button) {
        
    }
}
