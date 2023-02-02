package com.blue_core.context.event;

/**
 * @Author Jason
 * @CreationDate 2023/01/30 - 17:42
 * @Description ：ApplicationContext 关闭事件
 */
public class ContextCloseEvent extends ApplicationContextEvent{
    /**
     * 构造 ApplicationContext 关闭事件
     * @param source 关闭的 ApplicationContext
     * @throws IllegalArgumentException 当事件源为空时
     */
    public ContextCloseEvent(Object source) {
        super(source);
    }
}
