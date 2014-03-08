/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals.gameobject.pools;

import bropals.gameobject.Human;
import bropals.gameobject.HumanState;
import bropals.gameobject.HumanType;
import bropals.level.Area;
import bropals.util.ObjectPool;
import bropals.util.Vector2;

/**
 *
 * @author Jonathon
 */
public class HumanPool extends ObjectPool<Human> {

    public HumanPool(Area area) {
        super(area);
    }

    @Override
    protected Human makeDefaultObject() {
        return new Human(area, 0, 0, 0, 0, Vector2.UNIT_Y);
    }

    @Override
    protected void setToDefault(Human obj) {
        obj.setX(0);
        obj.setY(0);
        obj.setPatrolPath(null);
        obj.setSightRange(0);
        obj.setType(HumanType.PITCHFORK);
        obj.setState(HumanState.PATROLLING);
        obj.setTurnSpeed(0);
        obj.setBackTrackWaypoints(null);
        obj.setStoredWaypoint(null);
        obj.setCurrentGoalWaypoint(null);
        obj.setFaceDirection(Vector2.UNIT_Y);
        obj.setFieldOfView(0);
        obj.setAlertTimer(0);
    }
    
}
