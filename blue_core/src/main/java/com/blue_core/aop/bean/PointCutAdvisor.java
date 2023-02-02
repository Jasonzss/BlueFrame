package com.blue_core.aop.bean;

import com.blue_core.aop.PointCut;

/**
 * @Author Jason
 * @CreationDate 2023/01/31 - 22:28
 * @Description ：由 PointCut 驱动的所有 Advisor 的超级接口。
 * 这几乎包括了除 introduction advisors 之外的所有 Advisor，方法级匹配不适用
 *
 * Advisor 承担了 Pointcut 和 Advice 的组合，Pointcut 用于获取 JoinPoint，
 * 而 Advice 决定于 JoinPoint 执行什么操作。
 */
public interface PointCutAdvisor extends Advisor{
    /**
     * 返回驱动此 Advisor 的 PointCut。
     * @return 驱动此 Advisor 的 PointCut。
     */
    PointCut getPointCut();
}
