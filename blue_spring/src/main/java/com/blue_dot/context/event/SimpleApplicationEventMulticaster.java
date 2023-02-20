package com.blue_dot.context.event;

import com.blue_dot.beans.factory.BeanFactory;
import com.blue_dot.context.ApplicationEvent;
import com.blue_dot.context.ApplicationListener;

/**
 * @Author Jason
 * @CreationDate 2023/01/30 - 20:00
 * @Description ：
 */
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster{
    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void multicastEvent(final ApplicationEvent event) {
        for (final ApplicationListener listener : getApplicationListeners(event)) {
            listener.onApplicationEvent(event);
        }
    }
}
