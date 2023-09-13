package com.example.coders_course.util;

import com.example.coders_course.dto.UserDTO;
import com.example.coders_course.entity.Student;
import com.example.coders_course.entity.Teacher;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;

@Component
public class ToUserDTOMapper {

    public static UserDTO mapTeacherToUserDTO(Teacher teacher) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(teacher.getEmail());
        userDTO.setPassword(teacher.getPassword());
        // You can map other fields if needed
        return userDTO;
    }
    public static UserDTO mapStudentToUserDTO(Student student) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(student.getEmail());
        userDTO.setPassword(student.getPassword());
        return userDTO;
    }
}
