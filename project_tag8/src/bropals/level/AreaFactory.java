/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals.level;

import bropals.debug.Debugger;
import bropals.gameobject.GameObject;
import bropals.gameobject.pools.AvacadoBinPool;
import bropals.gameobject.pools.AvacadoPool;
import bropals.gameobject.pools.BlockPool;
import bropals.gameobject.pools.HayBalePool;
import bropals.gameobject.pools.NormalDoorPool;
import bropals.gameobject.pools.TeleportDoorPool;
import bropals.gameobject.pools.WallPool;

/**
 *
 * @author Jonathon
 */
public class AreaFactory {
    
    private final Area theArea;
    private final AvacadoBinPool avacadoBinPool;
    private final AvacadoPool avacadoPool;
    private final BlockPool blockPool;
    private final HayBalePool hayBalePool;
    private final NormalDoorPool normalDoorPool;
    private final TeleportDoorPool teleportDoorPool;
    private final WallPool wallPool;
    
    public Area getArea() { return theArea; }
    
    public AreaFactory() {
        theArea = new Area(this);
        avacadoBinPool = new AvacadoBinPool(theArea);
        avacadoPool = new AvacadoPool(theArea);
        blockPool = new BlockPool(theArea);
        hayBalePool = new HayBalePool(theArea);
        normalDoorPool = new NormalDoorPool(theArea);
        teleportDoorPool = new TeleportDoorPool(theArea);
        wallPool = new WallPool(theArea);
    }
    
    /**
     * Makes the area that matches the id. This is a high maintenence object for hard-coding the areas.
     * @param id the id of the area that you want.
     */
    public void setArea(int id) {
        theArea.getObjects().clear();
        theArea.defaults();
        switch(id) {
            
            case -1:
                break;
            default:
                Debugger.print("Need constructor for ID: " + id + " in AreaFactory", Debugger.ERROR);
        }
    }
    
    /**
     * Puts an object back into the game object pool to be reused.
     * @param recycle the GameObject being recycled
     */
    public void recycleGameObject(GameObject recycle) {
        
    }
}
