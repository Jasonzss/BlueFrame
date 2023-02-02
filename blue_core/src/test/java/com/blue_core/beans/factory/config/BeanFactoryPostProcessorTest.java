package com.blue_core.beans.factory.config;

import com.blue_core.beans.factory.support.DefaultListableBeanFactory;
import com.blue_core.beans.factory.xml.XmlBeanDefinitionReader;
import com.blue_core.core.io.ResourceLoader;
import org.junit.Test;

import java.util.Arrays;

/**
 * @Author Jason
 * @CreationDate 2023/01/29 - 13:45
 * @Description ：
 */
public class BeanFactoryPostProcessorTest {
    @Test
    public void postProcessBeanFactoryTest(){
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();

        //手动读取配置文件
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(ResourceLoader.CLASSPATH_URL_PREFIX+"test.xml");
        System.out.println(Arrays.toString(factory.getBeanDefinitionNames()));

        //手动执行BeanFactoryPostProcessor
        MyBeanFactoryPostProcessor myBeanFactoryPostProcess = new MyBeanFactoryPostProcessor();
        myBeanFactoryPostProcess.postProcessBeanFactory(factory);

        //将BeanDefinition初始化为Bean
        factory.preInstantiateSingletons();

        System.out.println("最终或得到的bean是 === "+factory.getBean("xmlTest1").toString());
    }
}

