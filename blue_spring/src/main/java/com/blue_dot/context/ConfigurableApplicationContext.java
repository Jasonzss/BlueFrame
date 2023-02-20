package com.blue_dot.context;

import com.blue_dot.beans.factory.ConfigurableListableBeanFactory;

/**
 * @Author Jason
 * @CreationDate 2023/01/28 - 19:17
 * @Description ：可配置的ApplicationContext，执行refresh()方法以进行配置读取，并刷新应用程序的上下文
 */
public interface ConfigurableApplicationContext extends ApplicationContext{
    /**
     * 刷新ApplicationContext
     */
    void refresh();

    /**
     * 注册虚拟机钩子的方法
     * 向 JVM 运行时注册一个关闭钩子，在 JVM 关闭时关闭此上下文，除非它当时已经关闭。
     */
    void registryShutdownHook();

    /**
     * 关闭DisposableBean的方法，目的是释放当前ApplicationContext所持有的资源以及锁
     */
    void close();

    /**
     * 向容器中注册监听器，这样当ApplicationContext发布对应的事件时，监听器才能收到事件信息
     * @param applicationListener 事件监听器
     */
    void addApplicationListener(ApplicationListener<?> applicationListener);
    /**
     * 判断当前ApplicationContext是否激活状态
     * 激活状态：ApplicationContext执行过至少一次refresh()并且之后没有关闭
     * @return 当前ApplicationContext是否激活状态
     */
    boolean isActive();


    ConfigurableListableBeanFactory getConfigurableListableBeanFactory();
}
