package com.blue_dot.aop;

/**
 * @Author Jason
 * @CreationDate 2023/01/31 - 16:26
 * @Description ：用于筛选AOP选择的Class类
 * 类筛选器
 */
public interface ClassFilter {
    /**
     * 这个pointCut是否可以用于这个类或者这个类实现的接口
     * @param clazz 等待筛选的目标Class
     * @return 这个advice是否适用于这个Class
     */
    boolean matches(Class<?> clazz);
}
