/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bropals.gameobject;

import bropals.debug.Debugger;
import bropals.graphics.ImageLoader;
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
        super(x, y, 34, 50, 10, Direction.getUnitVector(Direction.NORTH));
        health = 5;
        this.setTextureString("RobinCow");
    }
    
    @Override
    public BufferedImage getTexture() {
        return ImageLoader.getLoader().getImage("RobinCow", 0);
    }
    
    public void die() {
        AvacadoManager.get().losePouch(); // :(
    }
    
    public void damage() {
        if (health <= 0) return; // already dead
        Debugger.print("Ouch! Robin Cow got hurt!", Debugger.INFO);
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
