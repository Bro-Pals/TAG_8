/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bropals.gameobject;

import bropals.level.Area;
import java.awt.image.BufferedImage;

/**
 *
 * @author Owner
 */
public class Player extends GameObject {
    
    public Player(Area parent, float x, float y) {
        super(parent, x, y);
    }
    
    @Override
    public BufferedImage getTexture() {
        return null;
    }
    
}
