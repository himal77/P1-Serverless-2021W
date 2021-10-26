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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class MyController {

    @PostMapping(value = "/test1", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> uploadFile(@RequestParam MultipartFile file, HttpServletResponse response) throws IOException {
        byte[] bytes = StreamUtils.copyToByteArray(file.getInputStream());
        System.out.println(file.getName());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
        // there should be always produces MediaType.IMAGE_JPEG_VALUE
    }
}