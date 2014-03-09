/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals.level;

import bropals.datafiles.CowAreaFileManager;
import bropals.debug.Debugger;
import bropals.gameobject.GameObject;
import bropals.gameobject.Human;
import bropals.gameobject.Waypoint;
import bropals.gameobject.block.Avacado;
import bropals.gameobject.block.AvacadoBin;
import bropals.gameobject.block.Block;
import bropals.gameobject.block.HayBale;
import bropals.gameobject.block.NormalDoor;
import bropals.gameobject.block.TeleportDoor;
import bropals.gameobject.block.Wall;
import bropals.util.Direction;
import bropals.util.Direction;

/**
 *
 * @author Jonathon
 */
public class AreaFactory {
    
    private final Area theArea;
    private CowAreaFileManager cowAreaFileManager;
    
    public Area getArea() { return theArea; }
    
    public AreaFactory() {
        theArea = new Area(this);
        cowAreaFileManager = new CowAreaFileManager();
    }
    
    /**
     * Makes the area that matches the id. This is a high maintenence object for hard-coding the areas.
     * @param id the id of the area that you want.
     */
    public void setArea(int id) {
        switch(id) {
            case -2:
            Debugger.print("I am running setArea!", Debugger.INFO);
            Block b = new Block(150, 200, 100, 300);
            b.setParent(theArea);
            Block b2 = new NormalDoor(260, 200, 130, 200);
            b2.setParent(theArea);
            
            Human h = new Human(300, 50, 30, 30, Direction.getUnitVector(Direction.EAST));
            h.setPatrolPath(new Waypoint[]{
                new Waypoint(300, 50), new Waypoint(150, 150)});
            h.setParent(theArea);
            
            Debugger.print("Made an area with " + theArea.getObjects().size() + " objects!", Debugger.INFO);
                break;
            case -1:
                break;
            default:
                Debugger.print("Need constructor for ID: " + id + " in AreaFactory", Debugger.ERROR);
        }
    }

    public void makeNewAreaOverOldArea() {
        theArea.defaults();
    }
}
