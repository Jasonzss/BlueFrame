package com.blue_dot.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import com.blue_dot.beans.BeansException;
import com.blue_dot.beans.factory.DisposableBean;
import com.blue_dot.beans.factory.config.BeanDefinition;

/**
 * @Author Jason
 * @CreationDate 2023/01/29 - 18:14
 * @Description ：
 */
public class DisposableBeanAdapter implements DisposableBean {
    private final static String DEFAULT_DESTROY_METHOD_NAME = "destroy";

    private final String beanName;
    private final Object bean;
    private String destroyMethodName;

    public DisposableBeanAdapter(String beanName, Object bean, BeanDefinition beanDefinition) {
        this.beanName = beanName;
        this.bean = bean;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }

    @Override
    public void destroy() throws Exception {
        // 1. 如果是实现了接口DisposableBean
        if (bean instanceof DisposableBean){
            ((DisposableBean) bean).destroy();
        }

        // 2. 如果是注解配置的，右边的判断逻辑是防止同一个方法执行两遍
        if (StrUtil.isNotEmpty(destroyMethodName)  && !(bean instanceof DisposableBean && DEFAULT_DESTROY_METHOD_NAME.equals(this.destroyMethodName))){
            try{
                bean.getClass().getMethod(destroyMethodName).invoke(bean);
            }catch (NoSuchMethodException e){
                throw new BeansException("在["+beanName+"]中找不到名为["+destroyMethodName+"]的destroy方法",e);
            }
        }
    }
}
