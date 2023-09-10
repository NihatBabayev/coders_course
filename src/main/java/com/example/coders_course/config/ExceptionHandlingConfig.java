package com.example.coders_course.config;

import com.example.coders_course.dto.ResponseModel;
import com.example.coders_course.exceptions.*;
import jakarta.validation.ConstraintViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ControllerAdvice
public class ExceptionHandlingConfig {

    @ExceptionHandler
            ({
                    EmailAlreadyTakenException.class,
                    StudentNotFoundException.class,
                    GroupNotFoundException.class,
                    TeacherNotFoundException.class,
                    GroupAlreadyExistsException.class,
                    IllegalContentTypeException.class
            })
    public ResponseEntity<ResponseModel<String>> handleCustomExceptions(Exception ex) throws Exception {
        ResponseModel<String> exceptionResponseModel = new ResponseModel<>();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        StringBuilder errorMessage = new StringBuilder(ex.getMessage());

        if (ex instanceof EmailAlreadyTakenException || ex instanceof GroupAlreadyExistsException || ex instanceof IllegalContentTypeException) {
            httpStatus = HttpStatus.BAD_REQUEST;
            errorMessage.append(ex.getMessage() + ". ");
        }

        else if (ex instanceof StudentNotFoundException || ex instanceof GroupNotFoundException || ex instanceof TeacherNotFoundException) {
            httpStatus = HttpStatus.NOT_FOUND;
            errorMessage.append(ex.getMessage() + ". ");
        }


        exceptionResponseModel.setMessage(String.valueOf(errorMessage));
        return new ResponseEntity<>(exceptionResponseModel, httpStatus);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseModel<String>> handleConstraintViolationException(ConstraintViolationException ex) {
        StringBuilder errorMessage = new StringBuilder();
        ResponseModel<String> responseModel = new ResponseModel<>();
        ex.getConstraintViolations().forEach(v -> {
            String message = v.getMessage();
            errorMessage.append(message).append(". ");
        });
        responseModel.setMessage(errorMessage.toString());

        return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
    }



}