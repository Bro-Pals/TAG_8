/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals.leveldesigner;

import bropals.level.Area;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;

/**
 *
 * @author Jonathon
 */
public class CowAreaLevelDesignerMain {
    
    public static void main(String args[]) {
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
    private JMenuItem save, open, create;
    private JButton createBlock, createWall, createAvacado, createAvacadoBin, createHayBale, createHuman, createGrapplePoint, createNormalDoor, createTeleportDoor, deleteSelected;
            
    //Data based things
    private Area editingArea;
    
    public CowAreaLevelDesignerMain() {
        initializeIconForWindows();
        initializeMainFrame();
        initializeLayoutOfPanels();
        initializeFileMenu();
        initializeCanvas();
        initializeFormatter();
        initializeObjectCreationPanel();
        centerFrame();
        mainFrame.setVisible(true);
    }
    
    private void initializeMainFrame() {
        mainFrame = new JFrame("Cow Area Level Designer");
        mainFrame.setIconImage(iconImage);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1200, 720);
        mainFrame.setMinimumSize(new Dimension(900, 700));
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
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        propertyExplorer.add(tabbedPane, BorderLayout.CENTER);
        //Put things in tabs
        tabbedPane.add("Object Properties", objectProperties);
        tabbedPane.add("Global Properties", globalProperties);
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
    }

    private void initializeFileMenu() {
        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("File");
        save = new JMenuItem("Save");
        open = new JMenuItem("Open");
        create = new JMenuItem("Create");
        menu.add(create);
        menu.add(save);
        menu.add(open);
        bar.add(menu);
        mainFrame.setJMenuBar(bar);
    }

    private void initializeCanvas() {
        canvas = new LevelDesignerCanvas();
        canvas.setPreferredSize(new Dimension(800, 600));
        mainFrame.add(canvas, BorderLayout.CENTER);
    }

    private void centerFrame() {
        DisplayMode dm = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
        mainFrame.setLocation((dm.getWidth()/2)-(mainFrame.getWidth()/2), (dm.getHeight()/2)-(mainFrame.getHeight()/2));
    }

    private void initializeFormatter() {
        propertyFormatter = new PropertyFormatter();
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
}
