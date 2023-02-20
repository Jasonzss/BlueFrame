package com.blue_dot.aop.bean;

import java.lang.reflect.Method;

/**
 * @Author Jason
 * @CreationDate 2023/02/01 - 15:57
 * @Description ：
 */
public class MyBeforeAdvice implements MethodBeforeAdvice{
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("捕捉到即将执行的方法："+method.getName());
    }
}
