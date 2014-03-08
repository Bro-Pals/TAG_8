/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bropals.debug;

import bropals.level.AreaRunner;
import java.awt.Component;

/**
 *
 * @author Owner
 */
public class EventSimulator {
   
    private static final EventSimulator es = new EventSimulator();
    private AreaRunner source;
    
    public EventSimulator() {
        
    }
    
    public static EventSimulator getSimulator() {
        return es;
    }
    
    
    public void setInputSource(AreaRunner source) {
        this.source = source;
    }
    
    public void simulateKeyPress(boolean pressed, int keyCode) {
        source.reactToKeyInput(pressed, keyCode);
    }
    
    public void simulateMousePress(boolean pressed, int button, int x, int y) {
        source.reactToMouseInput(pressed, button, x, y);
    }

}
