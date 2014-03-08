/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals;

import bropals.debug.Debugger;
import bropals.engine.Engine;
import bropals.graphics.ImageLoader;

/**
 *
 * @author Jonathon
 */
public class Main {
    
    public static void main(String[] args) {
        Main main = new Main(Integer.parseInt(args[0]));
        main.run();
    }
    
    private Engine engine;
    
    public Main(int fps) {
        loadImagesFromDirectories();
        makeEngine(fps);
    }
    
    private void loadImagesFromDirectories() {
        ImageLoader loader = ImageLoader.getLoader();
        loadFrameIconImage(loader);
    }
    
    private void loadFrameIconImage(ImageLoader loader) {
        loader.loadSingleImage("GameIcon", "GameIcon.png");
    }
    
    private void makeEngine(int fps) {
        engine = new Engine(fps);
    }
    
    private void run() {
        
        while(!engine.isCloseRequested()) {
            engine.doGameCycle();
            //Debugger.print("Frame!", Debugger.INFO);
        }
        //Exit program now
        Debugger.print("Exiting normally", Debugger.INFO);
        System.exit(0);
    }
    
    
}
