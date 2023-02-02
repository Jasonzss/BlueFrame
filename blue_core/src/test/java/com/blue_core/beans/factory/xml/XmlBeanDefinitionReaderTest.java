package com.blue_core.beans.factory.xml;

import com.blue_core.beans.factory.support.DefaultListableBeanFactory;
import com.blue_core.core.io.ResourceLoader;
import org.junit.Test;

import java.util.Arrays;

/**
 * @Author Jason
 * @CreationDate 2023/01/28 - 14:45
 * @Description ：
 */
public class XmlBeanDefinitionReaderTest {
    @Test
    public void testLoadBeanDefinitions(){
        DefaultListableBeanFactory registry = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(registry);
        xmlBeanDefinitionReader.loadBeanDefinitions(ResourceLoader.CLASSPATH_URL_PREFIX+"test.xml");
        System.out.println(registry.getBeanDefinitionCount());
        System.out.println(Arrays.toString(registry.getBeanDefinitionNames()));
    }
}
