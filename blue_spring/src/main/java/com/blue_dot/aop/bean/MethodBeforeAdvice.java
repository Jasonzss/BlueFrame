package com.blue_dot.aop.bean;

import java.lang.reflect.Method;

/**
 * @Author Jason
 * @CreationDate 2023/01/31 - 22:01
 * @Description ：在调用被增强方法之前调用的advice。这样的advice不能阻止被调用方法的调用进行，除非它们抛出一个 Throwable。
 */
public interface MethodBeforeAdvice extends BeforeAdvice{

    /**
     * 调用给定方法之前的回调（AOP增强逻辑）。
     *
     * @param method 被调用的方法
     * @param args   方法入参
     * @param target 执行方法的实例对象，可以为空
     * @throws Throwable 如果此对象希望中止调用。如果方法签名允许，抛出的任何异常都将返回给调用者。
     *                   否则异常将被包装为运行时异常。
     */
    void before(Method method, Object[] args, Object target) throws Throwable;

}
