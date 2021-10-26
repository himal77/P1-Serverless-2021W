package com.p1.serverless;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class Thumbnail implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Thumbnail.class);
    }

    @Override
    public void run(String... args){

    }

    public void imageToThumbnail() throws IOException {
        String inputImagePath = "src/main/resources/testimage.jpeg";
        File inputFile = new File(inputImagePath);
        String filename = inputFile.getName();
        String outputPath = "src/main/resources/t_" + filename;
        Thumbnail.createFromImageFile(inputFile, outputPath);
    }

    private static void createFromImageFile(File inputFile, String outputPath) throws IOException {
        Thumbnails.of(inputFile).scale(0.25).toFile(outputPath);
    }
}
