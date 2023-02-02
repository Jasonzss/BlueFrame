package com.blue_core.context;

/**
 * @Author Jason
 * @CreationDate 2023/01/30 - 19:30
 * @Description ：封装事件发布功能的接口。作为 ApplicationContext 的父接口
 * ApplicationContext实现此接口拥有发布事件的能力
 */
public interface ApplicationEventPublisher {
    /**
     * 将事件的发生发布给所有注册到此Application的监听器
     * @param event 要发布的事件
     */
    void publishEvent(ApplicationEvent event);
}
