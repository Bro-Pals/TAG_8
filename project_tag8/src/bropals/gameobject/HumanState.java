/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals.gameobject;

/**
 *
 * @author Jonathon
 */
public enum HumanState {
    PATROLLING, ALERT;

    public static HumanState fromString(String string) {
        switch(string) {
            case "PATROLLNG":
                return PATROLLING;
            case "ALERT":
                return ALERT;
            default:
            return null;
        }
    }
    
    @Override
    public String toString() {
        switch(this) {
            case PATROLLING:
                return "PATROLLNG";
            case ALERT:
                return "ALERT";
            default:
            return "";
        }
    }
}
