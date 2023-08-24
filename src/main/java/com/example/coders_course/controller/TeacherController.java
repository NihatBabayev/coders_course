package com.example.coders_course.controller;

import com.example.coders_course.model.Teacher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "teacher")
public class TeacherController {
    private final TeacherService teacherService;

    public StudentController(TeacherService teacherService) {
        this.studentService = studentService;
    }
    @GetMapping
    public List<Teacher> getTeachers(){
        return teacherService.getTeachers();
    }

}
