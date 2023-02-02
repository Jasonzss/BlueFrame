package com.blue_core.beans.factory.support;

import com.blue_core.beans.BeansException;
import com.blue_core.core.io.ResourceLoader;
import com.blue_core.core.io.resource.Resource;

/**
 * @Author Jason
 * @CreationDate 2023/01/08 - 18:16
 * @Description ：
 */
public interface BeanDefinitionReader {
    /**
     * 获取BeanDefinition注册表，将读取的BeanDefinition注册进去
     */
    BeanDefinitionRegistry getRegistry();

    /**
     * 获取资源加载器，加载配置资源
     */
    ResourceLoader getResourceLoader();

    /**
     * 读取资源并解析成BeanDefinition
     */
    void loadBeanDefinitions(Resource resource) throws BeansException;

    void loadBeanDefinitions(Resource... resources) throws BeansException;

    void loadBeanDefinitions(String location) throws BeansException;
}
