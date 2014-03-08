/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals.gameobject.pools;

import bropals.gameobject.block.Avacado;
import bropals.level.Area;
import bropals.util.ObjectPool;

/**
 *
 * @author Jonathon
 */
public class AvacadoPool extends ObjectPool<Avacado> {

    public AvacadoPool(Area area) {
        super(area);
    }

    @Override
    protected Avacado makeDefaultObject() {
        return new Avacado(area, 0, 0, 0, 0);
    }

    @Override
    protected void setToDefault(Avacado obj) {
       obj.setHeight(0);
       obj.setWidth(0);
       obj.setX(0);
       obj.setY(0);
    }
    
}
