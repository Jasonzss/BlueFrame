package com.blue_core.beans.factory.config;

import com.blue_core.beans.BeansException;
import com.blue_core.beans.PropertyValues;

/**
 * @Author Jason
 * @CreationDate 2023/01/31 - 23:57
 * @Description ：BeanPostProcessor的子接口，它添加了一个bean实例化前回调，
 * 以及一个在实例化之后，但在显式属性设置或自动装配发生之前，的回调。
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {
    /**
     * 在目标 bean 被实例化之前，应用这个 BeanPostProcessor。
     * 返回的 bean 对象可能是代替目标 bean 使用的代理，有效地阻止了目标代理 bean 的默认实例化。
     * @param beanClass 目标bean的Class
     * @param beanName 目标bean的name
     * @return 可能是代理bean
     */
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;


    /**
     * Post-process the given property values before the factory applies them
     * to the given bean. Allows for checking whether all dependencies have been
     * satisfied, for example based on a "Required" annotation on bean property setters.
     *
     * 在 Bean 对象实例化完成后，设置属性操作之前执行此方法
     *
     * @param pvs
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException;

}
