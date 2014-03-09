/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals.level;

import bropals.debug.Debugger;
import bropals.engine.Engine;
import bropals.gameobject.GameObject;
import bropals.gameobject.Player;
import bropals.graphics.ImageLoader;
import bropals.util.Direction;
import bropals.util.Direction;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Jonathon
 */
public class Area {
    
    private static int areaCount = 0;
    
    private ArrayList<GameObject> objects;
    private Player player;
    private final int[] boundryTargetIds;
    private AreaFactory factory;
    private int areaId;
    private BufferedImage backgroundTile;
    private int bgTimesX, bgTimesY;

    public int[] getBoundryTargetIds() {
        return boundryTargetIds;
    }

    public int getAreaId() {
        return areaId;
    }
    

    public BufferedImage getBackgroundTile() {
        return backgroundTile;
    }

    public void setBackgroundTile(BufferedImage backgroundTile, int canvasX, int canvasY) {
        this.backgroundTile = backgroundTile;
        bgTimesX = (int)Math.ceil((float)canvasX/(float)backgroundTile.getWidth());
        bgTimesY = (int)Math.ceil((float)canvasY/(float)backgroundTile.getHeight());
    }
    
    public int getNorthTargetId() { return boundryTargetIds[Direction.getDirectionId(Direction.NORTH)]; }
    public int getSouthTargetId() { return boundryTargetIds[Direction.getDirectionId(Direction.SOUTH)]; }
    public int getEastTargetId() { return boundryTargetIds[Direction.getDirectionId(Direction.EAST)]; }
    public int getWestTargetId() { return boundryTargetIds[Direction.getDirectionId(Direction.WEST)]; }

    public void setNorthTargetId(int id) {
        boundryTargetIds[Direction.getDirectionId(Direction.NORTH)] = id;
    }
    
    public void setSouthTargetId(int id) {
        boundryTargetIds[Direction.getDirectionId(Direction.SOUTH)] = id;
    }
    
    public void setEastTargetId(int id) {
        boundryTargetIds[Direction.getDirectionId(Direction.EAST)] = id;
    }
    
    public void setWestTargetId(int id) {
        boundryTargetIds[Direction.getDirectionId(Direction.WEST)] = id;
    }
    
    public Area(AreaFactory factory) {
        areaCount++;
        Debugger.print("There are "+areaCount+" area object(s)", Debugger.INFO);
        areaId = -1;
        boundryTargetIds = new int[]{ -1, -1, -1, -1 };
        this.factory = factory;
        objects = new ArrayList<GameObject>();
        defaults();
    }
    
    /**
     * Sets everything to a default value so the area is ready to be changed around with in AreaFactory.
     */
    public void defaults() {
        setBackgroundTile(ImageLoader.getLoader().getImage("placeholder_background", 0), Engine.SCREEN_WIDTH, Engine.SCREEN_HEIGHT);
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
    
    /**
     * Give the Area a copy of the Player reference
     * @param p A reference to the Player
     */
    public void givePlayer(Player p) {
        player = p;
    }
    
    public Player getPlayer() {
        return player;
    }
    
    /**
     * For use by the level designer. Adds an object to the area.
     * @param object the object to add.
     */
    public void addObject(GameObject object) {
        objects.add(object);
    }
    
    /**
     * For use by the level designer. Removes an object from the area.
     * @param object the object to remove.
     */
    public void removeObject(GameObject object) {
        objects.remove(object);
    }
}
