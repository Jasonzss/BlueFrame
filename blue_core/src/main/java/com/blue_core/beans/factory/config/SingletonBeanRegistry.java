package com.blue_core.beans.factory.config;

/**
 * @Author Jason
 * @CreationDate 2023/01/10 - 15:59
 * @Description ：
 */
public interface SingletonBeanRegistry {
    /**
     * 获取单例bean
     * @param beanName 单例bean的唯一名称
     * @return 单例bean
     */
    Object getSingleton(String beanName);

    /**
     * 向单例注册表中添加新单例
     * @param beanName bean的name
     * @param singletonObject 单例bean
     */
    void registerSingleton(String beanName, Object singletonObject);
}
