package com.blue_core.context.support;

import cn.hutool.core.bean.BeanException;
import com.blue_core.beans.factory.config.BeanPostProcessor;
import com.blue_core.context.ApplicationContext;
import com.blue_core.context.ApplicationContextAware;

/**
 * @Author Jason
 * @CreationDate 2023/01/29 - 22:33
 * @Description ：由于 ApplicationContext 并不是在 AbstractAutowireCapableBeanFactory 中 createBean 方法下的内容。
 * 所以需要向容器中注册 BeanPostProcessor，再由 createBean 统一调用 applyBeanPostProcessorsBeforeInitialization 时
 * 进行ApplicationContext注入操作。
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {
    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * 判断当前bean是否需要ApplicationContext，需要则直接注入
     * @param bean 新的 bean 实例
     * @param beanName bean的name
     * @return 可能为注入了ApplicationContext的Bean
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeanException {
        if (bean instanceof ApplicationContextAware){
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException {
        return bean;
    }
}
