package com.example.coders_course.exceptions;

public class UserNotFoundException extends Exception{
    private static final String DEFAULT_MESSAGE ="User not found";

    public UserNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
