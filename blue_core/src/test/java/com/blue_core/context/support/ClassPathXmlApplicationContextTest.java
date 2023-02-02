package com.blue_core.context.support;

import com.blue_core.beans.factory.ConfigurableListableBeanFactory;
import com.blue_core.core.io.ResourceLoader;
import org.junit.Test;

import java.util.Arrays;

/**
 * @Author Jason
 * @CreationDate 2023/01/29 - 13:35
 * @Description ：
 */
public class ClassPathXmlApplicationContextTest {
    @Test
    public void loadBeanDefinitionsTest(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(ResourceLoader.CLASSPATH_URL_PREFIX+"test.xml");
        System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
    }

    @Test
    public void closeTest(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(ResourceLoader.CLASSPATH_URL_PREFIX+"test.xml");
        context.registryShutdownHook();

        System.out.println(context.getBean("xmlTest2"));
        System.out.println(context.getBean("xmlTest1"));
    }

    @Test
    public void test_hook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("close！")));
    }
}
