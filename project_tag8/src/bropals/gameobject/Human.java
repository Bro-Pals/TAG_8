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
    private int waypointWait;
    private int waypointOn;
    
    private Player playerRef;
    private ArrayList<Waypoint> backTrackWaypoints;
    private int recordTime;
    private int RECORD_RATE = 2;
    
    float nearEnough = 40;
    
    private int alertTimer;
    private int ALERT_TIME = 15;
    private boolean alerted;
    private float sightRange;
    private float fieldOfView;
    private float attackDistance;
    private int attackTimer;
    private final int ATTACK_SPEED = 35; // frames
    private float turnSpeed;
    
    public Human(float x, float y, float size, float speed, Vector2 faceDirection) {
        super(x, y, size, size, speed, faceDirection);
        patrolPath = new Waypoint[0];
        waypointOn = 0;
        alerted = false;
        sightRange = 300;
        fieldOfView = (float)(Math.PI*5.0/6.0); // radians
        attackTimer = 0;
        setSpeed(10);
        this.setTextureString("Human");
    }

    public void alerted() {
        alerted = true;
        alertTimer = ALERT_TIME;
        backTrackWaypoints = new ArrayList<>();
        backTrackWaypoints.add(new Waypoint(getCenterX(), getCenterY())); // waypoint to return to
        recordTime = RECORD_RATE; // recrod a new point every 4 frames
        setStandingStill(false);
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
        Line2D.Float sightLine = new Line2D.Float(getCenterX(), getCenterY(), x, y);
        for (GameObject obj : getParent().getObjects()) {
            if (obj != this && obj!= playerRef && obj instanceof Block) {
                // skip checking if there is an open door
                if (obj instanceof NormalDoor && ((NormalDoor)obj).isOpen()) continue;
                
                if (((Block)obj).getRectangle2D().intersectsLine(sightLine)) {
                    return false; // there is something blocking the line of sight!
                }
            }
        }
        return true;
    }
    
    public void backtrack() {
        //System.out.println("Backtrackign");
        if (backTrackWaypoints.size() > 0) {
            int moveToNum = 0;
            if (backTrackWaypoints.size() > 1 && !canSee(backTrackWaypoints.get(0).getX(), backTrackWaypoints.get(0).getY())) {
                moveToNum = backTrackWaypoints.size()-1;
            } else {
                moveToNum = 0;
            }
                moveTowardsPoint(backTrackWaypoints.get(moveToNum));   
                float xDiff = backTrackWaypoints.get(moveToNum).getX() - getCenterX();
                float yDiff = backTrackWaypoints.get(moveToNum).getY() - getCenterY();
                if (canSee(patrolPath[waypointOn].getX(), patrolPath[waypointOn].getY())) {
                    //System.out.println("Done backtracking!");
                    currentGoalWaypoint = patrolPath[waypointOn];
                    backTrackWaypoints.clear();
                } else if (nearEnough*nearEnough > (xDiff*xDiff)+(yDiff*yDiff)) {
                    backTrackWaypoints.remove(moveToNum);
                }
        }
    }
    
    @Override
    public void update() {
        if (alerted) {
            // if the player is not to be seen or dead
            if (playerRef.getHealth() <= 0 || !canSeePlayer()) {
                if (alertTimer < 0) {
                    setStandingStill(false);
                    backtrack();
                    if (canSee(patrolPath[waypointOn].getX(), patrolPath[waypointOn].getY())) {
                        alerted = false;
                        currentGoalWaypoint = patrolPath[waypointOn];
                        backTrackWaypoints.clear();
                    }
                } else {
                    setStandingStill(true);
                    alertTimer--;
                }
            } else if (playerRef.getHealth() > 0 && canSeePlayer()) {
                float xDiff = playerRef.getCenterX() - getCenterX();
                float yDiff = playerRef.getCenterY() - getCenterY();
                if (type == HumanType.PITCHFORK) {
                    setStandingStill(false);
                    if (recordTime < 0) {
                        recordTime = RECORD_RATE;
                        int lastIndex = backTrackWaypoints.size()-1;
                        if (!canSee(backTrackWaypoints.get(lastIndex).getX(), backTrackWaypoints.get(lastIndex).getY())) {
                            backTrackWaypoints.add(new Waypoint(getCenterX(), getCenterY()));
                        }
                    }
                    recordTime--;
                    Debugger.print("attackTimer:"+attackTimer+"  AttackSpeed:"+ATTACK_SPEED, Debugger.INFO);
                    if (attackTimer > ATTACK_SPEED && attackDistance*attackDistance > (xDiff*xDiff)+(yDiff*yDiff)) {
                        setStandingStill(true);
                        playerRef.damage();
                        attackTimer = 0;
                    } else {
                        this.moveTowardsPoint(playerRef.getCenterX(), playerRef.getCenterY());   
                    }
                } else if (type == HumanType.ROCK_THROWER) {
                    setStandingStill(true);
                    facePoint(playerRef.getCenterX(), playerRef.getCenterY());
                    
                    if (attackTimer > ATTACK_SPEED && attackDistance*attackDistance > (xDiff*xDiff)+(yDiff*yDiff)) {
                        shootAtPlayer();
                        attackTimer = 0;
                    }
                }
            }
        } else if (!alerted && patrolPath.length > 0) {
            //Debugger.print("I have a path :D", Debugger.INFO);
            if (currentGoalWaypoint == null) currentGoalWaypoint = patrolPath[waypointOn];
            
            currentGoalWaypoint = patrolPath[waypointOn];
            moveTowardsPoint(currentGoalWaypoint);
            
            float xDiff = getCenterX() - currentGoalWaypoint.getX();
            float yDiff = getCenterY() - currentGoalWaypoint.getY();
            //Debugger.print("Distance from goal: " + Math.sqrt(((xDiff*xDiff) + (yDiff*yDiff))), Debugger.INFO);
            if (((xDiff*xDiff) + (yDiff*yDiff)) < nearEnough*nearEnough) {
                setStandingStill(true);
                if (waypointWait > currentGoalWaypoint.getDelay()) {
                    System.out.println("WaypointOn:"+waypointOn + "  waypointTimerWait:"+waypointWait);
                    waypointOn++;
                    waypointWait = 0; // reset wait timer
                    if (waypointOn >= patrolPath.length) waypointOn = 0;
                } else {
                    waypointWait++;
                }
            } else {
                setStandingStill(false);   
            }
            //Debugger.print("Currently on point "+waypointOn, Debugger.INFO);
            if (canSeePlayer()) {
                Debugger.print("The human can see the player", Debugger.INFO);
                alerted();
            }
        }
        attackTimer++;
        super.update();
    }
    
    public void shootAtPlayer() {
        float diffX = playerRef.getCenterX() - getCenterX();
        float diffY = playerRef.getCenterY() - getCenterY();
        Vector2 bulletVector = (new Vector2(diffX, diffY)).getUnitVector();
        Stone stone = new Stone(getCenterX() + (float)(20*bulletVector.getX()), 
                getCenterY() + (float)(20*bulletVector.getY()), bulletVector);
        stone.setParent(getParent());
    }
    
    public boolean canSeePlayer() {
        if (playerRef != null) {
            if (playerRef.isHiding() || playerRef.getHealth() <= 0) return false;
            
            float playerXDiff = playerRef.getCenterX() - getCenterX();
            float playerYDiff = playerRef.getCenterY() - getCenterY();
            Vector2 playerPositionVector = (new Vector2(playerXDiff, playerYDiff)).getUnitVector();
            //Debugger.print("There is a player", Debugger.INFO);
            //Debugger.print("Angle between human facing direction and player position: " + Vector2.dot(playerPositionVector, getFaceDirection()), Debugger.INFO);
            if ((playerXDiff*playerXDiff)+(playerYDiff*playerYDiff) < sightRange*sightRange) {
                // if the player is too close they see them anyways
                boolean override = (playerXDiff*playerXDiff)+(playerYDiff*playerYDiff) < 50*50;
                // in their field of view!
                if (override || Vector2.dot(playerPositionVector, getFaceDirection()) > Math.cos(fieldOfView/2)) {
                    //Debugger.print("Player is close enough", Debugger.INFO);
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
        return (type == HumanType.PITCHFORK) ? ImageLoader.getLoader().getImage("Human", 0) :ImageLoader.getLoader().getImage("Human", 1);
    }

    public HumanType getType() {
        return type;
    }

    public void setType(HumanType type) {
        this.type = type;
        if (type == HumanType.PITCHFORK) attackDistance = 30;
        else if (type == HumanType.ROCK_THROWER) attackDistance = 400;
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

    public float getAttackDistance() {
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
    
    public boolean isAlert() {
        return alerted;
    }
}
