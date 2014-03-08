/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals.util;

/**
 *
 * @author Jonathon
 */
public enum Direction {
    NORTH, SOUTH, EAST, WEST;
    
    public static int getDirectionId(Direction d) {
        switch(d) {
            case NORTH:
                return 0;
            case SOUTH:
                return 1;
            case EAST:
                return 2;
            case WEST:
                return 3;
        }
        return -1;
    }
}
