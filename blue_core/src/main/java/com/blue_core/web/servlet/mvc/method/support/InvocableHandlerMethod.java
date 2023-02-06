package com.blue_core.web.servlet.mvc.method.support;

import cn.hutool.core.util.ReflectUtil;
import com.blue_core.web.servlet.mvc.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Author Jason
 * @CreationDate 2023/02/06 - 15:00
 * @Description ：表示可以调用执行的方法，比父类多了方法执行所需要的参数
 */
public class InvocableHandlerMethod extends HandlerMethod {
    private static final Object[] EMPTY_ARGS = new Object[0];

    private Object[] args;

    public InvocableHandlerMethod(HandlerMethod handlerMethod) {
        super(handlerMethod);
    }

    public Object invokeAndHandle(){
        return ReflectUtil.invoke(getBean(), getMethod(), args.length == 0 ? null : args);
    }

    public void setArgs(Object[] args){
        Class<?>[] parameterTypes = getParameterTypes();
        if (parameterTypes == null || parameterTypes.length == 0){
            this.args = EMPTY_ARGS;
        }else {
            this.args = args;
        }
    }
}
