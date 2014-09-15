package com.imagefilters;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.imageio.ImageIO;

public class StainedGlassFilter {
    private BufferedImage inputImage = null;
    private List<Point> centerPoints;
    private Map<Point, List<Point>> regions;
    private BufferedImage outputImage = null;
    
    StainedGlassFilter(File input, File output, int points){
        
        try {
            inputImage = ImageIO.read(input);
        } catch (IOException e) {
        }
        centerPoints = new ArrayList<>();
        regions = new HashMap<>();
        initializeCenterPoints(points);
        splitRegions();
        applyFilter();
    }

    private void initializeCenterPoints(int points) {
        Random random = new Random();
        for(int i = 0; i < points; i++) {
            centerPoints.add(new Point(random.nextInt(inputImage.getWidth()), random.nextInt(inputImage.getHeight())));
            regions.put(centerPoints.get(i), new ArrayList<Point>());
        }
    }

    private void applyFilter() {
        outputImage = inputImage;
        for(Point p: regions.keySet()){
            int red = 0;
            int green = 0;
            int blue = 0;
            for(Point p2: regions.get(p)){
                Color c = new Color(inputImage.getRGB(p2.x, p2.y));
                red += c.getRed();
                green += c.getGreen();
                blue += c.getBlue();
            }
            for(Point p2: regions.get(p)){
                outputImage.setRGB(p2.x, p2.y, new Color(red/regions.get(p).size(), green/regions.get(p).size(), blue/regions.get(p).size()).getRGB());
            }
        }
    }

    private void splitRegions() {
        Point currentPoint = null;
        Point nearestPoint = null;
        for(int i = 0; i < inputImage.getWidth(); i++) {
            for(int j = 0; j < inputImage.getHeight(); j++) {
                double smallestDistance = inputImage.getWidth() + inputImage.getHeight();
                currentPoint = new Point(i, j);
                for(Point p : centerPoints) {
                    if(getDistance(currentPoint, p) < smallestDistance){
                        smallestDistance = getDistance(currentPoint, p);
                        nearestPoint = p;
                    }
                }
                regions.get(nearestPoint).add(currentPoint);
            }
        }
    }

    public void export(File output) {
        try {
            ImageIO.write(outputImage, "jpg", output);
        } catch (IOException e) {
        }
    }
    
    private static double getDistance(Point a, Point b){
        return Math.sqrt(pow((int) (a.getX() - b.getX()),2) + pow((int) (a.getY() - b.getY()),2));
    }
    
    public static double pow(int a, int b){
        double res = 1;
        for (; b > 0; b--){
            res*=a;
        }
        return res;
    }
}
