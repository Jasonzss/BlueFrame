package com.blue_dot.web.servlet;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Jason
 * @CreationDate 2023/02/04 - 22:46
 * @Description ：表示请求 与 请求处理器 映射关系。输入请求以获取对应的处理器（链）
 */
public interface HandlerMapping {
    HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception;
}
