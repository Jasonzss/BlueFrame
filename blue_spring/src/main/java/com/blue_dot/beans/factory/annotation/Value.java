package com.blue_dot.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * <p>
 * 修饰在属性 或者 方法/构造器 的参数上。
 * 表示这个参数的默认值，当Value里是占位符时会被Spring替换
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Value {

    /**
     * The actual value expression: e.g. "#{systemProperties.myProp}".
     */
    String value();

}
