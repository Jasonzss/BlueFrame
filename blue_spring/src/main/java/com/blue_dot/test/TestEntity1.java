package com.blue_dot.test;


import com.blue_dot.stereotype.Component;

/**
 * @Author Jason
 * @CreationDate 2023/01/08 - 14:48
 * @Description ：
 */
@Component("test")
public class TestEntity1 {
    private String a;
    private int b;

    public TestEntity1() {
    }

    public TestEntity1(String a) {
        this.a = a;
    }

    public TestEntity1(int b) {
        this.b = b;
    }

    public TestEntity1(String a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public String toString() {
        return "TestEntity1{" +
                "a='" + a + '\'' +
                ", b=" + b +
                '}';
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }


    public void myDestroy() throws Exception {
        System.out.println("TestEntity1 执行销毁方法");
    }

    public void myInit() throws Exception {
        System.out.println("TestEntity1 执行初始化方法");
    }
}
