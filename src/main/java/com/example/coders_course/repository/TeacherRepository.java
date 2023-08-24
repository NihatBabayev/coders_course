package com.example.coders_course.repository;

import com.example.coders_course.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    @Query("SELECT t from Teacher t where t.email = ?1")
    Optional<Teacher> findTeacherByEmail(String email);
    @Query("SELECT t from Teacher t where t.state = ?1")
    List<Teacher> findTeacherByState(Integer state);
}
