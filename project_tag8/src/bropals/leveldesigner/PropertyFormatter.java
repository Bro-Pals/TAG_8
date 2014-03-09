/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals.leveldesigner;

import bropals.gameobject.GameObject;
import bropals.gameobject.GrappleHookPoint;
import bropals.gameobject.Human;
import bropals.gameobject.block.Avacado;
import bropals.gameobject.block.AvacadoBin;
import bropals.gameobject.block.Block;
import bropals.gameobject.block.NormalDoor;
import bropals.gameobject.block.TeleportDoor;
import bropals.gameobject.block.Wall;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Jonathon
 */
public class PropertyFormatter {

    private final int pw = 10, mpw = 4;
    private final int ph = 25, mph = 10;
    
    void format(GameObject forObject, JPanel formatting) {
        formatting.removeAll(); //Clean up old components
        //Create necessary components
        if (forObject instanceof Block) {
            makeBlockFormat((Block)forObject, formatting);
        } else
        if (forObject instanceof Wall) {
            makeWallFormat(forObject, formatting);
        } else
        if (forObject instanceof GrappleHookPoint) {
            makeGrapplePointFormat(forObject, formatting);
        } else
        if (forObject instanceof Avacado) {
            makeAvacadoFormat(forObject, formatting);
        } else
        if (forObject instanceof AvacadoBin) {
            makeAvacadoBinFormat(forObject, formatting);
        } else
        if (forObject instanceof NormalDoor) {
            makeNormalDoorFormat(forObject, formatting);
        } else
        if (forObject instanceof TeleportDoor) {
            makeTeleportDoorFormat(forObject, formatting);
        } else
        if (forObject instanceof Human) {
            makeHumanFormat(forObject, formatting);
        }
    }
    
    //Formatters: Add components to edit game objects
    
    private void makeBlockFormat(Block forObject, JPanel inPanel) {
        inPanel.setLayout(new GridLayout(3, 1, pw, ph));
        
        JPanel titlePanel = new JPanel();
        titlePanel.add(new JLabel("Block:"));
        
        //
        
        JPanel physicalPanel = new JPanel();
        physicalPanel.setLayout(new GridLayout(2, 4, mpw, mph));
        
        physicalPanel.add(new JLabel("X Position"));
        JTextField xPosInput = new JTextField("" + (int)forObject.getX() + "", 3);
        xPosInput.addActionListener(new XPositionFieldListener(forObject));
        physicalPanel.add(xPosInput);
        
        physicalPanel.add(new JLabel("Y Position"));
        JTextField yPosInput = new JTextField("" + (int)forObject.getY() + "", 3);
        yPosInput.addActionListener(new YPositionFieldListener(forObject));
        physicalPanel.add(yPosInput);
        
        physicalPanel.add(new JLabel("Width"));
        JTextField widthInput = new JTextField("" + (int)forObject.getWidth() + "", 3);
        widthInput.addActionListener(new WidthFieldListener(forObject));
        physicalPanel.add(widthInput);
        
        physicalPanel.add(new JLabel("Height"));
        JTextField heightInput = new JTextField("" + (int)forObject.getHeight() + "", 3);
        heightInput.addActionListener(new HeightFieldListener(forObject));
        physicalPanel.add(heightInput);
        
        // 
        
        JPanel renderPanel = new JPanel();
        renderPanel.setLayout(new GridLayout(1, 2, mpw, mph));
        renderPanel.add(new JLabel("Texture"));
        JTextField textureInput = new JTextField(forObject.getTextureString(), 10);
        textureInput.addActionListener(new TextureFieldListener(forObject));
        renderPanel.add(textureInput);
        
        //
        inPanel.add(titlePanel);
        inPanel.add(physicalPanel);
        inPanel.add(renderPanel);
    }
    
    private void makeWallFormat(GameObject forObject, JPanel inPanel) {
        
    }
    
    private void makeAvacadoFormat(GameObject forObject, JPanel inPanel) {
        
    }
    
    private void makeAvacadoBinFormat(GameObject forObject, JPanel inPanel) {
        
    }
    
    private void makeHayBaleFormat(GameObject forObject, JPanel inPanel) {
        
    }
    
    private void makeGrapplePointFormat(GameObject forObject, JPanel inPanel) {
        
    }
    
    private void makeNormalDoorFormat(GameObject forObject, JPanel inPanel) {
        
    }
    
    private void makeTeleportDoorFormat(GameObject forObject, JPanel inPanel) {
        
    }
    
    private void makeHumanFormat(GameObject forObject, JPanel inPanel) {
        
    }
    
    class XPositionFieldListener implements ActionListener {
        private GameObject editing;
        public XPositionFieldListener(GameObject editing) { this.editing = editing; }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                editing.setX(Integer.parseInt( ((JTextField)e.getSource()).getText() ));
            } catch(Exception ex) {
                
            }
        }
    }
    
    class YPositionFieldListener implements ActionListener {
        private GameObject editing;
        public YPositionFieldListener(GameObject editing) { this.editing = editing; }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                editing.setY(Integer.parseInt( ((JTextField)e.getSource()).getText() ));
            } catch(Exception ex) {
                
            }
        }
    }
    
    class WidthFieldListener implements ActionListener {
        private Block editing;
        public WidthFieldListener(Block editing) { this.editing = editing; }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                editing.setWidth(Integer.parseInt( ((JTextField)e.getSource()).getText() ));
            } catch(Exception ex) {
                
            }
        }
    }
    
    class HeightFieldListener implements ActionListener {
        private Block editing;
        public HeightFieldListener(Block editing) { this.editing = editing; }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                editing.setHeight(Integer.parseInt( ((JTextField)e.getSource()).getText() ));
            } catch(Exception ex) {
                
            }
        }
    }
    
    class TextureFieldListener implements ActionListener {
        private GameObject editing;
        public TextureFieldListener(GameObject editing) { this.editing = editing; }

        @Override
        public void actionPerformed(ActionEvent e) {
            editing.setTextureString(((JTextField)e.getSource()).getText());
        }
    }
}
