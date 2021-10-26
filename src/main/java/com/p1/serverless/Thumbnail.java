package com.p1.serverless;

import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;


public class Thumbnail {
    public static void main(String[] args) {
        String inputImagePath = "src/main/resources/testimage.jpeg";
        File inputFile = new File(inputImagePath);
        String filename = inputFile.getName();
        String outputPath = "src/main/resources/t_"+ filename;
        Thumbnail.createFromImageFile(inputFile, outputPath);
    }

    private static void createFromImageFile(File inputFile, String outputPath) {
        try {
            Thumbnails.of(inputFile).scale(0.25).toFile(outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
