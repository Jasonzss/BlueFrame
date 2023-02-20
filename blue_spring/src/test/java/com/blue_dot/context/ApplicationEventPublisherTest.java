package com.blue_dot.context;

import com.blue_dot.context.support.ClassPathXmlApplicationContext;
import com.blue_dot.core.io.ResourceLoader;
import org.junit.Test;

/**
 * @Author Jason
 * @CreationDate 2023/01/30 - 20:53
 * @Description ：
 */
public class ApplicationEventPublisherTest {
    @Test
    public void publishEventTest(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(ResourceLoader.CLASSPATH_URL_PREFIX+"test.xml");

        context.publishEvent(new MyEvent(context, "全体目光像我看齐，我宣布个事！"));
        context.registryShutdownHook();
    }
}
