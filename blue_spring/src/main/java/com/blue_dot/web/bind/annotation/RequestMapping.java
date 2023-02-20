package com.blue_dot.web.bind.annotation;

import java.lang.annotation.*;

/**
 * @Author Jason
 * @CreationDate 2023/02/05 - 13:49
 * @Description ：包含请求与处理方法之间的映射关系。用于注释在类和方法上。
 * value()的值即为url中对应的servletPath：
 *      http://localhost:8080/mvc_test/my/t01 -> /my/t01（由类注解+方法注解得到）
 * 当类上没有注解时，对应的Path只会保留方法上的注解的值
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
    String value() default "";
}