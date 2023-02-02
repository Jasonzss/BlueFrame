package com.blue_core.context;

/**
 * @Author Jason
 * @CreationDate 2023/01/30 - 18:20
 * @Description ：
 */
public interface ApplicationListener<E extends ApplicationEvent>  {
    void onApplicationEvent(E event);
}
