
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bropals.gameobject.block;

import bropals.gameobject.GameObject;
import bropals.gameobject.Interactable;
import bropals.graphics.ImageLoader;
import bropals.level.Area;
import bropals.level.AvacadoManager;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Owner
 */
public class AvacadoBin extends Block implements Interactable {
    
    private float interactDistance;
    
    public AvacadoBin(float x, float y, float width, float height) {
        super( x, y, width, height);
        interactDistance = 80;
    }

    @Override
    public void interact(GameObject instance) {
        AvacadoManager.get().depositAvacadoPouch();
    }

    @Override
    public BufferedImage getTexture() {
        return ImageLoader.getLoader().getImage("Blocks", 7);
    }
    
    @Override
    public float getInteractDistance() {
        return interactDistance; // some random number
    }
    
    @Override
    public String toString() {
        String str = "Avacado Bin:\n" + 
        "   X: " + getX() + "\n"+
        "   Y: " + getY() + "\n"+
        "   Texture String: " + getTextureString()+ "\n"+
        "   Width: " + getWidth()+ "\n"+
        "   Height: " + getHeight()+ "\n"+
        "";
        return str;
    }

    @Override
    public void setInteractDistance(float distance) {
        interactDistance = distance;
    }
}
