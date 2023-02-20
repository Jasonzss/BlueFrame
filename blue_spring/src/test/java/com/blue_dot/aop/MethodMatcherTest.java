package com.blue_dot.aop;

import com.blue_dot.aop.aspectj.AspectJExpressionPointCut;
import com.blue_dot.test.TestEntity1;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @Author Jason
 * @CreationDate 2023/01/31 - 17:13
 * @Description ：
 */
public class MethodMatcherTest {
    @Test
    public void matchesTest(){
        PointCut pointCut = new AspectJExpressionPointCut("execution(* com.blue_core.test.TestEntity1.*(..))");
        PointCut pointCut1 = new AspectJExpressionPointCut("execution(* com.blue_core.test.TestEntity1.getA(..))");

        Class<TestEntity1> testEntity1Class = TestEntity1.class;
        Method[] methods = testEntity1Class.getMethods();
        System.out.println(Arrays.toString(methods));

        System.out.println(pointCut.getClassFilter().matches(testEntity1Class));                //true
        System.out.println(pointCut.getMethodMatcher().matches(methods[0], testEntity1Class));  //true
        System.out.println(pointCut.getMethodMatcher().matches(methods[1], testEntity1Class));  //true
        System.out.println(pointCut.getMethodMatcher().matches(methods[2], testEntity1Class));  //true

        System.out.println(pointCut1.getClassFilter().matches(testEntity1Class));                   //true
        System.out.println(pointCut1.getMethodMatcher().matches(methods[0], testEntity1Class)); //false
        System.out.println(pointCut1.getMethodMatcher().matches(methods[1], testEntity1Class)); //false
        System.out.println(pointCut1.getMethodMatcher().matches(methods[2], testEntity1Class)); //false
        System.out.println(pointCut1.getMethodMatcher().matches(methods[3], testEntity1Class)); //false
        System.out.println(pointCut1.getMethodMatcher().matches(methods[4], testEntity1Class));     //true
        System.out.println(pointCut1.getMethodMatcher().matches(methods[5], testEntity1Class)); //false
        System.out.println(pointCut1.getMethodMatcher().matches(methods[6], testEntity1Class)); //false
    }
}
