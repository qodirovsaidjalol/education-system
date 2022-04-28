package com.education.validation.authuser;

import com.education.repository.AuthUserRepository;
import com.education.validation.authuser.UniqueUsername;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameValidation implements ConstraintValidator<UniqueUsername,String> {
    @Autowired
    AuthUserRepository repository;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
      return   repository.existsByUsername(s);
    }
}
