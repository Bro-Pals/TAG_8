/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals.level;

import bropals.debug.Debugger;

/**
 *
 * @author Jonathon
 */
public class AreaFactory {
    
    private final Area theArea;
    
    public Area getArea() { return theArea; }
    
    public AreaFactory() {
        theArea = new Area();
    }
    
    /**
     * Makes the area that matches the id. This is a high maintenence object for hard-coding the areas.
     * @param id the id of the area that you want.
     */
    public void setArea(int id) {
        theArea.getObjects().clear();
        theArea.defaults();
        switch(id) {
            
            case -1:
                break;
            default:
                Debugger.print("Need constructor for ID: " + id + " in AreaFactory", Debugger.ERROR);
        }
    }
}
