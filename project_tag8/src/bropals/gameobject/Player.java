/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bropals.gameobject;

import bropals.level.Area;
import bropals.level.AvacadoManager;
import java.awt.image.BufferedImage;

/**
 *
 * @author Owner
 */
public class Player extends GameObject {
    
    private int health;
    
    public Player(Area parent, float x, float y) {
        super(parent, x, y);
    }
    
    @Override
    public BufferedImage getTexture() {
        return null;
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
    
}
