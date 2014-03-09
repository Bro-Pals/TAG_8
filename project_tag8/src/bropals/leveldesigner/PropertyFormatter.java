/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals.leveldesigner;

import bropals.gameobject.GameObject;
import bropals.gameobject.GrappleHookPoint;
import bropals.gameobject.Human;
import bropals.gameobject.block.Avacado;
import bropals.gameobject.block.AvacadoBin;
import bropals.gameobject.block.Block;
import bropals.gameobject.block.NormalDoor;
import bropals.gameobject.block.TeleportDoor;
import bropals.gameobject.block.Wall;
import javax.swing.JPanel;

/**
 *
 * @author Jonathon
 */
public class PropertyFormatter {

    void format(GameObject forObject, JPanel formatting) {
        formatting.removeAll(); //Clean up old components
        //Create necessary components
        if (forObject instanceof Block) {
            makeBlockFormat(forObject, formatting);
        } else
        if (forObject instanceof Wall) {
            makeWallFormat(forObject, formatting);
        } else
        if (forObject instanceof GrappleHookPoint) {
            makeGrapplePointFormat(forObject, formatting);
        } else
        if (forObject instanceof Avacado) {
            makeAvacadoFormat(forObject, formatting);
        } else
        if (forObject instanceof AvacadoBin) {
            makeAvacadoBinFormat(forObject, formatting);
        } else
        if (forObject instanceof NormalDoor) {
            makeNormalDoorFormat(forObject, formatting);
        } else
        if (forObject instanceof TeleportDoor) {
            makeTeleportDoorFormat(forObject, formatting);
        } else
        if (forObject instanceof Human) {
            makeHumanFormat(forObject, formatting);
        }
    }
    
    private void makeBlockFormat(GameObject forObject, JPanel inPanel) {
        
    }
    
    private void makeWallFormat(GameObject forObject, JPanel inPanel) {
        
    }
    
    private void makeAvacadoFormat(GameObject forObject, JPanel inPanel) {
        
    }
    
    private void makeAvacadoBinFormat(GameObject forObject, JPanel inPanel) {
        
    }
    
    private void makeHayBaleFormat(GameObject forObject, JPanel inPanel) {
        
    }
    
    private void makeGrapplePointFormat(GameObject forObject, JPanel inPanel) {
        
    }
    
    private void makeNormalDoorFormat(GameObject forObject, JPanel inPanel) {
        
    }
    
    private void makeTeleportDoorFormat(GameObject forObject, JPanel inPanel) {
        
    }
    
    private void makeHumanFormat(GameObject forObject, JPanel inPanel) {
        
    }
}
