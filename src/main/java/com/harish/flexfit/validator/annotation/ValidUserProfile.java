package com.harish.flexfit.validator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.harish.flexfit.validator.impl.UserProfileValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserProfileValidator.class)
public @interface ValidUserProfile {
    String message() default "Invalid profile data";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
