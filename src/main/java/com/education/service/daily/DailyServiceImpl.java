package com.education.service.daily;


import com.education.criteria.DailyCriteria;
import com.education.dto.daily.DailyCreateDto;
import com.education.dto.daily.DailyDto;
import com.education.dto.daily.DailyUpdateDto;
import com.education.dto.response.AppErrorDto;
import com.education.dto.response.DataDto;
import com.education.entity.*;
import com.education.mapper.DailyMapper;
import com.education.repository.AuthUserRepository;
import com.education.repository.DailyRepository;
import com.education.repository.GroupRepository;
import com.education.service.AbstractService;
import com.education.service.auth.AuthUserService;
import com.education.service.group.GroupServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DailyServiceImpl extends AbstractService<DailyRepository, DailyMapper> implements DailyService {
    protected final AuthUserService userService;
    protected final GroupServiceImpl groupService;
    private final GroupRepository groupRepository;
    private final AuthUserRepository userRepository;



    protected DailyServiceImpl(DailyRepository repository, @Qualifier("dailyMapperImpl") DailyMapper mapper, AuthUserService userService, GroupServiceImpl groupService, GroupRepository groupRepository, AuthUserRepository userRepository) {
        super(repository, mapper);
        this.userService = userService;
        this.groupService = groupService;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<DataDto<Long>> create(DailyCreateDto createDto) {
        Daily daily = mapper.fromCreateDto(createDto);
        Optional<Group> group = groupRepository.findById(createDto.getGroupId());
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<AuthUser> byUsernameAndDeletedFalse = userRepository.findByUsernameAndDeletedFalse(name);
        AuthUser student = userRepository.getNotDelete(createDto.getStudentId());
        if (group.isEmpty() || student==null)
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().status(HttpStatus.BAD_REQUEST).message("User or Group not found!").build()), HttpStatus.BAD_REQUEST);
        daily.setGroup(group.get());
        daily.setStudent(student);
        daily = repository.save(daily);
        return new ResponseEntity<>(new DataDto<>(daily.getId()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DataDto<Void>> delete(Long id) {
        if(repository.findById(id).isEmpty()){
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().status(HttpStatus.BAD_REQUEST).message("Daily not found").build()), HttpStatus.BAD_REQUEST);
        }
        repository.deleteSoft(id);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DataDto<Long>> update(DailyUpdateDto updateDto) {
        Optional<Daily> optional = repository.findById(updateDto.getId());
        if (optional.isEmpty())
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().status(HttpStatus.BAD_REQUEST).message("Daily not found").build()), HttpStatus.BAD_REQUEST);
        Daily daily = optional.get();
        if (updateDto.getBall()!=null) {
            daily.setBall(updateDto.getBall());
        }
        if (!Objects.equals(updateDto.getDescription(), "string")) {
            daily.setDescription(updateDto.getDescription());
        }
        if (updateDto.getGroupId() > 0) {
            Optional<Group> optionalGroup = groupRepository.findById(updateDto.getGroupId());
            if (optionalGroup.isEmpty())
                return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().status(HttpStatus.BAD_REQUEST).message("Group not found!").build()), HttpStatus.BAD_REQUEST);
            daily.setGroup(optionalGroup.get());
        }
        if(updateDto.getIsCame()!=null){
            daily.setCame(updateDto.getIsCame());
        }
        if(updateDto.getTime()!=null){
            daily.setTime(updateDto.getTime());
        }
        repository.save(daily);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DataDto<List<DailyDto>>> getAll(DailyCriteria criteria) {
        Pageable pageable=  PageRequest.of(criteria.getPage(),criteria.getSize());
        Page<Daily> dailies=repository.findAllByDeletedFalse(pageable);
//        List<Daily> dailies = repository.getAllNotDelete(criteria.getPage(),criteria.getSize());
        List<Daily> collect = dailies.get().collect(Collectors.toList());
        List<DailyDto> dailyDtos = mapper.toDto(collect);
        if(dailies.getTotalElements()==0){
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().status(HttpStatus.BAD_REQUEST).message("You haven't any Daily").build()), HttpStatus.BAD_REQUEST);
        }
        return new  ResponseEntity<>(new DataDto<>(dailyDtos),HttpStatus.OK);
//        return getAllCheck(null);
    }

    private ResponseEntity<DataDto<List<DailyDto>>> getAllCheck(List<Daily> dailies) {
        if(dailies.isEmpty()){
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().status(HttpStatus.BAD_REQUEST).message("You haven't any Daily").build()), HttpStatus.BAD_REQUEST);
        }
        dailies.removeIf(Auditable::isDeleted);
        return new ResponseEntity<>(new DataDto<>(mapper.toDto(dailies)), HttpStatus.OK);
    }

    public ResponseEntity<DataDto<List<DailyDto>>> getAllByStudentAndGroupId(DailyCriteria criteria,Long studentId,Long groupId){
        List<Daily> dailies=repository.findAllByStudentIdAndGroupIdAndDeletedFalse(studentId,groupId);
        if(dailies.isEmpty()){
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().status(HttpStatus.BAD_REQUEST).message("Haven't any daily").build()), HttpStatus.BAD_REQUEST);
        }
        return getAllCheck(dailies);
    }

    public ResponseEntity<DataDto<List<DailyDto>>> getAllByTime(DailyCriteria criteria, LocalDate date){
        List<Daily> dailies=repository.findAllByTimeAndDeleted(date,false);
        return getAllCheck(dailies);
    }

    @Override
    public ResponseEntity<DataDto<DailyDto>> get(Long id) {
        Optional<Daily> daily = repository.findByIdAndDeletedFalse(id);
        if(daily.isEmpty()){
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().status(HttpStatus.BAD_REQUEST).message("Daily not found").build()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new DataDto<>(mapper.toDto(daily.get())), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DataDto<Long>> totalCount(DailyCriteria criteria) {
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.OK);
    }
}
