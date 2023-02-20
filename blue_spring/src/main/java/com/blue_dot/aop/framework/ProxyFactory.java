package com.blue_dot.aop.framework;

import com.blue_dot.aop.AdvisedSupport;
import com.blue_dot.beans.BeansException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;

/**
 * @Author Jason
 * @CreationDate 2023/01/31 - 23:34
 * @Description ：此类提供了一种在代码中获取和配置 AOP 代理的简单方法。
 * 判断目标类的一些条件来选择 cglib动态代理 和 JDK动态代理
 */
public class ProxyFactory {
    private AdvisedSupport advisedSupport;
    public static final Logger log = LoggerFactory.getLogger(ProxyFactory.class);

    public ProxyFactory(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    public Object getProxy() {
        return createAopProxy().getProxy();
    }

    public AopProxy createAopProxy() {
        if (advisedSupport.isProxyTargetClass()) {
            //先判断被代理对象类是否存在
            Class<?> targetClass = advisedSupport.getTargetSource().getTarget().getClass();
            if (targetClass == null) {
                throw new BeansException("不存在的代理对象");
            }

            if (targetClass.isInterface() || Proxy.isProxyClass(targetClass)) {
                return new JdkDynamicAopProxy(advisedSupport);
            }
            return new Cglib2AopProxy(advisedSupport);
        }else {
//            return new JdkDynamicAopProxy(advisedSupport);
            //TODO Spring中原来为JDK代理，但是在代理的对象为实现类而非接口时会出错，这个后面需要调整下选择策略
            return new Cglib2AopProxy(advisedSupport);
        }
    }
}
