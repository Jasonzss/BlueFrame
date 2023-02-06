package com.blue_core.context.annotation;

import cn.hutool.core.util.ClassUtil;
import com.blue_core.beans.factory.config.BeanDefinition;
import com.blue_core.beans.factory.config.GenericBeanDefinition;
import com.blue_core.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @Author Jason
 * @CreationDate 2023/02/01 - 23:57
 * @Description ：
 */
public class ClassPathScanningCandidateComponentProvider {
    /**
     * 扫描目标包路径下的被Component注解注释了的Class类，将其作为Spring的Bean加入容器
     * @param basePackage 被扫描的包
     * @return 被Component注解修饰的类构成的BeanDefinition集合
     */
    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        for (Class<?> clazz : classes) {
            //TODO 没有重写hashCode和equals方法，可能会出现同一个类导入多次的情况
            candidates.add(new GenericBeanDefinition(clazz));
        }
        return candidates;
    }

}
