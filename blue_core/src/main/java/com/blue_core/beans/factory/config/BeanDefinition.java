package com.blue_core.beans.factory.config;

import com.blue_core.beans.PropertyValue;
import com.blue_core.beans.PropertyValues;

/**
 * @Author Jason
 * @CreationDate 2023/01/05 - 21:12
 * @Description ：
 */
public interface BeanDefinition {

    String getBeanName();

    void setBeanName(String beanName);

    /**
     * 获取bean的class对象
     * @return bean的class对象
     */
    Class<?> getBeanClass();

    ConstructorArgumentValues getConstructorArgumentValues();

    void setConstructorArgumentValues(ConstructorArgumentValues values);

    PropertyValues getProperties();

    void addProperty(PropertyValue value);

    String getInitMethodName();

    void setInitMethodName(String initMethodName);

    String getDestroyMethodName();

    void setDestroyMethodName(String destroyMethodName);

    String getScope();

    void setScope(String scope);

    boolean isSingleton();

    boolean isPrototype();
}
