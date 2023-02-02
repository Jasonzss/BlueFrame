package com.blue_core.beans.factory.config;

import com.blue_core.beans.BeansException;
import com.blue_core.beans.factory.ConfigurableListableBeanFactory;

/**
 * @Author Jason
 * @CreationDate 2023/01/28 - 20:02
 * @Description ：BeanFactory处理器
 * 唯一方法postProcessBeanFactory会在指定阶段依次执行
 */
public interface BeanFactoryPostProcessor {
    /**
     * 在 Bean 对象注册后但未实例化之前，对 BeanDefinition 执行修改操作或其他操作。
     * @param beanFactory   被处理的BeanFactory
     * @throws BeansException 处理异常
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
}
