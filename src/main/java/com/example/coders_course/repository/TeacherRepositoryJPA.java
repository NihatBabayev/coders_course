package com.example.coders_course.repository;

import com.example.coders_course.entity.Teacher;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TeacherRepositoryJPA {
    private final EntityManager entityManager;


    public Teacher getTeacherById (Long id) {
        try {
            Teacher teacher = entityManager.find(Teacher.class, id);
            return teacher;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
