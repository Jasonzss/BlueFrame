package com.blue_dot.context.annotation;

import java.lang.annotation.*;

/**
 * @Author Jason
 * @CreationDate 2023/02/01 - 23:49
 * @Description ：TODO 方法也有生命周期？
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Scope {
    String value() default "singleton";
}
