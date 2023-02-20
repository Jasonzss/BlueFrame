package com.blue_dot.beans.beanUtils;

import com.blue_dot.beans.factory.config.BeanDefinition;

/**
 * @Author Jason
 * @CreationDate 2023/01/07 - 19:38
 * @Description ：
 */
public interface BeanNameGenerator {
    String generateBeanName(BeanDefinition definition);
}
