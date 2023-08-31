package com.example.coders_course.exceptions;

public class StudentNotFoundException extends Exception {
    private  static final String DEFAULT_MESSAGE = "Student not found";
    public StudentNotFoundException(){
        super(DEFAULT_MESSAGE);
    }
}
