/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals.gameobject.pools;

import bropals.gameobject.block.HayBale;
import bropals.level.Area;
import bropals.util.ObjectPool;

/**
 *
 * @author Jonathon
 */
public class HayBalePool extends ObjectPool<HayBale> {

    public HayBalePool(Area area) {
        super(area);
    }

    @Override
    protected HayBale makeDefaultObject() {
        return new HayBale(area, 0, 0, 0, 0);
    }

    @Override
    protected void setToDefault(HayBale obj) {
       obj.setHeight(0);
       obj.setWidth(0);
       obj.setX(0);
       obj.setY(0);
    }
    
}
