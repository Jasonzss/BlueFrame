package com.blue_dot.stereotype;

import com.blue_dot.context.support.ClassPathXmlApplicationContext;
import com.blue_dot.core.io.ResourceLoader;
import com.blue_dot.test.TestEntity1;
import com.blue_dot.test.TestEntity2;
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
        TestEntity2 test2 = (TestEntity2) context.getBean("test2");
        test.setA("aaaa");
        System.out.println(test.toString());
        System.out.println(test2.toString());
        System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
    }
}
