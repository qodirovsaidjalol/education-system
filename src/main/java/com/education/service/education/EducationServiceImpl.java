package com.education.service.education;


import com.education.criteria.EducationCriteria;
import com.education.dto.education.EducationCreateDto;
import com.education.dto.education.EducationDto;
import com.education.dto.education.EducationUpdateDto;
import com.education.dto.response.DataDto;
import com.education.entity.AuthUser;
import com.education.entity.Education;
import com.education.enums.Role;
import com.education.mapper.AddressMapper;
import com.education.mapper.EducationMapper;
import com.education.repository.AddressRepository;
import com.education.repository.AuthUserRepository;
import com.education.repository.EducationRepository;
import com.education.service.AbstractService;
import com.education.service.FileStorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


@Service
public class EducationServiceImpl extends AbstractService<EducationRepository, EducationMapper> implements EducationService {

    private final FileStorageService fileStorageService;

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final AuthUserRepository authUserRepository;

    protected EducationServiceImpl(EducationRepository repository, EducationMapper mapper, FileStorageService fileStorageService, AddressRepository addressRepository, AddressMapper addressMapper, AuthUserRepository authUserRepository) {
        super(repository, mapper);
        this.fileStorageService = fileStorageService;
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
        this.authUserRepository = authUserRepository;
    }

    @Override
    public ResponseEntity<DataDto<Long>> create(EducationCreateDto createDto) throws ParseException {

        Education education = mapper.fromCreateDto(createDto);
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(createDto.getPayedUntil());
        education.setPayedUntil(date);
        addressRepository.save(education.getAddress());

        // education.setAddress(addressRepository.save(addressMapper.fromCreateDto(createDto.getAddress())));
        //education.setLecithinPath(fileStorageService.store(createDto.getLecithin()));
        //  education.setLogoPath(fileStorageService.store(createDto.getLogo()));
        return new ResponseEntity<>(new DataDto<>(repository.save(education).getId()), HttpStatus.OK);


    }


    @Override
    public ResponseEntity<DataDto<Void>> delete(Long id) {

        Education education = repository.getNotDelete(id);
        education.setDeleted(true);
        repository.save(education);

        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<DataDto<Long>> update(EducationUpdateDto updateDto) {

        Education education1 = repository.getNotDelete(updateDto.getId());
        if (Objects.isNull(education1)) {
            throw new NotFoundException("{education.not.found}");
        }

        if(!Objects.equals(education1.getOwner(), getSession().getId()) || !getSession().getRole().equals(Role.SUPER_ADMIN)) {
            throw new RuntimeException("{bad.request}");
        }
        if (Objects.nonNull(updateDto.getEmail())) education1.setEmail(updateDto.getEmail());
        if (Objects.nonNull(updateDto.getName())) education1.setName(updateDto.getEmail());
        if (Objects.nonNull(updateDto.getPhone())) education1.setPhone(updateDto.getEmail());
        if (Objects.nonNull(updateDto.getUrl())) education1.setPhone(updateDto.getUrl());

        return new ResponseEntity<>(new DataDto<>(repository.save(education1).getId()), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<DataDto<List<EducationDto>>> getAll(EducationCriteria criteria) {

        List<Education> all = repository.getAll(criteria.getPage(), criteria.getSize());

        return new ResponseEntity<>(new DataDto<>(mapper.toDto(all)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DataDto<EducationDto>> get(Long id) {
        Education education = repository.getNotDelete(id);
        if (Objects.isNull(education)) {
            throw new NotFoundException("education not found");
        }
        return new ResponseEntity<>(new DataDto<>(mapper.toDto(education)), HttpStatus.OK);

    }


    @Override
    public ResponseEntity<DataDto<Long>> totalCount(EducationCriteria criteria) {

        return null;
    }

    public ResponseEntity<DataDto<Void>> block(Long id) {

        Education education = repository.getNotDelete(id);
        if (Objects.isNull(education)) {
            throw new NotFoundException("Education not found");
        }
        education.setBlock(!education.isBlock());
        repository.save(education);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.OK);

    }

    public Void payed(String data, Long id) throws ParseException {
        String sDate1 = "31/12/1998";
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(data);
        Education education = repository.getNotDelete(id);
        if (Objects.isNull(education)) {
            throw new NotFoundException("{education.not.found}");
        }
        education.setPayedUntil(date);

        repository.save(education);

        return null;
    }

    public ResponseEntity<DataDto<Void>> updateOwner(Long ownerId, Long eduId) {
        AuthUser authUser = authUserRepository.getNotDelete(ownerId);
        if (!authUser.isBlock()) {
            throw new RuntimeException("{user.block}");
        }
        if (!authUser.isActive()) {
            throw new RuntimeException("{user.not.active}");
        }
        Education education = repository.getNotDelete(eduId);
        education.setOwner(authUser.getId());
        return null;
    }

    public ResponseEntity<DataDto<EducationDto>> getOne() {

        return get(4L);
    }


    public AuthUser getSession() {
        String name=SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<AuthUser> optional = authUserRepository.findByUsernameAndDeletedFalse(name);
        return optional.get();
    }

    @SuppressWarnings("rawtypes")
    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    void testRequest() {
        System.out.println("asssalom alaykum");
        List<Education> educations = repository.getAllByDeletedIsFalse();
        for (Education education : educations) {
            long millis = System.currentTimeMillis();
            if (education.getPayedUntil().getTime() > millis) {
                education.setBlock(false);
            }
        }
        repository.saveAll(educations);
    }
}