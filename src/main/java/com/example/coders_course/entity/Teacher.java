package com.example.coders_course.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "teachers", schema = "course_project_2")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name must not be blank")
    private String name;
    @NotBlank(message = "Surname must not be blank")
    private String surname;
    private String address;
    @Email
    private String email;
    private String profilePhotoName;


    @NotBlank(message = "Password must not be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;




    private LocalDate birthdate;
    private Integer state;
    @OneToMany(mappedBy = "teacher")
    @JsonIgnore
    private Set<Group> groups;

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, address, email, birthdate, state);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Teacher teacher = (Teacher) obj;
        return Objects.equals(id, teacher.id) &&
                Objects.equals(name, teacher.name) &&
                Objects.equals(surname, teacher.surname) &&
                Objects.equals(address, teacher.address) &&
                Objects.equals(email, teacher.email) &&
                Objects.equals(birthdate, teacher.birthdate) &&
                Objects.equals(state, teacher.state);
    }

}
