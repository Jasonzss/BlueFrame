package com.blue_dot.context.support;

import com.blue_dot.beans.BeansException;
import com.blue_dot.beans.factory.ConfigurableListableBeanFactory;
import com.blue_dot.beans.factory.support.DefaultListableBeanFactory;

/**
 * <p>继承自AbstractApplicationContext，之所以在父类名字上添加了Refreshable，是因为实现了父类的抽象方法：refreshBeanFactory[个人理解]。
 * 这个类实现了ApplicationContext内的核心容器BeanFactory是如何创建或刷新配置的
 *
 * <p>而这个刷新BeanFactory的流程也比较简单：创建BeanFactory -> 导入BeanDefinition -> 设置为ApplicationContext的容器
 * 其中再次用到了模板方法模式，将导入BeanDefinition的逻辑抽象用于子类实现，因为不同的配置方式，所对应的导入方式也是不同的
 * @Author Jason
 * @CreationDate 2023/01/28 - 21:55
 * @Description ：
 *
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {
    //ConfigurableListableBeanFactory的子实现类
    private DefaultListableBeanFactory beanFactory;

    @Override
    public ConfigurableListableBeanFactory getConfigurableListableBeanFactory() {
        return beanFactory == null ? (beanFactory = createBeanFactory()) : beanFactory;
    }

    private DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }

    @Override
    protected void refreshBeanFactory() throws BeansException {
        DefaultListableBeanFactory beanFactory = createBeanFactory();
        loadBeanDefinitions(beanFactory);
        //新的BeanFactory覆盖旧的
        this.beanFactory = beanFactory;
    }

    /**
     * 将BeanDefinition导入到目标的DefaultListableBeanFactory中
     * @param beanFactory DefaultListableBeanFactory
     */
    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);


    @Override
    protected void closeBeanFactory() {
        //待定
    }
}
