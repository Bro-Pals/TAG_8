/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals.datafiles;

/**
 *
 * @author Jonathon
 */
public abstract class GameObjectIOMachine<T> {
    
    public abstract String makeDataLine(T object);
    public abstract T readDataLine(String line);
}
