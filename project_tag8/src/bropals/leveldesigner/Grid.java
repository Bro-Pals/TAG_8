/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals.leveldesigner;

/**
 *
 * @author Jonathon
 */
class Grid {
    
    public static final int MAX_SPACING = 100, MIN_SPACING = 10;
    
    private boolean enabled;
    private int gridSpacing;
    private int timesX, timesY;

    public int getTimesX() {
        return timesX;
    }

    public int getTimesY() {
        return timesY;
    }
    
    public Grid() {
        enabled = false;
        gridSpacing = 10;
    }
    
    boolean isEnabled() {
        return enabled;
    }
    
    void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    int getSpacing() {
        return gridSpacing;
    }
    
    void setSpacing(int spacing, int canvasWidth, int canvasHeight) {
        this.gridSpacing = spacing;
    }
}
