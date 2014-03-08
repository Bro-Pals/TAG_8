/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals.engine;

import bropals.debug.Debugger;
import bropals.graphics.ImageLoader;
import bropals.graphics.Renderer;
import bropals.level.AreaRunner;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

/**
 *
 * @author Jonathon
 */
public class Engine {
    
    public static final int SCREEN_WIDTH = 800, SCREEN_HEIGHT = 600;
    
    private AreaRunner runner;
    private int framesPerSecond;

    public AreaRunner getRunner() {
        return runner;
    }

    public int getFramesPerSecond() {
        return framesPerSecond;
    }

    public long getMillisecondsPerFrame() {
        return millisecondsPerFrame;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public Frame getFrame() {
        return frame;
    }

    public Canvas getCanvas() {
        return canvas;
    }
    private long millisecondsPerFrame;
    private Renderer renderer;
    private Frame frame;
    private Canvas canvas;
    private boolean closeRequested;

    public boolean isCloseRequested() {
        return closeRequested;
    }
    
    public Engine(int framesPerSecond) {
        setFPS(framesPerSecond);
        makeFrame();
        makeCanvas();
        setupInput();
        makeAreaRunner();
        makeRenderer();
        moveFrameToCenterOfScreen();
        finalizeFrame();
    }
    
    public void setFPS(int fps) {
        this.framesPerSecond = fps;
        this.millisecondsPerFrame = (long)((long)1000/(long)fps); //Converts frames per second into milliseconds per frame
    }
    
    private void makeAreaRunner() {
        runner = new AreaRunner();
    }
    
    private void makeRenderer() {
        renderer = new Renderer(this.canvas);
    }
    
    private void setupInput() {
        canvas.addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                runner.reactToKeyInput(false, e.getKeyCode());
            }

            @Override
            public void keyPressed(KeyEvent e) {
                runner.reactToKeyInput(true, e.getKeyCode());
            }
            
        });
        canvas.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                runner.reactToMouseInput(false, e.getButton(), e.getX(), e.getY());        
            }

            @Override
            public void mousePressed(MouseEvent e) {
                runner.reactToMouseInput(true, e.getButton(), e.getX(), e.getY());
            }
            
        });
    }
    
    private void makeCanvas() {
        //Resize frame to fit canvas as well
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        canvas.setSize(canvas.getPreferredSize());
        canvas.setFocusable(true);
        //Add to frame
        frame.add(canvas);
        frame.pack();
    }
    
    private void makeFrame() {
        frame = new JFrame();
        frame.setTitle("Robin Cow");
        frame.setIconImage(ImageLoader.getLoader().getImage("GameIcon", 0));
        frame.setResizable(false);
        closeRequested = false;
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                exitGame();
            }
            
        });
    }
    
    public void exitGame() {
        Debugger.print("Requesting to exit...", Debugger.INFO);
        closeRequested = true;
    }
    
    private void moveFrameToCenterOfScreen() {
        DisplayMode dm = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
        frame.setLocation((dm.getWidth()/2)-(frame.getWidth()/2), (dm.getHeight()/2)-(frame.getHeight()/2));
    }
    
    private void finalizeFrame() {
        Debugger.print("Frame Size (" + frame.getWidth() + ", " + frame.getHeight() + "). Canvas Size (" + canvas.getWidth() + ", " + canvas.getHeight() + ")", Debugger.INFO);
        frame.setVisible(true);
        //Renderer can only be set up when the frame is visible!
        renderer.setupRenderer();
        Debugger.print("Ready to start drawing!", Debugger.INFO);
    }
    
    /**
     * Updates the Area for 1 update and draws the state of everything in the current area.
     * The time it take for this method to complete will always be equal to the amount given from
     * "getMillisecondsPerFrame"
     */
    public void doGameCycle() {
        b = System.currentTimeMillis();
        runGameFrame();
        a = System.currentTimeMillis();
        s = millisecondsPerFrame - (a-b);
        if (s>0) {
            try {
                Thread.sleep(s);
            } catch(Exception e) {
                Debugger.print("Problem occured when thread was sleeping in Engine.doGameCycle method", Debugger.ERROR);
            }
        }
    }
    
    private long a, b, s; //Variables for doGameCycle so we don't have to reinitialize the storage of it a billion times
    
    /**
     * Ran by "doGameCycle" with "doGameCycle" handling the timing and this
     * method handling the actually calling of the parts of the game.
     */
    private void runGameFrame() {
        
        renderGame();
    }
    
    private void renderGame() {
        renderer.startDrawing();
        renderer.clear(Color.BLACK, SCREEN_WIDTH, SCREEN_HEIGHT);
        //Actual game drawing methods
        renderer.drawArea(runner.getCurrentArea());
        renderer.drawGui(runner);
        
        renderer.finishUpDrawing();
        renderer.swapBuffers();
    }
}
