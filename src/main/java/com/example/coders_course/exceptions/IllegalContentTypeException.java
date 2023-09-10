package com.example.coders_course.exceptions;

public class IllegalContentTypeException extends Exception{
    private  static final String DEFAULT_MESSAGE = "file extension is not jpeg either png.";
    public IllegalContentTypeException(){
        super(DEFAULT_MESSAGE);
    }
}
