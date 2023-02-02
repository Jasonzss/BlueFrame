package com.blue_core.beans.factory.config;

import com.blue_core.beans.BeansException;
import com.blue_core.beans.factory.ConfigurableListableBeanFactory;
import com.blue_core.test.TestEntity1;

/**
 * @Author Jason
 * @CreationDate 2023/01/29 - 14:56
 * @Description ：
 */
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition xmlTest1 = beanFactory.getBeanDefinition("xmlTest1");
        System.out.println("修改前====" + xmlTest1.getProperties().toString());
        xmlTest1.getProperties().getValue("a").setValue("被postProcessBeanFactory修改啦");
        System.out.println("修改后====" + xmlTest1.getProperties().toString());
    }
}
