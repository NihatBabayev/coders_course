package com.example.coders_course.service.Impl;

import com.example.coders_course.dto.GroupDTO;
import com.example.coders_course.dto.ResponseModel;
import com.example.coders_course.dto.StudentDTO;
import com.example.coders_course.dto.TeacherDTO;
import com.example.coders_course.entity.Teacher;
import com.example.coders_course.exceptions.EmailAlreadyTakenException;
import com.example.coders_course.exceptions.GroupNotFoundException;
import com.example.coders_course.exceptions.StudentNotFoundException;
import com.example.coders_course.entity.Group;
import com.example.coders_course.entity.Student;
import com.example.coders_course.repository.GroupRepository;
import com.example.coders_course.repository.StudentRepository;
import com.example.coders_course.service.StudentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, GroupRepository groupRepository) {
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public ResponseEntity<ResponseModel<List<StudentDTO>>> getStudents() {
        List<Student> students = studentRepository.findStudentByState();

        List<StudentDTO> studentDTOs = new ArrayList<>();
        for (Student student : students) {
            StudentDTO studentDTO = new StudentDTO();
            studentDTO.setName(student.getName());
            studentDTO.setSurname(student.getSurname());
            studentDTO.setAddress(student.getAddress());
            studentDTO.setEmail(student.getEmail());
            studentDTO.setBirthdate(student.getBirthdate());
            List<GroupDTO> groupDTOs = student.getGroups().stream()
                    .map(group -> {
                        GroupDTO groupDTO = new GroupDTO();
                        groupDTO.setName(group.getName());
                        groupDTO.setLessonName(group.getLessonName());
                        Teacher teacher = group.getTeacher();
                        groupDTO.setTeacher(new TeacherDTO(teacher.getName(), teacher.getSurname(), teacher.getAddress(), teacher.getEmail(), teacher.getBirthdate()));

                        return groupDTO;
                    })
                    .collect(Collectors.toList());

            studentDTO.setGroups(groupDTOs);

            studentDTOs.add(studentDTO);
        }

        ResponseModel<List<StudentDTO>> studentResponseModel = new ResponseModel<>();
        studentResponseModel.setData(studentDTOs);
        studentResponseModel.setMessage("success");

        return new ResponseEntity<>(studentResponseModel, HttpStatus.OK);
    }


    public ResponseEntity<ResponseModel<StudentDTO>> getStudentById(Long id) {
        Optional<Student> studentOptional = studentRepository.getStudentById(id);

        if (studentOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Student student = studentOptional.get();

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName(student.getName());
        studentDTO.setSurname(student.getSurname());
        studentDTO.setAddress(student.getAddress());
        studentDTO.setEmail(student.getEmail());
        studentDTO.setBirthdate(student.getBirthdate());

        List<GroupDTO> groupDTOs = student.getGroups().stream()
                .map(group -> {
                    GroupDTO groupDTO = new GroupDTO();
                    groupDTO.setName(group.getName());
                    groupDTO.setLessonName(group.getLessonName());
                    Teacher teacher = group.getTeacher();
                    groupDTO.setTeacher(new TeacherDTO(teacher.getName(), teacher.getSurname(), teacher.getAddress(), teacher.getEmail(), teacher.getBirthdate()));

                    return groupDTO;
                })
                .collect(Collectors.toList());

        studentDTO.setGroups(groupDTOs);

        ResponseModel<StudentDTO> studentResponseModel = new ResponseModel<>();
        studentResponseModel.setData(studentDTO);
        studentResponseModel.setMessage("success");

        return new ResponseEntity<>(studentResponseModel, HttpStatus.OK);
    }

    public void addNewStudent(Student student, List<Long> groupIds) throws GroupNotFoundException, EmailAlreadyTakenException {
        Optional<Student> studentByEmail = studentRepository.findStudentByEmail(student.getEmail());
        if (studentByEmail.isPresent()) {
            throw new EmailAlreadyTakenException();
        }
        for (Long groupId :
                groupIds) {
            Optional<Group> groupOptional = groupRepository.findById(groupId);
            if (!groupOptional.isPresent()) {
                throw new GroupNotFoundException();
            }
            Group group = groupOptional.get();
            student.getGroups().add(group);
            group.getStudents().add(student);
        }
        student.setState(1);
        studentRepository.save(student);
    }

    @Transactional
    public void deleteStudent(Long studentId) throws StudentNotFoundException {
        Student student = studentRepository.findById(studentId).orElseThrow(
                StudentNotFoundException::new);
        student.setState(0);
    }

    @Transactional
    public void updateStudent(Long studentId, List<Long> newGroupIds, Student updatedFields) throws EmailAlreadyTakenException, StudentNotFoundException {

        Set<Long> newGroupIdsSet = new HashSet<>(newGroupIds);

        Student student = studentRepository.findById(studentId).orElseThrow(
                StudentNotFoundException::new);

        if (updatedFields.getName() != null) {
            student.setName(updatedFields.getName());
        }
        if (updatedFields.getSurname() != null) {
            student.setSurname(updatedFields.getSurname());
        }
        if (updatedFields.getAddress() != null) {
            student.setAddress(updatedFields.getAddress());
        }
        if (updatedFields.getEmail() != null) {
            Optional<Student> studentByEmail = studentRepository.findStudentByEmail(updatedFields.getEmail());

            if (studentByEmail.isPresent() && (student.getGroups()).equals(newGroupIdsSet)) {
                throw new EmailAlreadyTakenException();
            }

            student.setEmail(updatedFields.getEmail());
        }
        if (updatedFields.getBirthdate() != null) {
            student.setBirthdate(updatedFields.getBirthdate());
        }
        if (updatedFields.getPassword() != null) {
            student.setPassword(updatedFields.getPassword());
        }


        student.getGroups().clear();
        newGroupIdsSet.stream().forEach(
                id -> {
                    try {
                        student.getGroups()
                                .add((groupRepository
                                        .findById(id)
                                        .orElseThrow(
                                                GroupNotFoundException::new)
                                ));
                    } catch (GroupNotFoundException e) {
                        e.getMessage();
                    }
                });


        student.setState(1);
    }


}
