package com.p1.serverless;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class MyController {

    private final ImageService imageService;

    public MyController(ImageService imageService) {
        this.imageService = imageService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/thumbnail/{thumbnailId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> get_thumbnailImage(@PathVariable String thumbnailId) throws IOException {
        try {
            byte[] imageBytes = imageService.getThumbnailByte(thumbnailId);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
        } catch (Exception e) {
            return ResponseEntity.ok().body("Error! Thumbnail not found".getBytes());
        }
    }

    @PostMapping(value = "/file")
    public ResponseEntity<Object> post_Image(@RequestParam MultipartFile file) throws IOException {
        byte[] bytes = StreamUtils.copyToByteArray(file.getInputStream());
        String name = imageService.saveAsThumbnail(bytes);
        return ResponseEntity.ok().body("Image Path: " + name);
    }
}