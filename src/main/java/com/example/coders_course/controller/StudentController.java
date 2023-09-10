package com.example.coders_course.controller;

import com.amazonaws.services.s3.model.S3Object;
import com.example.coders_course.dto.ResponseModel;
import com.example.coders_course.dto.StudentDTO;
import com.example.coders_course.dto.TeacherDTO;
import com.example.coders_course.exceptions.EmailAlreadyTakenException;
import com.example.coders_course.exceptions.GroupNotFoundException;
import com.example.coders_course.exceptions.IllegalContentTypeException;
import com.example.coders_course.exceptions.StudentNotFoundException;
import com.example.coders_course.entity.Student;
import com.example.coders_course.service.Impl.S3Service;
import com.example.coders_course.service.Impl.StudentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "student")
public class StudentController {
    private final StudentServiceImpl studentServiceImpl;
    private final S3Service s3Service;



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
    @GetMapping("/profilephoto")
    public ResponseEntity<InputStreamResource> downloadProfilePhoto(@RequestParam Long id) {
        String key = new String("course/profile_photos/students/"+studentServiceImpl.getProfilePhotoName(id));

        S3Object s3Object = s3Service.getObjectFromS3(id, key);
        String fileName = studentServiceImpl.getProfilePhotoName(id);
        // Set the content type based on the file extension
        String contentType = "application/octet-stream"; // Default content type
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);

        if (fileExtension.equalsIgnoreCase("jpg") || fileExtension.equalsIgnoreCase("jpeg")) {
            contentType = "image/jpeg";
        } else if (fileExtension.equalsIgnoreCase("png")) {
            contentType = "image/png";
        }
        InputStreamResource resource = new InputStreamResource(s3Object.getObjectContent());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        headers.setContentType(MediaType.parseMediaType(contentType));

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

    @PostMapping("/upload/profilephoto")
    public ResponseEntity<ResponseModel<String>> uploadFileToS3(@RequestParam("id") Long id, @RequestParam("file") MultipartFile file) throws IOException, IllegalContentTypeException {
        if(!file.getContentType().equals("image/jpeg") && !file.getContentType().equals("image/png")){
            throw new IllegalContentTypeException();
        }
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        studentServiceImpl.addProfilePhoto(id, fileName);
        String key = "course/profile_photos/students/" + fileName;
        s3Service.uploadFile(key, file);
        ResponseModel<String> responseModel = new ResponseModel<>();
        responseModel.setMessage("Profile Photo added");

        return new ResponseEntity<>(responseModel, HttpStatus.OK);
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
