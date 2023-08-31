package com.example.coders_course.exceptions;

public class TeacherNotFoundException extends Exception{
    private static final String DEFAULT_MESSAGE ="Teacher not found";

    public TeacherNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
