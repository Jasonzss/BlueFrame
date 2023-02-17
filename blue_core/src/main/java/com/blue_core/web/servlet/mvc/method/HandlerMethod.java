package com.blue_core.web.servlet.mvc.method;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Enumeration;

/**
 * @Author Jason
 * @CreationDate 2023/02/05 - 21:54
 * @Description ：处理器方法的定义，包含有执行方法的对象，以及执行的方法及其所需参数
 */
public class HandlerMethod {
    private final Object bean;

    private final Class<?> beanType;

    private final Method method;

    private final Parameter[] parameters;


    public HandlerMethod(Object bean, Method method) {
        Assert.notNull(bean, "Handler对象不为空");
        Assert.notNull(method, "Handler的处理方法不能为空");
        this.bean = bean;
        this.beanType = bean.getClass();
        this.method = method;
        this.parameters = method.getParameters();
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

    public Parameter[] getParameters() {
        return parameters;
    }

    public Class<?>[] getParameterTypes(){
        return method.getParameterTypes();
    }


    /**
     * 根据提供的参数查找目标参数对应的真实参数
     * @param parameter 目标参数
     * @param request HttpServletRequest请求
     * @param providedArgs 提供的各类参数
     * @return 目标参数对应的真实参数
     */
    protected Object findProvidedArgument(Parameter parameter, HttpServletRequest request, Object... providedArgs) {
        if (parameter.getType().isInstance(request)){
            return request;
        }

        if (!ObjectUtil.isEmpty(providedArgs)) {
            for (Object providedArg : providedArgs) {
                if (parameter.getType().isInstance(providedArg)) {
                    return providedArg;
                }
            }
        }

        //从Attribute中byName查找参数
        Object attribute = request.getAttribute(parameter.getName());
        if (attribute != null){
            return attribute;
        }

        //从Attribute中byType查找参数
        Enumeration<String> attributeNames = request.getAttributeNames();
        while (attributeNames.hasMoreElements()){
            attribute = request.getAttribute(attributeNames.nextElement());
            if (parameter.getType().isInstance(attribute)){
                return attribute;
            }
        }

        //去parameter里找参数
        return request.getParameter(parameter.getName());
    }
}
