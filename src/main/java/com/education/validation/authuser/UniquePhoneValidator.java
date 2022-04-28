package com.education.validation.authuser;

import com.education.repository.AuthUserRepository;
import com.education.repository.EducationRepository;
import com.education.validation.authuser.PhoneUnique;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniquePhoneValidator implements ConstraintValidator<PhoneUnique, String> {
    @Autowired
    AuthUserRepository repository;
    @Autowired
    EducationRepository educationRepository;


    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

       return !(repository.existPhone(s) || educationRepository.existPhone(s));

    }
}
