package com.blue_core.context.support;

import com.blue_core.beans.BeansException;
import com.blue_core.beans.factory.ConfigurableListableBeanFactory;
import com.blue_core.beans.factory.support.DefaultListableBeanFactory;

/**
 * @Author Jason
 * @CreationDate 2023/01/28 - 21:55
 * @Description ：
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {
    private DefaultListableBeanFactory beanFactory;

    @Override
    protected ConfigurableListableBeanFactory getBeanFactory() {
        return beanFactory == null ? (beanFactory = createBeanFactory()) : beanFactory;
    }

    private DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }

    @Override
    protected void refreshBeanFactory() throws BeansException {
        DefaultListableBeanFactory beanFactory = createBeanFactory();
        loadBeanDefinitions(beanFactory);
        //新的BeanFactory覆盖旧的
        this.beanFactory = beanFactory;
    }

    /**
     * 将BeanDefinition导入到目标的DefaultListableBeanFactory中
     * @param beanFactory DefaultListableBeanFactory
     */
    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);
}
