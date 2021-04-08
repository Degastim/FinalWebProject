package com.epam.pharmacy.command;

import com.epam.pharmacy.model.entity.User;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandAccessLevel {
    User.Role[] value() default {
            User.Role.CUSTOMER,
            User.Role.DOCTOR,
            User.Role.PHARMACIST};
}