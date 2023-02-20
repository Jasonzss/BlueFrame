package com.blue_dot.aop.framework;

import com.blue_dot.aop.AdvisedSupport;
import com.blue_dot.aop.MyInterceptor;
import com.blue_dot.aop.TargetSource;
import com.blue_dot.aop.aspectj.AspectJExpressionPointCut;
import com.blue_dot.test.test01.Test1_1;
import com.blue_dot.test.test01.TestImplement;
import org.junit.Test;

/**
 * @Author Jason
 * @CreationDate 2023/02/01 - 13:32
 * @Description ：
 */
public class ProxyFactoryTest {
    @Test
    public void testProxy() throws InterruptedException {
        AdvisedSupport advisedSupport = new AdvisedSupport();

        TestImplement test = new Test1_1();
        advisedSupport.setTargetSource(new TargetSource(test));
        advisedSupport.setMethodInterceptor(new MyInterceptor());
        advisedSupport.setMethodMatcher(new AspectJExpressionPointCut("execution(* com.blue_core.test.test01.TestImplement.*(..))"));

        ProxyFactory proxyFactory = new ProxyFactory(advisedSupport);
        TestImplement proxy = (TestImplement) proxyFactory.getProxy();
        proxy.fuck();
    }
}
