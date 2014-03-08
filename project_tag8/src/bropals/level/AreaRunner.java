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
    
    private Area currentArea;
    private Player player;
    
    public Area getCurrentArea() {
        return currentArea;
    }

    public void setCurrentArea(Area currentArea) {
        this.currentArea = currentArea;
    }
    
    public void iterateThroughObjectsInCurrentArea() {
        ArrayList<GameObject> objects = currentArea.getObjects();
        for (int i=0; i<objects.size(); i++) {
            
        }
    }
    
    public void reactToMouseInput(boolean pressed, int button, int screenX, int screenY) {
        
    }
    
    public void reactToKeyInput(boolean pressed, int button) {
        
    }
}
