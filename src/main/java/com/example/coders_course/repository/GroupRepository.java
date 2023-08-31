package com.example.coders_course.repository;

import com.example.coders_course.dto.GroupDTO;
import com.example.coders_course.entity.Group;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query("SELECT DISTINCT g FROM Group g JOIN FETCH g.teacher t  WHERE g.state = 1 AND t.state = 1")
    List<Group> findGroupByState();

    @Query("SELECT g FROM Group g JOIN FETCH g.teacher t WHERE g.state = 1 AND g.id = :id AND t.state = 1")
    Group findGroupWithTeacherById(Long id);

    Optional<Group> findByName(String name);

    @Query("SELECT DISTINCT g FROM Group g JOIN FETCH g.teacher t  WHERE g.state = 1 AND t.state = 1")
    Page<Group> getAllWithinPage(PageRequest pageRequest);
}
