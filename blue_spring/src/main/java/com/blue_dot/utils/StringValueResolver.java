package com.blue_dot.utils;

/**
 * @Author Jason
 * @CreationDate 2023/02/02 - 15:25
 * @Description ：一个简单的策略接口，子类策略可以使用不同的策略解析字符串
 */
public interface StringValueResolver {
    /**
     * 解析字符串
     * @param strVal 待解析的字符串
     * @return 解析后的字符串
     */
    String resolveStringValue(String strVal);
}
