package com.blue_core.context;

import com.blue_core.beans.BeansException;
import com.blue_core.beans.factory.Aware;
import com.blue_core.context.ApplicationContext;

/**
 * @Author Jason
 * @CreationDate 2023/01/29 - 22:26
 * @Description ：实现此接口，就能感知到当前bean类所属的ApplicationContext
 */
public interface ApplicationContextAware extends Aware {
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
