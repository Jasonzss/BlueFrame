package com.blue_core.aop;

/**
 * @Author Jason
 * @CreationDate 2023/01/31 - 16:24
 * @Description ：意为切点，Aop切面插入的地方
 * 切入点由返回的ClassFilter和MethodMatcher决定
 */
public interface PointCut {
    /**
     * 返回当前PointCut的ClassFilter
     */
    ClassFilter getClassFilter();

    /**
     * 返回当前PointCut的MethodMatcher
     */
    MethodMatcher getMethodMatcher();

}
