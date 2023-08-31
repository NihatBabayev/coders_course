package com.example.coders_course.controller;

import com.example.coders_course.dto.ResponseModel;
import com.example.coders_course.dto.TeacherDTO;
import com.example.coders_course.exceptions.EmailAlreadyTakenException;
import com.example.coders_course.exceptions.TeacherNotFoundException;
import com.example.coders_course.entity.Teacher;
import com.example.coders_course.service.Impl.TeacherServiceImpl;
import com.example.coders_course.service.Impl.TeacherServiceImplJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "teacher")
public class TeacherController {
    private final TeacherServiceImpl teacherServiceImpl;
    private final TeacherServiceImplJPA teacherServiceImplJPA;



    @GetMapping("/all")
    public ResponseEntity<ResponseModel<List<TeacherDTO>>> getTeachers() {
        return teacherServiceImpl.getTeachers();
    }

    @GetMapping
    public ResponseEntity<ResponseModel<TeacherDTO>> getTeacherById(@RequestParam("id") Long id) {
        return teacherServiceImpl.getTeacherById(id);
    }

    @PostMapping
    public void registerNewTeacher(@RequestBody Teacher teacher) throws EmailAlreadyTakenException {
        teacherServiceImpl.addNewTeacher(teacher);
    }

    @DeleteMapping(path = "{teacherId}")
    public void deleteTeacher(@PathVariable("teacherId") Long teacherId) throws TeacherNotFoundException {
        teacherServiceImpl.deleteTeacher(teacherId);
    }

    @PutMapping(path = "{teacherId}")
    public void updateTeacher(
            @PathVariable("teacherId") Long teacherId,
            @RequestBody Teacher updatedFields
    ) throws TeacherNotFoundException, EmailAlreadyTakenException {
        teacherServiceImpl.updateTeacher(teacherId, updatedFields);
    }

}
