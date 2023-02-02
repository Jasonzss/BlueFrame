package com.blue_core.beans.factory;

import com.blue_core.beans.BeansException;

/**
 * @Author Jason
 * @CreationDate 2023/01/05 - 21:10
 * @Description ：IoC容器的顶级抽象
 * TODO 目前项目中存在很大的空指针异常的风险，后面有空再使用Optional来修复
 * TODO 异常处理还未细化，基本拿BeansException一笔带过，回头要处理一下
 */
public interface BeanFactory {
    /**
     * 从bean工厂中获取指定名称的bean
     * @param beanName bean的唯一名称
     * @return bean
     */
    Object getBean(String beanName) throws BeansException;

    /**
     * 从bean工厂中获取指定名称的bean，如果没有初始化，则使用参数进行初始化
     * @param beanName bean的唯一名称
     * @param args bean的构造器的参数
     * @return bean对象
     */
    Object getBean(String beanName, Object... args) throws BeansException;

    /**
     * 根据对应name的Class类型获取目标Bean
     * @param beanName bean的name
     * @param requiredType 目标bean类的class对象
     * @param <T> 对应类型的泛型
     * @return  目标Bean
     */
    <T> T getBean(String beanName, Class<T> requiredType) throws BeansException;


    <T> T getBean(Class<T> requiredType) throws BeansException;
}
