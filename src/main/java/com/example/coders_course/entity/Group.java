package com.example.coders_course.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "groups", schema = "course_project_2")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Group name must not be blank")
    private String name;
    @NotBlank(message = "Lesson name must not be blank")
    private String lessonName;
    private Integer state;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToMany(mappedBy = "groups")
    @JsonIgnore
    private Set<Student> students = new HashSet<>();

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lessonName, state);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Group group = (Group) obj;
        return Objects.equals(id, group.id) &&
                Objects.equals(name, group.name) &&
                Objects.equals(lessonName, group.lessonName) &&
                Objects.equals(state, group.state);
    }

}
