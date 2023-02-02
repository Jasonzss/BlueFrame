package com.blue_core.context;

import com.blue_core.beans.factory.HierarchicalBeanFactory;
import com.blue_core.beans.factory.ListableBeanFactory;
import com.blue_core.core.io.ResourceLoader;

/**
 * @Author Jason
 * @CreationDate 2022/12/19 - 22:21
 * @Description ：为应用程序提供配置的中央接口。
 * 在应用程序运行时这是只读的，但如果部分实现类可以做到重新加载，例如会refresh的ConfigurableApplicationContext。
 *
 * ApplicationContext 接口的定义是继承 BeanFactory 外新增加功能的接口，
 * 它可以满足于自动识别、资源加载、容器事件、监听器等功能，
 * 同时例如一些国际化支持、单例Bean自动初始化等，也是可以在这个类里实现和扩充的。
 */
public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader, ApplicationEventPublisher {

}
