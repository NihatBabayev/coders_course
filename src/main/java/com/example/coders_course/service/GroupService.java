package com.example.coders_course.service;

import com.example.coders_course.model.Group;
import com.example.coders_course.model.Teacher;
import com.example.coders_course.repository.GroupRepository;
import com.example.coders_course.repository.TeacherRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final TeacherRepository teacherRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository, TeacherRepository teacherRepository) {
        this.groupRepository = groupRepository;
        this.teacherRepository = teacherRepository;
    }

    public List<Group> getGroups() {
        return groupRepository.findGroupByState(1);
    }

    public void addNewGroup(Group group, Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(
                () -> new IllegalStateException("Teacher not found"));
        group.setState(1);
        group.setTeacher(teacher);
        teacher.getGroups().add(group);

        groupRepository.save(group);
    }


    @Transactional
    public void deleteGroup(Long groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow(
                () -> new IllegalStateException("Group with id" + groupId + "doesn't exist"));
        group.setState(0);

    }

    @Transactional
    public void updateGroup(Long groupId, Long newTeacherId, Group updatedFields) {
        Group group = groupRepository.findById(groupId).orElseThrow(
                () -> new IllegalStateException("Group with id " + groupId + " doesn't exist"));

        if (updatedFields.getName() != null) {
            group.setName(updatedFields.getName());
        }
        if (updatedFields.getLessonName() != null) {
            group.setLessonName(updatedFields.getLessonName());
        }

        group.setTeacher(teacherRepository.findById(newTeacherId).orElseThrow(
                () -> new IllegalStateException("Teacher not found")));

        group.setState(1);
    }
}
