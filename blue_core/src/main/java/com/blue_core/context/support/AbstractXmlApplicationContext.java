package com.blue_core.context.support;

import com.blue_core.beans.factory.support.DefaultListableBeanFactory;
import com.blue_core.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @Author Jason
 * @CreationDate 2023/01/28 - 22:05
 * @Description ：继承自AbstractRefreshableConfigApplicationContext类
 * 实现了父类的模板抽象方法loadBeanDefinitions，用于实现如何导入BeanDefinition
 * 此类便是导入xml中的BeanDefinitions，具体解析xml的逻辑在XMLBeanDefinitionReader中实现
 *
 * 读取xml -> 解析xml -> 加载BeanDefinitions进BeanFactory
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableConfigApplicationContext{

    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
        XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(beanFactory);
        //获取配置文件定位 并 读取并解析配置文件
        for (String location : getXmlConfigLocations()){
            xmlReader.loadBeanDefinitions(location);
        }
    }

    /**
     * 获取Xml配置文件的定位
     * @return Xml文件定位的数组
     */
    protected abstract String[] getXmlConfigLocations();
}
