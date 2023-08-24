package com.example.coders_course.repository;

import com.example.coders_course.model.Group;
import com.example.coders_course.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query("SELECT g from Group g where g.state = ?1")
    List<Group> findGroupByState(Integer groupId);
}
