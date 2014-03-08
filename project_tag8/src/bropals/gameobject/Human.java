/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals.gameobject;

import bropals.util.Vector2;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Jonathon
 */
public class Human extends GameObject {

    private float xPosition, yPosition, width, height;
    private HumanType type;
    private HumanState state;
    private Vector2 faceDirection;
    private float speed;
    private float angleOfSight;
    private Waypoint currentGoalWaypoint;
    private Waypoint[] patrolPath;
    private Waypoint storedWaypoint;
    private ArrayList<Waypoint> backTrackWaypoints;
    private int alertTimer;
    private float sightRange;
    private float attachDistance;
    private float turnSpeed;
    
    public Human(ArrayList<GameObject> parent, float x, float y) {
        super(parent, x, y);
    }

    @Override
    public BufferedImage getTexture() {
        return null;
    }

    public float getXPosition() {
        return xPosition;
    }

    public void setXPosition(float xPosition) {
        this.xPosition = xPosition;
    }

    public float getYPosition() {
        return yPosition;
    }

    public void setYPosition(float yPosition) {
        this.yPosition = yPosition;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
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

    public Vector2 getFaceDirection() {
        return faceDirection;
    }

    public void setFaceDirection(Vector2 faceDirection) {
        this.faceDirection = faceDirection;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getAngleOfSight() {
        return angleOfSight;
    }

    public void setAngleOfSight(float angleOfSight) {
        this.angleOfSight = angleOfSight;
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
