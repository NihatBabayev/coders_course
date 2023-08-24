package com.example.coders_course.controller;

import com.example.coders_course.model.Group;
import com.example.coders_course.model.Student;
import com.example.coders_course.model.Teacher;
import com.example.coders_course.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "student")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("{groupId}")
    public void registerNewStudent(@RequestBody Student student
                                    , @PathVariable("groupId") Long groupId){
        studentService.addNewStudent(student, groupId);
    }
    @GetMapping
    public List<Student> getStudents() {
        return studentService.getStudents();
    }
    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId){
        studentService.deleteStudent(studentId);
    }
    @PutMapping
    public void updateStudent(@RequestBody Map<String, Object> requestBody) {
        Long studentId = Long.parseLong(requestBody.get("studentId").toString());
        String groupIdsString = requestBody.get("groupId").toString();
        String[] groupIdStrings = groupIdsString.split(",");
        List<Long> groupIds = new ArrayList<>();
        for (String groupIdStr : groupIdStrings) {
            groupIds.add(Long.parseLong(groupIdStr));
        }
        Map<String, Object> updatedStudentMap = (Map<String, Object>) requestBody.get("updatedStudent");

        Student updatedStudent = new Student();
        updatedStudent.setName(updatedStudentMap.get("name").toString());
        updatedStudent.setSurname(updatedStudentMap.get("surname").toString());
        updatedStudent.setAddress(updatedStudentMap.get("address").toString());
        updatedStudent.setBirthdate(LocalDate.parse(updatedStudentMap.get("birthdate").toString()));
        updatedStudent.setEmail(updatedStudentMap.get("email").toString());
        updatedStudent.setPassword(updatedStudentMap.get("password").toString());

        studentService.updateStudent(studentId, groupIds, updatedStudent);
    }
}
