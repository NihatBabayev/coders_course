package com.example.coders_course.exceptions;

public class EmailAlreadyTakenException extends Exception{
    private static final String DEFAULT_MESSAGE = "Email is already taken.";

    public EmailAlreadyTakenException() {
        super(DEFAULT_MESSAGE);
    }

}
