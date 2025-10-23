package com.phenikaa.hospital_management.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD}) 
@Retention(RetentionPolicy.RUNTIME) 
@Constraint(validatedBy = UniqueUsernameValidator.class)
public @interface UniqueUsername {
    
    String message() default "Tên đăng nhập đã tồn tại"; // thông báo lỗi
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}