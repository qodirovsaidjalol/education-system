package com.education.validation.authuser;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE,ElementType.FIELD})
@Documented
@Constraint(validatedBy = {UniquePhoneValidator.class})
public @interface PhoneUnique {

    String message() default "phone is already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
