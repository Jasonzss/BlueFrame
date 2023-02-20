package com.blue_dot.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.blue_dot.beans.BeansException;
import com.blue_dot.beans.PropertyValue;
import com.blue_dot.beans.PropertyValues;
import com.blue_dot.beans.factory.Aware;
import com.blue_dot.beans.factory.DisposableBean;
import com.blue_dot.beans.factory.InitializingBean;
import com.blue_dot.beans.factory.aware.BeanClassLoaderAware;
import com.blue_dot.beans.factory.aware.BeanFactoryAware;
import com.blue_dot.beans.factory.aware.BeanNameAware;
import com.blue_dot.beans.factory.config.*;
import com.blue_dot.beans.factory.support.instantiation.InstantiationStrategy;
import com.blue_dot.beans.factory.support.instantiation.SimpleInstantiationStrategy;
import com.blue_dot.utils.ClassUtil;

import java.lang.reflect.Constructor;

/**
 * @Author Jason
 * @CreationDate 2023/01/10 - 17:20
 * @Description ：实现自动注入的factory的抽象类
 * 包括set注入 和 构造器注入
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {
    /**
     * 策略模式：初始化bean有多种方式（策略），我们可以自主选择对应的初始化策略，此处使用JDK编写的简单初始化策略
     */
    private InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

    /**
     * AbstractBeanFactory的模板方法模式抽象方法的实现
     * 创建bean的核心流程
     */
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object... args) {
        Object bean;

        try{
            // 1. 判断是否返回代理 Bean 对象
            //TODO 被代理的对象这里直接返回的话 3、5、6都没有执行，4只执行了部分。这样是不合理的，回头需要改
            bean = resolveBeforeInstantiation(beanName, beanDefinition);
            if (null != bean) {
                return bean;
            }

            // 2. bean的实例化
            bean = createBeanInstance(beanName, beanDefinition, args);

            // 3. 在设置 Bean 属性（依赖注入）之前，允许 BeanPostProcessor 修改属性值
            applyBeanPostProcessorsBeforeApplyingPropertyValues(beanName, bean, beanDefinition);

            // 3. 属性填充 & 依赖注入
            applyPropertyValues(beanName, bean, beanDefinition);

            // 4. 进入Bean的初始化阶段，调用Bean的初始化方法、BeanPostProcessor进行前置和后置处理方法
            bean = initializeBean(beanName, bean, beanDefinition);
        }catch (Exception e){
            throw new BeansException("["+beanName+"] 实例化失败",e);
        }

        // 5. 判断创建出来的Bean是否为DisposableBean，是的话则注册到指定的容器中
        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);

        // 6. 判断创建好的bean是否为单例，是的话放入单例池中
        if (beanDefinition.isSingleton()){
            registerSingleton(beanName,bean);
        }

        return bean;
    }

    protected Object resolveBeforeInstantiation(String beanName, BeanDefinition beanDefinition) {
        //后置处理器
        Object bean = applyBeanPostProcessorsBeforeInstantiation(beanDefinition.getBeanClass(), beanName);
        if (null != bean) {
            bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        }
        return bean;
    }

    /**
     * 找到BeanPostProcessor中的InstantiationAwareBeanPostProcessor，执行其中的AOP代理逻辑
     */
    protected Object applyBeanPostProcessorsBeforeInstantiation(Class<?> beanClass, String beanName) {
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                //如果目标Bean需要Aop则返回的是代理Bean，不需要AOP的话直接返回Null
                Object result = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessBeforeInstantiation(beanClass, beanName);
                if (null != result) {
                    //返回代理对象
                    return result;
                }
            }
        }
        //目标Bean不需要代理
        return null;
    }

    /**
     * 根据输入的参数获取指定的构造器，并交由初始化策略去执行bean的初始化
     */
    protected Object createBeanInstance(String beanName, BeanDefinition beanDefinition, Object[] args){
        Constructor<?> targetCtor = null;
        args = (args == null) ? new Object[0] : args;
        Constructor<?>[] declaredConstructors = beanDefinition.getBeanClass().getDeclaredConstructors();
        for (Constructor<?> ctor : declaredConstructors) {
            //返回参数对应的构造器
            if (isTargetConstructor(ctor, args)){
                targetCtor = ctor;
                break;
            }
        }

        return instantiationStrategy.instantiate(beanDefinition,beanName,targetCtor,args);
    }

    /**
     * 在设置 Bean 属性之前，允许 BeanPostProcessor 修改属性值
     */
    protected void applyBeanPostProcessorsBeforeApplyingPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor){
                PropertyValues pvs = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessPropertyValues(beanDefinition.getProperties(), bean, beanName);
                if (null != pvs) {
                    for (PropertyValue propertyValue : pvs.getValues()) {
                        beanDefinition.getProperties().addValue(propertyValue);
                    }
                }
            }
        }
    }

    /**
     * 判断此构造器是否为目标构造参数所指定的构造器，参数顺序要严格遵循构造器的参数的顺序
     * @param targetArgs 目标构造器的参数
     */
    private boolean isTargetConstructor(Constructor<?> constructor, Object[] targetArgs){
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        if (targetArgs.length != parameterTypes.length){
            return false;
        }
        for (int i = 0; i < targetArgs.length; i++) {
            //要注意基类和包装类也是同类型的
            if (ClassUtil.isSameType(targetArgs[i],parameterTypes[i])){
                return false;
            }
        }

        return true;
    }

    /**
     * 给目标bean注入属性
     */
    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition){
        try{
            PropertyValue[] propertyValues = beanDefinition.getProperties().getValues();
            for(PropertyValue propertyValue : propertyValues){
                Object value = propertyValue.getValue();
                String name = propertyValue.getName();

                if (value.getClass().isAssignableFrom(BeanReference.class)){
                    value = getBean(((BeanReference)value).getBeanName());
                }

                //使用set注入
                BeanUtil.setProperty(bean, name, value);
            }
        }catch (Exception e){
            throw new BeansException("[" + beanName + "] 的属性set注入失败",e);
        }
    }

    /**
     * Bean的初始化阶段
     * @param beanName bean的name
     * @param bean bean
     * @param beanDefinition bean的定义
     * @return 初始化阶段过后处理完的bean
     */
    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition){
        // 1. 回调Aware方法
        //TODO 这种代码有点离谱，回头需要优化一下
        if (bean instanceof Aware){
            if (bean instanceof BeanFactoryAware) {
                ((BeanFactoryAware) bean).setBeanFactory(this);
            }
            if (bean instanceof BeanClassLoaderAware){
                ((BeanClassLoaderAware) bean).setBeanClassLoader(getBeanClassLoader());
            }
            if (bean instanceof BeanNameAware) {
                ((BeanNameAware) bean).setBeanName(beanName);
            }
        }


        // 2. 执行 BeanPostProcessor Before 处理
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

        // 3. 调用init方法
        try{
            invokeInitMethods(beanName, wrappedBean, beanDefinition);
        }catch (Exception e){
            throw new BeansException("调用[" + beanName + "]的init方法失败", e);
        }


        // 4. 执行 BeanPostProcessor After 处理
        wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);

        //返回处理完成的Bean
        return wrappedBean;
    }

    /**
     * 注册一次性的（可被销毁的）Bean
     * @param beanName bean的name
     * @param bean bean对象
     * @param beanDefinition bean的定义
     */
    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition){
        // 不是单例的话不进行生命周期中的销毁阶段
        if (!beanDefinition.isSingleton()){
            return;
        }

        if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())){
            //是一次性Bean，进行注册
            //因为DisposableBean有多个来源，所以使用适配器模式来统一接口
            registerDisposableBean(beanName, new DisposableBeanAdapter(beanName, bean, beanDefinition));
        }
    }

    /**
     * bean的初始化方法
     */
    private void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {
        // 1. 判断是否实现了 InitializingBean
        if (bean instanceof InitializingBean){
            ((InitializingBean) bean).afterPropertiesSet();
        }

        // 2.解析配置
        String initMethodName = beanDefinition.getInitMethodName();
        //判断的右边是方式同一个init方法执行两次
        if (StrUtil.isNotEmpty(initMethodName) && !(bean instanceof InitializingBean)){
            try{
                bean.getClass().getMethod(initMethodName).invoke(bean);
            }catch (NoSuchMethodException e){
                throw new BeansException("在["+beanName+"]中找不到名为["+initMethodName+"]的init方法",e);
            }
        }
    }


    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        //轮流执行初始化前处理方法，如果执行到该bean为空，则返回变空之前的Bean
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessBeforeInitialization(result, beanName);
            if (null == current) {
                return result;
            }
            result = current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        //逻辑和上个方法差不多
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessAfterInitialization(result, beanName);
            if (null == current) {
                return result;
            }
            result = current;
        }
        return result;
    }

    @Override
    public <T> T createBean(Class<T> beanClass) throws BeansException {
        // Use prototype bean definition, to avoid registering bean as dependent bean.
        BeanDefinition bd = new GenericBeanDefinition(beanClass);
        bd.setScope(SCOPE_PROTOTYPE);
        bd.setBeanName(beanClass.getSimpleName());
        return (T) createBean(beanClass.getName(), bd, null);
    }
}
