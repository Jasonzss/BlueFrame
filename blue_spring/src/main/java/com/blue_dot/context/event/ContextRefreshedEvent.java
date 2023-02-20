package com.blue_dot.context.event;

/**
 * @Author Jason
 * @CreationDate 2023/01/30 - 17:44
 * @Description ：ApplicationContext刷新事件
 */
public class ContextRefreshedEvent extends ApplicationContextEvent{
    /**
     * 构造ApplicationContext刷新事件
     * @param source 刷新的ApplicationContext
     * @throws IllegalArgumentException 当事件源为空时
     */
    public ContextRefreshedEvent(Object source) {
        super(source);
    }
}
