package com.blue_dot.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @Author Jason
 * @CreationDate 2023/01/31 - 16:14
 * @Description ：自定义代理逻辑
 */
public class MyInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        long timeMillis = System.currentTimeMillis();
        try{
            //1.
            Object result = invocation.proceed();

            //3.
            return result;
        }finally {
            //2.
            System.out.println("return之前会先执行finally中的内容");
            System.out.println("AOP监控逻辑的插入------------------");
            System.out.println("当前监控的方法："+invocation.getMethod().getName());
            System.out.println("此方法耗时："+(System.currentTimeMillis()-timeMillis));
            System.out.println("AOP监控结束-----------------------");
        }
    }
}
