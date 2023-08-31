package com.example.coders_course.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    private String name;
    private String surname;
    private String address;
    private String email;
    private LocalDate birthdate;
    private List<GroupDTO> groups;


}