/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals.leveldesigner;

import bropals.debug.Debugger;
import bropals.gameobject.Creature;
import bropals.gameobject.GameObject;
import bropals.gameobject.block.Block;
import bropals.level.Area;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Jonathon
 */
public class LevelDesignerCanvas extends Canvas {

    private Area drawing;
    private final CowAreaLevelDesignerMain caldm;
    private final Font bigFont, littleFont;
    
    public LevelDesignerCanvas(CowAreaLevelDesignerMain caldm) {
        this.caldm = caldm;
        bigFont = new Font(Font.SERIF, Font.BOLD, 40);
        littleFont = new Font(Font.SERIF, Font.BOLD, 12);
        setFocusable(true);
    }
    
    public void setDrawing(Area area) {
        this.drawing = area;
    }
    
    @Override
    public void paint(Graphics g) {
        clear(g, Color.WHITE);
        drawArea(g);
        drawGrid(g);
        g.dispose();
    }
    
    private void drawArea(Graphics g) {
        int sizelessRadius = CowAreaLevelDesignerMain.SIZELESS_RADIUS;
        if (drawing!=null) {
            ArrayList<GameObject> objects = drawing.getObjects();
            for (int j=0; j<objects.size(); j++) {
                if (objects.get(j)!=null) {
                    if (caldm.getSelectedGameObject()!=null) {
                        if (objects.get(j).hashCode()==caldm.getSelectedGameObject().hashCode()) {
                            //Blue outline to say this is being selected
                            g.setColor(Color.BLUE);
                            if (caldm.getSelectedGameObject() instanceof Block) {
                                g.fillRect((int)(caldm.getSelectedGameObject().getX())-5, (int)(caldm.getSelectedGameObject().getY())-5, (int)((Block)caldm.getSelectedGameObject()).getWidth()+10, (int)((Block)caldm.getSelectedGameObject()).getHeight()+10);
                            } else 
                            if (caldm.getSelectedGameObject() instanceof Creature) {
                               Creature c =((Creature)caldm.getSelectedGameObject());
                               g.fillOval( (int)(c.getX()) - 5 , (int)(c.getY()) - 5, (int)(c.getWidth() + 10) , (int)(c.getHeight() + 10)); 
                            }
                            else {
                                g.fillOval((int)(objects.get(j).getX())-(sizelessRadius/2)-5, (int)(objects.get(j).getY())-(sizelessRadius/2)-5, sizelessRadius+10, sizelessRadius+10);
                            }
                        }
                    }
                    BufferedImage texture = null;
                    texture = objects.get(j).getTexture();
                    if (texture==null) {
                        //Error graphic
                        g.setColor(Color.RED);
                        if (objects.get(j) instanceof Block) {
                            g.fillRect((int)objects.get(j).getX(), (int)objects.get(j).getY(), (int)(((Block)objects.get(j)).getWidth()), (int)(((Block)objects.get(j)).getHeight()));
                        } else {
                            g.fillOval((int)(objects.get(j).getX())-(sizelessRadius/2), (int)(objects.get(j).getY())-(sizelessRadius/2), sizelessRadius, sizelessRadius);
                        }
                        g.setColor(Color.BLACK);
                        g.setFont(littleFont);
                        g.drawString("Need Texture", (int)(objects.get(j).getX()), (int)(objects.get(j).getY()));
                        Debugger.print("Drew TEXTURELESS object", Debugger.INFO);
                    } else {
                        g.drawImage(texture, (int)objects.get(j).getX(), (int)objects.get(j).getY(), null);
                        Debugger.print("Drew textured object", Debugger.INFO);
                    }
                    //Addition debug rendering here
                } else {
                    Debugger.print("Null object in area!?", ERROR);
                }
            }
        } else {
            g.setFont(bigFont);
            g.setColor(Color.BLACK);
            g.drawString("Not editing an Area!", 200, 200);
            g.drawString("Create a new Area with File->Create", 50, 300);
            g.drawString("Open an existing area with File->Open", 50, 400);
        }
    }
    
    private void drawGrid(Graphics g) {
        Grid grid = caldm.getGrid();
        int spacing = grid.getSpacing();
        int timesX = (int)Math.ceil(getPreferredSize().width/spacing);
        int timesY = (int)Math.ceil(getPreferredSize().height/spacing);
        if (caldm.gridIsEnabled()) {
            g.setColor(Color.BLACK);
            for (int i=0; i<timesX; i++) {
                //Draw black lines to represent grid
                //Vertically
                g.drawLine((i*spacing), 0, (i*spacing), getPreferredSize().height);
            }
            for (int j=0; j<timesY; j++) {
                //Draw black lines to represent grid
                //Horizontally
                g.drawLine(0, (j*spacing), getPreferredSize().width, (j*spacing));
            }
        }
    }
    
    private void clear(Graphics g, Color clearColor) {
        g.setColor(clearColor);
        g.fillRect(0, 0, getPreferredSize().width, getPreferredSize().height);
    }
  
}
