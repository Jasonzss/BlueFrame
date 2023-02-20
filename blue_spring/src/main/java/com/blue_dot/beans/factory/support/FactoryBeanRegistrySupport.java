package com.blue_dot.beans.factory.support;

import com.blue_dot.beans.BeansException;
import com.blue_dot.beans.factory.FactoryBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author Jason
 * @CreationDate 2023/01/30 - 14:07
 * @Description ：扩展了 DefaultSingletonBeanRegistry 的功能，
 * 在原有的单例bean注册表的功能上添加了对FactoryBean注册的处理。
 * 之所以把FactoryBean的处理放到这样一个单独的类里，就是希望做到不同领域模块下只负责各自需要完成的功能，
 * 避免因为各种扩展导致 DefaultSingletonBeanRegistry 类膨胀到难以维护。
 */
public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {
    //FactoryBeans创建的单例对象的缓存：FactoryBean名称-->对象
    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

    /**
     * 根据BeanName获取对应的Factory生成的FactoryBean
     */
    protected Object getCacheObjectForFactoryBean(String beanName){
        Object o = this.factoryBeanObjectCache.get(beanName);
        //若FactoryBean只是个 null_object 对象，则返回null
        return (o != NULL_OBJECT ? o : null);
    }

    /**
     * 根据Factory和BeanName获取指定的Bean
     * @param factory 生成目标bean的Factory
     * @param beanName bean的name
     * @return FactoryBean
     */
    protected Object getObjectFromFactoryBean(FactoryBean<?> factory, String beanName) {
        //判断是否为单例FactoryBean
        if (factory.isSingleton()){
            //尝试获取单例缓存中的FactoryBean
            Object o = this.getCacheObjectForFactoryBean(beanName);

            if (o == null){
                //此FactoryBean在缓存中还不存在，首次将其创建出来。
                o = doGetObjectFromFactoryBean(factory,beanName);
                //将创建好的FactoryBean放入缓存后返回.如果FactoryBean为空，则放个普通 null_object 进去
                factoryBeanObjectCache.put(beanName, (o != null ? o : NULL_OBJECT));
            }

            return (o != NULL_OBJECT ? o : null);
        }else {
            //不为单例Bean，直接创建并返回目标bean就好
            return doGetObjectFromFactoryBean(factory,beanName);
        }
    }

    /**
     * 调用工厂的getObject方法获取目标bean
     * @param factory 生成FactoryBean的工厂
     * @param beanName Bean的Name
     */
    private Object doGetObjectFromFactoryBean(final FactoryBean<?> factory, final String beanName){
        try {
            return factory.getObject();
        } catch (Exception e) {
            throw new BeansException("FactoryBean [" + beanName + "] 在创建Bean的时候抛出异常", e);
        }
    }
}
