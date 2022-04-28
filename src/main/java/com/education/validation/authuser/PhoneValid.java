package com.education.validation.authuser;


import com.education.validation.authuser.PhoneValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.TYPE,ElementType.ANNOTATION_TYPE,ElementType.METHOD})
@Documented
@Constraint(validatedBy = {PhoneValidator.class})
public @interface PhoneValid {

    String message() default "invalid phone";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
