package com.example.coders_course.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TeacherDTO {
    private String name;
    private String surname;
    private String address;
    private String email;
    private LocalDate birthdate;

}
