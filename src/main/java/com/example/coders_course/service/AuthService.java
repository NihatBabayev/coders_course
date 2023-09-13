package com.example.coders_course.service;


import com.example.coders_course.dto.UserDTO;

public interface AuthService {

    String getToken(UserDTO userDTO);
}