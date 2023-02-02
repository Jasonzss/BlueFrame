package com.blue_core.general_test.inherited_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author Jason
 * @CreationDate 2022/12/19 - 21:52
 * @Description ：
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Father {
    String value() default "1";
}
