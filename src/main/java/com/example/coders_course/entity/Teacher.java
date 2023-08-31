package com.example.coders_course.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "teachers", schema = "course_project_2")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String address;
    private String email;
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
