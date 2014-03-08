/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bropals.util;

/**
 *
 * @author Owner
 */
public class Vector2 {
    private double x, y;
    
    public Vector2() {
        this.x=0;
        this.y=0;
    }
    
    public Vector2(double x, double y) {
        this.x=x;
        this.y=y;
    }
    
    @Override
    public Vector2 clone() {
        return new Vector2(this.x, this.y);
    }
    
    public Vector2 getUnitVector() {
        return new Vector2(x/Math.abs(this.getMagnitude()), y/Math.abs(this.getMagnitude()));
    }
    
    public void equal(Vector2 otherV2) {
        this.x = otherV2.getX();
        this.y = otherV2.getY();
    }
    
    public void scale(double amount) {
        this.x = this.x * amount;
        this.y = this.y * amount;
    }
    
    public void add(Vector2 v) {
        this.x = this.x + v.getX();
        this.y = this.y + v.getY();
    }
    
    public void add(double x, double y) {
        this.x = this.x + x;
        this.y = this.y + y;
    }
    
    public double getMagnitude() {
        return Math.sqrt((x*x)+(y*y));
    }
    
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
    
}
