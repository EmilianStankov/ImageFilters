package com.imagefilters;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PixelateFilter {
	private BufferedImage inputImage = null;
    private int scale;
    private BufferedImage outputImage = null;

    
    PixelateFilter(File input, File output, int columns){
        
        try {
            inputImage = ImageIO.read(input);
        } catch (IOException e) {
        }
        if (inputImage.getWidth() > columns) {
            scale = inputImage.getWidth() / columns + 1;
        } else {
            scale = 1;
        }
    }
    
    private void colorBlock(int x, int y) {
    	outputImage = inputImage;
    	int red = 0;
        int green = 0;
        int blue = 0;
        for (int i = x; i < scale + x; i++) {
            for (int j = y; j < scale + y; j++) {
            	Color c = new Color(inputImage.getRGB(i, j));
            	red += c.getRed();
                green += c.getGreen();
                blue += c.getBlue();
            }
        }
        red = red/(scale*scale);
        green = green/(scale*scale);
        blue = blue/(scale*scale);
        for (int i = x; i < scale + x; i++) {
            for (int j = y; j < scale + y; j++) {
            	outputImage.setRGB(i, j, new Color(red, green, blue).getRGB());
            }
        }
    }
    
    public void export(File output) {
    	pixelate();
        try {
            ImageIO.write(outputImage, "jpg", output);
        } catch (IOException e) {
        }
    }

	private void pixelate() {
		for(int i = inputImage.getWidth() - scale; i > 0; i -= scale){
    		for(int j = 0; j < inputImage.getHeight() - scale; j += scale){
    			colorBlock(i, j);
    		}
    		for(int j = inputImage.getHeight() - scale; j > 0; j -= scale){
    			colorBlock(i, j);
    		}
    	}
    	for(int i = 0; i < inputImage.getWidth() - scale; i += scale){
    		for(int j = 0; j < inputImage.getHeight() - scale; j += scale){
    			colorBlock(i, j);
    		}
    		for(int j = inputImage.getHeight() - scale; j > 0; j -= scale){
    			colorBlock(i, j);
    		}
    	}
	}
}
