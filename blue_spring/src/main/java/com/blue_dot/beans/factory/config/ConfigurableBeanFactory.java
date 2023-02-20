package com.blue_dot.beans.factory.config;

import com.blue_dot.beans.factory.BeanFactory;
import com.blue_dot.utils.StringValueResolver;

/**
 * @Author Jason
 * @CreationDate 2023/01/28 - 15:41
 * @Description ：表示可以配置的BeanFactory，并引入Bean的生命周期概念。
 */
public interface ConfigurableBeanFactory extends BeanFactory,SingletonBeanRegistry {
    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    /**
     * 添加bean处理器到容器中，等待bean初始化阶段调用
     * @param processor bean处理器
     */
    void addBeanPostProcessor(BeanPostProcessor processor);

    /**
     * 销毁单例对象，真正的实现逻辑在DefaultSingletonBeanRegistry里
     * 实现了这个方法的子类基本上实现了DefaultSingletonBeanRegistry就有对应的功能
     */
    void destroySingletons();


    /**
     * Add a String resolver for embedded values such as annotation attributes.
     * @param valueResolver the String resolver to apply to embedded values
     * @since 3.0
     */
    void addEmbeddedValueResolver(StringValueResolver valueResolver);

    /**
     * Resolve the given embedded value, e.g. an annotation attribute.
     * @param value the value to resolve
     * @return the resolved value (may be the original value as-is)
     * @since 3.0
     */
    String resolveEmbeddedValue(String value);
}
