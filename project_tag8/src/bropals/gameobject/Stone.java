/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bropals.gameobject;

import bropals.debug.Debugger;
import bropals.engine.Engine;
import bropals.gameobject.block.Block;
import bropals.graphics.ImageLoader;
import bropals.util.Vector2;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Owner
 */
public class Stone extends GameObject {
    
    Vector2 stoneMoveVector;
    float STONE_SPEED = 25;
    
    public Stone(float x, float y, Vector2 moveVector) {
        super(x, y);
        this.stoneMoveVector = moveVector;
    }
    
    public void update() {
        if (getParent() == null || getParent().getObjects() == null) return;
        
        //Debugger.print("The stone is getting updated!", Debugger.INFO);
        
        //System.out.println("The stone's velocity: "+stoneMoveVector.toString());
        setX((float)(getX() + (stoneMoveVector.getX() * STONE_SPEED)));
        setY((float)(getY() + (stoneMoveVector.getY() * STONE_SPEED)));
        
        for (int i=0; i<getParent().getObjects().size(); i++) {
            GameObject obj = (GameObject) getParent().getObjects().get(i);
            if (obj == this) continue;
            Line2D.Float travelLine = new Line2D.Float(getX(), getY(), 
                    getX() - (float)stoneMoveVector.getX(), getY() - (float)stoneMoveVector.getY());
            if (obj instanceof Block) {
               if (((Block)obj).getRectangle2D().intersectsLine(travelLine)) {
                   setParent(null); // remove if it smacks into a wall
                   break;
               }
            }
            if (obj instanceof Player) {
                if ((new Rectangle2D.Float(((Player)obj).getX(), 
                        ((Player)obj).getY(), ((Player)obj).getWidth(), 
                        ((Player)obj).getHeight())).intersectsLine(travelLine)) {
                    ((Player)obj).damage();
                    setParent(null);
                    break;
                }
            }
        }
        
        // remove if out of the screen bounds
        if (getX() < 0 || getX() > Engine.SCREEN_WIDTH || getY() < 0 || getY() > Engine.SCREEN_HEIGHT) setParent(null);
    }

    @Override
    public BufferedImage getTexture() {
        return ImageLoader.getLoader().getImage("Blocks", 9);
    }
}
