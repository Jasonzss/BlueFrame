package com.blue_dot.beans.factory.support;

import com.blue_dot.context.support.ClassPathXmlApplicationContext;
import com.blue_dot.core.io.ResourceLoader;
import com.blue_dot.test.test01.Test1_1;
import org.junit.Test;

/**
 * @Author Jason
 * @CreationDate 2023/01/30 - 15:17
 * @Description ：
 */
public class FactoryBeanRegistrySupportTest {
    @Test
    public void getObjectTest() throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(ResourceLoader.CLASSPATH_URL_PREFIX+"test.xml");
        context.registryShutdownHook();

        Test1_1 test1_1 = context.getBean("MyFactoryBean", Test1_1.class);
        System.out.println(test1_1);
        test1_1.fuck();
    }
}
