package com.example.coders_course.service.Impl;

;
import com.example.coders_course.config.JwtUtil;
import com.example.coders_course.dto.UserDTO;
import com.example.coders_course.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    private  final JwtUtil jwtUtil;
    @Override
    public String getToken(UserDTO userDTO) {
        return  jwtUtil.createToken(userDTO.getEmail());
    }
}