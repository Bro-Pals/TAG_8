/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals.graphics;

import bropals.level.Area;
import bropals.level.AreaRunner;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

/**
 *
 * @author Jonathon
 */
public class Renderer {
    
    private Canvas canvas;
    private BufferStrategy bs;
    private Graphics2D g2d;
    
    public Renderer(Canvas canvas) {
        this.canvas=canvas;
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
        
    }
    
    private void drawGameObjectSprites(Area area) {
        
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
