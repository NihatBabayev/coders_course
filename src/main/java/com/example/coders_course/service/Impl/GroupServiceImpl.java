package com.example.coders_course.service.Impl;

import com.example.coders_course.dto.GroupDTO;
import com.example.coders_course.dto.ResponseModel;
import com.example.coders_course.dto.TeacherDTO;
import com.example.coders_course.exceptions.GroupAlreadyExistsException;
import com.example.coders_course.exceptions.GroupNotFoundException;
import com.example.coders_course.exceptions.TeacherNotFoundException;
import com.example.coders_course.entity.Group;
import com.example.coders_course.entity.Teacher;
import com.example.coders_course.repository.GroupRepository;
import com.example.coders_course.repository.TeacherRepository;
import com.example.coders_course.service.GroupService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final TeacherRepository teacherRepository;




    public ResponseEntity<ResponseModel<List<GroupDTO>>> getGroups() {
        List<Group> groups = groupRepository.findGroupByState();

        List<GroupDTO> groupDTOs = new ArrayList<>();
        for (Group group : groups) {
            TeacherDTO teacherDTO = new TeacherDTO(
                    group.getTeacher().getName(),
                    group.getTeacher().getSurname(),
                    group.getTeacher().getAddress(),
                    group.getTeacher().getEmail(),
                    group.getTeacher().getBirthdate()
            );

            GroupDTO groupDTO = new GroupDTO(
                    group.getName(),
                    group.getLessonName(),
                    teacherDTO
            );

            groupDTOs.add(groupDTO);
        }

        ResponseModel<List<GroupDTO>> groupResponseModel = new ResponseModel<>();
        groupResponseModel.setData(groupDTOs);
        groupResponseModel.setMessage("success");

        return new ResponseEntity<>(groupResponseModel, HttpStatus.CREATED);

    }


    public ResponseEntity<ResponseModel<GroupDTO>> getGroupById(Long id) throws GroupNotFoundException {
        Group group = groupRepository.findGroupWithTeacherById(id);
        if (group == null) {
            // Handle not found case
            return ResponseEntity.notFound().build();
        }

        TeacherDTO teacherDTO = new TeacherDTO(
                group.getTeacher().getName(),
                group.getTeacher().getSurname(),
                group.getTeacher().getAddress(),
                group.getTeacher().getEmail(),
                group.getTeacher().getBirthdate()
        );

        GroupDTO groupDTO = new GroupDTO(
                group.getName(),
                group.getLessonName(),
                teacherDTO
        );

        ResponseModel<GroupDTO> groupResponseModel = new ResponseModel<>();
        groupResponseModel.setData(groupDTO);
        groupResponseModel.setMessage("success");
        return new ResponseEntity<>(groupResponseModel, HttpStatus.CREATED);

    }


    public void addNewGroup(Group group, Long teacherId) throws TeacherNotFoundException, GroupAlreadyExistsException {
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(
                () -> new TeacherNotFoundException());
        Optional<Group> groupByName = groupRepository.findByName(group.getName());
        if(groupByName.isPresent()){
            throw new GroupAlreadyExistsException();
        }
        group.setState(1);
        group.setTeacher(teacher);
        teacher.getGroups().add(group);

        groupRepository.save(group);
    }

    @Transactional
    public void deleteGroup(Long groupId) throws GroupNotFoundException {
        Group group = groupRepository.findById(groupId).orElseThrow(
                () -> new GroupNotFoundException());
        group.setState(0);

    }

    @Transactional
    public void updateGroup(Long groupId, Long newTeacherId, Group updatedFields) throws TeacherNotFoundException, GroupNotFoundException {
        Group group = groupRepository.findById(groupId).orElseThrow(
                () -> new GroupNotFoundException());

        if (updatedFields.getName() != null) {
            group.setName(updatedFields.getName());
        }
        if (updatedFields.getLessonName() != null) {
            group.setLessonName(updatedFields.getLessonName());
        }

        group.setTeacher(teacherRepository.findById(newTeacherId).orElseThrow(
                () -> new TeacherNotFoundException()));

        group.setState(1);
    }
}
