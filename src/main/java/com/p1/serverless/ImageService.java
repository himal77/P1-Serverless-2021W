package com.p1.serverless;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class ImageService {
    private static int thumbnailCount = 0;

    public byte[] getThumbnailByte(String thumbnailId) throws IOException {
        String filePath = "src/main/resources/thumbnail/" + thumbnailId + ".JPEG";
        File file = new File(filePath);
        return Files.readAllBytes(file.toPath());
    }

    public String saveAsThumbnail(byte[] bytes) throws IOException {
        File tempFile = convertToFile(bytes);
        String outputPath = "src/main/resources/thumbnail/" + thumbnailCount++;
        Thumbnails.of(tempFile).scale(thumbnailCount * 0.02).toFile(outputPath);
        return outputPath + ".JPEG";
    }

    public File convertToFile(byte[] data) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            BufferedImage image = ImageIO.read(bis);
            File outputFile = new File("temp");
            ImageIO.write(image, "jpeg", outputFile);
            return outputFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}