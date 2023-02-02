package com.blue_core.general_test.inherited_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author Jason
 * @CreationDate 2022/12/19 - 21:53
 * @Description ：
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Father
public @interface Son {
    String name();
}
