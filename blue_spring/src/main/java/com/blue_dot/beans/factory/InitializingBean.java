package com.blue_dot.beans.factory;

/**
 * @Author Jason
 * @CreationDate 2023/01/29 - 16:21
 * @Description ：实现了这个接口的Bean会在它的内部属性全部注入完成后，立马执行这个接口的方法：afterPropertiesSet。
 * 这个接口有一个代替方案，就是自行写一个 init 方法，然后在xml配置文件中 或是 添加注解 标注这个init方法。
 */
public interface InitializingBean {
    /**
     * 此方法允许 bean 实例在设置完所有 bean 属性后，执行其整体配置和最终初始化的验证。
     *      例如：执行自定义初始化，或 仅检查是否已设置所有必需属性。
     * @throws Exception 如果配置错误（例如未能设置基本属性）或由于任何其他原因初始化失败
     */
    void afterPropertiesSet() throws Exception;
}
