/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals.level;

import bropals.gameobject.GameObject;
import bropals.util.Direction;
import bropals.util.Direction;
import java.util.ArrayList;

/**
 *
 * @author Jonathon
 */
public class Area {
    
    private ArrayList<GameObject> objects;
    private int[] boundryTargetIds;
    private int areaId;
    
    public Area(int northId, int southId, int eastId, int westId, int areaId) {
        boundryTargetIds = new int[4];
        boundryTargetIds[Direction.getDirectionId(Direction.NORTH)] = northId;
        boundryTargetIds[Direction.getDirectionId(Direction.SOUTH)] = southId;
        boundryTargetIds[Direction.getDirectionId(Direction.EAST)] = westId;
        boundryTargetIds[Direction.getDirectionId(Direction.WEST)] = eastId;
        this.areaId = areaId;
    }
    
    public Area(int[] boundryTargetIds, int areaId) {
        this.boundryTargetIds = boundryTargetIds;
        this.areaId = areaId;
    }
    
    public ArrayList<GameObject> getObjects() {
        return objects;
    }  
}
