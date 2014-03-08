/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals.graphics;

import static bropals.debug.Debugger.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 *
 * @author Jonathon
 */
public class ImageLoader {
    
    private static final ImageLoader loader = new ImageLoader();
    private File jarRoot;
    private final String imageDirectory = "assets/images";
    private final String[] extensions = { "png", "jpg", "jpeg", "gif" };
    
    public static ImageLoader getLoader() {
        return loader;
    }
    
    private HashMap<String, BufferedImage> images;
    
    public ImageLoader() {
        images = new HashMap<>();
        try {
            jarRoot = new File(ImageLoader.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch(Exception e) {
            print("Could not get jar file root!", ERROR);
        }
    }
    
    /**
     * Loads all of the images in the given directory.
     * @param directoryRelativeToImageDirectory the directory relative to the assets/images. Must point to a folder
     */
    public void loadImages(String directoryRelativeToImageDirectory) {
        File dir = new File(jarRoot.getParentFile().toPath() + "/" + imageDirectory + "/" + directoryRelativeToImageDirectory);
        File[] files;
        String[] tokens;
        try {
            if (dir.exists()) {
                files = dir.listFiles();
                //Iterate through files and load images
                for (int i=0; i<files.length; i++) {
                    tokens = files[i].getName().split(".");
                    if (tokens.length>1) { //Has an extension
                       boolean canLoad = false;
                       for (int k=0; k<extensions.length; k++) {
                           if (tokens[1].equals(extensions[k])) {
                               canLoad = true;
                           }
                       }
                       if (canLoad) {
                           String nameForMap = tokens[0];
                           BufferedImage image = (BufferedImage)ImageIO.read(files[i]);
                           images.put(nameForMap, image);
                       }
                    }
                }
            }
            print("Successfully loaded images from directory \"" + directoryRelativeToImageDirectory + "\"", INFO);
        } catch(Exception e) {
            print("Could not load images in directory \"" + directoryRelativeToImageDirectory + "\"", ERROR);
        }
    }
    
    /**
     * Gets a loaded image from ImageLoader.
     * @param name the name of the loading image (no extension).
     * @return the image (if it is loaded)
     */
    public BufferedImage getImage(String name) {
        return images.get(name);
    }
}
