package com.blue_core.web.servlet.mvc.method.annotation;

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

        //注入参数并调用
        return method.invoke(request,response);
    }
}
