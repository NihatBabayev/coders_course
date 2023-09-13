package com.example.coders_course.repository;

import com.example.coders_course.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT DISTINCT s FROM Student s JOIN FETCH s.groups g JOIN FETCH g.teacher t WHERE s.state = 1 AND g.state = 1 AND t.state = 1")
    List<Student> findStudentByState();

    Optional<Student> findStudentByEmail(String email);

    @Query("SELECT DISTINCT s FROM Student s JOIN FETCH s.groups g JOIN FETCH g.teacher t WHERE s.state = 1 AND s.id = ?1 AND g.state = 1 AND t.state = 1")
    Optional<Student> getStudentById(Long id);
    @Query("SELECT DISTINCT s FROM Student s JOIN FETCH s.groups g JOIN FETCH g.teacher t WHERE s.state = 1 AND g.state = 1 AND t.state = 1")
    Page<Student> getAllWithinPage(PageRequest pageRequest);
    @Query("Select s.profilePhotoName from Student s where s.id=?1")
    String getProfilePhotoNameById(Long id);

    @Query
    Student getStudentByEmailAndPassword(String email, String password);
}