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
import bropals.gameobject.HumanState;
import bropals.gameobject.HumanType;
import bropals.gameobject.Waypoint;
import bropals.gameobject.block.Avacado;
import bropals.gameobject.block.AvacadoBin;
import bropals.gameobject.block.Block;
import bropals.gameobject.block.HayBale;
import bropals.gameobject.block.NormalDoor;
import bropals.gameobject.block.TeleportDoor;
import bropals.gameobject.block.Wall;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Jonathon
 */
public class PropertyFormatter {

    public PropertyFormatter(CowAreaLevelDesignerMain caldm) {
        this.caldm = caldm;
    }
    
    protected CowAreaLevelDesignerMain caldm;
    
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
            makeHumanFormat((Human)forObject, formatting, acceptButton);
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
    
    private void makeHumanFormat(final Human forObject, JPanel inPanel, JButton acceptButton) {
        inPanel.setLayout(new GridLayout(8, 1, pw, ph));
        
        JPanel titlePanel = new JPanel();
        titlePanel.add(new JLabel("Human:"));
        
        //
        
        JPanel physicalPanel = new JPanel();
        physicalPanel.setLayout(new GridLayout(2, 4, mpw, mph));
        
        physicalPanel.add(new JLabel("X Position"));
        final JTextField xPosInput = new JTextField("" + (int)forObject.getX() + "", 3);
        physicalPanel.add(xPosInput);
        
        physicalPanel.add(new JLabel("Y Position"));
        final JTextField yPosInput = new JTextField("" + (int)forObject.getY() + "", 3);
        physicalPanel.add(yPosInput);
        
        physicalPanel.add(new JLabel("Width"));
        final JTextField widthInput = new JTextField("" + (int)forObject.getWidth() + "", 3);
        physicalPanel.add(widthInput);
        
        physicalPanel.add(new JLabel("Height"));
        final JTextField heightInput = new JTextField("" + (int)forObject.getHeight() + "", 3);
        physicalPanel.add(heightInput);
        
        //
        
        JPanel statePanel = new JPanel();
        statePanel.setLayout(new GridLayout(1, 2, mpw, mph));
        final JComboBox typeBox = new JComboBox(new HumanType[]{HumanType.PITCHFORK, HumanType.ROCK_THROWER});
        final JComboBox stateBox = new JComboBox(new HumanState[]{HumanState.PATROLLING, HumanState.ALERT});
        statePanel.add(typeBox);
        statePanel.add(stateBox);
        
        //
        
        WaypointPanel waypointPanel = new WaypointPanel();
        
        //
        
        inPanel.add(titlePanel);
        inPanel.add(physicalPanel);
        inPanel.add(statePanel);
        inPanel.add(waypointPanel);
        
        Debugger.print("Made property panel for Human", Debugger.INFO);
    }
    
    class WaypointPanel extends JPanel {
        
        protected final JPanel leftSide, rightSide;
        protected final JTextField waypointXPosition, waypointYPosition;
        protected final JButton createWaypoint, deleteWaypoint, moveUp, moveDown;
        protected final WayPointListModel wayPointDataModel;
        protected final JList waypointList;
        
        public WaypointPanel() {

            this.setLayout(new BorderLayout(mpw, mph));
            leftSide = new JPanel();
            leftSide.setLayout(new BorderLayout(mpw, mph));
            rightSide = new JPanel();
            rightSide.setLayout(new GridLayout(4, 2, mpw, mph));

            //Setup the right side
            rightSide.add(new JLabel("Selected X Position"));
            waypointXPosition = new JTextField();
            rightSide.add(waypointXPosition);
            rightSide.add(new JLabel("Selected Y Position"));
            waypointYPosition = new JTextField();
            rightSide.add(waypointYPosition);

            //Buttons
            createWaypoint = new JButton("Create Waypoint");
            deleteWaypoint = new JButton("Delete Waypoint");
            moveUp = new JButton("Move up");
            moveDown = new JButton("Move down");

            //Add them buttons
            rightSide.add(moveUp);
            rightSide.add(createWaypoint);
            rightSide.add(moveDown);
            rightSide.add(deleteWaypoint);
            
            //Add them listeners
            createWaypoint.addActionListener(new CreateWaypointButtonListener());
            deleteWaypoint.addActionListener(new DeleteWaypointButtonListener());
            
            //Setup the left side
            wayPointDataModel = new WayPointListModel();
            waypointList = new JList(wayPointDataModel);
            waypointList.setLayoutOrientation(JList.VERTICAL);
            waypointList.setDragEnabled(true);
            waypointList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            waypointList.addListSelectionListener(new WayPointListSelectionListener());

            //Scrolly bar
            JScrollPane scrolly = new JScrollPane(waypointList);
            scrolly.createVerticalScrollBar();
            scrolly.createHorizontalScrollBar();
            scrolly.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            
             //Add the list to the left side
            leftSide.add(scrolly, BorderLayout.CENTER);
            
            this.add(leftSide, BorderLayout.CENTER);
            this.add(rightSide, BorderLayout.EAST);
            this.add(new JLabel("Waypoints"), BorderLayout.NORTH);

        }
        
        class CreateWaypointButtonListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultListModel m = (DefaultListModel)waypointList.getModel();
                int selectedIndex = waypointList.getSelectedIndex();
                if (waypointList.getSelectedValue()!=null) {
                    m.add(selectedIndex++, new Waypoint(0, 0));
                } else {
                    m.add(m.size(), new Waypoint(0, 0));
                }
                m.trimToSize();
            }
        }
        
        class DeleteWaypointButtonListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                Waypoint p = (Waypoint)waypointList.getSelectedValue();
                if (p!=null) {
                    ((DefaultListModel)waypointList.getModel()).remove(waypointList.getSelectedIndex());
                    caldm.getArea().removeObject(p);
                }
                ((DefaultListModel)waypointList.getModel()).trimToSize();
            }
        }
        
        class WayPointListSelectionListener implements ListSelectionListener {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                //Waypoint selection logic here
                caldm.setSelectedWaypoint((Waypoint)((JList)e.getSource()).getSelectedValue());
            }
            
        } 
        
        class WayPointListModel extends DefaultListModel<Waypoint> {
            
        }
    }
}
