package com.example.coders_course.controller;

import com.amazonaws.services.s3.model.S3Object;
import com.example.coders_course.dto.ResponseModel;
import com.example.coders_course.dto.TeacherDTO;
import com.example.coders_course.exceptions.EmailAlreadyTakenException;
import com.example.coders_course.exceptions.IllegalContentTypeException;
import com.example.coders_course.exceptions.TeacherNotFoundException;
import com.example.coders_course.entity.Teacher;
import com.example.coders_course.service.Impl.S3Service;
import com.example.coders_course.service.Impl.TeacherServiceImpl;
import com.example.coders_course.service.Impl.TeacherServiceImplJPA;
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
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "teacher")
public class TeacherController {
    private final TeacherServiceImpl teacherServiceImpl;
    private final S3Service s3Service;


    @GetMapping("/all")
    public ResponseEntity<ResponseModel<List<TeacherDTO>>> getTeachers() {
        return teacherServiceImpl.getTeachers();
    }

    @GetMapping
    public ResponseEntity<ResponseModel<TeacherDTO>> getTeacherById(@RequestParam("id") Long id) {
        return teacherServiceImpl.getTeacherById(id);
    }

    @GetMapping("/page")
    public Page<TeacherDTO> getTeachersWithinPage(@RequestParam Integer page,
                                                  @RequestParam(value = "size", defaultValue = "5", required = false) Integer size) {
        return teacherServiceImpl.getTeacherWithinPage(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name")));
    }

    @GetMapping("/profilephoto")
    public ResponseEntity<InputStreamResource> downloadProfilePhoto(@RequestParam Long id) {
        String key = new String("course/profile_photos/teachers/"+teacherServiceImpl.getProfilePhotoName(id));
        S3Object s3Object = s3Service.getObjectFromS3(id, key);
        String fileName = teacherServiceImpl.getProfilePhotoName(id);
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
        teacherServiceImpl.addProfilePhoto(id, fileName);
        String key = "course/profile_photos/teachers/" + fileName;
        s3Service.uploadFile(key, file);
        ResponseModel<String> responseModel = new ResponseModel<>();
        responseModel.setMessage("Profile Photo added");

        return new ResponseEntity<>(responseModel, HttpStatus.OK);
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
