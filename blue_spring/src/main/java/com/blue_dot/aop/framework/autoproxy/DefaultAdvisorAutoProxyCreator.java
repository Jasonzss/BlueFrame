package com.blue_dot.aop.framework.autoproxy;

import cn.hutool.core.bean.BeanException;
import com.blue_dot.aop.AdvisedSupport;
import com.blue_dot.aop.ClassFilter;
import com.blue_dot.aop.PointCut;
import com.blue_dot.aop.TargetSource;
import com.blue_dot.aop.aspectj.AspectJExpressionPointCutAdvisor;
import com.blue_dot.aop.bean.Advisor;
import com.blue_dot.aop.framework.ProxyFactory;
import com.blue_dot.beans.BeansException;
import com.blue_dot.beans.PropertyValues;
import com.blue_dot.beans.factory.BeanFactory;
import com.blue_dot.beans.factory.aware.BeanFactoryAware;
import com.blue_dot.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.blue_dot.beans.factory.support.DefaultListableBeanFactory;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.Collection;

/**
 * @Author Jason
 * @CreationDate 2023/01/31 - 23:52
 * @Description ：
 */
public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {
    private DefaultListableBeanFactory beanFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeanException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException {
        return bean;
    }

    /**
     * 在Bean进行实例化之前进行的操作
     * @param beanClass 目标bean的Class
     * @param beanName 目标bean的name
     */
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if (isInfrastructureClass(beanClass)) {
            //如果是AOP基类，则不处理
            return null;
        }

        //获取容器中所有的Advisor
        Collection<AspectJExpressionPointCutAdvisor> advisors = beanFactory.getBeansOfType(AspectJExpressionPointCutAdvisor.class).values();

        //依次使用Advisor对Bean进行增强
        for (AspectJExpressionPointCutAdvisor advisor : advisors) {
            ClassFilter classFilter = advisor.getPointCut().getClassFilter();
            //筛选出目标bean需要的Advisor
            if (!classFilter.matches(beanClass)) {
                continue;
            }


            AdvisedSupport advisedSupport = new AdvisedSupport();

            TargetSource targetSource = null;
            try {
                //初始化目标Bean，并将其包装到AOP源目标中
                targetSource = new TargetSource(beanClass.getDeclaredConstructor().newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
            //封装AOP配置
            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            advisedSupport.setMethodMatcher(advisor.getPointCut().getMethodMatcher());
            advisedSupport.setProxyTargetClass(false);

            //执行AOP增强
            return new ProxyFactory(advisedSupport).getProxy();
        }

        //若BeanFactory中没有Advisor，则直接返回null，不处理当前bean
        return null;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        return pvs;
    }

    /**
     * 判断是否为AOP的基础类：Advice、PointCut、Advisor
     * @param beanClass 目标bean
     * @return 是否为AOP的基础类
     */
    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass) || PointCut.class.isAssignableFrom(beanClass) || Advisor.class.isAssignableFrom(beanClass);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (!(beanFactory instanceof DefaultListableBeanFactory)){
            //TODO 这个地方暂时先这么用，实际肯定不能这样写，这样子强转Factory不知道合不合理
            throw new BeansException("请使用DefaultListableBeanFactory以下的BeanFactory");
        }
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }
}
