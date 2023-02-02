package com.blue_core.stereotype;

import com.blue_core.context.support.ClassPathXmlApplicationContext;
import com.blue_core.core.io.ResourceLoader;
import com.blue_core.test.TestEntity1;
import org.junit.Test;

import java.util.Arrays;

/**
 * @Author Jason
 * @CreationDate 2023/02/02 - 0:40
 * @Description ：
 */
public class ComponentTest {
    @Test
    public void ComponentScanTest(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(ResourceLoader.CLASSPATH_URL_PREFIX+"test.xml");
        System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
        TestEntity1 test = (TestEntity1) context.getBean("test");
        test.setA("aaaa");
        System.out.println(test.toString());
    }
}
