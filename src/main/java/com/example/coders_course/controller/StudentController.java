package com.example.coders_course.controller;

import com.example.coders_course.dto.ResponseModel;
import com.example.coders_course.dto.StudentDTO;
import com.example.coders_course.dto.TeacherDTO;
import com.example.coders_course.exceptions.EmailAlreadyTakenException;
import com.example.coders_course.exceptions.GroupNotFoundException;
import com.example.coders_course.exceptions.StudentNotFoundException;
import com.example.coders_course.entity.Student;
import com.example.coders_course.service.Impl.StudentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "student")
public class StudentController {
    private final StudentServiceImpl studentServiceImpl;


    @GetMapping("/all")
    public ResponseEntity<ResponseModel<List<StudentDTO>>> getStudents() {
        return studentServiceImpl.getStudents();
    }
    @GetMapping
    public ResponseEntity<ResponseModel<StudentDTO>> getStudentById(@RequestParam("id") Long id){
        return studentServiceImpl.getStudentById(id);
    }
    @GetMapping("/page") //with page
    public Page<Student> getStudentsWithinPage(@RequestParam Integer page,
                                                  @RequestParam(value = "size", defaultValue = "5", required = false) Integer size){
        return studentServiceImpl.getStudentWithinPage(PageRequest.of(page,size, Sort.by(Sort.Direction.ASC, "name")));
    }
    @PostMapping()
    public void registerNewStudent(@RequestBody Map<String, Object> requestBody) throws GroupNotFoundException, EmailAlreadyTakenException {
        String groupIdsString = requestBody.get("groupId").toString();
        String[] groupIdStrings = groupIdsString.split(",");
        List<Long> groupIds = new ArrayList<>();
        for (String groupIdStr : groupIdStrings) {
            groupIds.add(Long.parseLong(groupIdStr));
        }
        Map<String, Object> StudentMap = (Map<String, Object>) requestBody.get("Student");

        Student student = new Student();
        student.setName(StudentMap.get("name").toString());
        student.setSurname(StudentMap.get("surname").toString());
        student.setAddress(StudentMap.get("address").toString());
        student.setBirthdate(LocalDate.parse(StudentMap.get("birthdate").toString()));
        student.setEmail(StudentMap.get("email").toString());
        student.setPassword(StudentMap.get("password").toString());
        studentServiceImpl.addNewStudent(student, groupIds);
    }
    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId) throws StudentNotFoundException {
        studentServiceImpl.deleteStudent(studentId);
    }
    @PutMapping
    public void updateStudent(@RequestBody Map<String, Object> requestBody) throws EmailAlreadyTakenException, StudentNotFoundException {
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

        studentServiceImpl.updateStudent(studentId, groupIds, updatedStudent);
    }
}
