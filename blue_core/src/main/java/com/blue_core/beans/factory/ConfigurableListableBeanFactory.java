package com.blue_core.beans.factory;

import com.blue_core.beans.BeansException;
import com.blue_core.beans.factory.config.BeanDefinition;
import com.blue_core.beans.factory.config.AutowireCapableBeanFactory;
import com.blue_core.beans.factory.config.ConfigurableBeanFactory;

/**
 * @Author Jason
 * @CreationDate 2023/01/28 - 15:42
 * @Description ：主要继承自 ConfigurableBeanFactory接口 和 ListableBeanFactory接口。
 * 它还提供分析和修改 beanDefinition 以及 预实例化单例 的工具。
 */
public interface ConfigurableListableBeanFactory extends ConfigurableBeanFactory, ListableBeanFactory, AutowireCapableBeanFactory {

    /**
     * 根据名称获取BeanDefinition
     * @param beanName bean的名称
     * @return beanName对应的BeanDefinition
     * @throws BeansException 未找到目标BeanDefinition的异常
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * Bean的预实例化
     * TODO 什么是预实例化
     */
    void preInstantiateSingletons() throws BeansException;
}
