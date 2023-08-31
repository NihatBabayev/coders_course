package com.example.coders_course.exceptions;

public class GroupAlreadyExistsException extends Exception{
    private static final String DEFAULT_MESSAGE = "Group already exists";

    public GroupAlreadyExistsException() {
        super(DEFAULT_MESSAGE);
    }
}
