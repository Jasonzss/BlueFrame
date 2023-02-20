package com.blue_dot.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * Qualifier 一般与 Autowired 配合使用在方法或属性上，用于指定属性注入的beanName
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Qualifier {

    String value() default "";

}
