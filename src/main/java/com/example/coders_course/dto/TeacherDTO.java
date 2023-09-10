package com.example.coders_course.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDTO {
    private String name;
    private String surname;
    private String address;
    private String email;
    private LocalDate birthdate;

}
