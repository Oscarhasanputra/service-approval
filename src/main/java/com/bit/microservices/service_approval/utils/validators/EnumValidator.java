package com.bit.microservices.service_approval.utils.validators;

import com.bit.microservices.service_approval.enums.EnumGetter;
import com.bit.microservices.service_approval.utils.annotations.ValidEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {

    private ValidEnum validEnum;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        // No initialization needed
        this.validEnum = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
//        return false;
        System.out.println("is valid ");
        System.out.println(value);
        System.out.println(this.validEnum.enumClass());
        return EnumGetter.isValidValue(this.validEnum.enumClass(),value);
    }
}
