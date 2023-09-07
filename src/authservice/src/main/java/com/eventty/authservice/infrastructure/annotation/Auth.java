package com.eventty.authservice.infrastructure.annotation;

import com.eventty.authservice.domain.Enum.UserRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {
    UserRole[] Roles() default {};
}
