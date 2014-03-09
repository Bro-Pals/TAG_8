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
import bropals.gameobject.Interactable;
import bropals.gameobject.Waypoint;
import bropals.gameobject.block.Avacado;
import bropals.gameobject.block.AvacadoBin;
import bropals.gameobject.block.Block;
import bropals.gameobject.block.HayBale;
import bropals.gameobject.block.NormalDoor;
import bropals.gameobject.block.TeleportDoor;
import bropals.gameobject.block.Wall;
import bropals.util.Vector2;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.event.ListDataEvent;
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
            makeNormalDoorFormat((NormalDoor)forObject, formatting, acceptButton);
        } else
        if (forObject instanceof TeleportDoor) {
            makeTeleportDoorFormat((TeleportDoor)forObject, formatting, acceptButton);
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
        inPanel.setLayout(new GridLayout(4, 1, pw, ph));
        
        final JPanel titlePanel = new JPanel();
        titlePanel.add(new JLabel("Block:"));   
        //    
        final XYPositionPanel xyPanel = new XYPositionPanel(forObject);  
        //  
        final WidthHeightPanel whPanel = new WidthHeightPanel(forObject);
        // 
        final TexturePanel texturePanel = new TexturePanel(forObject);
        
        //
        inPanel.add(titlePanel);
        inPanel.add(xyPanel);
        inPanel.add(whPanel);
        inPanel.add(texturePanel);
        
        if (acceptButton!=null) {
            acceptButton.addActionListener(new  ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    forObject.setX(xyPanel.getInputX());
                    forObject.setY(xyPanel.getInputY());
                    forObject.setWidth(whPanel.getInputWidth());
                    forObject.setHeight(whPanel.getInputHeight());
                    forObject.setTextureString(texturePanel.getTextureInput());
                }
            });
        }
        Debugger.print("Made property panel for Block", Debugger.INFO);
    }
    
    private void makeWallFormat(final Wall forObject, JPanel inPanel, JButton acceptButton) {
        inPanel.setLayout(new GridLayout(4, 1, pw, ph));
        
        final JPanel titlePanel = new JPanel();
        titlePanel.add(new JLabel("Wall:"));   
        //    
        final XYPositionPanel xyPanel = new XYPositionPanel(forObject);  
        //  
        final WidthHeightPanel whPanel = new WidthHeightPanel(forObject);
        // 
        final TexturePanel texturePanel = new TexturePanel(forObject);
        
        //
        inPanel.add(titlePanel);
        inPanel.add(xyPanel);
        inPanel.add(whPanel);
        inPanel.add(texturePanel);
        
        if (acceptButton!=null) {
            acceptButton.addActionListener(new  ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    forObject.setX(xyPanel.getInputX());
                    forObject.setY(xyPanel.getInputY());
                    forObject.setWidth(whPanel.getInputWidth());
                    forObject.setHeight(whPanel.getInputHeight());
                    forObject.setTextureString(texturePanel.getTextureInput());
                }
            });
        }
        Debugger.print("Made property panel for Wall", Debugger.INFO);
    }
    
    private void makeAvacadoFormat(final Avacado forObject, JPanel inPanel, JButton acceptButton) {
        inPanel.setLayout(new GridLayout(5, 1, pw, ph));
        
        final JPanel titlePanel = new JPanel();
        titlePanel.add(new JLabel("Avacado:"));   
        //    
        final XYPositionPanel xyPanel = new XYPositionPanel(forObject);
        
        //  
        final WidthHeightPanel whPanel = new WidthHeightPanel(forObject);
        whPanel.setEditable(false);
        // 
        final InteractablePanel interactablePanel = new InteractablePanel(forObject);
        //
        final TexturePanel texturePanel = new TexturePanel(forObject);
        texturePanel.setEditable(false);
        //
        inPanel.add(titlePanel);
        inPanel.add(xyPanel);
        inPanel.add(whPanel);
        inPanel.add(interactablePanel);
        inPanel.add(texturePanel);
        
        if (acceptButton!=null) {
            acceptButton.addActionListener(new  ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    forObject.setX(xyPanel.getInputX());
                    forObject.setY(xyPanel.getInputY());
                    forObject.setWidth(whPanel.getInputWidth());
                    forObject.setHeight(whPanel.getInputHeight());
                    forObject.setInteractDistance(interactablePanel.getInteractInput());
                    forObject.setTextureString(texturePanel.getTextureInput());
                }
            });
        }
        Debugger.print("Made property panel for Avacado", Debugger.INFO);
    }
    
    private void makeAvacadoBinFormat(final AvacadoBin forObject, JPanel inPanel, JButton acceptButton) {
        inPanel.setLayout(new GridLayout(5, 1, pw, ph));
        
        final JPanel titlePanel = new JPanel();
        titlePanel.add(new JLabel("Avacado Bin:"));   
        //    
        final XYPositionPanel xyPanel = new XYPositionPanel(forObject);  
        //  
        final WidthHeightPanel whPanel = new WidthHeightPanel(forObject);
        // 
        final InteractablePanel interactablePanel = new InteractablePanel(forObject);
        
        final TexturePanel texturePanel = new TexturePanel(forObject);
        
        //
        inPanel.add(titlePanel);
        inPanel.add(xyPanel);
        inPanel.add(whPanel);
        inPanel.add(interactablePanel);
        inPanel.add(texturePanel);
        
        if (acceptButton!=null) {
            acceptButton.addActionListener(new  ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    forObject.setX(xyPanel.getInputX());
                    forObject.setY(xyPanel.getInputY());
                    forObject.setWidth(whPanel.getInputWidth());
                    forObject.setHeight(whPanel.getInputHeight());
                    forObject.setInteractDistance(interactablePanel.getInteractInput());
                    forObject.setTextureString(texturePanel.getTextureInput());
                }
            });
        }
        Debugger.print("Made property panel for Avacado Bin", Debugger.INFO);
    }
    
    private void makeHayBaleFormat(final HayBale forObject, JPanel inPanel, JButton acceptButton) {
        inPanel.setLayout(new GridLayout(5, 1, pw, ph));
        
        final JPanel titlePanel = new JPanel();
        titlePanel.add(new JLabel("Hay Bale:"));   
        //    
        final XYPositionPanel xyPanel = new XYPositionPanel(forObject);  
        //  
        final WidthHeightPanel whPanel = new WidthHeightPanel(forObject);
        // 
        final InteractablePanel interactablePanel = new InteractablePanel(forObject);
        
        final TexturePanel texturePanel = new TexturePanel(forObject);
        
        //
        inPanel.add(titlePanel);
        inPanel.add(xyPanel);
        inPanel.add(whPanel);
        inPanel.add(interactablePanel);
        inPanel.add(texturePanel);
        
        if (acceptButton!=null) {
            acceptButton.addActionListener(new  ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    forObject.setX(xyPanel.getInputX());
                    forObject.setY(xyPanel.getInputY());
                    forObject.setWidth(whPanel.getInputWidth());
                    forObject.setHeight(whPanel.getInputHeight());
                    forObject.setInteractDistance(interactablePanel.getInteractInput());
                    forObject.setTextureString(texturePanel.getTextureInput());
                }
            });
        }
        Debugger.print("Made property panel for Hay Bale", Debugger.INFO);
    }
    
    private void makeGrapplePointFormat(final GrappleHookPoint forObject, JPanel inPanel, JButton acceptButton) {
        inPanel.setLayout(new GridLayout(4, 1, pw, ph));
        
        final JPanel titlePanel = new JPanel();
        titlePanel.add(new JLabel("Grapple Hook Point:"));   
        //    
        final XYPositionPanel xyPanel = new XYPositionPanel(forObject);  
        // 
        final InteractablePanel interactablePanel = new InteractablePanel(forObject);
        
        final TexturePanel texturePanel = new TexturePanel(forObject);
        
        //
        inPanel.add(titlePanel);
        inPanel.add(xyPanel);
        inPanel.add(interactablePanel);
        inPanel.add(texturePanel);
        
        if (acceptButton!=null) {
            acceptButton.addActionListener(new  ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    forObject.setX(xyPanel.getInputX());
                    forObject.setY(xyPanel.getInputY());
                    forObject.setInteractDistance(interactablePanel.getInteractInput());
                    forObject.setTextureString(texturePanel.getTextureInput());
                }
            });
        }
        Debugger.print("Made property panel for Grapple Hook Point", Debugger.INFO);
    }
    
    private void makeNormalDoorFormat(final NormalDoor forObject, JPanel inPanel, JButton acceptButton) {
        inPanel.setLayout(new GridLayout(5, 1, pw, ph));
        
        final JPanel titlePanel = new JPanel();
        titlePanel.add(new JLabel("Normal Door:"));   
        //    
        final XYPositionPanel xyPanel = new XYPositionPanel(forObject);  
        //  
        final WidthHeightPanel whPanel = new WidthHeightPanel(forObject);
        // 
        final InteractablePanel interactablePanel = new InteractablePanel(forObject);
        
        final TexturePanel texturePanel = new TexturePanel(forObject);
        
        //
        inPanel.add(titlePanel);
        inPanel.add(xyPanel);
        inPanel.add(whPanel);
        inPanel.add(interactablePanel);
        inPanel.add(texturePanel);
        
        if (acceptButton!=null) {
            acceptButton.addActionListener(new  ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    forObject.setX(xyPanel.getInputX());
                    forObject.setY(xyPanel.getInputY());
                    forObject.setWidth(whPanel.getInputWidth());
                    forObject.setHeight(whPanel.getInputHeight());
                    forObject.setInteractDistance(interactablePanel.getInteractInput());
                    forObject.setTextureString(texturePanel.getTextureInput());
                }
            });
        }
        Debugger.print("Made property panel for Normal Door", Debugger.INFO);
    }
    
    private void makeTeleportDoorFormat(final TeleportDoor forObject, JPanel inPanel, JButton acceptButton) {
        inPanel.setLayout(new GridLayout(5, 1, pw, ph));
        
        final JPanel titlePanel = new JPanel();
        titlePanel.add(new JLabel("Teleport Door:"));   
        //    
        final XYPositionPanel xyPanel = new XYPositionPanel(forObject);  
        //  
        final WidthHeightPanel whPanel = new WidthHeightPanel(forObject);
        // 
        JPanel targetAreaPanel = new JPanel();
        targetAreaPanel.setLayout(new GridLayout(2, 4, mpw, mph));
        final JTextField targetAreaIdInput = new JTextField("" + forObject.getTargetAreaID() + "");
        final JTextField playerXPosInput = new JTextField("" + forObject.getXPlayerPos() + "");
        final JTextField playerYPosInput = new JTextField("" + forObject.getYPlayerPos() + "");

        targetAreaIdInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    forObject.setTargetAreaID(Integer.parseInt( targetAreaIdInput.getText() )); //Area id is an int!
                } catch(NumberFormatException nfe) {
                    forObject.setTargetAreaID(0);
                    targetAreaIdInput.setText("0");
                    targetAreaIdInput.repaint();
                }
            }
        });
        
        playerXPosInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    forObject.setXPlayerPos(Float.parseFloat( playerXPosInput.getText() ));
                } catch(NumberFormatException nfe) {
                    forObject.setXPlayerPos(0);
                    playerXPosInput.setText("0");
                    playerXPosInput.repaint();
                }
            }
        });
        
        playerYPosInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    forObject.setYPlayerPos(Float.parseFloat( playerYPosInput.getText() ));
                } catch(NumberFormatException nfe) {
                    forObject.setYPlayerPos(0);
                    playerYPosInput.setText("0");
                    playerYPosInput.repaint();
                }
            }
        });
        
        targetAreaPanel.add(new JLabel("Target Area ID"));
        targetAreaPanel.add(targetAreaIdInput);
        targetAreaPanel.add(new JLabel()); //Spacer
        targetAreaPanel.add(new JLabel()); //Spacer
        targetAreaPanel.add(new JLabel("Player X Position"));
        targetAreaPanel.add(playerXPosInput);
        targetAreaPanel.add(new JLabel("Player Y Position"));
        targetAreaPanel.add(playerYPosInput);
        
        final InteractablePanel interactablePanel = new InteractablePanel(forObject);
        
        final TexturePanel texturePanel = new TexturePanel(forObject);
        
        //
        inPanel.add(titlePanel);
        inPanel.add(xyPanel);
        inPanel.add(whPanel);
        inPanel.add(targetAreaPanel);
        inPanel.add(interactablePanel);
        inPanel.add(texturePanel);
        
        if (acceptButton!=null) {
            acceptButton.addActionListener(new  ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    forObject.setX(xyPanel.getInputX());
                    forObject.setY(xyPanel.getInputY());
                    forObject.setWidth(whPanel.getInputWidth());
                    forObject.setHeight(whPanel.getInputHeight());
                    
                    try {
                        forObject.setTargetAreaID(Integer.parseInt( targetAreaIdInput.getText() )); //Area id is an int!
                    } catch(NumberFormatException nfe) {
                        forObject.setTargetAreaID(0);
                        targetAreaIdInput.setText("0");
                        targetAreaIdInput.repaint();
                    }
                    
                    try {
                        forObject.setXPlayerPos(Float.parseFloat( playerXPosInput.getText() ));
                    } catch(NumberFormatException nfe) {
                        forObject.setXPlayerPos(0);
                        playerXPosInput.setText("0");
                        playerXPosInput.repaint();
                    }
                    
                    try {
                        forObject.setYPlayerPos(Float.parseFloat( playerYPosInput.getText() ));
                    } catch(NumberFormatException nfe) {
                        forObject.setYPlayerPos(0);
                        playerYPosInput.setText("0");
                        playerYPosInput.repaint();
                    }
                    
                    forObject.setInteractDistance(interactablePanel.getInteractInput());
                    forObject.setTextureString(texturePanel.getTextureInput());
                }
            });
        }
        Debugger.print("Made property panel for Teleport Door", Debugger.INFO);
    }
    
    private void makeHumanFormat(final Human forObject, JPanel inPanel, JButton acceptButton) {
        inPanel.setLayout(new GridLayout(9, 1, pw, ph));
        
        JPanel titlePanel = new JPanel();
        titlePanel.add(new JLabel("Human:"));
        
        //
        
        final XYPositionPanel xyPanel = new XYPositionPanel(forObject);  
        //  
        final WidthHeightPanel2 whPanel = new WidthHeightPanel2(forObject);
        //
        
        JPanel statePanel = new JPanel();
        statePanel.setLayout(new GridLayout(1, 2, mpw, mph));
        final JComboBox typeBox = new JComboBox(new HumanType[]{HumanType.PITCHFORK, HumanType.ROCK_THROWER});
        typeBox.setSelectedItem(forObject.getType());
        final JComboBox stateBox = new JComboBox(new HumanState[]{HumanState.PATROLLING, HumanState.ALERT});
        stateBox.setSelectedItem(forObject.getState());
        typeBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                forObject.setType((HumanType)typeBox.getSelectedItem());
            }
        });
        stateBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                forObject.setState((HumanState)stateBox.getSelectedItem());
            }
        });
        
        statePanel.add(typeBox);
        statePanel.add(stateBox);
        
        //
        
        final WaypointPanel waypointPanel = new WaypointPanel(forObject);
        
        //
        
        JPanel speedPanel = new JPanel();
        speedPanel.setLayout(new GridLayout(1, 4, mpw, mph));
        final JTextField speedInput = new JTextField("" + forObject.getSpeed() + "");
        final JTextField turnSpeedInput = new JTextField("" + forObject.getTurnSpeed() + "");
        
        speedInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    forObject.setSpeed( Float.parseFloat(speedInput.getText()) );
                } catch(NumberFormatException nfe) {
                    forObject.setSpeed(0);
                    speedInput.setText("0");
                    speedInput.repaint();
                }
            }
        });
        
        turnSpeedInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    forObject.setTurnSpeed( Float.parseFloat(turnSpeedInput.getText()) );
                } catch(NumberFormatException nfe) {
                    forObject.setTurnSpeed(0);
                    turnSpeedInput.setText("0");
                    turnSpeedInput.repaint();
                }
            }
        });
        
        speedPanel.add(new JLabel("Move Speed"));
        speedPanel.add(speedInput);
        speedPanel.add(new JLabel("Turn Speed"));
        speedPanel.add(turnSpeedInput);
        //
        
        final FaceDirectionPanel directionPanel = new FaceDirectionPanel(forObject);
        
        //
        
        JPanel visionPanel = new JPanel();
        visionPanel.setLayout(new GridLayout(2, 4, mpw, mph));
        final JTextField sightRangeInput = new JTextField("0");
        final JTextField attackRangeInput = new JTextField("0");
        final JTextField fovInput = new JTextField("0");
        
        sightRangeInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    forObject.setSightRange(Float.parseFloat( sightRangeInput.getText() ));
                } catch(NumberFormatException epc) {
                    sightRangeInput.setText("0");
                    forObject.setSightRange(0);
                    sightRangeInput.repaint();
                }
            }
        });
        attackRangeInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    forObject.setAttackDistance(Float.parseFloat( attackRangeInput.getText() ));
                } catch(NumberFormatException epc) {
                    attackRangeInput.setText("0");
                    forObject.setAttackDistance(0);
                    attackRangeInput.repaint();
                }
            }
        });
        fovInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    forObject.setFieldOfView(Float.parseFloat( fovInput.getText() ));
                } catch(NumberFormatException epc) {
                    fovInput.setText("0");
                    forObject.setFieldOfView(0);
                    fovInput.repaint();
                }
            }
        });
        
        visionPanel.add(new JLabel("Sight Range"));
        visionPanel.add(sightRangeInput);
        visionPanel.add(new JLabel("Attack Range"));
        visionPanel.add(attackRangeInput);
        visionPanel.add(new JLabel("Field of View"));
        visionPanel.add(fovInput);
        visionPanel.add(new JLabel());
        visionPanel.add(new JLabel());
        
        //
        
        final TexturePanel texturePanel = new TexturePanel(forObject);
        
        //
        
        inPanel.add(titlePanel);
        inPanel.add(xyPanel);
        inPanel.add(whPanel);
        inPanel.add(statePanel);
        inPanel.add(waypointPanel);
        inPanel.add(speedPanel);
        inPanel.add(directionPanel);
        inPanel.add(visionPanel);
        inPanel.add(texturePanel);
        
        if (acceptButton!=null) {
            acceptButton.addActionListener(new  ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    forObject.setX(xyPanel.getInputX());
                    forObject.setY(xyPanel.getInputY());
                    forObject.setWidth(whPanel.getInputWidth());
                    forObject.setHeight(whPanel.getInputHeight());
                    
                    forObject.setType((HumanType)typeBox.getSelectedItem());
                    forObject.setState((HumanState)stateBox.getSelectedItem());
                    
                    //Vision panel
                    try {
                        forObject.setSightRange(Float.parseFloat( sightRangeInput.getText() ));
                    } catch(NumberFormatException epc) {
                        sightRangeInput.setText("0");
                        forObject.setSightRange(0);
                    }
                    try {
                        forObject.setAttackDistance(Float.parseFloat( attackRangeInput.getText() ));
                    } catch(NumberFormatException epc) {
                        attackRangeInput.setText("0");
                        forObject.setAttackDistance(0);
                    }
                    try {
                        forObject.setFieldOfView(Float.parseFloat( fovInput.getText() ));
                    } catch(NumberFormatException epc) {
                        fovInput.setText("0");
                        forObject.setFieldOfView(0);                    
                    }
                    //End of vision panel
                    
                    //Speed panel begin
                    try {
                        forObject.setSpeed( Float.parseFloat(speedInput.getText()) );
                    } catch(NumberFormatException nfe) {
                        forObject.setSpeed(0);
                        speedInput.setText("0");
                        speedInput.repaint();
                    }
                    
                    try {
                        forObject.setTurnSpeed( Float.parseFloat(turnSpeedInput.getText()) );
                    } catch(NumberFormatException nfe) {
                        forObject.setTurnSpeed(0);
                        turnSpeedInput.setText("0");
                        turnSpeedInput.repaint();
                    }
                    
                    //Speed panel end
                    forObject.setFaceDirection(directionPanel.getFaceDirectionInput());
                    
                    forObject.setTextureString(texturePanel.getTextureInput());
                }
            });
        }
        Debugger.print("Made property panel for Human", Debugger.INFO);
    }
    
    class WaypointPanel extends JPanel {
        
        protected final Human human;
        protected final JPanel leftSide, rightSide;
        protected final JTextField waypointXPosition, waypointYPosition;
        protected final JButton createWaypoint, deleteWaypoint, moveUp, moveDown;
        protected final WayPointListModel wayPointDataModel;
        protected final JList waypointList;
        
        public WaypointPanel(Human h) {
            this.human = h;
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
            wayPointDataModel = new WayPointListModel(human.getPatrolPath());
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

            //Put waypoint data into list to start editing it
            
        }
        
        class CreateWaypointButtonListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                WayPointListModel m = (WayPointListModel)waypointList.getModel();
                int selectedIndex = waypointList.getSelectedIndex();
                if (waypointList.getSelectedValue()!=null) {
                    m.add(selectedIndex++, new Waypoint(0, 0));
                } else {
                    m.add(m.getSize(), new Waypoint(0, 0));
                }
                m.trimToSize();
            }
        }
        
        class DeleteWaypointButtonListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                Waypoint p = (Waypoint)waypointList.getSelectedValue();
                if (p!=null) {
                    ((WayPointListModel)waypointList.getModel()).remove(waypointList.getSelectedIndex());
                    caldm.getArea().removeObject(p);
                }
                ((WayPointListModel)waypointList.getModel()).trimToSize();
            }
        }
        
        class WayPointListSelectionListener implements ListSelectionListener {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                //Waypoint selection logic here
                caldm.setSelectedWaypoint((Waypoint)((JList)e.getSource()).getSelectedValue());
            }
            
        } 
        
        class WayPointListModel implements ListModel<Waypoint> {

            private ArrayList<Waypoint> data;
            private ArrayList<ListDataListener> dataListeners;
            
            public WayPointListModel(Waypoint[] existingData) {
                data = new ArrayList<Waypoint>();
                dataListeners = new ArrayList<ListDataListener>();
                for (int i=0; i<existingData.length; i++) {
                    data.add(i, existingData[i]);
                }
            }
            
            public void shiftUp(int shiftingIndex, JList toRepaint) {
                if (shiftingIndex>0) {
                    data.set(shiftingIndex-1, data.get(shiftingIndex));
                }
            }
            
            public void shiftDown(int shiftingIndex, JList toRepaint) {
                if (shiftingIndex<(getSize()-1)) {
                    data.set(shiftingIndex+1, data.get(shiftingIndex));
                }
            }
            
            private void notifyDataListenersOfContentsChanged(int element) {
                for (int j=0; j<dataListeners.size(); j++) {
                    dataListeners.get(j).contentsChanged(new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, element, element));
                }
            }
            
            @Override
            public int getSize() {
                return data.size();
            }

            @Override
            public Waypoint getElementAt(int index) {
                return data.get(index);
            }

            @Override
            public void addListDataListener(ListDataListener l) {
                dataListeners.add(l);
            }

            @Override
            public void removeListDataListener(ListDataListener l) {
                dataListeners.remove(l);
            }

            private void trimToSize() {
                data.trimToSize();
            }

            private void add(int i, Waypoint waypoint) {
                data.add(i, waypoint);
                notifyDataListenersOfContentsChanged(i);
            }

            private void remove(int selectedIndex) {
                data.remove(selectedIndex);
                notifyDataListenersOfContentsChanged(selectedIndex);
            }
            
        }
    }
    
    class FaceDirectionPanel extends JPanel  {
        
        private final Human human;
        private final MouseAdapter mouseListener;
        private final FancyDirectionGraphic fdg;
        private final JTextField xPos, yPos;
        
        public FaceDirectionPanel(Human h) {
            this.human = h;
            this.setLayout(new BorderLayout(mpw, mph));
            fdg = new FancyDirectionGraphic();
            
            JPanel left = new JPanel();
            left.setLayout(new BorderLayout(mpw, mph));
            JPanel right = new JPanel();
            right.setLayout(new GridLayout(2, 2, mpw, mph));
            
            xPos = new JTextField( "" + human.getFaceDirection().getX() + "");
            yPos = new JTextField( "" + human.getFaceDirection().getY() + "" );
            
            left.add(fdg, BorderLayout.CENTER);
            
            right.add(new JLabel("X Component"));
            right.add(xPos);
            right.add(new JLabel("Y Component"));
            right.add(yPos);
            
            this.add(left, BorderLayout.WEST);
            this.add(right, BorderLayout.EAST);
            this.add(new JLabel("Face direction"), BorderLayout.NORTH);
            
            //Fancy graphic input coding
            mouseListener = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    fdg.setTo(e.getX(), e.getY());
                    xPos.setText("" + fdg.getFaceDir().getX() + "");
                    yPos.setText("" + fdg.getFaceDir().getY() + "");
                    human.setFaceDirection(fdg.getFaceDir().clone());
                    fdg.repaint();
                    caldm.tellRepaint();
                }
            };
            fdg.addMouseListener(mouseListener);
            
        }
        
        public Vector2 getFaceDirectionInput() {
            return fdg.getFaceDir();
        }
        
        class FancyDirectionGraphic extends Canvas {
            
            private Vector2 to, center, faceDir;
            private final int pointerLength = 60;
            
            public FancyDirectionGraphic() {
                setPreferredSize(new Dimension(200, 150));
                to = new Vector2(0, 1);
                center = new Vector2(100, 75);
                faceDir = new Vector2(0, 1);
            }
            
            private void clear(Graphics g) {
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, this.getPreferredSize().width, this.getPreferredSize().height);
            }
            
            @Override
            public void paint(Graphics g) {
                clear(g);
                drawCompass(g);
                drawPointer(g);
                g.dispose();
            }
            
            private float square(float n) { return n*n; }
            
            private void drawCompass(Graphics g) {
                g.setColor(Color.BLUE);
                g.drawOval((int)(center.getX())-70, (int)(center.getY())-70, 140, 140);
            }
            
            private void drawPointer(Graphics g) {
                g.setColor(Color.BLUE);
                g.drawLine((int)center.getX(), (int)center.getY(), (int)to.getX(), (int)to.getY());
            }

            public Vector2 getTo() {return to;}
            public Vector2 getFaceDir() { return faceDir; }
            public void setTo(int x, int y) {
                Vector2 v = new Vector2(x-(float)center.getX(), y-(float)center.getY());
                float mag = (float)Math.sqrt(square((float)v.getX())+square((float)v.getY()));
                faceDir.setX((1/mag)*v.getX());
                faceDir.setY((1/mag)*v.getY());
                to.setX((faceDir.getX()*pointerLength)+center.getX());
                to.setY((faceDir.getY()*pointerLength)+center.getY());
            }
        }
    }
    
    class TexturePanel extends JPanel {
        
        private GameObject object;
        private JTextField textureInput;
        public TexturePanel(GameObject obj) {
            this.object = obj;
            this.setLayout(new GridLayout(1, 2, mpw, mph));
            this.add(new JLabel("Texture"));
            textureInput = new JTextField(obj.getTextureString());
            textureInput.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    object.setTextureString(textureInput.getText());
                    textureInput.repaint();
                    caldm.tellRepaint();
                }
            });
            //textureInput.addActionListener(new TextureFieldListener(forObject));
            this.add(textureInput);
        }
        
        public String getTextureInput() {
            return textureInput.getText();
        }

        private void setEditable(boolean b) {
            textureInput.setEditable(b);
        }
    }
    
    class XYPositionPanel extends JPanel {
        
        private final GameObject obje;
        private final JTextField xPos, yPos;
        
        public XYPositionPanel(GameObject obj) {
            this.obje = obj;
            this.setLayout(new GridLayout(1, 4, mpw, mph));
            xPos = new JTextField("" + obj.getX() + "");
            yPos = new JTextField("" + obj.getY() + "");
            
            xPos.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        obje.setX(Float.parseFloat(xPos.getText()));
                    } catch(NumberFormatException oe) {
                        xPos.setText("0");
                        obje.setX(0);
                        xPos.repaint();
                    }
                    caldm.tellRepaint();
                }
            });
            
            yPos.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        obje.setY(Float.parseFloat(yPos.getText()));
                    } catch(NumberFormatException oe) {
                        yPos.setText("0");
                        obje.setY(0);
                        yPos.repaint();
                    }
                    caldm.tellRepaint();
                }
            });
            
            //Format
            this.add(new JLabel("X Position"));
            this.add(xPos);
            this.add(new JLabel("Y Position"));
            this.add(yPos);
        }
        
        public float getInputX() {
            try {
                return Float.parseFloat( xPos.getText() );
            } catch(NumberFormatException nfe) {
                return 0;
            }
        }
            
            public float getInputY() {
            try {
                return Float.parseFloat( yPos.getText() );
            } catch(NumberFormatException nfe) {
                return 0;
            }
        }
    }
    
    class WidthHeightPanel extends JPanel {
            
            private final Block b;
            private final JTextField wInput, hInput;
                    
            public WidthHeightPanel(Block bl) {
                this.b = bl;
                this.setLayout(new GridLayout(1, 4, mpw, mph));
                wInput = new JTextField("" + b.getWidth() + "");
                hInput = new JTextField("" + b.getHeight() + "");
                
                wInput.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            float w = Float.parseFloat( wInput.getText() );
                            if (w<10) { w = 10; }
                            wInput.setText("" + w + "");
                            b.setWidth(w);  
                            wInput.repaint();
                        } catch(NumberFormatException oe) {
                            wInput.setText("10");
                            b.setWidth(10);
                            wInput.repaint();
                        }
                        caldm.tellRepaint();
                    }
                });
                
                hInput.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            float h = Float.parseFloat( hInput.getText() );
                            if (h<10) { h = 10; }
                            hInput.setText("" + h + "");
                            b.setHeight(h);
                            hInput.repaint();
                        } catch(NumberFormatException oe) {
                            hInput.setText("10");
                            b.setHeight(10);
                            hInput.repaint();
                        }
                        caldm.tellRepaint();
                    }
                });
                
                this.add(new JLabel("Width"));
                this.add(wInput);
                this.add(new JLabel("Height"));
                this.add(hInput);
            }
            
            public float getInputWidth() {
                try {
                    float w = Float.parseFloat( wInput.getText() );
                    if (w<10) { w = 10; }
                    return w;
                } catch(NumberFormatException nfe) {
                    return 10;
                }
            }
            
            public float getInputHeight() {
                try {
                    float h = Float.parseFloat( hInput.getText() );
                    if (h<10) { h = 10; }
                    return h;
                } catch(NumberFormatException nfe) {
                    return 10;
                }
            }

            private void setEditable(boolean b) {
                hInput.setEditable(b);
                wInput.setEditable(b);
            }
        }
    
    class WidthHeightPanel2 extends JPanel {
            
            private final Human b;
            private final JTextField wInput, hInput;
                    
            public WidthHeightPanel2(Human bl) {
                this.b = bl;
                this.setLayout(new GridLayout(1, 4, mpw, mph));
                wInput = new JTextField("" + b.getWidth() + "");
                hInput = new JTextField("" + b.getHeight() + "");
                
                wInput.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            float w = Float.parseFloat( wInput.getText() );
                            if (w<10) { w = 10; }
                            wInput.setText("" + w + "");
                            b.setWidth(w);   
                            wInput.repaint();
                        } catch(NumberFormatException oe) {
                            wInput.setText("10");
                            b.setWidth(10);
                            wInput.repaint();
                        }
                        caldm.tellRepaint();
                    }
                });
                
                hInput.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            float h = Float.parseFloat( hInput.getText() );
                            if (h<10) { h = 10; }
                            hInput.setText("" + h + "");
                            b.setHeight(h);
                            hInput.repaint();
                        } catch(NumberFormatException oe) {
                            hInput.setText("10");
                            b.setHeight(10);
                            hInput.repaint();
                        }
                        caldm.tellRepaint();
                    }
                });
                
                this.add(new JLabel("Width"));
                this.add(wInput);
                this.add(new JLabel("Height"));
                this.add(hInput);
            }
            
            public float getInputWidth() {
                try {
                    float w = Float.parseFloat( wInput.getText() );
                    if (w<10) { w = 10; }
                    return w;
                } catch(NumberFormatException nfe) {
                    return 10;
                }
            }
            
            public float getInputHeight() {
                try {
                    float h = Float.parseFloat( hInput.getText() );
                    if (h<10) { h = 10; }
                    return h;
                } catch(NumberFormatException nfe) {
                    return 10;
                }
            }

            private void setEditable(boolean b) {
                hInput.setEditable(b);
                wInput.setEditable(b);
            }
        }
    
    class InteractablePanel extends JPanel {
        private final Interactable interactable;
        private final JTextField interactInput;
        public InteractablePanel(Interactable i) {
            this.interactable = i;
            this.setLayout(new GridLayout(1, 2, mpw, mph));
            
            interactInput = new JTextField("" + interactable.getInteractDistance() + "");
            interactInput.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        interactable.setInteractDistance(Float.parseFloat( interactInput.getText() ));
                    } catch(NumberFormatException nfe) {
                        interactInput.setText("100");
                        interactable.setInteractDistance(100);
                        interactInput.repaint();
                    }
                }
            });
            this.add(new JLabel("Interact Distance"));
            this.add(interactInput);
        }
        
        public float getInteractInput() {
            try {
                return (Float.parseFloat( interactInput.getText() ));
            } catch(NumberFormatException nfe) {
                interactInput.setText("0");
                interactable.setInteractDistance(0);
                interactInput.repaint();
                return 0;
            }
        }
    }
}
