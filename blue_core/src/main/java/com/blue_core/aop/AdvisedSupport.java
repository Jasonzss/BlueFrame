package com.blue_core.aop;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * @Author Jason
 * @CreationDate 2023/01/31 - 17:21
 * @Description ：AOP配置管理器的基类。
 */
public class AdvisedSupport {
    // ProxyConfig
    private boolean proxyTargetClass = false;

    //被代理的目标对象
    private TargetSource targetSource;

    //方法匹配器，检查目标方法是否符合通知条件
    private MethodMatcher methodMatcher;

    //方法拦截器
    private MethodInterceptor methodInterceptor;


    public AdvisedSupport() {
    }

    public AdvisedSupport(TargetSource targetSource, MethodMatcher methodMatcher, MethodInterceptor methodInterceptor) {
        this.targetSource = targetSource;
        this.methodMatcher = methodMatcher;
        this.methodInterceptor = methodInterceptor;
    }

    /**
     * @return 是否直接代理目标类以及任何接口
     */
    public boolean isProxyTargetClass() {
        return proxyTargetClass;
    }

    public void setProxyTargetClass(boolean proxyTargetClass) {
        this.proxyTargetClass = proxyTargetClass;
    }

    public TargetSource getTargetSource() {
        return targetSource;
    }

    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

    public MethodMatcher getMethodMatcher() {
        return methodMatcher;
    }

    public void setMethodMatcher(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }

    public MethodInterceptor getMethodInterceptor() {
        return methodInterceptor;
    }

    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }
}
