package com.blue_dot.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Jason
 * @CreationDate 2023/02/10 - 15:02
 * @Description ：
 */
public interface ResultHandler extends Comparable<ResultHandler>{
    boolean support(Object result);

    void handleResult(Object result, HttpServletRequest request, HttpServletResponse response);

    int getPriority();
}
