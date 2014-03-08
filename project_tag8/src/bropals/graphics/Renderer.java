/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals.graphics;

import bropals.gameobject.Creature;
import bropals.gameobject.GameObject;
import bropals.level.Area;
import bropals.level.AreaRunner;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Jonathon
 */
public class Renderer {
    
    private Canvas canvas;
    private BufferStrategy bs;
    private Graphics2D g2d;
    private AffineTransform identity, working;
    
    public Renderer(Canvas canvas) {
        this.canvas=canvas;
        identity = new AffineTransform();
        identity.setToIdentity();
        working = new AffineTransform();
        working.setToIdentity();
    }
    
    public void setupRenderer() {
        canvas.createBufferStrategy(2);
        bs = canvas.getBufferStrategy();
    }
    
    /**
     * Disposes the graphics and cleans things up after drawing a frame.
     */
    public void finishUpDrawing() {
        this.g2d.dispose();
    }
    
    /**
     * Swaps the buffers so the frame that was just drawn gets shown.
     */
    public void swapBuffers() {
        bs.show();
    }
    
    /**
     * Tells the renderer to get the graphics object to start drawing a frame.
     */
    public void startDrawing() {
        this.g2d = (Graphics2D)bs.getDrawGraphics();
    }
    
    /**
     * Fills a rectangle over the old frame to start on a fresh surface.
     * @param clearColor The color of the frame clear
     * @param screenWidth the canvas width
     * @param screenHeight the canvas height
     */
    public void clear(Color clearColor, int screenWidth, int screenHeight) {
        g2d.setTransform(identity);
        this.g2d.setColor(clearColor);
        this.g2d.fillRect(0, 0, screenWidth, screenHeight);
    }

    /**
     * Draws the given area to the screen.
     * @param currentArea the area to draw
     */
    public void drawArea(Area currentArea) {
        drawAreaBackground(currentArea);
        drawGameObjectSprites(currentArea);
        drawActionIcon(currentArea);
    }
    
    private void drawAreaBackground(Area area) {
        g2d.setTransform(identity);
        //Draw the background tile in area
        BufferedImage bg = area.getBackgroundTile();
        int xTimes = area.getBgTimesX();
        int yTimes = area.getBgTimesY();
        for (int x=0; x<xTimes; x++) {
            for (int y=0; y<yTimes; y++) {
                this.g2d.drawImage(bg, (x*bg.getWidth()), (y*bg.getHeight()), null);
            }
        }
    }
    
    private void drawGameObjectSprites(Area area) {
        ArrayList<GameObject> objs = area.getObjects();
        for (GameObject drawing : objs) {
            if (drawing instanceof Creature) {
                working.setToRotation(((Creature)drawing).getAngleFacing());
                working.translate(drawing.getX(), drawing.getY());
                g2d.setTransform(working);
                g2d.drawImage(drawing.getTexture(), -(int)(((Creature)drawing).getRadius()/2), -(int)(((Creature)drawing).getRadius()/2), null);
            } else {
                g2d.setTransform(identity);
                g2d.drawImage(drawing.getTexture(), (int)drawing.getX(), (int)drawing.getY(), null);
            }
        }
    }
    
    private void drawActionIcon(Area area) {
        
    }

    /**
     * Draws the GUI info stored in AreaRunner.
     * @param runner the AreaRunner object
     */
    public void drawGui(AreaRunner runner) {
        
    }
}
