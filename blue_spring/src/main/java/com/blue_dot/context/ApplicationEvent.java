package com.blue_dot.context;

import java.util.EventObject;

/**
 * @Author Jason
 * @CreationDate 2023/01/30 - 17:34
 * @Description ：
 */
public abstract class ApplicationEvent extends EventObject {
    /**
     * 构造原型事件。
     * @param source 事件源（事件发生所在的对象）
     * @throws IllegalArgumentException 当事件源为空时
     */
    public ApplicationEvent(Object source) {
        super(source);
    }
}
