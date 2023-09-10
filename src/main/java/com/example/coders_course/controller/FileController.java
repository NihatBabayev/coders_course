package com.example.coders_course.controller;

import com.example.coders_course.service.Impl.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/file")
public class FileController {


    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {


        // You can return a success message or some other response here
        return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);

    }

    @Autowired
    private S3Service s3Service;

    @PostMapping("/uploading/s3")
    public ResponseEntity<String> uploadFileToS3(@RequestParam("file") MultipartFile file) throws IOException {
        // Generate a unique key for the file (e.g., using UUID)
        String key = "coders/" + UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        // Upload the file to S3
        s3Service.uploadFile(key, file);

        // Return a success message or other response as needed
        return new ResponseEntity<>("File uploaded to s3 successfully ", HttpStatus.OK);
    }
}

