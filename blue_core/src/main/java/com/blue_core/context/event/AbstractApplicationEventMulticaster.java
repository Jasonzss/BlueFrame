package com.blue_core.context.event;

import com.blue_core.beans.BeansException;
import com.blue_core.beans.factory.BeanFactory;
import com.blue_core.beans.factory.aware.BeanFactoryAware;
import com.blue_core.context.ApplicationEvent;
import com.blue_core.context.ApplicationListener;
import com.blue_core.utils.ClassUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * @Author Jason
 * @CreationDate 2023/01/30 - 18:25
 * @Description ：抽象的广播器，这里面涉及到的监听器、事件都是抽象过的，子类广播器根据广播筛选的逻辑不同自行实现。
 * 对事件广播器的公用方法提取，在这个类中可以实现一些基本功能，避免所有直接实现接口放还需要处理细节。
 */
public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster, BeanFactoryAware {
    //所有的监听器都注册在这个set集合中
    public final Set<ApplicationListener<ApplicationEvent>> applicationListeners = new LinkedHashSet<>();

    //通过实现BeanFactoryAware获取当前广播器所在的BeanFactory
    private BeanFactory beanFactory;


    @Override
    public void addApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.add((ApplicationListener<ApplicationEvent>)listener);
    }

    @Override
    public void removeApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.remove(listener);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    /**
     * 筛选出监听目标事件的所有监听器
     * @param event 目标事件
     * @return 监听目标事件的所有监听器
     */
    protected Collection<ApplicationListener> getApplicationListeners(ApplicationEvent event) {
        LinkedList<ApplicationListener> targetListeners = new LinkedList<>();
        for (ApplicationListener<ApplicationEvent> listener : applicationListeners) {
            if (supportsEvent(listener, event)) {
                targetListeners.add(listener);
            }
        }

        return targetListeners;
    }

    /**
     * 判断此监听器是否在监听此事件
     * @param applicationListener 目标监听器类
     * @param event 事件
     * @return 此监听器是否在监听此事件
     */
    protected boolean supportsEvent(ApplicationListener<ApplicationEvent> applicationListener, ApplicationEvent event) {
        Class<? extends ApplicationListener> listenerClass = applicationListener.getClass();

        //判断监听器对象是否是Cglib初始化出来的，是的话获取其父类才是目标监听器类
        Class<?> targetListenerClass = ClassUtil.isCglibProxyClass(listenerClass) ? listenerClass.getSuperclass() : listenerClass;

        //获取监听器实现的接口
        //TODO 这里实现的接口还需要判定，万一实现的是多接口那就会找错接口了
        Type genericInterface = targetListenerClass.getGenericInterfaces()[0];

        //获取这个Listener接口的事件泛型
        Type actualTypeArgument = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
        //获得事件泛型的名称
        String className = actualTypeArgument.getTypeName();

        Class<?> eventClassName;
        try {
            //根据事件泛型名称获取对应的Class对象
            eventClassName = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new BeansException("找不到此Event： " + className);
        }

        // 判定此监听器所监听的 eventClassName 对象所表示的类或接口与指定的 event.getClass() 参数所表示的类或接口是否相同，或是否是其父类或父接口。
        // isAssignableFrom是用来判断子类和父类的关系的，或者接口的实现类和接口的关系的，默认所有的类的终极父类都是Object。
        // 如果A.isAssignableFrom(B)结果是true，证明B可以转换成为A,也就是A可以由B转换而来。B是A的父类 或 B就是A
        return eventClassName.isAssignableFrom(event.getClass());
    }
}
