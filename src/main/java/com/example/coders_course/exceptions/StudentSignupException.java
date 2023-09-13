package com.example.coders_course.exceptions;

public class StudentSignupException extends Exception{
    private static final String DEFAULT_MESSAGE ="Unable to signup student";

    public StudentSignupException() {
        super(DEFAULT_MESSAGE);
    }
}
