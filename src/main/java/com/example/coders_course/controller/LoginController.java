package com.example.coders_course.controller;

import com.example.coders_course.config.JwtUtil;
import com.example.coders_course.dto.UserDTO;
import com.example.coders_course.entity.Student;
import com.example.coders_course.entity.Teacher;
import com.example.coders_course.exceptions.EmailAlreadyTakenException;
import com.example.coders_course.exceptions.StudentSignupException;
import com.example.coders_course.exceptions.UserNotFoundException;
import com.example.coders_course.service.AuthService;
import com.example.coders_course.service.Impl.StudentServiceImpl;
import com.example.coders_course.service.Impl.TeacherServiceImpl;
import com.example.coders_course.util.ToUserDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final AuthService authService;
    private final TeacherServiceImpl teacherService;
    private final StudentServiceImpl studentService;
    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;


    @GetMapping("/login")
    String getToken(@RequestBody UserDTO userDTO) throws UserNotFoundException {
        if (studentService.getStudentByEmailAndPassword(userDTO.getEmail(), userDTO.getPassword()) != null
                || teacherService.getTeacherByEmailAndPassword(userDTO.getEmail(), userDTO.getPassword()) != null) {

            return authService.getToken(userDTO);
        } else {
            throw new UserNotFoundException();
        }

    }

    @PostMapping("/signup/teacher")
    String signupTeacher(@RequestBody Teacher teacher) throws EmailAlreadyTakenException {
        teacherService.addNewTeacher(teacher);
        return jwtUtil.createToken(teacher.getEmail());

    }

    @PostMapping("/signup/student")
    String signupStudent(@RequestBody Student student) throws StudentSignupException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create a request entity with the student object as the request body
        HttpEntity<Student> requestEntity = new HttpEntity<>(student, headers);

        // Replace with the actual URL of your endpoint
        String apiUrl = "http://localhost:8080/student/save"; // Example URL, replace with your actual URL

        // Send a POST request to the absolute URL
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return jwtUtil.createToken(student.getEmail());
        } else {
            throw new StudentSignupException();
        }
    }
}