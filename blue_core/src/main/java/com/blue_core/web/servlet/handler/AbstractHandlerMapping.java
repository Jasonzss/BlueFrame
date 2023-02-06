package com.blue_core.web.servlet.handler;

import com.blue_core.web.servlet.HandlerExecutionChain;
import com.blue_core.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Jason
 * @CreationDate 2023/02/05 - 22:30
 * @Description ：HandlerMapping接口的抽象实现。
 * 使用模板方法模式，实现了大部分处理器映射的通用逻辑，留有抽象方法由子类自由实现
 */
public abstract class AbstractHandlerMapping implements HandlerMapping {
    @Override
    public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
        return null;
    }
}
