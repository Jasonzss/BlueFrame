package com.blue_dot.aop.framework.adapter;

import com.blue_dot.aop.bean.BeforeAdvice;
import com.blue_dot.aop.bean.MethodBeforeAdvice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @Author Jason
 * @CreationDate 2023/01/31 - 23:03
 * @Description ：用于包装MethodBeforeAdvice的拦截器。
 * 由AOP框架内部使用；应用程序开发人员不需要直接使用此类。
 */
public class MethodBeforeAdviceInterceptor implements MethodInterceptor, BeforeAdvice {
    private MethodBeforeAdvice advice;

    public MethodBeforeAdviceInterceptor() {
    }

    public MethodBeforeAdviceInterceptor(MethodBeforeAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        //在目标方法调用前执行before里的AOP逻辑
        this.advice.before(invocation.getMethod(), invocation.getArguments(), invocation.getThis());
        //执行目标方法
        return invocation.proceed();
    }
}
