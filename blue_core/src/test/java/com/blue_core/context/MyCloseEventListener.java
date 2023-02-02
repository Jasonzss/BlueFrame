package com.blue_core.context;

import com.blue_core.context.event.ContextCloseEvent;

/**
 * @Author Jason
 * @CreationDate 2023/01/30 - 21:01
 * @Description ：
 */
public class MyCloseEventListener implements ApplicationListener<ContextCloseEvent> {
    @Override
    public void onApplicationEvent(ContextCloseEvent event) {
        System.out.println("执行ApplicationContext关闭事件被我逮到！！！");
    }
}
