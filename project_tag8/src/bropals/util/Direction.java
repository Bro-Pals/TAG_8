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
    
    /**
     * Get a number representing this direction
     * @return The direction's number ID
     */
    public int getDirectionId() {
        switch(this) {
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
    
    /**
     * Get a number representing a direction
     * @param d The Direction you want to find the number ID of
     * @return The direction's number ID
     */
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
    
    /**
     * Get a direction's unit vector (vector magnitude 1)
     * @param d The direction you want the unit vector of
     * @return The direction's unit vector
     */
    public static Vector2 getUnitVector(Direction d) {
        switch(d) {
            case NORTH:
                return new Vector2(0, 1);
            case SOUTH:
                return new Vector2(0, -1);
            case EAST:
                return new Vector2(1, 0);
            case WEST:
                return new Vector2(-1, 0);
        }
        return new Vector2();
    }
    
    /**
     * Get this direction's unit vector (magnitude is 1)
     * @return This direction's unit vector.
     */
    public Vector2 getUnitVector() {
        switch(this) {
            case NORTH:
                return new Vector2(0, 1);
            case SOUTH:
                return new Vector2(0, -1);
            case EAST:
                return new Vector2(1, 0);
            case WEST:
                return new Vector2(-1, 0);
        }
        return new Vector2();
    }
}
