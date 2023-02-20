package com.blue_dot.beans.factory.config;

import com.blue_dot.context.support.ClassPathXmlApplicationContext;
import com.blue_dot.core.io.ResourceLoader;
import org.junit.Test;

import java.util.Arrays;

/**
 * @Author Jason
 * @CreationDate 2023/01/30 - 15:33
 * @Description ：
 */
public class BeanDefinitionTest {
    @Test
    public void isPrototypeTest(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(ResourceLoader.CLASSPATH_URL_PREFIX+"test.xml");
        context.registryShutdownHook();

        System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
        System.out.println(context.getBean("xmlTest2"));
        System.out.println(context.getBean("xmlTest2"));
        System.out.println(context.getBean("xmlTest2"));
    }
}
