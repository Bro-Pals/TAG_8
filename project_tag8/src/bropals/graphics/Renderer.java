/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals.graphics;

import bropals.debug.Debugger;
import bropals.engine.Engine;
import bropals.gameobject.Creature;
import bropals.gameobject.GameObject;
import bropals.gameobject.GrappleHookPoint;
import bropals.gameobject.Human;
import bropals.gameobject.Interactable;
import bropals.gameobject.Player;
import bropals.gameobject.block.Avacado;
import bropals.gameobject.block.AvacadoBin;
import bropals.gameobject.block.Block;
import bropals.gameobject.block.HayBale;
import bropals.gameobject.block.NormalDoor;
import bropals.level.Area;
import bropals.level.AreaRunner;
import bropals.level.AvacadoManager;
import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
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
                // thanks stack overflow!!!
                float rotationRequired = ((Creature)drawing).getAngleFacing();
                float locationX = ((Creature)drawing).getTexture().getWidth() / 2;
                float locationY = ((Creature)drawing).getTexture().getHeight() / 2;
                AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
                AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

                // Drawing the rotated image at the required drawing locations
                try {
                    g2d.drawImage(op.filter(((Creature)drawing).getTexture(), null), (int)((Creature)drawing).getX(), (int)((Creature)drawing).getY(), null);
                } catch(Exception e) {
                    Debugger.print(e.toString(), Debugger.ERROR);
                    e.printStackTrace();
                    g2d.drawImage(((Creature)drawing).getTexture(), (int)((Creature)drawing).getX(), (int)((Creature)drawing).getY(), null);
                }
                
                if (drawing instanceof Human && ((Human)drawing).isAlert()) {
                    g2d.drawImage(ImageLoader.getLoader().getImage("Blocks", 8), (int)drawing.getX(), (int)drawing.getY() - 60, null);
                }
                //System.out.println("This creatures is at an angle of " + (((Creature)drawing).getAngleFacing()) + " radians");
                if (drawing instanceof Player && ((Player)drawing).getHookUsing() != null && ((Player)drawing).isGrappling()) {
                    Player p = (Player)drawing;
                    g2d.setColor(new Color(160, 60, 0));
                    g2d.setStroke(new BasicStroke(3));
                    g2d.drawLine((int)p.getCenterX(), (int)p.getCenterY(), 
                            (int)p.getHookUsing().getCenterX(), (int)p.getHookUsing().getCenterY());
                }
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
        g2d.setFont(new Font("Impact", Font.PLAIN, 20));
        g2d.drawString("Avacados Collected / In Pouch / Remaining : "+AvacadoManager.get().getAvacadosCollectedNum() + 
                " / "+AvacadoManager.get().getAvacadosInPouchNum()+" / "+AvacadoManager.get().getAvacadosInWorldNum(), 300, 40);
        for (int h=0; h<runner.getPlayer().getHealth(); h++) {
            int rowWidth = (80 * runner.getPlayer().getHealth());
            int leftOfRow = (Engine.SCREEN_WIDTH/2) - (rowWidth/2);
            g2d.drawImage(ImageLoader.getLoader().getImage("HeartImage"), leftOfRow + (80 * h), Engine.SCREEN_HEIGHT - 60, null);
        }
        
        if (AvacadoManager.get().getAvacadosInPouchNum() == 0 && AvacadoManager.get().getAvacadosInWorldNum() == 0 &&
                AvacadoManager.get().getAvacadosCollectedNum() == Avacado.avacadosMade) {
            System.out.println("YOU WIN!");
            g2d.drawImage(ImageLoader.getLoader().getImage("WinScreen"), 0, 0, null);
        }
    }
}
