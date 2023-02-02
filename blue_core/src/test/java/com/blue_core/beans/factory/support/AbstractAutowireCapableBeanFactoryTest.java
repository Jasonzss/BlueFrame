package com.blue_core.beans.factory.support;

import com.blue_core.beans.PropertyValue;
import com.blue_core.beans.factory.config.BeanDefinition;
import com.blue_core.beans.factory.config.BeanReference;
import com.blue_core.beans.factory.config.GenericBeanDefinition;
import com.blue_core.test.TestEntity1;
import com.blue_core.test.TestEntity2;
import org.junit.Test;

/**
 * @Author Jason
 * @CreationDate 2023/01/11 - 14:56
 * @Description ：
 */
public class AbstractAutowireCapableBeanFactoryTest {
    /**
     * 测试单个String参数构造器创建对象
     */
    @Test
    public void createBeanTest1(){
        AbstractAutowireCapableBeanFactory factory = new DefaultListableBeanFactory();
        BeanDefinition definition = new GenericBeanDefinition(TestEntity1.class);
        Object testBean = factory.createBean("testBean", definition, "3");
        System.out.println(factory.getBean("testBean").toString());
        //TestEntity1{a='3', b=0}
    }

    /**
     * 测试单个Integer参数构造器创建对象
     */
    @Test
    public void createBeanTest2(){
        AbstractAutowireCapableBeanFactory factory = new DefaultListableBeanFactory();
        BeanDefinition definition = new GenericBeanDefinition(TestEntity1.class);
        Object testBean = factory.createBean("testBean", definition, 3);
        System.out.println(factory.getBean("testBean").toString());
        //TestEntity1{a='null', b=3}
    }

    /**
     * 测试多个参数构造器创建对象
     */
    @Test
    public void createBeanTest3(){
        AbstractAutowireCapableBeanFactory factory = new DefaultListableBeanFactory();
        BeanDefinition definition = new GenericBeanDefinition(TestEntity1.class);
        Object testBean = factory.createBean("testBean", definition, "aaa", 3);
        System.out.println(factory.getBean("testBean").toString());
        //TestEntity1{a='aaa', b=3}
    }

    /**
     * 测试多个乱序参数构造器创建对象
     */
    @Test
    public void createBeanTest4(){
//        AbstractAutowireCapableBeanFactory factory = new DefaultListableBeanFactory();
//        BeanDefinition definition = new GenericBeanDefinition(TestEntity1.class);
//        Object testBean = factory.createBean("testBean", definition, 3, "aaa");
//        System.out.println(factory.getBean("testBean").toString());
        //com.blue_core.beans.BeansException: Instantiation of bean failed
    }

    /**
     * 测试属性注入
     */
    @Test
    public void createBeanTest5(){
        AbstractAutowireCapableBeanFactory factory = new DefaultListableBeanFactory();
        BeanDefinition definition = new GenericBeanDefinition(TestEntity1.class);
        definition.addProperty(new PropertyValue("a","这是a属性的值"));
        factory.createBean("testBean", definition, 3);
        System.out.println(factory.getBean("testBean").toString());
        //TestEntity1{a='这是a属性的值', b=3}
    }

    /**
     * 测试依赖注入
     */
    @Test
    public void createBeanTest6(){
        AbstractAutowireCapableBeanFactory factory = new DefaultListableBeanFactory();

        BeanDefinition definition1 = new GenericBeanDefinition(TestEntity1.class);
        definition1.addProperty(new PropertyValue("a","这是a属性的值"));

        BeanDefinition definition2 = new GenericBeanDefinition(TestEntity2.class);
        definition2.addProperty(new PropertyValue("testEntity1",new BeanReference("testBean1")));

        factory.createBean("testBean1", definition1, 3);
        factory.createBean("testBean2", definition2);

        System.out.println(factory.getBean("testBean1").toString());
        //TestEntity1{a='这是a属性的值', b=3}
        System.out.println(factory.getBean("testBean2").toString());
        //TestEntity2{testEntity1=TestEntity1{a='这是a属性的值', b=3}}
    }


}
