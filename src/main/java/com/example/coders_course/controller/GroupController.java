package com.example.coders_course.controller;

import com.example.coders_course.dto.GroupDTO;
import com.example.coders_course.dto.ResponseModel;
import com.example.coders_course.dto.TeacherDTO;
import com.example.coders_course.exceptions.GroupAlreadyExistsException;
import com.example.coders_course.exceptions.GroupNotFoundException;
import com.example.coders_course.exceptions.TeacherNotFoundException;
import com.example.coders_course.entity.Group;
import com.example.coders_course.service.Impl.GroupServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "group")
public class GroupController {
    private final GroupServiceImpl groupServiceImpl;


    @GetMapping("/all")
    public ResponseEntity<ResponseModel<List<GroupDTO>>> getGroups() {
        return groupServiceImpl.getGroups();
    }
    @GetMapping
    public ResponseEntity<ResponseModel<GroupDTO>> getGroupById(@RequestParam("id") Long id) throws GroupNotFoundException {
        return groupServiceImpl.getGroupById(id);
    }
    @GetMapping("/page") //with page
    public Page<Group> getGroupsWithinPage(@RequestParam Integer page,
                                                  @RequestParam(value = "size", defaultValue = "5", required = false) Integer size){
        return groupServiceImpl.getGroupsWithinPage(PageRequest.of(page,size, Sort.by(Sort.Direction.ASC, "name")));
    }
    @PostMapping
    public void registerNewGroup(@RequestBody Group group,
                                 @RequestParam("teacherId") Long teacherId) throws TeacherNotFoundException, GroupAlreadyExistsException {
        groupServiceImpl.addNewGroup(group, teacherId);
    }
    @DeleteMapping(path = "{groupId}")
    public void deleteGroup(@PathVariable("groupId") Long groupId) throws GroupNotFoundException {
        groupServiceImpl.deleteGroup(groupId);
    }
    @PutMapping
    public void updateGroupWithIds(@RequestBody Map<String, Object> requestBody) throws TeacherNotFoundException, GroupNotFoundException {
        Long teacherId = Long.parseLong(requestBody.get("teacherId").toString());
        Long groupId = Long.parseLong(requestBody.get("groupId").toString());
        Map<String, Object> updatedGroupMap = (Map<String, Object>) requestBody.get("updatedGroup");

        Group updatedGroup = new Group();
        updatedGroup.setName(updatedGroupMap.get("name").toString());
        updatedGroup.setLessonName(updatedGroupMap.get("lessonName").toString());

        groupServiceImpl.updateGroup(groupId, teacherId, updatedGroup);
    }

}
