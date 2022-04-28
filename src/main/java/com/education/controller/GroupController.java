package com.education.controller;


import com.education.criteria.GenericCriteria;
import com.education.criteria.GroupCriteria;
import com.education.dto.auth.AuthUserDto;
import com.education.dto.group.GroupCreateDto;
import com.education.dto.group.GroupDto;
import com.education.dto.group.GroupUpdateDto;
import com.education.dto.response.DataDto;
import com.education.dto.subject.SubjectCreateDto;
import com.education.dto.subject.SubjectDto;
import com.education.dto.subject.SubjectUpdateDto;
import com.education.service.group.GroupServiceImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("group/*")
public class GroupController  extends AbstractController<GroupServiceImpl>{

    public GroupController(GroupServiceImpl service) {
        super(service);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<DataDto<Long>> create(@RequestBody GroupCreateDto dto) {
        return service.create(dto);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<DataDto<Boolean>> update(@RequestBody GroupUpdateDto dto) {
        return service.update(dto);
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<DataDto<Boolean>> delete(@PathVariable Long id) {
        return service.delete(id);
    }

    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<DataDto<GroupDto>> get(@PathVariable Long id) {
        return service.get(id);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<DataDto<List<GroupDto>>> getAll() {
        return service.getAll();
    }

    @RequestMapping(value = "/addUser/{userId}", method = RequestMethod.POST)
    public ResponseEntity<DataDto<Boolean>> addUser( @PathVariable Long userId,Long groupId) {
        return service.addUser(userId,groupId);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<DataDto<List<AuthUserDto>>> users(Long groupId) {
        return service.users(groupId);
    }



    @RequestMapping(value = "/deleteUser/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<DataDto<Boolean>> deleteUser(@PathVariable  Long userId,Long groupId) {
        return service.deleteUser(userId,groupId);
    }



}
