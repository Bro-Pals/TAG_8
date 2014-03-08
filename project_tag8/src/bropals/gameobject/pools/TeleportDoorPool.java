/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals.gameobject.pools;

import bropals.gameobject.block.TeleportDoor;
import bropals.level.Area;
import bropals.util.ObjectPool;

/**
 *
 * @author Jonathon
 */
public class TeleportDoorPool extends ObjectPool<TeleportDoor> {

    public TeleportDoorPool(Area area) {
        super(area);
    }

    @Override
    protected TeleportDoor makeDefaultObject() {
        return new TeleportDoor(area, 0, 0, 0, 0, -1, 0, 0);
    }

    @Override
    protected void setToDefault(TeleportDoor obj) {
       obj.setHeight(0);
       obj.setWidth(0);
       obj.setX(0);
       obj.setY(0);
    }
    
}
