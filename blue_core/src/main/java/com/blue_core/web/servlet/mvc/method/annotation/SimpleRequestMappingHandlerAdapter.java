package com.blue_core.web.servlet.mvc.method.annotation;

import com.blue_core.beans.BeansException;
import com.blue_core.web.servlet.mvc.method.AbstractHandlerMethodAdapter;
import com.blue_core.web.servlet.mvc.method.HandlerMethod;
import com.blue_core.web.servlet.mvc.method.support.InvocableHandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Jason
 * @CreationDate 2023/02/05 - 21:59
 * @Description ：RequestMappingHandler适配器的简单实现
 */
public class SimpleRequestMappingHandlerAdapter extends AbstractHandlerMethodAdapter {
    /**
     * 目前仅支持对request和response参数的注入
     */
    @Override
    protected Object handleInternal(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) throws Exception {
        InvocableHandlerMethod method = new InvocableHandlerMethod(handlerMethod);
        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] args = parameterTypes == null ? new Object[0] : new Object[parameterTypes.length];

        for (int i = 0; i < args.length; i++) {
            Class<?> parameterType = parameterTypes[i];
            if (parameterType.isAssignableFrom(request.getClass())){
                args[i] = request;
            }else if (parameterType.isAssignableFrom(response.getClass())){
                args[i] = response;
            }else {
                //TODO RequestMapping方法参数的注入
                throw new BeansException("尚不支持这类参数的自动注入"+handlerMethod.getMethod()+"【"+parameterType+"】");
            }
        }

        //注入参数
        method.setArgs(args);

        return method.invokeAndHandle();
    }
}
