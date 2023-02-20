package com.blue_dot.aop;

import com.blue_dot.aop.aspectj.AspectJExpressionPointCut;
import com.blue_dot.aop.framework.Cglib2AopProxy;
import com.blue_dot.aop.framework.JdkDynamicAopProxy;
import com.blue_dot.test.test01.Test1_1;
import com.blue_dot.test.test01.TestImplement;
import org.junit.Test;

/**
 * @Author Jason
 * @CreationDate 2023/01/31 - 16:08
 * @Description ：
 */
public class AopTest {
    @Test
    public void cglibProxyTest() throws InterruptedException {
        TestImplement test1_1 = new Test1_1();

        TargetSource targetSource = new TargetSource(test1_1);
        AspectJExpressionPointCut pointCut = new AspectJExpressionPointCut("execution(* com.blue_core.test.test01.TestImplement.*(..))");
        MyInterceptor myInterceptor = new MyInterceptor();

        AdvisedSupport advisedSupport = new AdvisedSupport(targetSource,pointCut,myInterceptor);

        //由于是JDK动态代理生成的，所以代理对象只能转换为接口，而不能转换为实现类
        TestImplement jdkProxy = (TestImplement) new JdkDynamicAopProxy(advisedSupport).getProxy();
        jdkProxy.fuck();

        TestImplement cglibProxy = (Test1_1) new Cglib2AopProxy(advisedSupport).getProxy();
        System.out.println(cglibProxy);
        cglibProxy.fuck();
    }

    @Test
    public void jdkProxyTest() throws InterruptedException {
        TestImplement test1_1 = new Test1_1();

        TargetSource targetSource = new TargetSource(test1_1);
        AspectJExpressionPointCut pointCut = new AspectJExpressionPointCut("execution(* com.blue_core.test.test01.TestImplement.*(..))");
        MyInterceptor myInterceptor = new MyInterceptor();

        AdvisedSupport advisedSupport = new AdvisedSupport(targetSource,pointCut,myInterceptor);

        TestImplement proxy = (TestImplement) new JdkDynamicAopProxy(advisedSupport).getProxy();
        proxy.fuck();
    }
}

