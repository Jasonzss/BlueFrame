package com.blue_dot.beans.factory.config;

import cn.hutool.core.bean.BeanException;
import com.blue_dot.test.TestEntity1;

/**
 * @Author Jason
 * @CreationDate 2023/01/29 - 14:56
 * @Description ：
 */
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeanException {
        if ("xmlTest1".equals(beanName)) {
            if (bean instanceof TestEntity1) {
                ((TestEntity1) bean).setA("这里经过了postProcessBeforeInitialization修改");
            }
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException {
        if ("xmlTest1".equals(beanName)) {
            if (bean instanceof TestEntity1) {
                ((TestEntity1) bean).setB(9999);
            }
        }

        return bean;
    }
}
