package com.example.coders_course.repository;

import com.example.coders_course.dto.TeacherDTO;
import com.example.coders_course.entity.Teacher;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findTeacherByEmail(String email);

    @Query("SELECT new com.example.coders_course.dto.TeacherDTO(t.name,t.surname, t.address, t.email, t.birthdate) from Teacher t where t.state = 1")
    List<TeacherDTO> findTeacherDTOByState();

    @Query("select new com.example.coders_course.dto.TeacherDTO(t.name,t.surname, t.address, t.email, t.birthdate) from Teacher  t where t.id=:id and t.state = 1")
    TeacherDTO findTeacherDTOById(Long id);

    @Query("SELECT new com.example.coders_course.dto.TeacherDTO(t.name,t.surname, t.address, t.email, t.birthdate) from Teacher t where t.state = 1")
    Page<TeacherDTO> getAllByPage(PageRequest pageRequest);
    @Query("Select t.profilePhotoName from Teacher t where t.id=?1")
    String getProfilePhotoNameById(Long id);
}
