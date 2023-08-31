package com.example.coders_course.dto;

import com.example.coders_course.entity.Teacher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDTO {
    private String name;
    private String lessonName;
    private TeacherDTO teacher;


}
