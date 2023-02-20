package com.blue_dot.beans.factory.config;

import com.blue_dot.beans.PropertyValue;
import com.blue_dot.beans.PropertyValues;

/**
 * @Author Jason
 * @CreationDate 2023/01/08 - 14:29
 * @Description ：
 */
public class GenericBeanDefinition implements BeanDefinition {
    public static final String DEFAULT_SCOPE = ConfigurableBeanFactory.SCOPE_SINGLETON;

    private String beanName;

    private final Class<?> beanClass;

    private ConstructorArgumentValues constructorArgumentValues;

    private PropertyValues properties;

    private String initMethodName;

    private String destroyMethodName;

    private String scope = DEFAULT_SCOPE;

    public GenericBeanDefinition(Class<?> beanClass) {
        this(beanClass, null);
    }

    public GenericBeanDefinition(Class<?> beanClass, PropertyValues properties) {
        this.beanClass = beanClass;
        this.properties = properties == null ? new PropertyValues() : properties;
    }

    @Override
    public String getBeanName() {
        return beanName;
    }
    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public Class<?> getBeanClass() {
        return beanClass;
    }

    @Override
    public ConstructorArgumentValues getConstructorArgumentValues() {
        return constructorArgumentValues == null ? constructorArgumentValues = new ConstructorArgumentValues() : constructorArgumentValues;
    }
    @Override
    public void setConstructorArgumentValues(ConstructorArgumentValues values) {
        this.constructorArgumentValues = values;
    }

    @Override
    public PropertyValues getProperties() {
        return properties;
    }
    @Override
    public void addProperty(PropertyValue value){
        if (properties == null){
            properties = new PropertyValues();
        }

        properties.addValue(value);
    }

    @Override
    public String getInitMethodName() {
        return initMethodName;
    }
    @Override
    public void setInitMethodName(String initMethodName) {
        this.initMethodName = "".equals(initMethodName) ? null : initMethodName;
    }

    @Override
    public String getDestroyMethodName() {
        return destroyMethodName;
    }
    @Override
    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = "".equals(destroyMethodName) ? null : destroyMethodName;
    }

    @Override
    public String getScope() {
        return scope;
    }

    @Override
    public void setScope(String scope) {
        this.scope = "".equals(scope) ? DEFAULT_SCOPE : scope;
    }

    @Override
    public boolean isSingleton() {
        return DEFAULT_SCOPE.equals(scope);
    }

    @Override
    public boolean isPrototype() {
        return ConfigurableBeanFactory.SCOPE_PROTOTYPE.equals(scope);
    }
}
