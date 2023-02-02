package com.blue_core.aop.bean;

import com.blue_core.aop.AdvisedSupport;
import com.blue_core.aop.MyInterceptor;
import com.blue_core.aop.TargetSource;
import com.blue_core.aop.aspectj.AspectJExpressionPointCut;
import com.blue_core.aop.framework.ProxyFactory;
import com.blue_core.aop.framework.adapter.MethodBeforeAdviceInterceptor;
import com.blue_core.test.test01.Test1_1;
import com.blue_core.test.test01.TestImplement;
import org.junit.Test;

/**
 * @Author Jason
 * @CreationDate 2023/02/01 - 13:56
 * @Description ：
 */
public class BeforeAdviceTest {
    @Test
    public void beforeTest() throws InterruptedException {
        AdvisedSupport advisedSupport = new AdvisedSupport();

        TestImplement test = new Test1_1();
        advisedSupport.setTargetSource(new TargetSource(test));
        advisedSupport.setMethodMatcher(new AspectJExpressionPointCut("execution(* com.blue_core.test.test01.TestImplement.*(..))"));


        MethodBeforeAdvice beforeAdvice = new MyBeforeAdvice();
        MethodBeforeAdviceInterceptor beforeAdviceInterceptor = new MethodBeforeAdviceInterceptor(beforeAdvice);
        advisedSupport.setMethodInterceptor(beforeAdviceInterceptor);

        TestImplement proxy = (TestImplement) new ProxyFactory(advisedSupport).getProxy();
        proxy.fuck();
    }
}
