package com.blue_dot.beans.factory.aware;

import com.blue_dot.beans.factory.Aware;

/**
 * @Author Jason
 * @CreationDate 2023/01/29 - 22:18
 * @Description ：实现此接口，就能感知到当前类在Spring容器中的Bean名称是什么
 */
public interface BeanNameAware extends Aware {
    void setBeanName(String beanName);
}
