package com.blue_dot.web;

import com.blue_dot.stereotype.Component;
import com.blue_dot.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Jason
 * @CreationDate 2023/02/06 - 16:03
 * @Description ：
 */

@Component
public class MyInterceptor implements HandlerInterceptor {
    private long time;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        time = System.currentTimeMillis();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, Object result) throws Exception {
        System.out.println(handler.getClass().getName() + " handle the request in " + (System.currentTimeMillis() - time)+" ms");
    }
}
