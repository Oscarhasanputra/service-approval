package com.bit.microservices.service_approval.utils.annotations;

import com.bit.microservices.service_approval.enums.EnumGetter;
import com.bit.microservices.service_approval.utils.validators.EnumValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EnumValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEnum {
    String message() default "Invalid Field Format";
    Class<?>[] groups() default {};

    Class<? extends EnumGetter> enumClass();
    Class<? extends Payload>[] payload() default {};
}
