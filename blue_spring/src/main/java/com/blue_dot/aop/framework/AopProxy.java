package com.blue_dot.aop.framework;

/**
 * @Author Jason
 * @CreationDate 2023/01/31 - 18:35
 * @Description ：已配置 AOP 代理的委托接口，允许创建实际的代理对象。
 * 开箱即用的实现，可用于 JDK 动态代理和 CGLIB 代理
 */
public interface AopProxy {
    /**
     * 获取代理对象
     * @return 代理对象
     */
    Object getProxy();
}
