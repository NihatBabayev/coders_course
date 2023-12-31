package com.example.coders_course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CodersCourseApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodersCourseApplication.class, args);
    }

}