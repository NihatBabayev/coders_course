package com.example.coders_course.service.Impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3Service {


    private final AmazonS3 amazonS3;
    private final TeacherServiceImpl teacherService;

    @Value("${aws.s3.bucketName}") // Add your S3 bucket name in application.properties
    private String bucketName;

    public void uploadFile(String key, MultipartFile file) throws IOException {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("image/jpeg");
            amazonS3.putObject(new PutObjectRequest(bucketName, key, file.getInputStream(), metadata));
    }
    public S3Object getObjectFromS3(Long id, String key) {
        try {
            // Use the AmazonS3 client to get the object from S3
            S3Object s3Object = amazonS3.getObject(bucketName, key);
            return s3Object;
        } catch (Exception e) {
            // Handle exceptions, e.g., object not found, authentication issues, etc.
            throw new RuntimeException("Error fetching object from S3", e);
        }
    }

}
