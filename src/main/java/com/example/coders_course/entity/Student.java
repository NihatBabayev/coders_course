package com.example.coders_course.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "students", schema = "course_project_2")
public class Student {
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
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            schema = "course_project_2",
            name = "student_group",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )

    private Set<Group> groups = new HashSet<>();
    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, address, email, birthdate, state);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student student = (Student) obj;
        return Objects.equals(id, student.id) &&
                Objects.equals(name, student.name) &&
                Objects.equals(surname, student.surname) &&
                Objects.equals(address, student.address) &&
                Objects.equals(email, student.email) &&
                Objects.equals(birthdate, student.birthdate) &&
                Objects.equals(state, student.state);
    }
}
