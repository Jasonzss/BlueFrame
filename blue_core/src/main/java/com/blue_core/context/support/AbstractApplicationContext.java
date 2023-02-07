package com.blue_core.context.support;

import com.blue_core.beans.BeansException;
import com.blue_core.beans.factory.ConfigurableListableBeanFactory;
import com.blue_core.beans.factory.config.AutowireCapableBeanFactory;
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
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * <p>实现了ConfigurableApplicationContext、ApplicationContext接口的几乎所有方法
 *
 * <p>核心方法是{@link #refresh()}，其中定义了所有ApplicationContext刷新过程，
 * 同时也是ApplicationContext初始化的过程。此外这里还用到了模板方法模式，让子类自己实现refreshBeanFactory。
 * 其中的由子类实现的逻辑就包括：
 *      创建什么类型的BeanFactory；
 *      如何加载BeanDefinition进入BeanFactory；
 *      ...
 *
 * <p>与普通的 BeanFactory 不同，ApplicationContext 应该检测在其内部 BeanFactory 中定义的特殊 bean：
 * 因此，此类会自动注册在上下文中 并 定义为 bean 的BeanFactoryPostProcessors 、 BeanPostProcessors和ApplicationListeners
 *
 * <p>通过继承DefaultResourceLoader实现资源加载的能力
 * @Author Jason
 * @CreationDate 2023/01/28 - 19:20
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {
    /**
     * Spring中 ApplicationEventMulticaster 的默认 BeanName
     */
    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    /**
     * 表示ApplicationContext是否激活状态
     */
    private final AtomicBoolean active = new AtomicBoolean();

    /**
     * Spring向所有监听者发布事件的广播器
     */
    private ApplicationEventMulticaster applicationEventMulticaster;


    /* -----------------------以下是BeanFactory的实现方法---------------------  */

    @Override
    public Object getBean(String beanName) throws BeansException {
        return getConfigurableListableBeanFactory().getBean(beanName);
    }

    @Override
    public Object getBean(String beanName, Object... args) throws BeansException {
        return getConfigurableListableBeanFactory().getBean(beanName, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getConfigurableListableBeanFactory().getBean(name, requiredType);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return getConfigurableListableBeanFactory().getBean(requiredType);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) {
        return getConfigurableListableBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getConfigurableListableBeanFactory().getBeanDefinitionNames();
    }


    /* -----------------------以下是ApplicationContext的实现方法---------------------  */

    @Override
    public AutowireCapableBeanFactory getAutowireCapableBeanFactory() {
        return getConfigurableListableBeanFactory();
    }


    @Override
    public void publishEvent(ApplicationEvent event) {
        //使用Spring中央广播发布事件
        applicationEventMulticaster.multicastEvent(event);
    }
    /* -----------------------以下是ConfigurableApplicationContext的实现方法---------------------  */

    /**
     * 模板方法模式中需要子类实现的方法。
     * 此类的BeanFactory的相关功能基本上都由此方法得到的BeanFactory真正执行
     * @return 可以获取 Bean 和 BeanDefinition 的ConfigurableListableBeanFactory
     */
    @Override
    public abstract ConfigurableListableBeanFactory getConfigurableListableBeanFactory();


        /* -----------------------以下是refresh相关的方法---------------------  */

    /**
     * 刷新ApplicationContext
     *
     * 加载或刷新配置的持久表示，它可能是 XML 文件、属性文件或关系数据库架构。
     * 由于这是一个 启动方法 ，如果失败，它应该销毁已经创建的单例，以避免悬空资源。
     * 换句话说，在调用该方法之后，要么实例化所有单例，要么根本不实例化单例。
     */
    @Override
    public void refresh() {
        //1. 加载BeanDefinition：不同的子类有不同的加载方式，例如xml相关子类会去读取xml文件并解析出BeanDefinition，然后加载
        refreshBeanFactory();

        //2. 获取到刚创建的 ConfigurableListableBeanFactory
        ConfigurableListableBeanFactory beanFactory = getConfigurableListableBeanFactory();

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
        ConfigurableListableBeanFactory beanFactory = getConfigurableListableBeanFactory();
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
        getConfigurableListableBeanFactory().destroySingletons();

        //关闭BeanFactory：抽象方法，子类实现
        closeBeanFactory();
    }

    protected abstract void closeBeanFactory();

    @Override
    public void addApplicationListener(ApplicationListener<?> applicationListener) {
        this.applicationEventMulticaster.addApplicationListener(applicationListener);
    }

    @Override
    public boolean isActive() {
        return this.active.get();
    }
}
