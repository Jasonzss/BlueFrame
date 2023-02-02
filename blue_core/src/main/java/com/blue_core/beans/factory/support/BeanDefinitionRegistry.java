package com.blue_core.beans.factory.support;

import com.blue_core.beans.factory.config.BeanDefinition;

/**
 * @Author Jason
 * @CreationDate 2023/01/07 - 19:35
 * @Description ：BeanDefinition的注册表的顶级抽象
 */
public interface BeanDefinitionRegistry {
    /**
     * 查看注册表中当前有多少个BeanDefinition
     * @return BeanDefinition的数量
     */
    int getBeanDefinitionCount();

    /**
     * 向注册表中注册BeanDefinition
     * @param beanName bean的name
     * @param beanDefinition Bean的定义
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    /**
     * 注册表中是否存在此BeanDefinition
     * @param beanName BeanDefinition对应的Name
     * @return 是否存在
     */
    boolean containsBeanDefinitionName(String beanName);

    /**
     * 获取所有BeanDefinition的名称
     * @return 所有BeanDefinition的名称
     */
    String[] getBeanDefinitionNames();
}
