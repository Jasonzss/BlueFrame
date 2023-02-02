package com.blue_core.context;

import com.blue_core.context.event.ContextRefreshedEvent;

/**
 * @Author Jason
 * @CreationDate 2023/01/30 - 20:54
 * @Description ：
 */
public class MyRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent>{
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("执行ApplicationContext刷新事件被我逮到！！！");
    }
}
