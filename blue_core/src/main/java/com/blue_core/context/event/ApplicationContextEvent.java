package com.blue_core.context.event;

import com.blue_core.context.ApplicationContext;
import com.blue_core.context.ApplicationEvent;

/**
 * @Author Jason
 * @CreationDate 2023/01/30 - 17:37
 * @Description ：
 */
public class ApplicationContextEvent extends ApplicationEvent {
    /**
     * 构造ApplicationContext事件
     * @param source 事件源（事件发生所在的对象）
     * @throws IllegalArgumentException 当事件源为空时
     */
    public ApplicationContextEvent(Object source) {
        super(source);
    }

    /**
     * @return 事件所发生的的ApplicationContext
     */
    public ApplicationContext getApplicationContext(){
        return (ApplicationContext) source;
    }
}
