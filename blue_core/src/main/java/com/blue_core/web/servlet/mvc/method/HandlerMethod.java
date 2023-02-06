package com.blue_core.web.servlet.mvc.method;

import cn.hutool.core.lang.Assert;

import java.lang.reflect.Method;

/**
 * @Author Jason
 * @CreationDate 2023/02/05 - 21:54
 * @Description ：处理器方法的定义，包含有执行方法的对象，以及执行的方法及其所需参数
 */
public class HandlerMethod {
    private final Object bean;

    private final Class<?> beanType;

    private final Method method;

    private final Class<?>[] parameterTypes;


    public HandlerMethod(Object bean, Method method) {
        this(bean, method, method.getParameterTypes());
    }

    public HandlerMethod(Object bean, Method method, Class<?>[] parameterTypes) {
        Assert.notNull(bean, "Handler对象不为空");
        Assert.notNull(method, "Handler的处理方法不能为空");
        this.bean = bean;
        this.beanType = bean.getClass();
        this.method = method;
        this.parameterTypes = parameterTypes;
    }

    public HandlerMethod(HandlerMethod handlerMethod) {
        this(handlerMethod.getBean(), handlerMethod.getMethod());
    }

    public Object getBean() {
        return bean;
    }

    public Class<?> getBeanType() {
        return beanType;
    }

    public Method getMethod() {
        return method;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }
}
