/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals.level;

import bropals.debug.Debugger;
import bropals.gameobject.GameObject;
import bropals.gameobject.Human;
import bropals.gameobject.block.Avacado;
import bropals.gameobject.block.AvacadoBin;
import bropals.gameobject.block.Block;
import bropals.gameobject.block.HayBale;
import bropals.gameobject.block.NormalDoor;
import bropals.gameobject.block.TeleportDoor;
import bropals.gameobject.block.Wall;
import bropals.gameobject.pools.AvacadoBinPool;
import bropals.gameobject.pools.AvacadoPool;
import bropals.gameobject.pools.BlockPool;
import bropals.gameobject.pools.HayBalePool;
import bropals.gameobject.pools.HumanPool;
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
    private final HumanPool humanPool;
    
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
        humanPool = new HumanPool(theArea);
    }
    
    /**
     * Makes the area that matches the id. This is a high maintenence object for hard-coding the areas.
     * @param id the id of the area that you want.
     */
    public void setArea(int id) {
        theArea.recycleAll();
        theArea.defaults();
        switch(id) {
            case -2:
            /*Block b = requestBlock();
            b.setParent(theArea);
            b.setHeight(100);
            b.setWidth(300);
            theArea.getObjects().add(b);*/
                break;
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
        if (recycle instanceof Block) {
            blockPool.recycle((Block)recycle);
        } else
        if (recycle instanceof Wall) {
            wallPool.recycle((Wall)recycle);
        } else
        if (recycle instanceof Avacado) {
            avacadoPool.recycle((Avacado)recycle);
        } else
        if (recycle instanceof HayBale) {
            hayBalePool.recycle((HayBale)recycle);
        } else
        if (recycle instanceof NormalDoor) {
            normalDoorPool.recycle((NormalDoor)recycle);
        } else
        if (recycle instanceof TeleportDoor) {
            teleportDoorPool.recycle((TeleportDoor)recycle);
        } else
        if (recycle instanceof AvacadoBin) {
            avacadoBinPool.recycle((AvacadoBin)recycle);
        } else
        if (recycle instanceof Human) {
            humanPool.recycle((Human)recycle);
        }
    }
    
    public Human requestHuman() {
        return humanPool.request();
    }
    
    public Block requestBlock() {
        return blockPool.request();
    }
    
    public Wall requestWall() {
        return wallPool.request();
    }
    
    public HayBale requestHayBale() {
        return hayBalePool.request();
    }
    
    public Avacado requestAvacado() {
        return avacadoPool.request();
    }
    
    public AvacadoBin requestAvacadoBin() {
        return avacadoBinPool.request();
    }
    
    public NormalDoor requestNormalDoor() {
        return normalDoorPool.request();
    }
    
    public TeleportDoor requestTeleportDoor() {
        return teleportDoorPool.request();
    }
}
