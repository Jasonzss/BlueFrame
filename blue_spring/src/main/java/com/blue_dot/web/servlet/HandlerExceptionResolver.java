package com.blue_dot.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Jason
 * @CreationDate 2023/02/14 - 22:02
 * @Description ：全局异常处理器的顶级接口，实现此接口并注册在DispatcherServlet中，
 * 可以在项目中抛出异常后，对异常进行一个兜底处理，这样就不至于返回tomcat的异常界面给用户看了。
 */
public interface HandlerExceptionResolver extends Comparable<HandlerExceptionResolver> {
    /**
     * 解析异常并处理异常
     * @param request 请求
     * @param response 响应
     * @param handler 处理器
     * @param ex 待处理的异常
     */
    void resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex);

    /**
     * 获取异常解析器的优先级，数字越小优先级越高
     */
    int getPriority();
}
