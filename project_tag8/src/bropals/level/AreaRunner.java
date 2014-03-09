/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals.level;

import bropals.gameobject.Creature;
import bropals.gameobject.GameObject;
import bropals.gameobject.Player;
import bropals.util.Direction;
import bropals.util.Vector2;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 *
 * @author Jonathon
 */
public class AreaRunner {
    
    private final Player player;
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
        player = new Player(areaFactory.getArea(), 40, 100);
        //Initial area
        setCurrentArea(-2);
    }
    
    public Area getCurrentArea() {
        return areaFactory.getArea();
    }

    public void setCurrentArea(int areaId) {
        areaFactory.setArea(areaId);
        areaFactory.getArea().givePlayer(player);
        player.setParent(areaFactory.getArea());
    }
    
    public void iterateThroughObjectsInCurrentArea() {
        if (player != null) {
            Vector2 playerFaceDir = player.getFaceDirection();
            if (movement[Direction.NORTH.getDirectionId()]) {
                playerFaceDir.setY(-1);
            } else if (movement[Direction.SOUTH.getDirectionId()]) {
                playerFaceDir.setY(1);
            } else if (!movement[Direction.NORTH.getDirectionId()] && !movement[Direction.SOUTH.getDirectionId()] &&
                    (movement[Direction.WEST.getDirectionId()] || movement[Direction.EAST.getDirectionId()])) {
                playerFaceDir.setY(0);
            }
            
            if (movement[Direction.EAST.getDirectionId()]) {
                playerFaceDir.setX(1);
            } else if (movement[Direction.WEST.getDirectionId()]) {
                playerFaceDir.setX(-1);
            } else if (!movement[Direction.EAST.getDirectionId()] && !movement[Direction.WEST.getDirectionId()] &&
                    (movement[Direction.NORTH.getDirectionId()] || movement[Direction.SOUTH.getDirectionId()])) {
                playerFaceDir.setX(0);
            }
            
            player.setFaceDirection(playerFaceDir.getUnitVector());
            System.out.println("MOVE!!! vector: "+player.getFaceDirection());
            
            
            if (!movement[0] && !movement[1] && !movement[2] && !movement[3]) {
                player.setMoveVector(new Vector2(0, 0));
            } else {
                player.setMoveVector(player.getFaceDirection().clone());
            }
        }
        ArrayList<GameObject> objects = areaFactory.getArea().getObjects();
        for (int i=0; i<objects.size(); i++) {
            if (objects.get(i) instanceof Creature) {
                ((Creature)objects.get(i)).update();
            }
        }
    }
    
    public void reactToMouseInput(boolean pressed, int button, int screenX, int screenY) {
        System.out.println("Click");
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
