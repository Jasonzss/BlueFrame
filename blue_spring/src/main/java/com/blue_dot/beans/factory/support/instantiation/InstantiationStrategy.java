package com.blue_dot.beans.factory.support.instantiation;

import com.blue_dot.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * @Author Jason
 * @CreationDate 2023/01/10 - 16:48
 * @Description ：BeanDefinition实例化为Bean时所需要的策略类
 * 使用策略模式，此接口为实例化策略的顶级抽象
 */
public interface InstantiationStrategy {
    /**
     * 实例化bean，并返回
     * @param beanDefinition 包含有将要初始化bean的全部信息
     * @param beanName bean的名称
     * @param ctor 实例化bean的构造器
     * @param args 实例化bean构造器的参数
     * @return bean对象
     */
    Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor<?> ctor, Object[] args);
}
