/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals.graphics;

import bropals.Main;
import static bropals.debug.Debugger.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 *
 * @author Jonathon
 */
public class ImageLoader {
    
    private static final ImageLoader loader = new ImageLoader();
    private final String imageDirectory = "images";
    private String jarPath;
    
    public static ImageLoader getLoader() {
        return loader;
    }
    
    private final HashMap<String, BufferedImage[]> images;
    
    public ImageLoader() {
        images = new HashMap<>();
        try {
            jarPath = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath();
            print("Jar Root: " + jarPath, INFO);
        } catch(URISyntaxException e) {
            print("Can't get jar root!", ERROR);
        }
    }

    /**
     * Loads a single image.
     * @param nameOfImage the name that the image will be stored as in the images HashMap.
     * @param pathFromImageDirectory the path of the image file relative to "images" in the assets directory.
     */
    public void loadSingleImage(String nameOfImage, String pathFromImageDirectory) {
        File f = new File(jarPath + "\\" + imageDirectory + "\\" + pathFromImageDirectory);
        try {
            BufferedImage img = (BufferedImage)ImageIO.read(f);
            images.put(nameOfImage, new BufferedImage[]{img});
            print("Loaded image: " + f.getAbsolutePath() + " as name " + nameOfImage, INFO);
        } catch(IOException e) {
            print("Can't load image: " + f.getAbsolutePath(), ERROR);
        }
    }
    
    /**
     * Gets a loaded image from ImageLoader. If the image was loaded by "loadSingleImage" then the index should be 0.
     * @param name the name of the loading image (no extension).
     * @param index the index of the image in the internal array
     * @return the image (if it is loaded)
     */
    public BufferedImage getImage(String name, int index) {
        return images.get(name)[index];
    }
    
    /**
     * Returns an array of BufferedImages from an image being split up into sprites.
     * @param img The image
     * @param width The width of each sprite
     * @param height The height of each sprite
     * @return The array of sprites
     */
    public BufferedImage[] getSpriteSheet(BufferedImage img, int width, int height) {
        if (height < 0 || width < 0 || height > img.getHeight() || width > img.getWidth()) {
            print("The size of the sprites are too small or too big!", ERROR);
            return null;
        }
        int rows = img.getHeight() / height;
        int columns = img.getWidth() / width;
        BufferedImage[] images = new BufferedImage[rows*columns];
        for (int r=0; r<rows; r++) {
            for (int c=0; c>columns; c++) {
                try {
                    images[(r+c)] = img.getSubimage(width*c, height*r, width, height);
                } catch(Exception e) {
                    print(e.toString(), ERROR);
                }
            }
        }
        return images;
    }
}
