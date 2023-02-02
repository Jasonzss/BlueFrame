package com.blue_core.beans.factory.support;

import com.blue_core.beans.BeansException;
import com.blue_core.beans.factory.DisposableBean;
import com.blue_core.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author Jason
 * @CreationDate 2023/01/10 - 16:53
 * @Description ：
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    //空单例对象的内部标记：用作并发映射（不支持空值）的标记值。
    //TODO 不明白为什么要拿这个当做空单例的标记，null不是一样的吗？
    protected static final Object NULL_OBJECT = new Object();

    //单例池
    private final ConcurrentHashMap<String,Object> singletonObjects = new ConcurrentHashMap<>();

    private final Map<String, DisposableBean> disposableBeans = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    /**
     * 添加单例Bean到单例池中
     * @param beanName bean的唯一名称
     * @param singletonObject bean对象
     */
    @Override
    public void registerSingleton(String beanName,Object singletonObject){
        singletonObjects.put(beanName, singletonObject);
    }

    /**
     * 注册一次性Bean
     * @param beanName bean的name
     * @param disposableBean 一次性bean对象
     */
    public void registerDisposableBean(String beanName, DisposableBean disposableBean){
        disposableBeans.put(beanName, disposableBean);
    }

    /**
     * 通过调用Destroy方法，销毁所有一次性Bean
     */
    public void destroySingletons(){
        String[] disposableBeanNames = disposableBeans.keySet().toArray(new String[0]);

        for (int i = disposableBeanNames.length-1; i >= 0; i--) {
            String disposableBeanName = disposableBeanNames[i];
            DisposableBean disposableBean = disposableBeans.remove(disposableBeanName);
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeansException("["+disposableBeanName+"] 的 destroy 方法抛出了异常",e);
            }
        }
    }
}
