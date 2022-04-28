package com.education.service.group;

import com.education.dto.auth.AuthUserDto;
import com.education.dto.group.GroupCreateDto;
import com.education.dto.group.GroupDto;
import com.education.dto.group.GroupUpdateDto;
import com.education.dto.response.AppErrorDto;
import com.education.dto.response.DataDto;
import com.education.entity.AuthUser;
import com.education.entity.Education;
import com.education.entity.Group;
import com.education.mapper.AuthUserMapper;
import com.education.mapper.GroupMapper;
import com.education.repository.AuthUserRepository;
import com.education.repository.EducationRepository;
import com.education.repository.GroupRepository;
import com.education.service.AbstractService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;


@Service
public class
GroupServiceImpl extends AbstractService<GroupRepository, GroupMapper> {
    private final EducationRepository educationRepository;
    private final AuthUserRepository authUserRepository;
    private final AuthUserMapper authUserMapper;


    protected GroupServiceImpl(GroupRepository repository, GroupMapper mapper, EducationRepository educationRepository, AuthUserRepository authUserRepository, AuthUserMapper authUserMapper) {
        super(repository, mapper);
        this.educationRepository = educationRepository;
        this.authUserRepository = authUserRepository;
        this.authUserMapper = authUserMapper;
    }

    public ResponseEntity<DataDto<Long>> create(GroupCreateDto createDto) {

/*        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<AuthUser> byUsernameAndDeletedFalse = authUserRepository.findByUsernameAndDeletedFalse(name);
        AuthUser admin = authUserRepository.getNotDelete(byUsernameAndDeletedFalse.get().getId(),byUsernameAndDeletedFalse.get().getEducation().getId());
        if ( admin==null)
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().status(HttpStatus.BAD_REQUEST).message("User or Group not found!").build()), HttpStatus.BAD_REQUEST);*/
     //AuthUser session = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("students = " + name);
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println();

        Optional<AuthUser> authUser=authUserRepository.findByUsernameAndDeletedFalse(name);
        System.out.println("authUser = " + authUser);
        System.out.println("authUser.get().getEducation().getId() = " + authUser.get().getEducation().getId());
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        String groupName = createDto.getName().toUpperCase(Locale.ROOT);
        Optional<Education> optional = educationRepository.findById(authUser.get().getEducation().getId());
        if (optional.isEmpty())
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().status(HttpStatus.BAD_REQUEST).build()), HttpStatus.BAD_REQUEST);
        Education education = optional.get();
        /*if (repository.existsByNameAndEducation(groupName, education))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().status(HttpStatus.BAD_REQUEST).build()), HttpStatus.BAD_REQUEST);*/
        Group group = mapper.fromCreateDto(createDto);
        group.setEducation(optional.get());
        group.setName(groupName);
        group.setPrice(createDto.getPrice());
        group = repository.save(group);
        return new ResponseEntity<>(new DataDto<>(group.getId()), HttpStatus.OK);
    }

    private AuthUser getTeacher(Long teacherId) {
        Optional<AuthUser> optional = authUserRepository.findById(teacherId);
        if (optional.isEmpty())
            return null;
        AuthUser teacher=optional.get();
        /*if (!teacher.getRole().equals(Role.TEACHER))
            return null;*/
        return teacher;
    }

    public ResponseEntity<DataDto<Boolean>> delete(Long id) {

        Optional<Group> optional = repository.getNotDelete(id);
        if (optional.isEmpty())
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().status(HttpStatus.BAD_REQUEST).build()), HttpStatus.BAD_REQUEST);
        Group group = optional.get();
        group.setDeleted(true);
        repository.save(group);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.OK);
    }

    public ResponseEntity<DataDto<Boolean>> update(GroupUpdateDto updateDto) {
        Optional<Group> optional = repository.getNotDelete(updateDto.getId());
        if (optional.isEmpty())
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().status(HttpStatus.BAD_REQUEST).build()), HttpStatus.BAD_REQUEST);
        // TODO: 3/25/2022

        Group group=optional.get();
        group.setName(updateDto.getName());
        group.setPrice(updateDto.getPrice());

        Group saved = repository.save(group);
        mapper.toDto(saved);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.OK);
    }

    public ResponseEntity<DataDto<List<GroupDto>>> getAll() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<AuthUser> authUser=authUserRepository.findByUsernameAndDeletedFalse(name);


        List<Group> all = repository.findByEducationIdAndDeletedFalse(authUser.get().getEducation().getId());
        return new ResponseEntity<>(new DataDto<>(mapper.toDto(all)), HttpStatus.OK);
    }

   /* public ResponseEntity<DataDto<List<GroupDto>>> getAll(Pageable pageable) {
        AuthUser user = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long orgId = user.getEducation().getId();
        List<Group> groups = repository.findByEducationIdAndDeletedFalse(orgId, pageable);
        return new ResponseEntity<>(new DataDto<>(mapper.toDto(groups)), HttpStatus.OK);
    }*/

    public ResponseEntity<DataDto<GroupDto>> get(Long id) {
        Optional<Group> optional = repository.getNotDelete(id);
        if (optional.isEmpty())
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().build()), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(new DataDto<>(mapper.toDto(optional.get())), HttpStatus.OK);
    }

    public ResponseEntity<DataDto<Boolean>> addUser(Long studentId,Long groupId){
        Optional<AuthUser> userOptional=authUserRepository.findById(studentId);
        if (userOptional.isEmpty())
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().status(HttpStatus.BAD_REQUEST).build()), HttpStatus.BAD_REQUEST);

        Optional<Group> groupOptional = repository.getNotDelete(groupId);
        if (groupOptional.isEmpty())
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().status(HttpStatus.BAD_REQUEST).build()), HttpStatus.BAD_REQUEST);
        Group group = groupOptional.get();
        List<AuthUser> users = group.getUsers();
        for (AuthUser authUser : users) {
            if (Objects.equals(authUser.getId(), studentId))
                return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().status(HttpStatus.BAD_REQUEST).build()), HttpStatus.BAD_REQUEST);
        }
        users.add(userOptional.get());
        group.setUsers(users);
        repository.save(group);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.OK);
    }


    public ResponseEntity<DataDto<List<AuthUserDto>>> users(Long groupId){
        List<AuthUser> users = repository.findByUsers(groupId);
        return new ResponseEntity<>(new DataDto<>(authUserMapper.toDto(users)), HttpStatus.OK);
    }



    public ResponseEntity<DataDto<Boolean>> deleteUser(Long studentId,Long groupId) {
        Optional<AuthUser> userOptional=authUserRepository.findById(studentId);
        if (userOptional.isEmpty())
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().status(HttpStatus.BAD_REQUEST).build()), HttpStatus.BAD_REQUEST);
        Optional<Group> groupOptional = repository.findById(groupId);
        if (groupOptional.isEmpty())
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().status(HttpStatus.BAD_REQUEST).build()), HttpStatus.BAD_REQUEST);

        Group group = groupOptional.get();
        List<AuthUser> users = groupOptional.get().getUsers();
        for (AuthUser authUser : users) {
            if (Objects.equals(authUser.getId(), studentId)) {
                users.remove(userOptional.get());
                group.setUsers(users);
                repository.save(group);
                return new ResponseEntity<>(new DataDto<>(true), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().status(HttpStatus.BAD_REQUEST).build()), HttpStatus.BAD_REQUEST);
    }
}
