package com.example.coders_course.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
public class PushNotification {

    @Scheduled(cron = "*/5 * * * * ?")
    void notifyUser(){
//        System.out.println(LocalDateTime.now());
    }

}