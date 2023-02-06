package com.blue_core.web.servlet.mvc.method;

import com.blue_core.beans.BeansException;
import com.blue_core.web.servlet.HandlerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Jason
 * @CreationDate 2023/02/05 - 21:50
 * @Description ：方法处理器适配器的抽象实现，使用了模板方法模式（不过目前模板方法 handle() 还未成型），具体调用执行方法由子类实现
 */
public abstract class AbstractHandlerMethodAdapter implements HandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return handler instanceof HandlerMethod;
    }

    @Override
    public Object handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (supports(handler)){
            return handleInternal(request, response, (HandlerMethod) handler);
        }
        throw new BeansException(this.getClass().getSimpleName()+" 适配器无法执行的处理器类型："+handler.getClass().getName());
    }

    protected abstract Object handleInternal(HttpServletRequest request, HttpServletResponse response,
                                             HandlerMethod handlerMethod) throws Exception;
}
