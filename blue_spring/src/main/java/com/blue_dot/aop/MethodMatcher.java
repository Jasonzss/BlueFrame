package com.blue_dot.aop;

import java.lang.reflect.Method;

/**
 * @Author Jason
 * @CreationDate 2023/01/31 - 16:35
 * @Description ：Pointcut的一部分：检查目标方法是否符合advice的条件。
 * 方法筛选器
 */
public interface MethodMatcher {

    /**
     * 判断给定的方法是否匹配
     * @param method 目标给定的方法
     * @param targetClass 方法所在的类
     * @return 给定的方法是否匹配
     */
    boolean matches(Method method, Class<?> targetClass);
}
