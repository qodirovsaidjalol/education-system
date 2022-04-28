package com.education.validation.authuser;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;


@Target({ElementType.TYPE,ElementType.FIELD,ElementType.METHOD})
@Documented
@Constraint(validatedBy = {UniqueUsernameValidation.class})
public @interface UniqueUsername {
    String message() default "username already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
