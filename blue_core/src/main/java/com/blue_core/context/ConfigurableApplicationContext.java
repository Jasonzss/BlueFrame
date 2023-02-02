package com.blue_core.context;

/**
 * @Author Jason
 * @CreationDate 2023/01/28 - 19:17
 * @Description ：
 */
public interface ConfigurableApplicationContext extends ApplicationContext{
    /**
     * 刷新ApplicationContext
     */
    void refresh();

    /**
     * 注册虚拟机钩子的方法
     */
    void registryShutdownHook();

    /**
     * 关闭一次性Bean的方法
     */
    void close();
}
