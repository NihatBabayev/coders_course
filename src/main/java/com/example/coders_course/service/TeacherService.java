package com.example.coders_course.service;

import com.example.coders_course.model.Teacher;
import com.example.coders_course.repository.TeacherRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<Teacher> getTeachers() {
        return teacherRepository.findTeacherByState(1);
    }

    public void addNewTeacher(Teacher teacher) {
        Optional<Teacher> teacherByEmail = teacherRepository.findTeacherByEmail(teacher.getEmail());
        if (teacherByEmail.isPresent()) {
            throw new IllegalStateException("email is already taken");
        }
        teacher.setState(1);
        teacherRepository.save(teacher);
    }

    @Transactional //why transactional is used?
    public void deleteTeacher(Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(
                () -> new IllegalStateException("Teacher with id" + teacherId + "doesn't exist"));
        teacher.setState(0);

    }

    @Transactional
    public void updateTeacher(Long teacherId, Teacher updatedFields) {
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(
                () -> new IllegalStateException("Teacher with id " + teacherId + " doesn't exist"));

        if (updatedFields.getName() != null) {
            teacher.setName(updatedFields.getName());
        }
        if (updatedFields.getSurname() != null) {
            teacher.setSurname(updatedFields.getSurname());
        }
        if (updatedFields.getAddress() != null) {
            teacher.setAddress(updatedFields.getAddress());
        }
        if (updatedFields.getEmail() != null) {
            Optional<Teacher> teacherByEmail = teacherRepository.findTeacherByEmail(updatedFields.getEmail());

            if (teacherByEmail.isPresent()) {
                throw new IllegalStateException("email is already taken");
            }

            teacher.setEmail(updatedFields.getEmail());
        }
        if (updatedFields.getPassword() != null) {
            teacher.setPassword(updatedFields.getPassword());
        }
        if (updatedFields.getBirthdate() != null) {
            teacher.setBirthdate(updatedFields.getBirthdate());
        }
        teacher.setState(1);
    }
}