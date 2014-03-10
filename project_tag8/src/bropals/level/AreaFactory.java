/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals.level;

import bropals.datafiles.CowAreaFileManager;
import bropals.debug.Debugger;
import bropals.gameobject.GameObject;
import bropals.gameobject.GrappleHookPoint;
import bropals.gameobject.Human;
import bropals.gameobject.HumanType;
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
import bropals.util.Vector2;

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
        theArea.defaults();

        theArea.setRoomID(id);
        switch(id) {
            case 1:
                Block barn1 = new Wall(100, 100, 20, 300);
                barn1.setParent(theArea);
                Block barn2 = new Block(120, 100, 300, 20);
                barn2.setParent(theArea);
                Block barn3 = new Wall(420, 100, 20, 300);
                barn3.setParent(theArea);
                Block barn4 = new Block(100, 400, 120, 20);
                barn4.setParent(theArea);
                Block barn5 = new Block(320, 400, 120, 20);
                barn5.setParent(theArea);
                
                Avacado pah = new Avacado(0, 0, 10, 10, 1);
                
                AvacadoBin bin = new AvacadoBin(450, 200, 50, 50);
                bin.setParent(theArea);
                
                NormalDoor door = new NormalDoor(220, 403, 100, 15);
                door.setParent(theArea);
                        
                break;
            case -2:
            //Debugger.print("I am running setArea!", Debugger.INFO);
            Block b = new Block(150, 200, 100, 300);
            b.setParent(theArea);
            NormalDoor b2 = new NormalDoor(260, 200, 80, 80);
            b2.setParent(theArea);
                
            
            Human h = new Human(300, 50, 30, 8, Direction.getUnitVector(Direction.EAST));
            h.setPatrolPath(new Waypoint[]{
                new Waypoint(300, 50), new Waypoint(150, 150)});
            h.setParent(theArea);
            if (theArea.getPlayer() == null) Debugger.print("PLAYER IS NULL!", Debugger.ERROR);
            h.givePlayerRef(theArea.getPlayer());
            h.setType(HumanType.PITCHFORK);
            
            HayBale hb = new HayBale(500, 300, 50, 50);
            hb.setParent(theArea);
            
            GrappleHookPoint a = new GrappleHookPoint(450, 450);
            a.setParent(theArea);
            
            theArea.setNorthTargetId(-1);
            
            Debugger.print("Made an area with " + theArea.getObjects().size() + " objects!", Debugger.INFO);
                break;
            case -1:
                Block b4 = new Block(400, 30, 200, 100);
                b4.setParent(theArea);
                
                theArea.setSouthTargetId(-2);
                
                break;
            
            default:
                Debugger.print("Need constructor for ID: " + id + " in AreaFactory", Debugger.ERROR);
        }
        AvacadoManager.get().appearAvacadosForRoom(theArea.getAreaId(), theArea);
        theArea.updateBoundries();
    }

    public void makeNewAreaOverOldArea() {
        theArea.defaults();
    }

    public CowAreaFileManager getFileManager() {
        return cowAreaFileManager;
    }
}
