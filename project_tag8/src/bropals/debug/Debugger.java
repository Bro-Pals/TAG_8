/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals.debug;

/**
 *
 * @author Jonathon
 */
public class Debugger {
    
    public static final int ERROR = 0, INFO = 1;
    public static final boolean using = true;
    
    public static void print(String message, int type) {
        if (using) {
            switch(type) {
                case ERROR:
                    System.out.println("ERROR: " + message);
                case INFO:
                    System.out.println("INFO: " + message);
                default:
            }
        }
    }
}
