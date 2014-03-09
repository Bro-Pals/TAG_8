/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals.leveldesigner;

import bropals.datafiles.CowAreaFileManager;
import bropals.debug.Debugger;
import bropals.gameobject.Creature;
import bropals.gameobject.GameObject;
import bropals.gameobject.GrappleHookPoint;
import bropals.gameobject.Human;
import bropals.gameobject.Waypoint;
import bropals.gameobject.block.Avacado;
import bropals.gameobject.block.AvacadoBin;
import bropals.gameobject.block.Block;
import bropals.gameobject.block.HayBale;
import bropals.gameobject.block.NormalDoor;
import bropals.gameobject.block.TeleportDoor;
import bropals.gameobject.block.Wall;
import bropals.graphics.ImageLoader;
import bropals.level.Area;
import bropals.level.AreaFactory;
import bropals.leveldesigner.CowAreaLevelDesignerMain.GridSpacingOKButtonListener;
import bropals.util.Vector2;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Jonathon
 */
public class CowAreaLevelDesignerMain implements KeyListener, MouseListener {
    
    public static final int SIZELESS_RADIUS = 30;
    
    public static void main(String args[]) {
        //Load images here
        ImageLoader loader = ImageLoader.getLoader();
        loader.loadSingleImage("placeholder_background", "backgrounds/placeholder.png");
        loader.loadSingleImage("GameIcon", "GameIcon.png");
        loader.loadSingleImage("testCreature", "sprites/testCreature.png");
        loader.loadSingleImage("Avacado", "sprites/avacado.png");
        loader.loadSpriteSheet("ActionIcons", "actionIcons.png", 80, 80);
        
        //Then start the program
        CowAreaLevelDesignerMain cldm = new CowAreaLevelDesignerMain();
    }
    
    //Panels and formatting
    private BufferedImage iconImage;
    private final int hSpacing = 7, vSpacing = 7;
    private JFrame mainFrame;
    private LevelDesignerCanvas canvas;
    private PropertyFormatter propertyFormatter;
    private JPanel propertyExplorer, objectCreation, programOptions, globalProperties, objectProperties;
    private JPanel bottomFormatter;
    
    //Buttons and action doers
    private JMenuItem save, open, create, gridEnabled, gridSpacing;
    private JButton createBlock, createWall, createAvacado, createAvacadoBin, createHayBale, createHuman, createGrapplePoint, createNormalDoor, createTeleportDoor, deleteSelected;
    private JTextField northId, southId, eastId, westId, areaIdInput;        
    
    //Data based things
    private Area editingArea;
    private GameObject selectedGameObject;
    private Waypoint selectedWaypoint;
    private final AreaFactory theFactory;
    private Grid grid;
    private final String mainTitle = "Cow Area Level Designer";
    private final JFileChooser fc;
    
    public CowAreaLevelDesignerMain() {
        initializeIconForWindows();
        initializeMainFrame();
        initializeLayoutOfPanels();
        initializeMenu();
        initializeCanvas();
        initializeFormatter();
        initializeObjectCreationPanel();
        initializeGrid();
        centerFrame();
        initializeWhatButtonsDo();
        fc = new JFileChooser();
        selectedGameObject = null;
        theFactory = new AreaFactory();
        mainFrame.setVisible(true);
        mainFrame.revalidate();
    }
    
    private void initializeMainFrame() {
        mainFrame = new JFrame(mainTitle);
        mainFrame.setIconImage(iconImage);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1200, 720);
        mainFrame.setMinimumSize(new Dimension(1150, 720));
    }
    
    private void initializeIconForWindows() {
        try {
            iconImage = (BufferedImage)ImageIO.read(CowAreaLevelDesignerMain.class.getResource("leveldesignericon.png"));
        } catch(Exception e) {
            System.out.println("Could not load icon for level designer!");
        }
    }

    private void setupPropertyTabs() {
        globalProperties = new JPanel();
        objectProperties = new JPanel();
        final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
      //  propertyExplorer.setPreferredSize(new Dimension(325, 2000));
      // tabbedPane.setPreferredSize(propertyExplorer.getPreferredSize());
        //Put each panel into a scrolly pane and that into a tab
        propertyExplorer.add(tabbedPane, BorderLayout.CENTER);
        //Put things in tabs
        JScrollPane objectScrollyPane = new JScrollPane(objectProperties);
        objectScrollyPane.createVerticalScrollBar();
        objectScrollyPane.createHorizontalScrollBar();
        objectScrollyPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        objectScrollyPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JScrollPane globalScrollyPane = new JScrollPane(globalProperties);
        globalScrollyPane.createVerticalScrollBar();
        globalScrollyPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
        globalScrollyPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        tabbedPane.add("Object Properties", objectScrollyPane);
        tabbedPane.add("Global Properties", globalScrollyPane);
        
    }
    
    private void formatBottomPart() {
        bottomFormatter = new JPanel();
        bottomFormatter.setLayout(new BorderLayout(hSpacing, vSpacing));
        bottomFormatter.add(objectCreation, BorderLayout.WEST);
        bottomFormatter.add(programOptions, BorderLayout.EAST);
    }
    
    private void initializeLayoutOfPanels() {
        propertyExplorer = new JPanel();
        propertyExplorer.setLayout(new BorderLayout(hSpacing, vSpacing));
        objectCreation = new JPanel();
        programOptions = new JPanel();
        formatBottomPart();
        mainFrame.add(bottomFormatter, BorderLayout.SOUTH);
        setupPropertyTabs();
        mainFrame.add(propertyExplorer, BorderLayout.EAST);
    }

    private void initializeMenu() {
        JMenuBar bar = new JMenuBar();
        
        JMenu menu = new JMenu("File");
        save = new JMenuItem("Save");
        open = new JMenuItem("Open");
        create = new JMenuItem("Create");
        menu.add(create);
        menu.add(save);
        menu.add(open);
        
        JMenu menu2 = new JMenu("Grid");
        gridEnabled = new JCheckBoxMenuItem("Grid Enabled");
        gridSpacing = new JMenuItem("Grid Spacing...");
        menu2.add(gridSpacing);
        menu2.add(gridEnabled);
        
        bar.add(menu);
        bar.add(menu2);
        mainFrame.setJMenuBar(bar);
    }

    private void initializeCanvas() {
        canvas = new LevelDesignerCanvas(this);
        canvas.setPreferredSize(new Dimension(800, 600));
        canvas.setMaximumSize(canvas.getPreferredSize());
        canvas.setMinimumSize(canvas.getPreferredSize());
        canvas.setSize(canvas.getPreferredSize());
        mainFrame.add(canvas, BorderLayout.CENTER);
        canvas.addMouseListener(this);
        canvas.addKeyListener(this);
    }

    private void centerFrame() {
        DisplayMode dm = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
        mainFrame.setLocation((dm.getWidth()/2)-(mainFrame.getWidth()/2), (dm.getHeight()/2)-(mainFrame.getHeight()/2));
    }

    private void initializeFormatter() {
        propertyFormatter = new PropertyFormatter(this);
    }

    private void initializeObjectCreationPanel() {
        objectCreation.setLayout(new GridLayout(2, 5, hSpacing,vSpacing));
        //Create all of the buttons and add them to the object creationPanel
        createBlock = new JButton("Create Block");
        createWall = new JButton("Create Wall");
        createAvacado = new JButton("Create Avacado");
        createAvacadoBin = new JButton("Create Avacado Bin");
        createGrapplePoint = new JButton("Create Grapple Point");
        createHayBale = new JButton("Create Hay Bale");
        createNormalDoor = new JButton("Create Normal Door");
        createTeleportDoor = new JButton("Create Teleport Door");
        createHuman = new JButton("Create Human");
        deleteSelected = new JButton("Delete Selected");
        //Now add all the buttons to the panel
        objectCreation.add(createBlock);
        objectCreation.add(createWall);
        objectCreation.add(createAvacado);
        objectCreation.add(createAvacadoBin);
        objectCreation.add(createGrapplePoint);
        objectCreation.add(createHayBale);
        objectCreation.add(createNormalDoor);
        objectCreation.add(createTeleportDoor);
        objectCreation.add(createHuman);
        objectCreation.add(deleteSelected);
    }
    
    private void initializeGrid() {
        grid = new Grid();
    }

    private void initializeWhatButtonsDo() {
        //What all of the buttons and file menu bars do is defined in this method
        createBlock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (editingArea!=null) {
                    Block block = new Block(0, 0, 0, 0);
                    makeCreateDialog(block, "Create Block");
                }
            }
        });
        createWall.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (editingArea!=null) {
                    Wall wall = new Wall(0, 0, 0, 0);
                    makeCreateDialog(wall, "Create Wall");
                }
            }
        });
        createGrapplePoint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (editingArea!=null) {
                    GrappleHookPoint grapple = new GrappleHookPoint(0, 0);
                    makeCreateDialog(grapple, "Create Grapple Hook Point");
                }
            }
        });
        createTeleportDoor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (editingArea!=null) {
                    TeleportDoor teleDoor = new TeleportDoor(0, 0, 0, 0, -1, 0, 0);
                    makeCreateDialog(teleDoor, "Create Teleport Door");
                }
            }
        });
        createNormalDoor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (editingArea!=null) {
                    NormalDoor door = new NormalDoor(0, 0, 0, 0);
                    makeCreateDialog(door, "Create Normal Door");
                }
            }
        });
        createAvacado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (editingArea!=null) {
                    Avacado avacado = new Avacado(0, 0, 0, 0, editingArea.getAreaId());
                    makeCreateDialog(avacado, "Create Avacado");
                }
            }
        });
        createAvacadoBin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (editingArea!=null) {
                    AvacadoBin bin = new AvacadoBin(0, 0, 0, 0);
                    makeCreateDialog(bin, "Create Avacado Bin");
                }
            }
        });
        createHuman.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (editingArea!=null) {
                    Human human = new Human(0, 0, 0, 0, Vector2.UNIT_Y);
                    makeCreateDialog(human, "Create Human");
                }
            }
        });
        createHayBale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (editingArea!=null) {
                    HayBale bale = new HayBale(0, 0, 0, 0);
                    makeCreateDialog(bale, "Create Hay Bale");
                }
            }
        });
        
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean canGoThrough = true;
                if (editingArea!=null) {
                    //Prompt user
                    int picked = JOptionPane.showConfirmDialog(mainFrame, "If you haven't saved your current changes, then all of your work will be lost if you overwrite it with a blank Area. Are you sure you want to continue?", "Confirm overwriting possibly unsaved data", JOptionPane.YES_NO_OPTION);
                    if (picked==JOptionPane.NO_OPTION) {
                        canGoThrough = false;
                    }
                }
                if (canGoThrough) {
                    //Overwrite current area
                    setArea(null);
                    theFactory.makeNewAreaOverOldArea();
                    setArea(theFactory.getArea());
                    mainFrame.setTitle(mainTitle + " (Untitled.cowarea)");
                    mainFrame.repaint();
                }
            }
        });
        
        deleteSelected.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int picked = JOptionPane.showConfirmDialog(mainFrame, "Are you sure you want to delete this GameObject?", "Delete confirmation", JOptionPane.YES_NO_OPTION);
                if (picked==JOptionPane.YES_OPTION) {
                    editingArea.removeObject(getSelectedGameObject());
                    setSelectedGameObject(null);
                }
                canvas.repaint();
            }
        });
        
        gridEnabled.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBoxMenuItem checkBox = (JCheckBoxMenuItem)e.getSource();
                grid.setEnabled(checkBox.getState());
                canvas.repaint();
            }
        });
        
        gridSpacing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = makeDialog("Change Grid Spacing");
                JPanel formatterM = new JPanel();
                formatterM.setLayout(new BorderLayout(30, 30));
                dialog.setLayout(new BorderLayout(hSpacing, vSpacing));
                //Add a scrolly thing to change the dialog value
                JPanel core = new JPanel();
                core.setLayout(new BorderLayout(hSpacing, vSpacing));
                JPanel ok = new JPanel();
                ok.setLayout(new GridLayout(1, 2, hSpacing, vSpacing));
                
                JButton okButton = new JButton("OK");
                JButton cancelButton = new JButton("Cancel");
                
                JSlider spacingSlider = new JSlider(Grid.MIN_SPACING, Grid.MAX_SPACING);
                spacingSlider.setValue(grid.getSpacing());
                spacingSlider.createStandardLabels(30);
                spacingSlider.setPaintTicks(true);
                spacingSlider.setPaintTrack(true);
                spacingSlider.setPaintLabels(true);
                spacingSlider.setSnapToTicks(true);
                spacingSlider.setMajorTickSpacing(30);
                spacingSlider.setMinorTickSpacing(5);
                spacingSlider.setValue(grid.getSpacing());
                
                JTextField textField = new JTextField(3);
                textField.setMaximumSize(new Dimension(80, 40));
                textField.setText("" + grid.getSpacing() + "");
                
                core.add(textField, BorderLayout.NORTH);
                core.add(spacingSlider, BorderLayout.CENTER);
                
                spacingSlider.addChangeListener(new GridSpacingSliderListener(textField));
                textField.addActionListener(new GridSpacingFieldListener(spacingSlider));
                
                ok.add(cancelButton);
                ok.add(okButton);
                
                cancelButton.addActionListener(new GridSpacingCancelButtonListener(dialog));
                okButton.addActionListener(new GridSpacingOKButtonListener(dialog, spacingSlider));
                
                formatterM.add(new JLabel(), BorderLayout.NORTH);
                formatterM.add(new JLabel(), BorderLayout.SOUTH);
                formatterM.add(new JLabel(), BorderLayout.EAST);
                formatterM.add(new JLabel(), BorderLayout.WEST);
                formatterM.add(core, BorderLayout.CENTER);
                
                dialog.add(formatterM, BorderLayout.CENTER);
                dialog.add(ok, BorderLayout.SOUTH);
                
                dialog.setSize(mainFrame.getWidth()/2, mainFrame.getHeight()/2);
                dialog.setLocationRelativeTo(mainFrame);
                dialog.setVisible(true);
            }
        });
        
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (editingArea!=null) {
                    int returned = fc.showSaveDialog(mainFrame);
                    if (returned==JFileChooser.APPROVE_OPTION) {
                        //Save the area!
                        File f = fc.getSelectedFile();
                        String[] tokenized = f.getName().split(".");
                        String use;
                        if (tokenized.length>0) {
                            use = tokenized[0];
                        } else {
                            use = f.getName();
                        }
                        f = new File(f.getParentFile().getPath() + "/" + use + ".cowarea");
                        theFactory.getFileManager().export(editingArea, f);
                        mainFrame.setTitle(mainTitle + "(" + use + ")");
                    }
                }
            }
        });
        
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean canGoThrough = true;
                if (editingArea!=null) {
                    //Prompt user
                    int picked = JOptionPane.showConfirmDialog(mainFrame, "If you haven't saved your current changes, then all of your work will be lost if you overwrite it with a blank Area. Are you sure you want to continue?", "Confirm overwriting possibly unsaved data", JOptionPane.YES_NO_OPTION);
                    if (picked==JOptionPane.NO_OPTION) {
                        canGoThrough = false;
                    }
                }
                if (canGoThrough) {
                    //Open file stuff
                    int returned = fc.showOpenDialog(mainFrame);
                    if (returned==JFileChooser.APPROVE_OPTION) {
                        //Save the area!
                        String name = fc.getSelectedFile().getName();
                        theFactory.getFileManager().loadArea(theFactory, fc.getSelectedFile());
                        editingArea = theFactory.getArea();
                        mainFrame.setTitle(mainTitle + "(" + name + ")");
                        mainFrame.repaint();
                    }
                }
            }
        });
        
        //Global property stuffs now
        northId = new JTextField();
        southId = new JTextField();
        eastId = new JTextField();
        westId = new JTextField();
        areaIdInput = new JTextField();
        
        northId.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (editingArea!=null) {
                    try {
                        editingArea.setNorthTargetId( Integer.parseInt(northId.getText()) );
                    } catch(NumberFormatException efasd) {
                        northId.setText("0");
                        editingArea.setNorthTargetId(0);
                        northId.repaint();
                    }
                }
            }
        });
        southId.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (editingArea!=null) {
                    try {
                        editingArea.setSouthTargetId( Integer.parseInt(southId.getText()) );
                    } catch(NumberFormatException efasd) {
                        southId.setText("0");
                        editingArea.setSouthTargetId(0);
                        southId.repaint();
                    }
                }
            }
        });
        eastId.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (editingArea!=null) {
                    try {
                        editingArea.setEastTargetId( Integer.parseInt(eastId.getText()) );
                    } catch(NumberFormatException efasd) {
                        eastId.setText("0");
                        editingArea.setEastTargetId(0);
                        eastId.repaint();
                    }
                }
            }
        });
        westId.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (editingArea!=null) {
                    try {
                        editingArea.setWestTargetId( Integer.parseInt(westId.getText()) );
                    } catch(NumberFormatException efasd) {
                        westId.setText("0");
                        editingArea.setWestTargetId(0);
                        westId.repaint();
                    }
                }
            }
        });
        areaIdInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (editingArea!=null) {
                    try {
                        editingArea.setRoomID( Integer.parseInt(areaIdInput.getText()) );
                    } catch(NumberFormatException efasd) {
                        areaIdInput.setText("-1");
                        editingArea.setRoomID(-1);
                        areaIdInput.repaint();
                    }
                }
            }
        });
    }
    
    private void makeCreateDialog(GameObject forObject, String title) {
        JDialog l = makeDialog(title);
        JPanel core = new JPanel();
        JPanel accept = new JPanel();
        accept.setLayout(new GridLayout(1, 2, hSpacing, vSpacing));
        //Make accept and cancel buttons for dialog
        JButton acceptButton = new JButton("Create");
        JButton cancelCreate = new JButton("Cancel");
        accept.add(cancelCreate);
        accept.add(acceptButton);
        //Put "core" into a scrolly pane
        JScrollPane scrolly = new JScrollPane(core);
        scrolly.createVerticalScrollBar();
        scrolly.createHorizontalScrollBar();
        scrolly.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrolly.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        l.add(scrolly, BorderLayout.CENTER);
        l.add(accept, BorderLayout.SOUTH);
        //Make accept/cancel buttons do things
        acceptButton.addActionListener(new AcceptCreationButtonListener(l, forObject));
        cancelCreate.addActionListener(new CancelCreationButtonListener(l));
        //Format for properties of block
        propertyFormatter.format(forObject, core, acceptButton);
        l.setSize(mainFrame.getWidth()/3, mainFrame.getHeight()/2);
        l.setLocationRelativeTo(mainFrame);
        l.setVisible(true);
    }
    
    private JDialog makeDialog(String title) {
        JDialog l = new JDialog(mainFrame, title, true);
        l.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        l.setLayout(new BorderLayout());
        return l;
    }
    
    //Actual methods that do actual things
    
    public void setArea(Area area) {
        this.editingArea = area;
        canvas.setDrawing(this.editingArea);
        if (this.editingArea==null) {
            mainFrame.setTitle(mainTitle);
        }
        canvas.repaint();
    }
    
    public void setSelectedGameObject(GameObject object) {
        this.selectedGameObject = object;
        setSelectedWaypoint(null);
        if (selectedGameObject!=null) {
            propertyFormatter.format(this.selectedGameObject, objectProperties, null);
        } else {
            objectProperties.removeAll(); //No selected object
            objectProperties.revalidate();
            objectProperties.repaint();
        }
        mainFrame.revalidate();
        canvas.repaint();
    }
    
    Grid getGrid() {
        return grid;
    }
    
    boolean gridIsEnabled() {
        return grid.isEnabled();
    }
    
    public GameObject getSelectedGameObject() {
        return this.selectedGameObject;
    }

    void setSelectedWaypoint(Waypoint waypoint) {
        this.selectedWaypoint = waypoint; 
        mainFrame.revalidate();
        canvas.repaint();
    }

    Area getArea() {
        return editingArea;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //Check to see if it was near a game object to select it
        if (editingArea!=null) {
            ArrayList<GameObject> os = editingArea.getObjects();
            for (int i=0; i<os.size(); i++) {
                if (os.get(i) instanceof Block) {
                    //Use width for distance testing
                    Rectangle2D rect = (Rectangle2D)((Block)os.get(i)).getRectangle2D();
                    if (rect.contains(e.getPoint())) {
                        //Select this!
                        setSelectedGameObject(os.get(i));
                        return;
                    }
                } else
                if (os.get(i) instanceof Creature) {
                    if ( ((((Creature)os.get(i)).getCenterX()-e.getX())*(((Creature)os.get(i)).getCenterX()-e.getX()))+((((Creature)os.get(i)).getCenterY()-e.getY())*((Creature)os.get(i)).getCenterY()-e.getY()) < (SIZELESS_RADIUS*SIZELESS_RADIUS) ) {
                        //Close enough according to our friend pythagoreous
                        setSelectedGameObject(os.get(i));
                        return;
                    }
                } else {
                    //Use set value for distance testing
                    if ( (((os.get(i).getX()-e.getX())*(os.get(i).getX()-e.getX()))+((os.get(i).getY()-e.getY())*(os.get(i).getY()-e.getY()))) < (SIZELESS_RADIUS*SIZELESS_RADIUS) ) {
                        //Close enough according to our friend pythagoreous
                        setSelectedGameObject(os.get(i));
                        return;
                    }
                }
            }
            //If it made it here, then it didn't select anything
            setSelectedGameObject(null);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    Waypoint getSelectedWaypoint() {
        return selectedWaypoint;
    }
    
    class AcceptCreationButtonListener implements ActionListener {
        private GameObject object;
        private JDialog dialog;
        public AcceptCreationButtonListener(JDialog log, GameObject object) {
            this.object = object;
            this.dialog = log;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            ///Places block into area
            Debugger.print(object.toString(), Debugger.INFO);
            editingArea.addObject(object);
            dialog.dispose();
            canvas.repaint();
        }
    }
    
    class CancelCreationButtonListener implements ActionListener {
        private JDialog dialog;
        public CancelCreationButtonListener(JDialog dialog) { this.dialog = dialog; }
        @Override
        public void actionPerformed(ActionEvent e) {
            dialog.dispose();
        }
    }
    
    class GridSpacingSliderListener implements ChangeListener {
        private JTextField f;
        public GridSpacingSliderListener(JTextField f) {
            this.f = f;
        }
        @Override
        public void stateChanged(ChangeEvent e) {
            f.setText("" + ((JSlider)e.getSource()).getValue() + "");
        }
    }
    
    class GridSpacingFieldListener implements ActionListener {
        private JSlider slider;
        public GridSpacingFieldListener(JSlider slider) {
            this.slider=slider;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int val = Integer.parseInt(((JTextField)e.getSource()).getText());
                slider.setValue(val);
            } catch(NumberFormatException ex) {
                slider.setValue(10);
                ((JTextField)e.getSource()).setText("0");
            }
        }
        
        //Global property buttons/fields now
        
    }
    
    class GridSpacingCancelButtonListener implements ActionListener {
        private JDialog log;
        public GridSpacingCancelButtonListener(JDialog log) {
            this.log = log;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            this.log.dispose();
        } 
    }
    
    class GridSpacingOKButtonListener implements ActionListener {
        private JDialog log;
        private JSlider slider;
        public GridSpacingOKButtonListener(JDialog log, JSlider slider) {
            this.log = log;
            this.slider = slider;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            grid.setSpacing(slider.getValue(), canvas.getPreferredSize().width, canvas.getPreferredSize().height);
            canvas.repaint();
            this.log.dispose();
        } 
    }
    
    public void tellRepaint() {
        canvas.repaint();
    }
}
