package com.example.coders_course.controller;

import com.example.coders_course.service.Impl.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;
    @GetMapping("/mail")
    void sendMail(@RequestParam String to, @RequestParam String subject, @RequestParam String text){
        mailService.sendEmail(to, subject, text);
    }
}
