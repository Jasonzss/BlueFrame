package com.blue_core.beans.factory.config;

import cn.hutool.core.bean.BeanException;

/**
 * @Author Jason
 * @CreationDate 2023/01/28 - 20:08
 * @Description ：可自定义目标Bean在实例化前后进行指定操作
 */
public interface BeanPostProcessor {
    /**
     * 目标Bean在初始化之前进行的操作
     * @param bean 新的 bean 实例
     * @param beanName bean的name
     * @return 要使用的 bean 实例，原始实例或包装实例；如果为null ，则不会调用后续的 BeanPostProcessors
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeanException;

    /**
     * 目标Bean在初始化之后进行的操作
     * @param bean 新的 bean 实例
     * @param beanName bean的name
     * @return 要使用的 bean 实例，原始实例或包装实例；如果为null ，则不会调用后续的 BeanPostProcessors
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException;
}
