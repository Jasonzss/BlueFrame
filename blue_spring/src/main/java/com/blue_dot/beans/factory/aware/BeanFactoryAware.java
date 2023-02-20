package com.blue_dot.beans.factory.aware;

import com.blue_dot.beans.BeansException;
import com.blue_dot.beans.factory.Aware;
import com.blue_dot.beans.factory.BeanFactory;

/**
 * @Author Jason
 * @CreationDate 2023/01/29 - 22:15
 * @Description ：实现此接口，就能感知到所属的 BeanFactory
 * 感知到后就可以得到这个 BeanFactory 提供的服务
 */
public interface BeanFactoryAware extends Aware {
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
