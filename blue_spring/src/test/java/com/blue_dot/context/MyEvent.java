package com.blue_dot.context;

import com.blue_dot.context.event.ApplicationContextEvent;

/**
 * @Author Jason
 * @CreationDate 2023/01/30 - 21:01
 * @Description ：
 */
public class MyEvent extends ApplicationContextEvent {
    private String msg;

    /**
     * 构造原型事件。
     *
     * @param source 事件源（事件发生所在的对象）
     * @throws IllegalArgumentException 当事件源为空时
     */
    public MyEvent(Object source, String msg) {
        super(source);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
