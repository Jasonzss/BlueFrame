package com.blue_core.aop.bean;

import java.lang.reflect.Method;

/**
 * @Author Jason
 * @CreationDate 2023/01/31 - 21:38
 * @Description ：返回后advice仅在正常方法返回时调用，如果抛出异常则不会。
 * 这样的通知可以看到返回值，但不能改变它。
 */
public interface AfterReturningAdvice extends AfterAdvice {
    /**
     * Callback after a given method successfully returned.
     * @param returnValue the value returned by the method, if any
     * @param method method being invoked
     * @param args arguments to the method
     * @param target target of the method invocation. May be {@code null}.
     * @throws Throwable if this object wishes to abort the call.
     * Any exception thrown will be returned to the caller if it's
     * allowed by the method signature. Otherwise the exception
     * will be wrapped as a runtime exception.
     */
    void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable;
}
