/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bropals.gameobject;

import bropals.gameobject.block.Avacado;
import bropals.level.Area;
import bropals.level.AvacadoManager;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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
