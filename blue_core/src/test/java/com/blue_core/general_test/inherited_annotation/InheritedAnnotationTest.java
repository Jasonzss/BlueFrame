package com.blue_core.general_test.inherited_annotation;

import org.junit.Test;

/**
 * @Author Jason
 * @CreationDate 2022/12/19 - 21:55
 * @Description ：测试注解继承有哪些东西被保留
 */
public class InheritedAnnotationTest {
    @Test
    public void test01(){
        System.out.println(SonTest.class.isAnnotationPresent(Father.class));
    }
}

@Son(name = "a")
class SonTest{

}
