package com.blue_core.aop.bean;

import com.blue_core.aop.AdvisedSupport;
import com.blue_core.aop.TargetSource;
import com.blue_core.aop.aspectj.AspectJExpressionPointCut;
import com.blue_core.aop.aspectj.AspectJExpressionPointCutAdvisor;
import com.blue_core.aop.framework.ProxyFactory;
import com.blue_core.aop.framework.adapter.MethodBeforeAdviceInterceptor;
import com.blue_core.context.support.ClassPathXmlApplicationContext;
import com.blue_core.core.io.ResourceLoader;
import com.blue_core.test.test01.Test1_1;
import com.blue_core.test.test01.TestImplement;
import org.aopalliance.intercept.MethodInterceptor;
import org.junit.Test;

/**
 * @Author Jason
 * @CreationDate 2023/02/01 - 16:06
 * @Description ：
 */
public class AdvisorTest {
    @Test
    public void AdvisorTest01() throws InterruptedException {
        AdvisedSupport advisedSupport = new AdvisedSupport();

        //代理对象
        TestImplement test = new Test1_1();

        //方法代理访问者，带有方法加强逻辑 和 加强的位置
        AspectJExpressionPointCutAdvisor advisor = new AspectJExpressionPointCutAdvisor();

        //赋值
        advisor.setExpression("execution(* com.blue_core.test.test01.TestImplement.*(..))");
        advisor.setAdvice(new MethodBeforeAdviceInterceptor(new MyBeforeAdvice()));

        //给AOP配置赋值
        advisedSupport.setTargetSource(new TargetSource(test));
        advisedSupport.setMethodMatcher(advisor.getPointCut().getMethodMatcher());
        advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
        advisedSupport.setProxyTargetClass(false);

        //代理
        TestImplement proxy = (TestImplement) new ProxyFactory(advisedSupport).getProxy();
        proxy.fuck();
    }
    @Test
    public void AdvisorTest02() throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(ResourceLoader.CLASSPATH_URL_PREFIX+"test.xml");
        Test1_1 test1_1 = (Test1_1) context.getBean("Test1_1");
        System.out.println(test1_1.toString());
        test1_1.fuck();
    }
}
