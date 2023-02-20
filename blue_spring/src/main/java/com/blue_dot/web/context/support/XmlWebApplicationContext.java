package com.blue_dot.web.context.support;

import com.blue_dot.beans.factory.support.DefaultListableBeanFactory;
import com.blue_dot.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @Author Jason
 * @CreationDate 2023/02/04 - 22:24
 * @Description ：
 */
public class XmlWebApplicationContext extends AbstractRefreshableWebApplicationContext {

    //默认的配置文件定位
    public static final String DEFAULT_CONFIG_LOCATION = "/WEB-INF/applicationContext.xml";

    public XmlWebApplicationContext() {
        setConfigLocation(DEFAULT_CONFIG_LOCATION);
    }

    public XmlWebApplicationContext(String configLocation) {
        setConfigLocation(configLocation);
        //创建XmlApplicationContext完成后刷新一遍
        refresh();
    }


    /**
     * 读取xml文件进行BeanDefinition解析、导入
     * @param beanFactory DefaultListableBeanFactory
     */
    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory){
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        String[] configLocations = getConfigLocations();
        if (configLocations != null) {
            for (String configLocation : configLocations) {
                beanDefinitionReader.loadBeanDefinitions(configLocation);
            }
        }
    }
}
