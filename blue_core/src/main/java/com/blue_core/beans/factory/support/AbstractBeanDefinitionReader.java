package com.blue_core.beans.factory.support;

import com.blue_core.beans.beanUtils.BeanNameGenerator;
import com.blue_core.core.io.DefaultResourceLoader;
import com.blue_core.core.io.ResourceLoader;

/**
 * @Author Jason
 * @CreationDate 2023/01/08 - 18:44
 * @Description ：
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {
    private final BeanDefinitionRegistry registry;

    private final ResourceLoader resourceLoader;

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this(registry,new DefaultResourceLoader());
    }

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        this.registry = registry;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    @Override
    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }
}
