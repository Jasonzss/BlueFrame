package com.blue_dot.beans.factory.config;

import com.blue_dot.beans.BeansException;
import com.blue_dot.beans.factory.BeanFactory;

/**
 * @Author Jason
 * @CreationDate 2023/01/28 - 16:01
 * @Description ：
 */
public interface AutowireCapableBeanFactory extends BeanFactory {
    /**
     * 执行 BeanPostProcessors 接口实现类的 postProcessBeforeInitialization 方法
     * @param existingBean 现有的Bean
     * @param beanName 该bean的name
     */
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    /**
     * 执行 BeanPostProcessors 接口实现类的 postProcessAfterInitialization 方法
     * @param existingBean 现有的Bean
     * @param beanName 该bean的name
     */
    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException;

    <T> T createBean(Class<T> beanClass);
}
