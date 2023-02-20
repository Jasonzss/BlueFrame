package com.blue_dot.test;

import com.blue_dot.beans.factory.DisposableBean;
import com.blue_dot.beans.factory.InitializingBean;
import com.blue_dot.beans.factory.annotation.Autowired;
import com.blue_dot.beans.factory.annotation.Value;
import com.blue_dot.stereotype.Component;

/**
 * @Author Jason
 * @CreationDate 2023/01/08 - 14:48
 * @Description ：
 */
@Component("test2")
public class TestEntity2 implements InitializingBean, DisposableBean {
    @Autowired
    private TestEntity1 testEntity1;

    @Value("${name}")
    private String name;

    public TestEntity2() {
    }

    public TestEntity2(TestEntity1 testEntity1) {
        this.testEntity1 = testEntity1;
    }

    public TestEntity1 getTestEntity1() {
        return testEntity1;
    }

    public void setTestEntity1(TestEntity1 testEntity1) {
        this.testEntity1 = testEntity1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("TestEntity2 执行销毁方法");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("TestEntity2 执行初始化方法");
    }

    @Override
    public String toString() {
        return "TestEntity2{" +
                "testEntity1=" + testEntity1 +
                ", name='" + name + '\'' +
                '}';
    }
}
