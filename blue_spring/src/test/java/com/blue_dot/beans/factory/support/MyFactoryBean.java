package com.blue_dot.beans.factory.support;

import com.blue_dot.beans.factory.FactoryBean;
import com.blue_dot.test.test01.Test1_1;
import com.blue_dot.test.test01.TestImplement;

/**
 * @Author Jason
 * @CreationDate 2023/01/30 - 15:18
 * @Description ：
 */
public class MyFactoryBean implements FactoryBean<Test1_1> {
    @Override
    public Test1_1 getObject() throws Exception {
        return new Test1_1("通过FactoryBean创建的Test1_1类对象");
    }

    @Override
    public Class<?> getObjectType() {
        return TestImplement.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
