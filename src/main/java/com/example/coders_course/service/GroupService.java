package com.example.coders_course.service;

import com.example.coders_course.dto.GroupDTO;
import com.example.coders_course.dto.ResponseModel;
import com.example.coders_course.dto.TeacherDTO;
import com.example.coders_course.exceptions.GroupAlreadyExistsException;
import com.example.coders_course.exceptions.GroupNotFoundException;
import com.example.coders_course.exceptions.TeacherNotFoundException;
import com.example.coders_course.entity.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GroupService {
    ResponseEntity<ResponseModel<List<GroupDTO>>> getGroups();
    ResponseEntity<ResponseModel<GroupDTO>> getGroupById(Long id) ;
    void addNewGroup(Group group, Long teacherId) throws GroupAlreadyExistsException, TeacherNotFoundException;
    void deleteGroup(Long groupId) throws GroupNotFoundException;
    void updateGroup(Long groupId, Long newTeacherId, Group updatedFields) throws GroupNotFoundException, TeacherNotFoundException;

    Page<Group> getGroupsWithinPage(PageRequest name);
}
