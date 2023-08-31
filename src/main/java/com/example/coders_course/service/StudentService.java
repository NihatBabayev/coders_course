package com.example.coders_course.service;

import com.example.coders_course.dto.ResponseModel;
import com.example.coders_course.dto.StudentDTO;
import com.example.coders_course.exceptions.EmailAlreadyTakenException;
import com.example.coders_course.exceptions.GroupNotFoundException;
import com.example.coders_course.exceptions.StudentNotFoundException;
import com.example.coders_course.entity.Student;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StudentService {
    ResponseEntity<ResponseModel<List<StudentDTO>>> getStudents();
    ResponseEntity<ResponseModel<StudentDTO>> getStudentById(Long id);
    void addNewStudent(Student student, List<Long> groupIds) throws GroupNotFoundException, EmailAlreadyTakenException;
    void deleteStudent(Long studentId) throws StudentNotFoundException;
    void updateStudent(Long studentId, List<Long> newGroupIds, Student updatedFields) throws EmailAlreadyTakenException, StudentNotFoundException;


}
