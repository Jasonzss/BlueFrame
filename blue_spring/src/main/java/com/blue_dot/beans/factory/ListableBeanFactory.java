package com.blue_dot.beans.factory;

import java.util.Map;

/**
 * @Author Jason
 * @CreationDate 2023/01/05 - 21:11
 * @Description ：表示BeanFactory中的Bean或相关信息是可以批量获取的
 */
public interface ListableBeanFactory extends BeanFactory {
    /**
     * 获取指定类型的Bean
     * @param type 指定类型的Class对象
     * @param <T>  指定类型的泛型
     * @return  key为beanName，value为bean的Map集合
     */
    <T>Map<String,T> getBeansOfType(Class<T> type);

    /**
     * 获取注册表中所有的Bean名称
     * @return Bean名称数组
     */
    String[] getBeanDefinitionNames();
}
