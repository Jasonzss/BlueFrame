package com.blue_dot.stereotype;

import java.lang.annotation.*;

/**
 * 表名带注释的类是“组件”。
 * 当使用基于注解的配置和类路径扫描时，这些类被认为是自动检测的候选对象。
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {

    String value() default "";

}
