package com.blue_dot.web.servlet.exception_resolver;

import cn.hutool.core.exceptions.UtilException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @Author Jason
 * @CreationDate 2023/02/14 - 22:58
 * @Description ：默认的异常处理器，在所有异常处理器都无效后，使用这个作为最后的处理
 */
public class SimpleExceptionResolver extends AbstractExceptionResolver{
    private static final String EXCEPTION_MSG_PREFIX = "The server throws an exception with the message: ";

    @Override
    public int getPriority() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //移除反射调用对异常的包装
        Exception target = ex;
        if (ex instanceof UtilException){
            Throwable cause = ex.getCause();
            if (cause instanceof InvocationTargetException){
                target = (Exception) cause.getCause();
            }
        }

        try {
            response.getWriter().write(EXCEPTION_MSG_PREFIX + target.toString());
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
