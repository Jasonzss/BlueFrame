package com.blue_dot.beans.factory;

import com.blue_dot.context.support.ClassPathXmlApplicationContext;
import com.blue_dot.core.io.ResourceLoader;
import org.junit.Test;

import java.util.Arrays;

/**
 * @Author Jason
 * @CreationDate 2023/01/29 - 23:11
 * @Description ：
 */
public class AwareTest {
    @Test
    public void setAwareTest(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(ResourceLoader.CLASSPATH_URL_PREFIX+"test.xml");
        context.registryShutdownHook();

        System.out.println(Arrays.toString(context.getBeanDefinitionNames()));

        MyAware myAware = context.getBean("MyAware",MyAware.class);
        System.out.println(myAware.toString());
    }
}
