/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals.leveldesigner;

import bropals.debug.Debugger;
import bropals.gameobject.GameObject;
import bropals.gameobject.GrappleHookPoint;
import bropals.gameobject.Human;
import bropals.gameobject.block.Avacado;
import bropals.gameobject.block.AvacadoBin;
import bropals.gameobject.block.Block;
import bropals.gameobject.block.HayBale;
import bropals.gameobject.block.NormalDoor;
import bropals.gameobject.block.TeleportDoor;
import bropals.gameobject.block.Wall;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
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
    
    void format(GameObject forObject, JPanel formatting, JButton acceptButton) {
        formatting.removeAll(); //Clean up old components
        //Create necessary components
        
        if (forObject instanceof Wall) {
            makeWallFormat((Wall)forObject, formatting, acceptButton);
        } else
        if (forObject instanceof GrappleHookPoint) {
            makeGrapplePointFormat((GrappleHookPoint)forObject, formatting, acceptButton);
        } else
        if (forObject instanceof Avacado) {
            makeAvacadoFormat((Avacado)forObject, formatting, acceptButton);
        } else
        if (forObject instanceof AvacadoBin) {
            makeAvacadoBinFormat((AvacadoBin)forObject, formatting, acceptButton);
        } else
        if (forObject instanceof NormalDoor) {
            makeNormalDoorFormat(forObject, formatting, acceptButton);
        } else
        if (forObject instanceof TeleportDoor) {
            makeTeleportDoorFormat(forObject, formatting, acceptButton);
        } else
        if (forObject instanceof Human) {
            makeHumanFormat(forObject, formatting, acceptButton);
        } else
        if (forObject instanceof HayBale) {
            makeHayBaleFormat((HayBale)forObject, formatting, acceptButton);
        } else
        if (forObject instanceof Block) {
            makeBlockFormat((Block)forObject, formatting, acceptButton);
        }
    }
    
    //Formatters: Add components to edit game objects
    
    private void makeBlockFormat(final Block forObject, JPanel inPanel, JButton acceptButton) {
        inPanel.setLayout(new GridLayout(3, 1, pw, ph));
        
        JPanel titlePanel = new JPanel();
        titlePanel.add(new JLabel("Block:"));
        
        //
        
        JPanel physicalPanel = new JPanel();
        physicalPanel.setLayout(new GridLayout(2, 4, mpw, mph));
        
        physicalPanel.add(new JLabel("X Position"));
        final JTextField xPosInput = new JTextField("" + (int)forObject.getX() + "", 3);
      //  xPosInput.addActionListener(new XPositionFieldListener(forObject));
        physicalPanel.add(xPosInput);
        
        physicalPanel.add(new JLabel("Y Position"));
        final JTextField yPosInput = new JTextField("" + (int)forObject.getY() + "", 3);
      //  yPosInput.addActionListener(new YPositionFieldListener(forObject));
        physicalPanel.add(yPosInput);
        
        physicalPanel.add(new JLabel("Width"));
        final JTextField widthInput = new JTextField("" + (int)forObject.getWidth() + "", 3);
      //  widthInput.addActionListener(new WidthFieldListener(forObject));
        physicalPanel.add(widthInput);
        
        physicalPanel.add(new JLabel("Height"));
        final JTextField heightInput = new JTextField("" + (int)forObject.getHeight() + "", 3);
       // heightInput.addActionListener(new HeightFieldListener(forObject));
        physicalPanel.add(heightInput);
        
        // 
        
        JPanel renderPanel = new JPanel();
        renderPanel.setLayout(new GridLayout(1, 2, mpw, mph));
        renderPanel.add(new JLabel("Texture"));
        final JTextField textureInput = new JTextField(forObject.getTextureString(), 10);
       // textureInput.addActionListener(new TextureFieldListener(forObject));
        renderPanel.add(textureInput);
        
        //
        inPanel.add(titlePanel);
        inPanel.add(physicalPanel);
        inPanel.add(renderPanel);
        
        if (acceptButton!=null) {
            acceptButton.addActionListener(new  ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    forObject.setX(Integer.parseInt(xPosInput.getText()));
                    forObject.setY(Integer.parseInt(yPosInput.getText()));
                    forObject.setWidth(Integer.parseInt(widthInput.getText()));
                    forObject.setHeight(Integer.parseInt(heightInput.getText()));
                    forObject.setTextureString(textureInput.getText());
                }
            });
        }
        Debugger.print("Made property panel for Block", Debugger.INFO);
    }
    
    private void makeWallFormat(final Wall forObject, JPanel inPanel, JButton acceptButton) {
        inPanel.setLayout(new GridLayout(3, 1, pw, ph));
        
        JPanel titlePanel = new JPanel();
        titlePanel.add(new JLabel("Wall:"));
        
        //
        
        JPanel physicalPanel = new JPanel();
        physicalPanel.setLayout(new GridLayout(2, 4, mpw, mph));
        
        physicalPanel.add(new JLabel("X Position"));
        final JTextField xPosInput = new JTextField("" + (int)forObject.getX() + "", 3);
        //xPosInput.addActionListener(new XPositionFieldListener(forObject));
        physicalPanel.add(xPosInput);
        
        physicalPanel.add(new JLabel("Y Position"));
        final JTextField yPosInput = new JTextField("" + (int)forObject.getY() + "", 3);
      //  yPosInput.addActionListener(new YPositionFieldListener(forObject));
        physicalPanel.add(yPosInput);
        
        physicalPanel.add(new JLabel("Width"));
        final JTextField widthInput = new JTextField("" + (int)forObject.getWidth() + "", 3);
       // widthInput.addActionListener(new WidthFieldListener(forObject));
        physicalPanel.add(widthInput);
        
        physicalPanel.add(new JLabel("Height"));
        final JTextField heightInput = new JTextField("" + (int)forObject.getHeight() + "", 3);
       // heightInput.addActionListener(new HeightFieldListener(forObject));
        physicalPanel.add(heightInput);
        
        // 
        
        JPanel renderPanel = new JPanel();
        renderPanel.setLayout(new GridLayout(1, 2, mpw, mph));
        renderPanel.add(new JLabel("Texture"));
        final JTextField textureInput = new JTextField(forObject.getTextureString(), 10);
       // textureInput.addActionListener(new TextureFieldListener(forObject));
        renderPanel.add(textureInput);
        
        //
        inPanel.add(titlePanel);
        inPanel.add(physicalPanel);
        inPanel.add(renderPanel);
        
        if (acceptButton!=null) {
            acceptButton.addActionListener(new  ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    forObject.setX(Integer.parseInt(xPosInput.getText()));
                    forObject.setY(Integer.parseInt(yPosInput.getText()));
                    forObject.setWidth(Integer.parseInt(widthInput.getText()));
                    forObject.setHeight(Integer.parseInt(heightInput.getText()));
                    forObject.setTextureString(textureInput.getText());
                }
            });
        }
        Debugger.print("Made property panel for Wall", Debugger.INFO);
    }
    
    private void makeAvacadoFormat(final Avacado forObject, JPanel inPanel, JButton acceptButton) {
        inPanel.setLayout(new GridLayout(3, 1, pw, ph));
        
        JPanel titlePanel = new JPanel();
        titlePanel.add(new JLabel("Avacado:"));
        
        //
        
        JPanel physicalPanel = new JPanel();
        physicalPanel.setLayout(new GridLayout(2, 4, mpw, mph));
        
        physicalPanel.add(new JLabel("X Position"));
        final JTextField xPosInput = new JTextField("" + (int)forObject.getX() + "", 3);
      //  xPosInput.addActionListener(new XPositionFieldListener(forObject));
        physicalPanel.add(xPosInput);
        
        physicalPanel.add(new JLabel("Y Position"));
        final JTextField yPosInput = new JTextField("" + (int)forObject.getY() + "", 3);
       // yPosInput.addActionListener(new YPositionFieldListener(forObject));
        physicalPanel.add(yPosInput);
        
        physicalPanel.add(new JLabel("Width"));
        final JTextField widthInput = new JTextField("40", 3);
       // widthInput.addActionListener(new WidthFieldListener(forObject));
        widthInput.setEditable(false);
        physicalPanel.add(widthInput);
        
        physicalPanel.add(new JLabel("Height"));
        final JTextField heightInput = new JTextField("40", 3);
       // heightInput.addActionListener(new HeightFieldListener(forObject));
        heightInput.setEditable(false);
        physicalPanel.add(heightInput);
        
        // 
        
        JPanel renderPanel = new JPanel();
        renderPanel.setLayout(new GridLayout(1, 2, mpw, mph));
        renderPanel.add(new JLabel("Texture"));
        final JTextField textureInput = new JTextField("Avacado");
        textureInput.setEditable(false);
       // textureInput.addActionListener(new TextureFieldListener(forObject));
        renderPanel.add(textureInput);
        
        //
        inPanel.add(titlePanel);
        inPanel.add(physicalPanel);
        inPanel.add(renderPanel);
        
        if (acceptButton!=null) {
            acceptButton.addActionListener(new  ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    forObject.setX(Integer.parseInt(xPosInput.getText()));
                    forObject.setY(Integer.parseInt(yPosInput.getText()));
                    forObject.setWidth(Integer.parseInt(widthInput.getText()));
                    forObject.setHeight(Integer.parseInt(heightInput.getText()));
                    forObject.setTextureString(textureInput.getText());
                }
            });
        }
        Debugger.print("Made property panel for Avacado", Debugger.INFO);
    }
    
    private void makeAvacadoBinFormat(final AvacadoBin forObject, JPanel inPanel, JButton acceptButton) {
        inPanel.setLayout(new GridLayout(3, 1, pw, ph));
        
        JPanel titlePanel = new JPanel();
        titlePanel.add(new JLabel("Avacado Bin:"));
        
        //
        
        JPanel physicalPanel = new JPanel();
        physicalPanel.setLayout(new GridLayout(2, 4, mpw, mph));
        
        physicalPanel.add(new JLabel("X Position"));
        final JTextField xPosInput = new JTextField("" + (int)forObject.getX() + "", 3);
        //xPosInput.addActionListener(new XPositionFieldListener(forObject));
        physicalPanel.add(xPosInput);
        
        physicalPanel.add(new JLabel("Y Position"));
        final JTextField yPosInput = new JTextField("" + (int)forObject.getY() + "", 3);
        //yPosInput.addActionListener(new YPositionFieldListener(forObject));
        physicalPanel.add(yPosInput);
        
        physicalPanel.add(new JLabel("Width"));
        final JTextField widthInput = new JTextField("" + (int)forObject.getWidth() + "", 3);
        //widthInput.addActionListener(new WidthFieldListener(forObject));
        physicalPanel.add(widthInput);
        
        physicalPanel.add(new JLabel("Height"));
        final JTextField heightInput = new JTextField("" + (int)forObject.getHeight() + "", 3);
        //heightInput.addActionListener(new HeightFieldListener(forObject));
        physicalPanel.add(heightInput);
        
        // 
        
        JPanel renderPanel = new JPanel();
        renderPanel.setLayout(new GridLayout(1, 2, mpw, mph));
        renderPanel.add(new JLabel("Texture"));
        final JTextField textureInput = new JTextField(forObject.getTextureString(), 10);
        //textureInput.addActionListener(new TextureFieldListener(forObject));
        renderPanel.add(textureInput);
        
        //
        inPanel.add(titlePanel);
        inPanel.add(physicalPanel);
        inPanel.add(renderPanel);
        
        if (acceptButton!=null) {
            acceptButton.addActionListener(new  ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    forObject.setX(Integer.parseInt(xPosInput.getText()));
                    forObject.setY(Integer.parseInt(yPosInput.getText()));
                    forObject.setWidth(Integer.parseInt(widthInput.getText()));
                    forObject.setHeight(Integer.parseInt(heightInput.getText()));
                    forObject.setTextureString(textureInput.getText());
                }
            });
        }
        Debugger.print("Made property panel for Avacado Bin", Debugger.INFO);
    }
    
    private void makeHayBaleFormat(final HayBale forObject, JPanel inPanel, JButton acceptButton) {
        inPanel.setLayout(new GridLayout(3, 1, pw, ph));
        
        JPanel titlePanel = new JPanel();
        titlePanel.add(new JLabel("Hay Bale:"));
        
        //
        
        JPanel physicalPanel = new JPanel();
        physicalPanel.setLayout(new GridLayout(2, 4, mpw, mph));
        
        physicalPanel.add(new JLabel("X Position"));
        final JTextField xPosInput = new JTextField("" + (int)forObject.getX() + "", 3);
        //xPosInput.addActionListener(new XPositionFieldListener(forObject));
        physicalPanel.add(xPosInput);
        
        physicalPanel.add(new JLabel("Y Position"));
        final JTextField yPosInput = new JTextField("" + (int)forObject.getY() + "", 3);
       // yPosInput.addActionListener(new YPositionFieldListener(forObject));
        physicalPanel.add(yPosInput);
        
        physicalPanel.add(new JLabel("Width"));
        final JTextField widthInput = new JTextField("" + (int)forObject.getWidth() + "", 3);
        //widthInput.addActionListener(new WidthFieldListener(forObject));
        physicalPanel.add(widthInput);
        
        physicalPanel.add(new JLabel("Height"));
        final JTextField heightInput = new JTextField("" + (int)forObject.getHeight() + "", 3);
       // heightInput.addActionListener(new HeightFieldListener(forObject));
        physicalPanel.add(heightInput);
        
        // 
        
        JPanel renderPanel = new JPanel();
        renderPanel.setLayout(new GridLayout(1, 2, mpw, mph));
        renderPanel.add(new JLabel("Texture"));
        final JTextField textureInput = new JTextField(forObject.getTextureString(), 10);
        //textureInput.addActionListener(new TextureFieldListener(forObject));
        renderPanel.add(textureInput);
        
        //
        inPanel.add(titlePanel);
        inPanel.add(physicalPanel);
        inPanel.add(renderPanel);
        
        if (acceptButton!=null) {
            acceptButton.addActionListener(new  ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    forObject.setX(Integer.parseInt(xPosInput.getText()));
                    forObject.setY(Integer.parseInt(yPosInput.getText()));
                    forObject.setWidth(Integer.parseInt(widthInput.getText()));
                    forObject.setHeight(Integer.parseInt(heightInput.getText()));
                    forObject.setTextureString(textureInput.getText());
                }
            });
        }
        Debugger.print("Made property panel for Hay Bale", Debugger.INFO);
    }
    
    private void makeGrapplePointFormat(final GrappleHookPoint forObject, JPanel inPanel, JButton acceptButton) {
        inPanel.setLayout(new GridLayout(4, 1, pw, ph));
        
        JPanel titlePanel = new JPanel();
        titlePanel.add(new JLabel("Grapple Hook Point:"));
        
        //
        
        JPanel physicalPanel = new JPanel();
        physicalPanel.setLayout(new GridLayout(2, 4, mpw, mph));
        
        physicalPanel.add(new JLabel("X Position"));
        final JTextField xPosInput = new JTextField("" + (int)forObject.getX() + "", 3);
        //xPosInput.addActionListener(new XPositionFieldListener(forObject));
        physicalPanel.add(xPosInput);
        
        physicalPanel.add(new JLabel("Y Position"));
        final JTextField yPosInput = new JTextField("" + (int)forObject.getY() + "", 3);
        //yPosInput.addActionListener(new YPositionFieldListener(forObject));
        physicalPanel.add(yPosInput);
        
        // 
        
        JPanel interactDistancePanel = new JPanel();
        interactDistancePanel.setLayout(new GridLayout(1, 2, mpw, mph));
        final JTextField interactInput = new JTextField("" + forObject.getInteractDistance() + "");
        interactInput.setEditable(false);
        interactDistancePanel.add(interactInput);
        
        //
        
        JPanel renderPanel = new JPanel();
        renderPanel.setLayout(new GridLayout(1, 2, mpw, mph));
        renderPanel.add(new JLabel("Texture"));
        final JTextField textureInput = new JTextField(forObject.getTextureString(), 10);
        //textureInput.addActionListener(new TextureFieldListener(forObject));
        renderPanel.add(textureInput);
        
        //
        
        inPanel.add(titlePanel);
        inPanel.add(physicalPanel);
        inPanel.add(interactDistancePanel);
        inPanel.add(renderPanel);
        
        if (acceptButton!=null) {
            acceptButton.addActionListener(new  ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    forObject.setX(Integer.parseInt(xPosInput.getText()));
                    forObject.setY(Integer.parseInt(yPosInput.getText()));
                    forObject.setTextureString(textureInput.getText());
                }
            });
        }
        Debugger.print("Made property panel for Grapple Hook Point", Debugger.INFO);
    }
    
    private void makeNormalDoorFormat(GameObject forObject, JPanel inPanel, JButton acceptButton) {
        
        Debugger.print("Made property panel for Normal Door", Debugger.INFO);
    }
    
    private void makeTeleportDoorFormat(GameObject forObject, JPanel inPanel, JButton acceptButton) {
        
        Debugger.print("Made property panel for Teleport Door", Debugger.INFO);
    }
    
    private void makeHumanFormat(GameObject forObject, JPanel inPanel, JButton acceptButton) {
        
        Debugger.print("Made property panel for Human", Debugger.INFO);
    }
    /*
    class XPositionFieldListener implements ActionListener {
        private GameObject editing;
        public XPositionFieldListener(GameObject editing) { this.editing = editing; }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                editing.setX(Integer.parseInt( ((JTextField)e.getSource()).getText() ));
            } catch(Exception ex) {
                Debugger.print("Bad argument for X Position", Debugger.ERROR);
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
                Debugger.print("Bad argument for Y Position", Debugger.ERROR);
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
                Debugger.print("Bad argument for Width", Debugger.ERROR);
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
                Debugger.print("Bad argument for Height", Debugger.ERROR);
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
    */
}
