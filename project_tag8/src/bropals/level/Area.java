/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals.level;

import bropals.gameobject.GameObject;
import bropals.util.Direction;
import bropals.util.Direction;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Jonathon
 */
public class Area {
    
    private ArrayList<GameObject> objects;
    private final int[] boundryTargetIds;

    public int[] getBoundryTargetIds() {
        return boundryTargetIds;
    }

    public int getAreaId() {
        return areaId;
    }
    private int areaId;
    private BufferedImage backgroundTile;
    private int bgTimesX, bgTimesY;

    public BufferedImage getBackgroundTile() {
        return backgroundTile;
    }

    public void setBackgroundTile(BufferedImage backgroundTile, int canvasX, int canvasY) {
        this.backgroundTile = backgroundTile;
        bgTimesX = (int)Math.ceil((float)canvasX/(float)backgroundTile.getWidth());
        bgTimesY = (int)Math.ceil((float)canvasY/(float)backgroundTile.getHeight());
    }
    
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

    /**
     * For the Renderer. 
     * @return number of times to iterate background tile image in the x direction
     */
    public int getBgTimesX() {
        return bgTimesX;
    }
    
    /**
     * For the Renderer. 
     * @return number of times to iterate background tile image in the y direction
     */
    public int getBgTimesY() {
        return bgTimesY;
    }
}
