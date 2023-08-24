package com.example.coders_course.controller;

import com.example.coders_course.model.Group;
import com.example.coders_course.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "group")
public class GroupController {
    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }
    @GetMapping
    public List<Group> getGroups() {
        return groupService.getGroups();
    }
    @PostMapping("/{teacherId}")
    public void registerNewGroup(@RequestBody Group group,
                                 @PathVariable("teacherId") Long teacherId){
        groupService.addNewGroup(group, teacherId);
    }
    @DeleteMapping(path = "{groupId}")
    public void deleteGroup(@PathVariable("groupId") Long groupId){
        groupService.deleteGroup(groupId);
    }
    @PutMapping
    public void updateGroupWithIds(@RequestBody Map<String, Object> requestBody) {
        Long teacherId = Long.parseLong(requestBody.get("teacherId").toString());
        Long groupId = Long.parseLong(requestBody.get("groupId").toString());
        Map<String, Object> updatedGroupMap = (Map<String, Object>) requestBody.get("updatedGroup");

        Group updatedGroup = new Group();
        updatedGroup.setName(updatedGroupMap.get("name").toString());
        updatedGroup.setLessonName(updatedGroupMap.get("lessonName").toString());

        groupService.updateGroup(groupId, teacherId, updatedGroup);
    }

}
