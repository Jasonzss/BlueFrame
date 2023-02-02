package com.blue_core.aop.bean;

import org.aopalliance.aop.Advice;

/**
 * @Author Jason
 * @CreationDate 2023/01/31 - 22:21
 * @Description ：意为访问者，包含 Advice
 */
public interface Advisor {
    /**
     * 返回当前切面的Advice，这个Advice可以是 interceptor、beforeAdvice、throwsAdvice等
     * @return 当前切面的Advice
     */
    Advice getAdvice();
}
