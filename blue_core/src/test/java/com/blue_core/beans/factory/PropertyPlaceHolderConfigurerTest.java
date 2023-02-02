package com.blue_core.beans.factory;

import com.blue_core.context.support.ClassPathXmlApplicationContext;
import com.blue_core.core.io.ResourceLoader;
import com.blue_core.test.test01.Test1_1;
import org.junit.Test;

import java.util.Arrays;

/**
 * @Author Jason
 * @CreationDate 2023/02/02 - 13:36
 * @Description ：
 */
public class PropertyPlaceHolderConfigurerTest {
    @Test
    public void postProcessBeanFactoryTest(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(ResourceLoader.CLASSPATH_URL_PREFIX+"test.xml");
        System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
        Test1_1 test1_1 = (Test1_1) context.getBean("Test1_1");
        System.out.println(test1_1.toString());
    }
}
