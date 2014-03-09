/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bropals.gameobject;

/**
 *
 * @author Owner
 */
public interface Interactable {
    void interact(GameObject interactee);
    float getInteractDistance();
    void setInteractDistance(float distance);
}
