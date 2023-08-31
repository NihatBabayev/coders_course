package com.example.coders_course.exceptions;

public class GroupNotFoundException extends Exception{
    private static final String DEFAULT_MESSAGE = "Group not found.";

    public GroupNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

}
