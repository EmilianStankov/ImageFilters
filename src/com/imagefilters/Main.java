package com.imagefilters;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        //StainedGlassFilter stainedGlassFilter = new StainedGlassFilter(new File(args[0]), new File(args[1]), Integer.parseInt(args[2]));
        //stainedGlassFilter.export(new File(args[1]));
        PixelateFilter pixelateFilter = new PixelateFilter(new File(args[0]), new File(args[1]), Integer.parseInt(args[2]));
        pixelateFilter.export(new File(args[1]));
    }
}
