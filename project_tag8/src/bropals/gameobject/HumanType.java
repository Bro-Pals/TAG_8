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
