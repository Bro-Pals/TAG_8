/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals.level;

import bropals.debug.Debugger;
import bropals.engine.Engine;
import bropals.gameobject.Creature;
import bropals.gameobject.GameObject;
import bropals.gameobject.GrappleHookPoint;
import bropals.gameobject.MouseInteractable;
import bropals.gameobject.Player;
import bropals.gameobject.Stone;
import bropals.util.Direction;
import bropals.util.Vector2;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 *
 * @author Jonathon
 */
public class AreaRunner {
    
    public static final Player player = new Player(150, 150);
    private final AreaFactory areaFactory;

    private boolean[] movement = {false, false, false, false};
    
    public Player getPlayer() {
        return player;
    }

    public AreaFactory getAreaFactory() {
        return areaFactory;
    }
            
    public AreaRunner() {
        areaFactory = new AreaFactory();
        //Initial area
        setCurrentArea(1);
    }
    
    public Area getCurrentArea() {
        return areaFactory.getArea();
    }

    public void setCurrentArea(int areaId) {
        if (player == null) Debugger.print("PLAYER IS NULL HERE TOO?!!", Debugger.ERROR);
        areaFactory.getArea().givePlayer(player);
        areaFactory.setArea(areaId);
        player.setParent(areaFactory.getArea());
    }
    
    public void iterateThroughObjectsInCurrentArea(Point mousePoint) {
        //if (mousePoint!= null) Debugger.print("The mouse Point is at "+mousePoint.toString(), Debugger.INFO);
        if (player != null) {
            Vector2 playerFaceDir = player.getFaceDirection();
            if (movement[Direction.NORTH.getDirectionId()]) {
                playerFaceDir.setY(-1);
            } else if (movement[Direction.SOUTH.getDirectionId()]) {
                playerFaceDir.setY(1);
            } else if (!movement[Direction.NORTH.getDirectionId()] && !movement[Direction.SOUTH.getDirectionId()] &&
                    (movement[Direction.WEST.getDirectionId()] || movement[Direction.EAST.getDirectionId()])) {
                playerFaceDir.setY(0);
                player.getMoveVector().setY(0);
            }
            
            if (movement[Direction.EAST.getDirectionId()]) {
                playerFaceDir.setX(1);
            } else if (movement[Direction.WEST.getDirectionId()]) {
                playerFaceDir.setX(-1);
            } else if (!movement[Direction.EAST.getDirectionId()] && !movement[Direction.WEST.getDirectionId()] &&
                    (movement[Direction.NORTH.getDirectionId()] || movement[Direction.SOUTH.getDirectionId()])) {
                playerFaceDir.setX(0);
                player.getMoveVector().setX(0);
            }
            
            player.setFaceDirection(playerFaceDir.getUnitVector());
            
            if (!movement[0] && !movement[1] && !movement[2] && !movement[3]) {
                player.setMoveVector(new Vector2(0, 0));
            } else {
                player.setMoveVector(player.getFaceDirection().clone());
            }
            if (player.getCenterY() < 0 && areaFactory.getArea().getNorthTargetId() != 0) {
                Debugger.print("Going north!", Debugger.INFO);
                areaFactory.setArea(areaFactory.getArea().getNorthTargetId());
                player.setY(Engine.SCREEN_HEIGHT - (int)(player.getHeight()));
                player.setParent(areaFactory.getArea());
            } else if (player.getCenterY() > Engine.SCREEN_HEIGHT && areaFactory.getArea().getSouthTargetId() != 0) {
                Debugger.print("Going south!", Debugger.INFO);
                areaFactory.setArea(areaFactory.getArea().getSouthTargetId());
                player.setY(0);
                player.setParent(areaFactory.getArea());
            } else if (player.getCenterX() < 0 && areaFactory.getArea().getWestTargetId() != 0) {
                Debugger.print("Going west!", Debugger.INFO);
                areaFactory.setArea(areaFactory.getArea().getWestTargetId());
                player.setX(Engine.SCREEN_WIDTH - (int)(player.getWidth()));
                player.setParent(areaFactory.getArea());
            } else if (player.getCenterX() > Engine.SCREEN_WIDTH && areaFactory.getArea().getEastTargetId() != 0) {
                Debugger.print("Going east!", Debugger.INFO);
                areaFactory.setArea(areaFactory.getArea().getEastTargetId());
                player.setX(0);
                player.setParent(areaFactory.getArea());
            }
            
            if (player.getHealth() <= 0) {
                areaFactory.setArea(1); // the barn
                player.setX(150);
                player.setY(150);
                player.setParent(areaFactory.getArea());
                player.resetHealth();
            }
        }
        ArrayList<GameObject> objects = areaFactory.getArea().getObjects();
        for (int i=0; i<objects.size(); i++) {
            if (objects.get(i) instanceof Creature) {
                ((Creature)objects.get(i)).update();
            }
            if (objects.get(i) instanceof Stone) {
                ((Stone)objects.get(i)).update();
            }
        }
        // look for other interactables
        for (int i=0; i<objects.size(); i++) {
            if (mousePoint!=null && objects.get(i) instanceof GrappleHookPoint) {
                GrappleHookPoint ghp = (GrappleHookPoint)objects.get(i);
                float interactDist = ((MouseInteractable)objects.get(i)).getInteractDistance();
                float xDiff = (float)(ghp.getCenterX()-mousePoint.getX());
                float yDiff = (float)(ghp.getCenterY()-mousePoint.getY());
                float creatureDist = ((MouseInteractable)objects.get(i)).getCreatureInteractDistance();
                float cXDiff = (float)(ghp.getCenterX() - player.getCenterX());
                float cYDiff = (float)(ghp.getCenterY() - player.getCenterY());
                if (interactDist*interactDist > (xDiff*xDiff)+(yDiff*yDiff)
                        && creatureDist*creatureDist > (cXDiff*cXDiff)+(cYDiff*cYDiff)) {
                    player.setSelectedInteractable((MouseInteractable)objects.get(i));
                }
            }
        }
    }
    
    public void reactToMouseInput(boolean pressed, int button, int screenX, int screenY) {
        //System.out.println("Click");
    }
    
    public void reactToKeyInput(boolean pressed, int button) {
        switch(button) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                movement[Direction.NORTH.getDirectionId()] = pressed;
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                movement[Direction.SOUTH.getDirectionId()] = pressed;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                movement[Direction.EAST.getDirectionId()] = pressed;
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                movement[Direction.WEST.getDirectionId()] = pressed;
                break;
        }
        if (pressed && button == KeyEvent.VK_SPACE) {
            if (player.getSelectedInteractable() != null) {
                player.getSelectedInteractable().interact(player);
            }
        }
    }
}
