/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals.graphics;

import bropals.gameobject.Creature;
import bropals.gameobject.GameObject;
import bropals.gameobject.GrappleHookPoint;
import bropals.gameobject.Interactable;
import bropals.gameobject.block.Avacado;
import bropals.gameobject.block.AvacadoBin;
import bropals.gameobject.block.Block;
import bropals.gameobject.block.HayBale;
import bropals.gameobject.block.NormalDoor;
import bropals.level.Area;
import bropals.level.AreaRunner;
import bropals.level.AvacadoManager;
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
        //g2d.setTransform(identity);
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
        //g2d.setTransform(identity);
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
                if (((Creature)drawing).isHiding()) return; // don't draw a hiding creature!
                //System.out.println("Drew a creature!");
                //working.setToRotation(((Creature)drawing).getAngleFacing());
               // working.translate(drawing.getX(), drawing.getY());
               // g2d.setTransform(working);
                //System.out.println("This creatures is at an angle of " + (((Creature)drawing).getAngleFacing()/Math.PI) + " PI");
                g2d.drawImage(drawing.getTexture(), (int)(drawing.getX()), 
                        (int)(drawing.getY()), null);
            } else if (drawing.getTexture() != null) {
                //g2d.setTransform(identity);
                g2d.drawImage(drawing.getTexture(), (int)drawing.getX(), (int)drawing.getY(), null);
            } else if (drawing.getTexture() == null && drawing instanceof Block) {
                g2d.setColor(Color.BLACK);
                //g2d.setTransform(identity);
                g2d.fillRect((int)drawing.getX(), (int)drawing.getY(), (int)((Block)drawing).getWidth(), (int)((Block)drawing).getHeight());
            }
        }
    }
    
    private void drawActionIcon(Area area) {
        if (area.getPlayer() != null && area.getPlayer().getSelectedInteractable() != null) {
            Interactable in = area.getPlayer().getSelectedInteractable();
            BufferedImage drawnImage = null;
            //System.out.println("We are somehow drawing an interactable");
            if (in instanceof NormalDoor) {
                if (((NormalDoor)in).isOpen()) {
                    drawnImage = ImageLoader.getLoader().getImage("ActionIcons", 1);
                } else {
                    drawnImage = ImageLoader.getLoader().getImage("ActionIcons", 0);
                }
            } else if (in instanceof HayBale) {
                if (area.getPlayer().isHiding()) {
                    drawnImage = ImageLoader.getLoader().getImage("ActionIcons", 3);
                } else {
                    drawnImage = ImageLoader.getLoader().getImage("ActionIcons", 2);
                }
            } else if (in instanceof Avacado) {
                drawnImage = ImageLoader.getLoader().getImage("ActionIcons", 4);
            } else if (in instanceof GrappleHookPoint) {
                drawnImage = ImageLoader.getLoader().getImage("ActionIcons", 5);
            } else if (in instanceof AvacadoBin) {
                drawnImage = ImageLoader.getLoader().getImage("ActionIcons", 6);
            }
            if (drawnImage != null) {
              g2d.drawImage(drawnImage, (int)(((GameObject)in).getX()), (int)(((GameObject)in).getY())-80, null);  
            }
        }
    }

    /**
     * Draws the GUI info stored in AreaRunner.
     * @param runner the AreaRunner object
     */
    public void drawGui(AreaRunner runner) {
        g2d.setColor(Color.WHITE);
        g2d.drawString("Avacados: "+AvacadoManager.get().getAvacadosCollectedCount(), 400, 50);
    }
}
