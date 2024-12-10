package com.bit.microservices.service_approval.utils.annotations;

import org.mapstruct.Mappings;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface RelatedEntity {
    String prefix();

    Class<?> dtoClass();
}
