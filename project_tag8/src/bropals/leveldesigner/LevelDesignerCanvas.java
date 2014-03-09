/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals.leveldesigner;

import bropals.debug.Debugger;
import static bropals.debug.Debugger.INFO;
import bropals.engine.Engine;
import bropals.gameobject.Creature;
import bropals.gameobject.GameObject;
import bropals.gameobject.Human;
import bropals.gameobject.Waypoint;
import bropals.gameobject.block.Block;
import bropals.level.Area;
import bropals.util.Vector2;
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
    private final Color waypointColor;
    
    public LevelDesignerCanvas(CowAreaLevelDesignerMain caldm) {
        this.caldm = caldm;
        bigFont = new Font(Font.SERIF, Font.BOLD, 40);
        littleFont = new Font(Font.SERIF, Font.BOLD, 12);
        waypointColor = new Color(24, 170, 0);
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
                    if (objects.get(j) instanceof Human) {
                        drawHumansWaypoints(g, (Human)objects.get(j));
                    }
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
                    try {
                        texture = objects.get(j).getTexture();
                    } catch(NullPointerException npe) {
                        Debugger.print("No texture for " + objects.get(j).getTextureString(), INFO);
                    }
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
        
    private void drawHumansWaypoints(Graphics g, Human human) {
        Waypoint[] path = human.getPatrolPath();
        int x1, x2, y1, y2;
        for (int i=0; i<path.length; i++) {
            g.setColor(waypointColor);
            g.fillOval((int)(path[i].getX()-(CowAreaLevelDesignerMain.SIZELESS_RADIUS/4)), (int)(path[i].getY()-(CowAreaLevelDesignerMain.SIZELESS_RADIUS/4)), CowAreaLevelDesignerMain.SIZELESS_RADIUS/2, CowAreaLevelDesignerMain.SIZELESS_RADIUS/2);
            x1 = (int)path[i].getX();
            y1 = (int)path[i].getY();
            if (i==(path.length-1)) { //Last?
                x2 = (int)path[0].getX();
                y2 = (int)path[0].getY();
            } else {
                x2 = (int)path[i+1].getX();
                y2 = (int)path[i+1].getY();
            }
            drawConnectingLineWithHalfwayDirectionalArrow(x1, y1, x2, y2, g, 20);
            g.setColor(Color.YELLOW);
            g.setFont(littleFont);
            g.drawString("" + path[i].getDelay() + "", (int)path[i].getX()+10, (int)path[i].getY()+10);
        }
    }
    
    private void drawConnectingLineWithHalfwayDirectionalArrow(int xi, int yi, int xf, int yf, Graphics g, float arrowSize) {
        float deltaX= (float)(xf-xi), deltaY= (float)(yf-yi);
        //Normalizeers
        float mag = (float)(Math.sqrt( (deltaX*deltaX) + (deltaY*deltaY) ));
        //Normalizerereeers
        float normalizedDeltaX = (1/mag)*deltaX;
        float normalizedDeltaY = (1/mag)*deltaY;
        
        float perpendicularX = normalizedDeltaY;
        float perpendicularY = -normalizedDeltaX;
        //Jetzt wir sind normalizieren
        float halfX, halfY, minorX, minorY;
        halfX = normalizedDeltaX*(mag/2);
        halfY = normalizedDeltaY*(mag/2);
        minorX = normalizedDeltaX*((mag/2)-(arrowSize*1.5f));
        minorY = normalizedDeltaY*((mag/2)-(arrowSize*1.5f));
        float[] addVector = new float[] {
            (arrowSize)*(perpendicularX),(arrowSize)*(perpendicularY)
        };
        int[] leftWingPoint = new int[] {
            (int)(addVector[0]+minorX+xi),
            (int)(addVector[1]+minorY+yi),
        };
        int[] rightWingPoint = new int[] {
            (int)((-addVector[0])+minorX+xi),
            (int)((-addVector[1])+minorY+yi),
        };
        //Now draw the lines!
        g.setColor(waypointColor);
        g.drawLine(xi, yi, xf, yf);
        g.drawLine((int)(halfX+xi), (int)(halfY+yi), leftWingPoint[0], leftWingPoint[1]);
        g.drawLine((int)(halfX+xi), (int)(halfY+yi), rightWingPoint[0], rightWingPoint[1]);
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
