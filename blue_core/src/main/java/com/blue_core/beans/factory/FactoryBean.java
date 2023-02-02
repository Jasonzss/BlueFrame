package com.blue_core.beans.factory;

/**
 * @Author Jason
 * @CreationDate 2023/01/30 - 13:57
 * @Description ：  用户可以通过实现该接口定制实例化bean的逻辑。
 * 这样做了之后 Spring 的生态bean孵化箱就此提供了，谁家的框架都可以在此标准上完成自己服务的接入。
 * 使用各种手段将自家框架中的bean放到Spring中
 */
public interface FactoryBean<T> {
    /**
     * 从工厂中获取目标bean
     */
    T getObject() throws Exception;

    /**
     * 获取目标Bean的Class对象
     */
    Class<?> getObjectType();

    /**
     * 判断目标Bean是否为单例
     */
    boolean isSingleton();
}
