package com.blue_core.beans.factory;

import com.blue_core.beans.BeansException;
import com.blue_core.beans.factory.aware.BeanClassLoaderAware;
import com.blue_core.beans.factory.aware.BeanFactoryAware;
import com.blue_core.beans.factory.aware.BeanNameAware;
import com.blue_core.context.ApplicationContext;
import com.blue_core.context.ApplicationContextAware;

/**
 * @Author Jason
 * @CreationDate 2023/01/29 - 23:12
 * @Description ：
 */
public class MyAware implements BeanClassLoaderAware, BeanNameAware, BeanFactoryAware, ApplicationContextAware {
    private ClassLoader classLoader;
    private ApplicationContext applicationContext;
    private String beanName;
    private BeanFactory beanFactory;

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
        System.out.println("我知道我的BeanName啦！！！是："+beanName);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        System.out.println("让我看看有多少个Bean："+applicationContext.getBeanDefinitionNames().length);
    }

    @Override
    public String toString() {
        return "MyAware{" +
                "classLoader=" + classLoader +
                ", applicationContext=" + applicationContext +
                ", beanName='" + beanName + '\'' +
                ", beanFactory=" + beanFactory +
                '}';
    }
}
