package com.blue_core.beans.beanUtils;

import com.blue_core.beans.annotation.Component;
import com.blue_core.beans.factory.config.BeanDefinition;

/**
 * @Author Jason
 * @CreationDate 2023/01/08 - 14:24
 * @Description ：
 */
public class DefaultBeanNameGenerator implements BeanNameGenerator{
    @Override
    public String generateBeanName(BeanDefinition definition) {
        String name;
        Class<?> beanClass = definition.getBeanClass();
        if (beanClass.isAnnotationPresent(Component.class) && !beanClass.getAnnotation(Component.class).value().equals("")){
            name = beanClass.getAnnotation(Component.class).value();
        }else {
            name = definition.getBeanClass().getSimpleName();
        }
        return name;
    }
}
