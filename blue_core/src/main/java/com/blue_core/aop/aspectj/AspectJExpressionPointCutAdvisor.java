package com.blue_core.aop.aspectj;

import com.blue_core.aop.PointCut;
import com.blue_core.aop.bean.PointCutAdvisor;
import org.aopalliance.aop.Advice;

/**
 * @Author Jason
 * @CreationDate 2023/01/31 - 22:47
 * @Description ：  把切面 pointcut、拦截方法 advice 和 具体的 AspectJ拦截表达式 包装在一起。
 * 这样就可以在 xml 的配置中定义一个 pointcutAdvisor 切面拦截器了。
 */
public class AspectJExpressionPointCutAdvisor implements PointCutAdvisor {
    private Advice advice;
    private PointCut pointCut;
    private String expression;


    @Override
    public Advice getAdvice() {
        return advice;
    }


    public void setAdvice(Advice advice){
        this.advice = advice;
    }

    /**
     * 返回PointCut，若为空则解析 AspectJExpression 得到PointCut
     * @return Advisor 对应的 PointCut
     */
    @Override
    public PointCut getPointCut() {
        return pointCut == null ? pointCut = new AspectJExpressionPointCut(expression) : pointCut;
    }

    public void setExpression(String expression){
        this.expression = expression;
    }

}
