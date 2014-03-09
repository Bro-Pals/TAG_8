/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals.gameobject;

import bropals.debug.Debugger;
import bropals.gameobject.block.Block;
import bropals.gameobject.block.NormalDoor;
import bropals.graphics.ImageLoader;
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
    private int waypointOn;
    
    private Player playerRef;
    private ArrayList<Waypoint> backTrackWaypoints;
    private int recordTime;
    private int RECORD_RATE = 4;
    
    private int alertTimer;
    private int ALERT_TIME = 15;
    private boolean alerted;
    private float sightRange;
    private float fieldOfView;
    private float attackDistance;
    private float turnSpeed;
    
    public Human(float x, float y, float size, float speed, Vector2 faceDirection) {
        super(x, y, size, size, speed, faceDirection);
        waypointOn = 0;
        alerted = false;
        sightRange = 300;
        fieldOfView = (float)(Math.PI); // radians
        setSpeed(10);
    }

    public void alerted() {
        alerted = true;
        alertTimer = ALERT_TIME;
        backTrackWaypoints = new ArrayList<>();
        backTrackWaypoints.add(new Waypoint(getX(), getY())); // waypoint to return to
        recordTime = RECORD_RATE; // recrod a new point every 4 frames
    }
    
    /**
     * See if this human can see the given point, as in there are no blocks
     *      blocking its view of it.
     * @param x The x position of the point
     * @param y The y position of the point
     * @return True if there is nothing blocking the Human's line of sight to the
     *         point, otherwise returns false
     */
    public boolean canSee(float x, float y) {
        Line2D.Float sightLine = new Line2D.Float(getX(), getY(), x, y);
        for (GameObject obj : getParent().getObjects()) {
            if (obj instanceof Block) {
                // skip checking if there is an open door
                if (obj instanceof NormalDoor && !((NormalDoor)obj).isOpen()) continue;
                
                if (((Block)obj).getRectangle2D().intersectsLine(sightLine)) {
                    return false; // there is something blocking the line of sight!
                }
            }
        }
        return true;
    }
    
    public void backtrack() {
        if (backTrackWaypoints.size() > 0) {
            if (canSee(backTrackWaypoints.get(0).getX(), backTrackWaypoints.get(0).getY())) {
                moveTowardsPoint(backTrackWaypoints.get(0));
            }
        }
    }
    
    @Override
    public void update() {
        if (alerted) {
            // if the player is not to be seen or dead
            if (playerRef.getHealth() <= 0 || !canSeePlayer()) {
                if (alertTimer < 0) {
                    backtrack();
                    if (backTrackWaypoints.isEmpty()) {
                        alerted = false;
                    }
                } else {
                    alertTimer--;
                }
            } else if (playerRef.getHealth() > 0 && canSeePlayer()) {
                if (type == HumanType.PITCHFORK) {
                    if (recordTime < 0) {
                        recordTime = RECORD_RATE;
                        backTrackWaypoints.add(new Waypoint(getX(), getY()));
                    }
                    recordTime--;
                    alertTimer = ALERT_TIME;
                    this.moveTowardsPoint(playerRef.getCenterX(), playerRef.getCenterY());
                } else if (type == HumanType.ROCK_THROWER) {
                    facePoint(playerRef.getCenterX(), playerRef.getCenterY());
                }
            }
        } else if (!alerted && patrolPath.length > 0) {
            //Debugger.print("I have a path :D", Debugger.INFO);
            if (currentGoalWaypoint == null) currentGoalWaypoint = patrolPath[waypointOn];
            float nearEnough = 20;
            float xDiff = getX() - currentGoalWaypoint.getX();
            float yDiff = getY() - currentGoalWaypoint.getY();
            //Debugger.print("Distance from goal: " + Math.sqrt(((xDiff*xDiff) + (yDiff*yDiff))), Debugger.INFO);
            if (((xDiff*xDiff) + (yDiff*yDiff)) < nearEnough*nearEnough) {
                waypointOn++;
                if (waypointOn >= patrolPath.length) waypointOn = 0;
            }
            //Debugger.print("Currently on point "+waypointOn, Debugger.INFO);
            currentGoalWaypoint = patrolPath[waypointOn];
            moveTowardsPoint(currentGoalWaypoint);
            
            if (canSeePlayer()) {
                Debugger.print("The human can see the player", Debugger.INFO);
                alerted();
            }
        }
        super.update();
    }
    
    public boolean canSeePlayer() {
        if (playerRef != null) {
            float playerXDiff = playerRef.getCenterX() - getCenterX();
            float playerYDiff = playerRef.getCenterY() - getCenterY();
            Vector2 playerPositionVector = (new Vector2(playerXDiff, playerYDiff)).getUnitVector();
            Debugger.print("There is a player", Debugger.INFO);
            Debugger.print("Angle: " + Vector2.dot(playerPositionVector, getFaceDirection()), Debugger.INFO);
            if (Vector2.dot(playerPositionVector, getFaceDirection()) > Math.cos(fieldOfView/2)) {
                Debugger.print("Player in the FOV", Debugger.INFO);
                // in their field of view!
                if ((playerXDiff*playerXDiff)+(playerYDiff*playerYDiff) < sightRange*sightRange) {
                    Debugger.print("Player is close enough", Debugger.INFO);
                    if (canSee(playerRef.getCenterX(), playerRef.getCenterY())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    @Override
    public BufferedImage getTexture() {
        return ImageLoader.getLoader().getImage("testCreature", 0);
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
        return attackDistance;
    }

    public void setAttackDistance(float attachDistance) {
        this.attackDistance = attachDistance;
    }

    public float getTurnSpeed() {
        return turnSpeed;
    }

    public void setTurnSpeed(float turnSpeed) {
        this.turnSpeed = turnSpeed;
    }
    
    public void givePlayerRef(Player player) {
        this.playerRef = player;
    }
    
}
