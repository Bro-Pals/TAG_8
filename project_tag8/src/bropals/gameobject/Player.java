/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bropals.gameobject;

import bropals.level.Area;
import bropals.level.AvacadoManager;
import bropals.util.Direction;
import bropals.util.Vector2;
import java.awt.image.BufferedImage;

/**
 *
 * @author Owner
 */
public class Player extends Creature {
    
    private int health;
    private final int maxHealth = 5;
    
    public Player(float x, float y) {
        super(x, y, 50, 50, 10, Direction.getUnitVector(Direction.NORTH));
        health = 5;
    }
    
    public void die() {
        // be teleported to the barn
        AvacadoManager.get().loseAvacadosInPouch();
    }
    
    public void damage() {
        health--;
        if (health <= 0) die();
    }
    
    public int getHealth() {
        return health;
    }
    
    public void resetHealth() {
        health = maxHealth;
    }
}
