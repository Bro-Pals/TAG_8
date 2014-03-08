/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals.gameobject;

import bropals.gameobject.block.Block;
import bropals.level.Area;
import bropals.util.Vector2;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Jonathon
 */
public class Human extends Creature {

    private HumanType type;
    private HumanState state;
    private Waypoint currentGoalWaypoint;
    private Waypoint[] patrolPath;
    private Waypoint storedWaypoint;
    private ArrayList<Waypoint> backTrackWaypoints;
    private int alertTimer;
    private float sightRange;
    private float attachDistance;
    private float turnSpeed;
    
    public Human(Area parent, float x, float y, float size, float speed, Vector2 faceDirection) {
        super(parent, x, y, size, speed, faceDirection);
    }

    /**
     * See if this human can see the passed player object
     * @param player The player that we're testing the line of sight to
     * @return True if there is nothing blocking the Human's line of sight to the
     *         player, otherwise returns false
     */
    public boolean canSee(Player player) {
        Line2D.Float sightLine = new Line2D.Float(getX(), getY(), player.getX(), player.getY());
        for (GameObject obj : getParent().getObjects()) {
            if (obj instanceof Block) {
                if (((Block)obj).getRectangle2D().intersectsLine(sightLine)) {
                    return false; // there is something blocking the line of sight!
                }
            }
        }
        return true;
    }
    
    @Override
    public BufferedImage getTexture() {
        return null;
    }

    public HumanType getType() {
        return type;
    }

    public void setType(HumanType type) {
        this.type = type;
    }

    public HumanState getState() {
        return state;
    }

    public void setState(HumanState state) {
        this.state = state;
    }

    public Waypoint getCurrentGoalWaypoint() {
        return currentGoalWaypoint;
    }

    public void setCurrentGoalWaypoint(Waypoint currentGoalWaypoint) {
        this.currentGoalWaypoint = currentGoalWaypoint;
    }

    public Waypoint[] getPatrolPath() {
        return patrolPath;
    }

    public void setPatrolPath(Waypoint[] patrolPath) {
        this.patrolPath = patrolPath;
    }

    public Waypoint getStoredWaypoint() {
        return storedWaypoint;
    }

    public void setStoredWaypoint(Waypoint storedWaypoint) {
        this.storedWaypoint = storedWaypoint;
    }

    public ArrayList<Waypoint> getBackTrackWaypoints() {
        return backTrackWaypoints;
    }

    public void setBackTrackWaypoints(ArrayList<Waypoint> backTrackWaypoints) {
        this.backTrackWaypoints = backTrackWaypoints;
    }

    public int getAlertTimer() {
        return alertTimer;
    }

    public void setAlertTimer(int alertTimer) {
        this.alertTimer = alertTimer;
    }

    public float getSightRange() {
        return sightRange;
    }

    public void setSightRange(float sightRange) {
        this.sightRange = sightRange;
    }

    public float getAttachDistance() {
        return attachDistance;
    }

    public void setAttachDistance(float attachDistance) {
        this.attachDistance = attachDistance;
    }

    public float getTurnSpeed() {
        return turnSpeed;
    }

    public void setTurnSpeed(float turnSpeed) {
        this.turnSpeed = turnSpeed;
    }
    
}
