package com.blue_dot.beans.factory.annotation;

import com.blue_dot.context.support.ClassPathXmlApplicationContext;
import com.blue_dot.core.io.ResourceLoader;
import com.blue_dot.test.TestEntity2;
import com.blue_dot.test.test01.Test1_1;
import org.junit.Test;

import java.util.Arrays;

/**
 * @Author Jason
 * @CreationDate 2023/02/02 - 16:27
 * @Description ：
 */
public class AutowiredTest {
    @Test
    public void autowiredTest(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(ResourceLoader.CLASSPATH_URL_PREFIX+"test.xml");
        System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
        TestEntity2 xmlTest2 = (TestEntity2) context.getBean("xmlTest2");
        System.out.println(xmlTest2.toString());

        Test1_1 test1_1 = (Test1_1) context.getBean("Test1_1");
        System.out.println(test1_1.toString());
    }
}
