package com.blue_dot.beans.factory.config;

import com.blue_dot.beans.factory.support.DefaultListableBeanFactory;
import com.blue_dot.beans.factory.xml.XmlBeanDefinitionReader;
import com.blue_dot.core.io.ResourceLoader;
import org.junit.Test;

/**
 * @Author Jason
 * @CreationDate 2023/01/29 - 14:33
 * @Description ：
 */
public class BeanPostProcessorTest {
    @Test
    public void postProcessBeforeInitializationTest(){
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        //读取配置文件
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(ResourceLoader.CLASSPATH_URL_PREFIX+"test.xml");
        //注册BeanPostProcessor
        factory.addBeanPostProcessor(new MyBeanPostProcessor());

        //得到最终的Bean
        System.out.println(factory.getBean("xmlTest1"));
    }
}

