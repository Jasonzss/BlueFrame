package com.blue_core.beans.beanUtils;

import com.blue_core.beans.factory.config.GenericBeanDefinition;
import com.blue_core.test.TestEntity1;
import com.blue_core.test.TestEntity2;
import org.junit.Test;

/**
 * @Author Jason
 * @CreationDate 2023/01/08 - 16:21
 * @Description ：
 */
public class DefaultBeanNameGeneratorTest {
    @Test
    public void generateBeanNameTest(){
        GenericBeanDefinition definition2 = new GenericBeanDefinition(TestEntity2.class);
        GenericBeanDefinition definition1 = new GenericBeanDefinition(TestEntity1.class);
        DefaultBeanNameGenerator generator = new DefaultBeanNameGenerator();
        System.out.println(generator.generateBeanName(definition1));
        System.out.println(generator.generateBeanName(definition2));
    }
}
