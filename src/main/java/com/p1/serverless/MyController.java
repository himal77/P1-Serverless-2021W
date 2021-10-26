package com.p1.serverless;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class MyController {

    @GetMapping(value = "/test1", produces = MediaType.IMAGE_JPEG_VALUE)
    public void thumbnailImage(HttpServletResponse response) throws IOException {
        ClassPathResource file = new ClassPathResource("testimage.jpeg");
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(file.getInputStream(), response.getOutputStream());
    }

    @GetMapping(value = "/sid2", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> get_thumbnailImage() throws IOException {
        ClassPathResource file = new ClassPathResource("testimage.jpeg");
        byte[] bytes = StreamUtils.copyToByteArray(file.getInputStream());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
    }

    @GetMapping(value = "/sid3", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<InputStreamResource> get_thumbnailImage_resource() throws IOException {
        ClassPathResource file = new ClassPathResource("testimage.jpeg");
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(file.getInputStream()));
    }

    @PostMapping(value = "/file")
    public void uploadFile(@RequestParam MultipartFile file, HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(file.getInputStream(), response.getOutputStream());

        // Everything was fine, you should give name as file in postman key.
    }
}