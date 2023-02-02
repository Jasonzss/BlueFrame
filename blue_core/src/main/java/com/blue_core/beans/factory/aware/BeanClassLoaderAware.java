package com.blue_core.beans.factory.aware;

import com.blue_core.beans.factory.Aware;

/**
 * @Author Jason
 * @CreationDate 2023/01/29 - 22:23
 * @Description ：实现此接口，就能感知到所属bean工厂中用来加载当前bean类的类加载器
 */
public interface BeanClassLoaderAware extends Aware {
    void setBeanClassLoader(ClassLoader classLoader);
}
