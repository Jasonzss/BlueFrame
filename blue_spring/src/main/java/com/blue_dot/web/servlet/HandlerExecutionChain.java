package com.blue_dot.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Jason
 * @CreationDate 2023/02/04 - 22:47
 * @Description ：处理器链，由一个处理器和多个拦截器组成
 * 每个拦截器都有 前置处理方法 和 后置处理方法，用在处理器执行的前后调用。
 * 这些方法默认的实现都为空
 */
public class HandlerExecutionChain {
    private final Object handler;

    private final List<HandlerInterceptor> interceptors = new ArrayList<>();

    public HandlerExecutionChain(Object handler) {
        this.handler = handler;
    }

    /**
     * 执行所有的前置处理方法
     * @return 返回false时则表示请求被过滤，即处理器不会执行，处理器链执行提前结束。
     */
    boolean applyPreHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        for (HandlerInterceptor interceptor : interceptors){
            if (!interceptor.preHandle(request,response,handler)){
                return false;
            }
        }
        return true;
    }

    /**
     * 执行所有的后置处理方法
     */
    void applyPostHandle(HttpServletRequest request, HttpServletResponse response, Object result) throws Exception {
        for (HandlerInterceptor interceptor : interceptors){
            interceptor.postHandle(request,response,handler,result);
        }
    }

    public void addInterceptor(HandlerInterceptor interceptor){
        interceptors.add(interceptor);
    }


    public Object getHandler() {
        return this.handler;
    }
}
