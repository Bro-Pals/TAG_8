/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals.leveldesigner;

import bropals.gameobject.GameObject;
import bropals.level.Area;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author Jonathon
 */
public class LevelDesignerCanvas extends Canvas {

    private Area drawing;
    private final CowAreaLevelDesignerMain caldm;
    private Font bigFont;
    
    public LevelDesignerCanvas(CowAreaLevelDesignerMain caldm) {
        this.caldm = caldm;
        bigFont = new Font(Font.SERIF, Font.BOLD, 40);
    }
    
    public void setDrawing(Area area) {
        this.drawing = area;
    }
    
    @Override
    public void paint(Graphics g) {
        clear(g, Color.WHITE);
        drawArea(g);
        g.dispose();
    }
    
    private void drawArea(Graphics g) {
        if (drawing!=null) {
            ArrayList<GameObject> objects = drawing.getObjects();
            for (int j=0; j<objects.size(); j++) {
                if (objects.get(j)!=null) {
                    if (objects.get(j).hashCode()==caldm.getSelectedGameObject().hashCode()) {
                        //Blue outline to say this is being selected
                        g.setColor(Color.BLUE);
                        g.fillRect((int)(objects.get(j).getX())-5, (int)(objects.get(j).getY())-5, objects.get(j).getTexture().getWidth()+10, objects.get(j).getTexture().getHeight()+10);
                    }
                    g.drawImage(objects.get(j).getTexture(), (int)objects.get(j).getX(), (int)objects.get(j).getY(), null);
                    //Addition debug rendering here
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
    
    private void clear(Graphics g, Color clearColor) {
        g.setColor(clearColor);
        g.fillRect(0, 0, getPreferredSize().width, getPreferredSize().height);
    }
  
}
