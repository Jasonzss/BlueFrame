package com.blue_dot.beans.factory.config;

/**
 * @Author Jason
 * @CreationDate 2023/01/11 - 16:02
 * @Description ：Bean对象的引用，记录bean的唯一名称
 */
public class BeanReference {
    private final String beanName;

    public BeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }
}
