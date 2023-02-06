package com.blue_core.context.annotation;

import cn.hutool.core.util.StrUtil;
import com.blue_core.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.blue_core.beans.factory.config.BeanDefinition;
import com.blue_core.beans.factory.config.GenericBeanDefinition;
import com.blue_core.beans.factory.support.BeanDefinitionRegistry;
import com.blue_core.stereotype.Component;

import java.util.Set;

/**
 * @Author Jason
 * @CreationDate 2023/02/02 - 0:08
 * @Description ：
 */
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {

    private BeanDefinitionRegistry registry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    /**
     * 扫描指定多个包的组件
     * @param basePackages
     */
    public void doScan(String... basePackages) {
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            for (BeanDefinition beanDefinition : candidates) {
                // 解析 Bean 的作用域 singleton、prototype
                String beanScope = resolveBeanScope(beanDefinition);
                if (StrUtil.isNotEmpty(beanScope)) {
                    beanDefinition.setScope(beanScope);
                }
                // 将扫描到的BeanDefinition注册到注册表中
                registry.registerBeanDefinition(determineBeanName(beanDefinition), beanDefinition);
            }
        }

        // 注册处理注解的 BeanPostProcessor（@Autowired、@Value）
        registry.registerBeanDefinition("AutowiredAnnotationBeanPostProcessor", new GenericBeanDefinition(AutowiredAnnotationBeanPostProcessor.class));

    }

    /**
     * 解析得到Bean的生命周期
     * @param beanDefinition bean的定义
     * @return bean的生命周期，未设置则返回空字符串
     */
    private String resolveBeanScope(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Scope scope = beanClass.getAnnotation(Scope.class);
        if (null != scope) {
            return scope.value();
        }
        return StrUtil.EMPTY;
    }

    /**
     * 生成Bean的名称，注解中有就是用注解中的，没有的话就小写类名首字母作为BeanName
     * @param beanDefinition bean的定义
     * @return Bean的名称
     */
    private String determineBeanName(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Component component = beanClass.getAnnotation(Component.class);
        String value = component.value();
        if (StrUtil.isEmpty(value)) {
            value = StrUtil.lowerFirst(beanClass.getSimpleName());
        }
        return value;
    }

}
