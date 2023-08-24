package com.example.coders_course.service;

import com.example.coders_course.model.Group;
import com.example.coders_course.model.Student;
import com.example.coders_course.model.Teacher;
import com.example.coders_course.repository.GroupRepository;
import com.example.coders_course.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, GroupRepository groupRepository) {
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findStudentByState(1);
    }

    public void addNewStudent(Student student, Long groupId) {
        Optional<Student> studentByEmail = studentRepository.findStudentByEmail(student.getEmail());
        if (studentByEmail.isPresent()) {
            throw new IllegalStateException("email is already taken");
        }
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if (!groupOptional.isPresent()) {
            throw new IllegalStateException("Group doesn't exist");
        }
        Group group = groupOptional.get();
        student.getGroups().add(group);
        group.getStudents().add(student);
        student.setState(1);
        studentRepository.save(student);
    }

    @Transactional
    public void deleteStudent(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new IllegalStateException("Student with id" + studentId + "doesn't exist"));
        student.setState(0);
    }

    @Transactional
    public void updateStudent(Long studentId, List<Long> newGroupIds, Student updatedFields) {
        Set<Long> newGroupIdsSet = new HashSet<>(newGroupIds);

        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new IllegalStateException("Student with id " + studentId + " doesn't exist"));

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
                throw new IllegalStateException("email is already taken");
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
                id -> student.getGroups()
                        .add((groupRepository
                                .findById(id)
                                .orElseThrow(
                                        () -> new IllegalStateException("Group not found with id "+ id))))
        );


        student.setState(1);
    }
}
