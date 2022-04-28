package com.education.service.subject;

import com.education.criteria.GenericCriteria;
import com.education.dto.response.AppErrorDto;
import com.education.dto.response.DataDto;
import com.education.dto.subject.SubjectCreateDto;
import com.education.dto.subject.SubjectDto;
import com.education.dto.subject.SubjectUpdateDto;
import com.education.entity.Education;
import com.education.entity.Subject;
import com.education.exceptions.BadRequestException;
import com.education.mapper.SubjectMapper;
import com.education.repository.EducationRepository;
import com.education.repository.SubjectRepository;
import com.education.service.AbstractService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SubjectServiceImpl extends AbstractService<SubjectRepository, SubjectMapper> {

    private final EducationRepository educationRepository;

    protected SubjectServiceImpl(SubjectRepository repository, SubjectMapper mapper, EducationRepository educationRepository) {
        super(repository, mapper);
        this.educationRepository = educationRepository;
    }

    public ResponseEntity<DataDto<Long>> create(SubjectCreateDto createDto) {
        String name = createDto.getName().toUpperCase(Locale.ROOT);
        // TODO: 3/28/2022 sessionni education idsi bo'yicha olinadi
        Education education = getEducation(1L);
        if (repository.existsByNameAndEducation(name, education))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().status(HttpStatus.BAD_REQUEST).build()), HttpStatus.BAD_REQUEST);
        Subject subject = mapper.fromCreateDto(createDto);
        subject.setEducation(education);
        subject.setName(name);
        subject = repository.save(subject);
        return new ResponseEntity<>(new DataDto<>(subject.getId()), HttpStatus.OK);
    }

    public ResponseEntity<DataDto<Boolean>> delete(Long id) {
        // TODO: 3/28/2022 sessionni education idsi bo'yicha olinadi
        Optional<Subject> optional = repository.findByIdAndEducationAndDeletedFalse(id, 1L);
        if (optional.isEmpty())
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().status(HttpStatus.BAD_REQUEST).build()), HttpStatus.BAD_REQUEST);
        Subject subject = optional.get();
        subject.setDeleted(true);
        repository.save(subject);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.OK);
    }

    public ResponseEntity<DataDto<Boolean>> update(SubjectUpdateDto updateDto) {
        // TODO: 3/28/2022 sessionni education idsi bo'yicha olinadi
        Optional<Subject> optional = repository.findByIdAndEducationAndDeletedFalse(updateDto.getId(), 1L);
        if (optional.isEmpty() || repository.existsByNameAndEducation(updateDto.getName().toUpperCase(Locale.ROOT), getEducation(1L)))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().status(HttpStatus.BAD_REQUEST).build()), HttpStatus.BAD_REQUEST);
        Subject subject = optional.get();
        subject.setName(updateDto.getName());
        repository.save(subject);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.OK);
    }

    public ResponseEntity<DataDto<List<SubjectDto>>> getAll(GenericCriteria criteria) {
        List<Long> ids = repository.findAllByNotDeleted();
        return getDataDtoResponseEntity(ids);
    }

    public ResponseEntity<DataDto<List<SubjectDto>>> getAllByEducation() {
        // TODO: 3/28/2022 sessionni education idsi bo'yicha olinadi
        List<Long> ids = repository.findAllByEducationAndDeletedFalse(1L);
        return getDataDtoResponseEntity(ids);
    }

    private ResponseEntity<DataDto<List<SubjectDto>>> getDataDtoResponseEntity(List<Long> ids) {
        List<SubjectDto> lists = new ArrayList<>();
        for (Long id : ids) {
            lists.add(Objects.requireNonNull(get(id).getBody()).getData());
        }
        return new ResponseEntity<>(new DataDto<>(lists), HttpStatus.OK);
    }

    public ResponseEntity<DataDto<SubjectDto>> get(Long id) {
        Optional<Subject> optional = repository.findByIdAndDeletedFalse(id);
        if (optional.isEmpty())
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().message("subject not found").status(HttpStatus.BAD_REQUEST).build()), HttpStatus.BAD_REQUEST);
        SubjectDto subjectDto = mapper.toDto(optional.get());
        subjectDto.setId(id);
        subjectDto.setName(optional.get().getName());
       // subjectDto.setEducationName(optional.get().getEducation().getName());
        return new ResponseEntity<>(new DataDto<>(mapper.toDto(optional.get())), HttpStatus.OK);
    }

    public Education getEducation(Long id) {
        Optional<Education> optional = educationRepository.findById(id);
        if (optional.isEmpty()) throw new BadRequestException("not found", new Throwable());
        return optional.get();
    }
}
