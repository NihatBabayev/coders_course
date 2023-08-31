package com.example.coders_course.dto;

import lombok.Data;

@Data
public class ResponseModel<T> {
    T data;
    String message;
}
