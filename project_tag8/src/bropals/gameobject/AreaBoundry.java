/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals.gameobject;

import bropals.util.Direction;

/**
 *
 * @author Jonathon
 */
public class AreaBoundry {
    
    private Direction edgeSide;
    private int targetAreaId;

    public Direction getEdgeSide() {
        return edgeSide;
    }

    public void setEdgeSide(Direction edgeSide) {
        this.edgeSide = edgeSide;
    }

    public int getTargetAreaId() {
        return targetAreaId;
    }

    public void setTargetAreaId(int targetAreaId) {
        this.targetAreaId = targetAreaId;
    }
}
