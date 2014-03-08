/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bropals.gameobject.block;

import bropals.level.Area;

/**
 * Exactly like a Block only it can't be grappled over.
 * @author Owner
 */
public class Wall extends Block {
    public Wall(Area parent, float x, float y, float width, float height) {
        super(parent, x, y, width, height);
    }
}
