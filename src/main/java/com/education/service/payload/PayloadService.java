package com.education.service.payload;

import com.education.dto.response.AppErrorDto;
import com.education.dto.response.DataDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.education.dto.payload.PayloadCreateDto;
import com.education.dto.payload.PayloadDto;
import com.education.dto.payload.PayloadUpdateDto;
import com.education.entity.AuthUser;
import com.education.entity.Group;
import com.education.entity.Payload;
import com.education.mapper.PayloadMapper;
import com.education.repository.AuthUserRepository;
import com.education.repository.GroupRepository;
import com.education.repository.PayloadRepository;
import com.education.service.AbstractService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PayloadService extends AbstractService<PayloadRepository, PayloadMapper> {

    private final AuthUserRepository userRepository;
    private final GroupRepository groupRepository;

//    private static final AuthUser getSession() = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


    protected PayloadService(PayloadRepository repository, PayloadMapper mapper, AuthUserRepository userRepository, GroupRepository groupRepository) {
        super(repository, mapper);
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    private AuthUser getSession() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<AuthUser> optional = userRepository.findByUsernameAndDeletedFalse(name);
        if (optional.isEmpty())
            return null;
        return optional.get();
    }

    public ResponseEntity<DataDto<Long>> create(PayloadCreateDto createDto) {
        Long id = createDto.getStudentId();
        AuthUser user = getUser(id);
        if (user == null)
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().status(HttpStatus.BAD_REQUEST).message("user not found").build()), HttpStatus.OK);
        Payload payload = mapper.fromCreateDto(createDto);
        Group group = getGroup(createDto.getGroupId());
        if (group == null || !Objects.equals(group.getEducation().getId(), getSession().getId()))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().status(HttpStatus.BAD_REQUEST).message("group not found").build()), HttpStatus.OK);
        payload.setStudent(user);
        payload.setGroup(group);
        payload = repository.save(payload);
        return new ResponseEntity<>(new DataDto<>(payload.getId()), HttpStatus.OK);
    }

    public ResponseEntity<DataDto<List<PayloadDto>>> getAll() {
        List<Long> ids = repository.findAllByDeletedFalse();
        return getDataDtoResponseEntity(ids);
    }

    public ResponseEntity<DataDto<List<PayloadDto>>> getAllByEducation() {
        List<Long> ids = repository.findAllByEducation(1L);
        return getDataDtoResponseEntity(ids);
    }
    
    
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','HR','STUDENT')")
    public ResponseEntity<DataDto<List<PayloadDto>>> getByStudentAndGroup(Long studentId, Long groupId) {
        List<Long> ids = repository.findAllByStudentAndGroupAndDeletedFalse(studentId, groupId);
        if (ids.isEmpty())
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().status(HttpStatus.BAD_REQUEST).message("Student not found").build()), HttpStatus.BAD_REQUEST);
        return getDataDtoResponseEntity(ids);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','HR')")
    public ResponseEntity<DataDto<PayloadDto>> get(Long id) {
        Optional<Payload> optional = repository.findByIdAndDeletedFalse(id);
        if (optional.isEmpty())
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().status(HttpStatus.BAD_REQUEST).message("Payload not found").build()), HttpStatus.BAD_REQUEST);
        PayloadDto payload = mapper.toDto(optional.get());
        payload.setStudentName(optional.get().getStudent().getUsername());
        payload.setGroupName(optional.get().getGroup().getName());
        payload.setDateOfPayment(optional.get().getCreatedAt().toLocalDate().toString());
        payload.setId(id);
        return new ResponseEntity<>(new DataDto<>(payload), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','HR','STUDENT')")
    public ResponseEntity<DataDto<List<PayloadDto>>> getByStudent(Long id) {
        List<Long> ids = repository.findAllByStudentAndDeletedFalse(id);
        if (ids.isEmpty())
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().status(HttpStatus.BAD_REQUEST).message("Student not found").build()), HttpStatus.BAD_REQUEST);
        return getDataDtoResponseEntity(ids);
    }

    private ResponseEntity<DataDto<List<PayloadDto>>> getDataDtoResponseEntity(List<Long> ids) {
        List<PayloadDto> lists = new ArrayList<>();
        for (Long aLong : ids) {
            lists.add(Objects.requireNonNull(get(aLong).getBody()).getData());
        }
        return new ResponseEntity<>(new DataDto<>(lists), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','HR')")
    public ResponseEntity<DataDto<Boolean>> update(PayloadUpdateDto updateDto) {
        Optional<Payload> optional = repository.findByIdAndDeletedFalse(updateDto.getId());
        if (optional.isEmpty())
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().status(HttpStatus.BAD_REQUEST).message("Payload not found").build()), HttpStatus.BAD_REQUEST);
        Payload payload = optional.get();
        if (!Objects.equals(payload.getGroup().getEducation().getId(), getSession().getId()))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().status(HttpStatus.BAD_REQUEST).message("group not found").build()), HttpStatus.OK);
        payload.setAmount(updateDto.getAmount());
        repository.save(payload);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','HR')")
    public ResponseEntity<DataDto<Boolean>> delete(Long id) {
        Optional<Payload> optional = repository.findByIdAndDeletedFalse(id);
        if (optional.isEmpty())
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().status(HttpStatus.BAD_REQUEST).message("Payload not found").build()), HttpStatus.BAD_REQUEST);
        Payload payload = optional.get();
        payload.setDeleted(true);
        repository.save(payload);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.OK);
    }


    private AuthUser getUser(Long id) {
        AuthUser userOptional = userRepository.getNotDelete(id);
        if (userOptional == null || !userOptional.getRole().name().equals("STUDENT")) return null;
        return userOptional;
    }

    private Group getGroup(Long id) {
        Optional<Group> optional = groupRepository.findById(id);
        if (optional.isEmpty()) return null;
        return optional.get();
    }

}
