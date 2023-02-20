package com.blue_dot.beans.factory.support;

import com.blue_dot.beans.BeansException;
import com.blue_dot.beans.factory.FactoryBean;
import com.blue_dot.beans.factory.config.BeanDefinition;
import com.blue_dot.beans.factory.config.BeanPostProcessor;
import com.blue_dot.beans.factory.config.ConfigurableBeanFactory;
import com.blue_dot.utils.ClassUtil;
import com.blue_dot.utils.StringValueResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Jason
 * @CreationDate 2023/01/10 - 16:58
 * @Description ：
 */
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {
    //在创建Bean时会调用的BeanPostProcessor集合
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    //如有必要，ClassLoader 用于解析 bean 类名
    //不明白为什么这里放个 ClassLoader
    private ClassLoader beanClassLoader = ClassUtil.getDefaultClassLoader();

    /**
     * String resolvers to apply e.g. to annotation attribute values
     */
    private final List<StringValueResolver> embeddedValueResolvers = new ArrayList<>();

    @Override
    public Object getBean(String beanName) {
        return doGetBean(beanName, null);
    }

    @Override
    public Object getBean(String beanName, Object... args) throws BeansException {
        return doGetBean(beanName, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        Object bean = getBean(name);
        return bean.getClass().isAssignableFrom(requiredType) ? (T)bean : null;
    }

    /**
     * 真正去容器中获取bean的方法。
     * 使用模板方法模式，将 获取BeanDefinition、创建Bean实例 的逻辑操作延迟到子类中实现
     * 为什么在这使用泛型？
     * @param beanName bean的唯一名称
     * @param args 初始化bean的参数
     * @param <T> bean对象的泛型
     * @return bean对象
     */
    protected <T> T doGetBean(final String beanName, final Object[] args){
        //尝试去单例池中找目标Bean
        Object beanInstance = getSingleton(beanName);

        if (beanInstance == null){
            //该Bean还未创建，先获取 BeanDefinition 并创建
            BeanDefinition beanDefinition = getBeanDefinition(beanName);
            beanInstance = createBean(beanName, beanDefinition, args);
        }

        //创建出来的Bean还需要考虑是否为FactoryBean
        return (T) getObjectForBeanInstance(beanInstance, beanName);
    }

    /**
     * 判断当前Bean是否为FactoryBean，不是的话直接返回
     * 是FactoryBean的话就调用对应的方法将FactoryBean从Factory中取出
     * @param beanInstance 当前待判断Bean
     * @param beanName  BeanName
     */
    private Object getObjectForBeanInstance(Object beanInstance, String beanName){
        if (!(beanInstance instanceof FactoryBean)){
            return beanInstance;
        }

        FactoryBean<?> factoryBean = (FactoryBean<?>) beanInstance;
        return getObjectFromFactoryBean(factoryBean, beanName);
    }

    /**
     * 根据Bean的名称获取 BeanDefinition
     * 模板方法模式中的抽象父类的抽象方法
     * @param beanName bean的唯一名称
     * @return 指定名称的BeanDefinition
     */
    protected abstract BeanDefinition getBeanDefinition(String beanName);

    /**
     * Bean的初始化操作
     * 模板方法模式中的抽象父类的抽象方法
     * @param beanName bean的唯一名称
     * @param beanDefinition bean定义信息
     * @param args 初始化的参数
     * @return 已经创建好的Bean
     */
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object... args);

    @Override
    public void addBeanPostProcessor(BeanPostProcessor processor) {
        //如果已经存在目标BeanPostProcessor，则新的覆盖旧的
        beanPostProcessors.remove(processor);
        beanPostProcessors.add(processor);
    }

    @Override
    public void addEmbeddedValueResolver(StringValueResolver valueResolver) {
        this.embeddedValueResolvers.add(valueResolver);
    }

    @Override
    public String resolveEmbeddedValue(String value) {
        String result = value;
        for (StringValueResolver resolver : this.embeddedValueResolvers) {
            result = resolver.resolveStringValue(result);
        }
        return result;
    }

    /**
     * 返回 BeanPostProcessor 的列表，这些 BeanPostProcessors 将应用于使用该工厂创建的 bean
     */
    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }
}
