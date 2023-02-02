package com.blue_core.context.event;

import com.blue_core.context.ApplicationEvent;
import com.blue_core.context.ApplicationListener;

/**
 * @Author Jason
 * @CreationDate 2023/01/30 - 18:18
 * @Description ：事件广播器，可以管理多个ApplicationListener对象
 */
public interface ApplicationEventMulticaster {
    /**
     * 添加监听器以监听所有的广播事件
     * @param listener 添加的监听器
     */
    void addApplicationListener(ApplicationListener<?> listener);

    /**
     * 移除监听器，不再发送广播事件给改监听器
     * @param listener 移除的监听器
     */
    void removeApplicationListener(ApplicationListener<?> listener);

    /**
     * 根据发生的广播事件通知给对应的监听器
     * @param event 将要广播的事件
     */
    void multicastEvent(ApplicationEvent event);
}
