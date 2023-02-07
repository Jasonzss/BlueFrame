package com.blue_core.context;

import com.blue_core.beans.factory.HierarchicalBeanFactory;
import com.blue_core.beans.factory.ListableBeanFactory;
import com.blue_core.beans.factory.config.AutowireCapableBeanFactory;
import com.blue_core.core.io.ResourceLoader;

/**
 * @Author Jason
 * @CreationDate 2022/12/19 - 22:21
 * @Description ：为应用程序提供配置的中央接口。
 * 在应用程序运行时ApplicationContext是只读的，除了部分实现类可以做到重新加载ApplicationContext，例如会refresh的ConfigurableApplicationContext。
 *
 * ApplicationContext 接口的定义是继承 BeanFactory 外新增加功能的接口，
 * 它可以满足于自动识别、资源加载、容器事件、监听器等功能，
 * 同时例如一些国际化支持、单例Bean自动初始化等，也是可以在这个类里实现和扩充的。
 */
public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader, ApplicationEventPublisher {
    /**
     * 获取具有自动注入功能的BeanFactory，说明ApplicationContext的实现类中都应该有AutowireCapableBeanFactory
     * @return AutowireCapableBeanFactory
     */
    AutowireCapableBeanFactory getAutowireCapableBeanFactory();
}
