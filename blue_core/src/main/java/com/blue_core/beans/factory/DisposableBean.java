package com.blue_core.beans.factory;

/**
 * @Author Jason
 * @CreationDate 2023/01/29 - 16:27
 * @Description ：实现了这个接口的Bean会在生命周期的某一时刻被BeanFactory调用destroy销毁方法并释放资源。
 *   另外这个Bean所在的ApplicationContext在关闭时，也会先调用这些销毁方法再关闭。
 *   这个接口有一个代替方案，就是自行写一个 destroy 方法，然后在xml配置文件中 或是 添加注解 标注这个 destroy 方法。
 */
public interface DisposableBean {
    /**
     * 在销毁 bean 时由包含的BeanFactory调用。
     * @throws Exception 在关闭错误的情况下。异常将被记录但不会重新抛出以允许其他 bean 也释放它们的资源。
     */
    void destroy() throws Exception;
}
