package com.blue_core.test.test01;

import com.blue_core.beans.annotation.Component;

/**
 * @Author Jason
 * @CreationDate 2023/01/08 - 22:00
 * @Description ：TODO xml配置的bean和Component配置的名字不一样，所以容器中出现了两个此类生成的Bean
 */
@Component
public class Test1_1 implements TestImplement {
    private String testStr;

    public Test1_1() {
    }

    public Test1_1(String testStr) {
        this.testStr = testStr;
    }

    public String getTestStr() {
        return testStr;
    }

    public void setTestStr(String testStr) {
        this.testStr = testStr;
    }

    @Override
    public void fuck() throws InterruptedException {
        Thread.sleep(100);
        System.out.println("fuck:实现了测试接口的方法！打印此对象的toString："+toString());
    }

    @Override
    public String toString() {
        return "Test1_1{" +
                "testStr='" + testStr + '\'' +
                '}';
    }
}
