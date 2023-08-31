package com.example.coders_course.service.Impl;

import com.example.coders_course.dto.ResponseModel;
import com.example.coders_course.entity.Teacher;
import com.example.coders_course.repository.TeacherRepositoryJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherServiceImplJPA {

    private final TeacherRepositoryJPA teacherRepositoryJPA;

    public ResponseEntity<ResponseModel<Teacher>> getTeacherById(Long id) {

        Teacher teacher = teacherRepositoryJPA.getTeacherById(id);

        ResponseModel<Teacher> teacherResponseModel = new ResponseModel<>();
        teacherResponseModel.setData(teacher);
        teacherResponseModel.setMessage("Success");
        return new ResponseEntity<>(teacherResponseModel, HttpStatus.CREATED);
    }
}
