/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals.gameobject.pools;

import bropals.gameobject.block.Wall;
import bropals.level.Area;
import bropals.util.ObjectPool;

/**
 *
 * @author Jonathon
 */
public class WallPool extends ObjectPool<Wall> {

    public WallPool(Area area) {
        super(area);
    }

    @Override
    protected Wall makeDefaultObject() {
        return new Wall(area, 0, 0, 0, 0);
    }

    @Override
    protected void setToDefault(Wall obj) {
       obj.setHeight(0);
       obj.setWidth(0);
       obj.setX(0);
       obj.setY(0);    
    }
    
}
