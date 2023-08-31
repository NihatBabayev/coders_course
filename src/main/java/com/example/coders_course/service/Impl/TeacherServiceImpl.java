package com.example.coders_course.service.Impl;

import com.example.coders_course.dto.ResponseModel;
import com.example.coders_course.dto.TeacherDTO;
import com.example.coders_course.exceptions.EmailAlreadyTakenException;
import com.example.coders_course.exceptions.TeacherNotFoundException;
import com.example.coders_course.entity.Teacher;
import com.example.coders_course.repository.TeacherRepository;
import com.example.coders_course.service.TeacherService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public ResponseEntity<ResponseModel<List<TeacherDTO>>> getTeachers() {
        ResponseModel<List<TeacherDTO>> teacherResponseModel = new ResponseModel<>();
        teacherResponseModel.setData(teacherRepository.findTeacherDTOByState());
        teacherResponseModel.setMessage("success");
        return new ResponseEntity<>(teacherResponseModel, HttpStatus.CREATED);
    }

    public ResponseEntity<ResponseModel<TeacherDTO>> getTeacherById(Long id) {
        ResponseModel<TeacherDTO> teacherResponseModel = new ResponseModel<>();
        teacherResponseModel.setData(teacherRepository.findTeacherDTOById(id));
        teacherResponseModel.setMessage("success");
        return new ResponseEntity<>(teacherResponseModel, HttpStatus.CREATED);
    }

    public void addNewTeacher(Teacher teacher) throws EmailAlreadyTakenException {
        Optional<Teacher> teacherByEmail = teacherRepository.findTeacherByEmail(teacher.getEmail());
        if (teacherByEmail.isPresent()) {
            throw new EmailAlreadyTakenException();
        }
        teacher.setState(1);
        teacherRepository.save(teacher);
    }

    @Transactional
    public void deleteTeacher(Long teacherId) throws TeacherNotFoundException {
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(
                TeacherNotFoundException::new);
        teacher.setState(0);

    }

    @Transactional
    public void updateTeacher(Long teacherId, Teacher updatedFields) throws EmailAlreadyTakenException, TeacherNotFoundException {
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(
                () -> new TeacherNotFoundException());

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
                throw new EmailAlreadyTakenException();
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


    public Page<TeacherDTO> getTeacherWithinPage(PageRequest pageRequest) {
        return teacherRepository.getAllByPage(pageRequest);
    }
}