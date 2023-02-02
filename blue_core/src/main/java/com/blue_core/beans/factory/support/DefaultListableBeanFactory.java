package com.blue_core.beans.factory.support;

import com.blue_core.beans.BeansException;
import com.blue_core.beans.factory.config.BeanDefinition;
import com.blue_core.beans.factory.ConfigurableListableBeanFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author Jason
 * @CreationDate 2023/01/10 - 17:53
 * @Description ：
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {
    private final Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

    /**
     * 获取当前BeanDefinition容器的大小
     */
    @Override
    public int getBeanDefinitionCount() {
        return beanDefinitionMap.size();
    }

    /**
     * 实现BeanDefinitionRegistry的方法
     */
    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    public boolean containsBeanDefinitionName(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }

    /**
     * 实现AbstractBeanFactory的方法
     */
    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null){
            throw new BeansException("no beans named "+beanName+" is defined");
        }
        return beanDefinition;
    }

    @Override
    public void preInstantiateSingletons() throws BeansException {
        beanDefinitionMap.keySet().forEach(this::getBean);
    }

    /**
     * 实现ConfigurableListableBeanFactory的方法
     * TODO 这里可能有点问题，因为getBeansOfType方法扫描的应该是Bean的注册表，而非BeanDefinition注册表
     */
    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) {
        Map<String, T> beans = new HashMap<>();

        beanDefinitionMap.forEach((beanName,beanDefinition) -> {
            if (type.isAssignableFrom(beanDefinition.getBeanClass())){
                beans.put(beanName,  (T) getBean(beanName));
            }
        });

        return beans;
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return beanDefinitionMap.keySet().toArray(new String[0]);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        List<String> beanNames = new ArrayList<>();
        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            Class beanClass = entry.getValue().getBeanClass();
            if (requiredType.isAssignableFrom(beanClass)) {
                beanNames.add(entry.getKey());
            }
        }
        if (1 == beanNames.size()) {
            return getBean(beanNames.get(0), requiredType);
        }

        throw new BeansException(requiredType + "expected single bean but found " + beanNames.size() + ": " + beanNames);
    }
}
