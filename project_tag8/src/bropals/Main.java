/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bropals;

import bropals.graphics.ImageLoader;

/**
 *
 * @author Jonathon
 */
public class Main {
    
    public static void main(String[] args) {
        loadImagesFromDirectories();
    }
    
    private static void loadImagesFromDirectories() {
        ImageLoader loader = ImageLoader.getLoader();
    }
}
