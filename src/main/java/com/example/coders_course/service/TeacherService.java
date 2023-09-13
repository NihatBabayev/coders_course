package com.example.coders_course.service;

import com.example.coders_course.dto.ResponseModel;
import com.example.coders_course.dto.TeacherDTO;
import com.example.coders_course.exceptions.EmailAlreadyTakenException;
import com.example.coders_course.exceptions.TeacherNotFoundException;
import com.example.coders_course.entity.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TeacherService {
    ResponseEntity<ResponseModel<List<TeacherDTO>>> getTeachers();

    ResponseEntity<ResponseModel<TeacherDTO>> getTeacherById(Long id);

    void addNewTeacher(Teacher teacher) throws EmailAlreadyTakenException;

    void deleteTeacher(Long teacherId) throws TeacherNotFoundException;

    void updateTeacher(Long teacherId, Teacher updatedFields) throws EmailAlreadyTakenException, TeacherNotFoundException;
    Page<TeacherDTO> getTeacherWithinPage(PageRequest name);

    void addProfilePhoto(Long id, String fileName);
    String getProfilePhotoName(Long id);


    Object getTeacherByEmailAndPassword(String email, String password);
}
