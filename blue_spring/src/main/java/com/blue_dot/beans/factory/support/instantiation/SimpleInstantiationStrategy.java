package com.blue_dot.beans.factory.support.instantiation;

import com.blue_dot.beans.BeansException;
import com.blue_dot.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @Author Jason
 * @CreationDate 2023/01/10 - 17:30
 * @Description ：初始化bean策略的具体实现策略，使用jdk反射对bean进行初始化
 */
public class SimpleInstantiationStrategy implements InstantiationStrategy{
    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor<?> ctor, Object[] args) {
        Class<?> clazz = beanDefinition.getBeanClass();

        try{
            if (null != ctor){
                //构造器可用
                return clazz.getDeclaredConstructor(ctor.getParameterTypes()).newInstance(args);
            }else{
                //输入的构造器不可用，使用bean的无参构造器器初始化
                return clazz.getDeclaredConstructor().newInstance();
            }
        } catch (IllegalAccessException e) {
            throw new BeansException("构造方法不可用", e);
        } catch (NoSuchMethodException e) {
            throw new BeansException("构造方法不存在", e);
        } catch (InvocationTargetException | InstantiationException e) {
            throw new BeansException("Instantiation of bean failed", e);
        }
    }
}
