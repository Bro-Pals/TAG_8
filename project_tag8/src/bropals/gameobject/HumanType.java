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
public enum HumanType {
    ROCK_THROWER, PITCHFORK;

    public static HumanType fromString(String string) {
        switch(string) {
            case "ROCKTHROWER":
                return ROCK_THROWER;
            case "PITCHFORK":
                return PITCHFORK;
            default:
                return null;
        }
    }
    
    @Override
    public String toString() {
        switch(this) {
            case ROCK_THROWER:
                return "ROCKTHROWER";
            case PITCHFORK:
                return "PITCHFORK";
            default:
            return "";
        }
    }
}
