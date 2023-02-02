package com.blue_core.context.support;

import com.blue_core.beans.BeansException;
import com.blue_core.beans.factory.ConfigurableListableBeanFactory;
import com.blue_core.beans.factory.config.BeanFactoryPostProcessor;
import com.blue_core.beans.factory.config.BeanPostProcessor;
import com.blue_core.context.ApplicationEvent;
import com.blue_core.context.ApplicationListener;
import com.blue_core.context.ConfigurableApplicationContext;
import com.blue_core.context.event.ApplicationEventMulticaster;
import com.blue_core.context.event.ContextCloseEvent;
import com.blue_core.context.event.ContextRefreshedEvent;
import com.blue_core.context.event.SimpleApplicationEventMulticaster;
import com.blue_core.core.io.DefaultResourceLoader;

import java.util.Collection;
import java.util.Map;

/**
 * @Author Jason
 * @CreationDate 2023/01/28 - 19:20
 * @Description ：
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {
    /**
     * Spring中 ApplicationEventMulticaster 的默认 BeanName
     */
    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    /**
     * Spring向所有监听者发布事件的广播器
     */
    private ApplicationEventMulticaster applicationEventMulticaster;


    @Override
    public Object getBean(String beanName) throws BeansException {
        return getBeanFactory().getBean(beanName);
    }

    @Override
    public Object getBean(String beanName, Object... args) throws BeansException {
        return getBeanFactory().getBean(beanName, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    /**
     * 模板方法模式中需要子类实现的方法。
     * 此类的BeanFactory的相关功能基本上都由此方法得到的BeanFactory真正执行
     * @return 可以获取 Bean 和 BeanDefinition 的ConfigurableListableBeanFactory
     */
    protected abstract ConfigurableListableBeanFactory getBeanFactory();


    /* -----------------------以下是refresh相关的方法---------------------  */

    /**
     * 刷新ApplicationContext
     */
    @Override
    public void refresh() {
        //1. 加载BeanDefinition：不同的子类有不同的加载方式，例如xml相关子类会去读取xml文件并解析出BeanDefinition，然后加载
        refreshBeanFactory();

        //2. 获取到刚创建的 ConfigurableListableBeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        //3. 添加 ApplicationContextAwareProcessor，让继承自 ApplicationContextAware 的 Bean 对象都能感知所属的 ApplicationContext
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

        //4. 调用所有的BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);

        //5. 将所有的BeanPostProcessor单独放到BeanFactory的特别容器中
        registerBeanProcessor(beanFactory);

        //6. 初始化事件发布者
        initApplicationEventMulticaster();

        //7. 注册监听器
        registerListener();

        //8. 调用BeanFactory的方法对Bean进行预初始化
        beanFactory.preInstantiateSingletons();

        //9. 发布ApplicationContext刷新成功事件
        finishRefresh();
    }

    /**
     * 模板方法模式的抽象方法之一，创建一个新的BeanFactory
     */
    protected abstract void refreshBeanFactory() throws BeansException;

    /**
     * 先找出目标容器所有的BeanFactoryPostProcessor，然后再全部执行一遍
     * 在初始化之前对BeanFactory中的BeanDefinition进行操作
     * @param beanFactory 目标容器ConfigurableListableBeanFactory
     */
    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory){
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for(BeanFactoryPostProcessor processor : beanFactoryPostProcessorMap.values()){
            processor.postProcessBeanFactory(beanFactory);
        }
    }

    /**
     * 找出目标容器中所有的 BeanPostProcessor，然后再单独放到 ConfigurableListableBeanFactory 中的特别容器中
     * @param beanFactory 目标容器ConfigurableListableBeanFactory
     */
    private void registerBeanProcessor(ConfigurableListableBeanFactory beanFactory){
        Map<String, BeanPostProcessor> beanPostProcessors = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor processor : beanPostProcessors.values()){
            beanFactory.addBeanPostProcessor(processor);
        }
    }

    /**
     * 初始化Spring的事件广播器
     * new 一个 SimpleApplicationEventMulticaster 并将其注册到单例池中
     */
    private void initApplicationEventMulticaster() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
    }

    /**
     * 注册所有的监听器
     */
    private void registerListener(){
        //获取所有的监听器bean
        Collection<ApplicationListener> listeners = getBeansOfType(ApplicationListener.class).values();
        //向Spring中央广播注册监听器
        listeners.forEach((l) -> {
            applicationEventMulticaster.addApplicationListener(l);
        });
    }

    /**
     * 容器刷新完成，发布ContextRefreshedEvent
     */
    private void finishRefresh(){
        publishEvent(new ContextRefreshedEvent(this));
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(requiredType);
    }

    /*******************ConfigurableApplicationContext的其他方法重写**********************/

    @Override
    public void registryShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {
        //发布ApplicationContext关闭事件
        publishEvent(new ContextCloseEvent(this));

        //执行销毁单例bean的销毁方法
        getBeanFactory().destroySingletons();
    }

    /************************重写ApplicationEventPublisher的方法******************************/
    @Override
    public void publishEvent(ApplicationEvent event) {
        //使用Spring中央广播发布事件
        applicationEventMulticaster.multicastEvent(event);
    }
}
