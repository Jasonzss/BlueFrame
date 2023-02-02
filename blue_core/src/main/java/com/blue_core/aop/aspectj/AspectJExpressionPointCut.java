package com.blue_core.aop.aspectj;

import com.blue_core.aop.ClassFilter;
import com.blue_core.aop.MethodMatcher;
import com.blue_core.aop.PointCut;
import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author Jason
 * @CreationDate 2023/01/31 - 16:41
 * @Description ：Spring 切入点实现，使用 AspectJWeaver 来计算切入点表达式。
 */
public class AspectJExpressionPointCut implements ClassFilter, MethodMatcher, PointCut {
    //记录表达式中哪些语法基元是支持的
    private static final Set<PointcutPrimitive> SUPPORTED_PRIMITIVES = new HashSet<>();

    static {
        //添加一个Execution基元
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.EXECUTION);
    }

    private final PointcutExpression pointcutExpression;

    public AspectJExpressionPointCut(String expression) {
        //根据指定的语法基元（SUPPORTED_PRIMITIVES）生成自定义PointCut解析器
        PointcutParser pointcutParser =
                PointcutParser.
                        getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution(
                                SUPPORTED_PRIMITIVES,
                                this.getClass().getClassLoader()
                        );

        //调用自定义PointCut解析器来解析用户的expression
        this.pointcutExpression = pointcutParser.parsePointcutExpression(expression);
    }

    @Override
    public boolean matches(Class<?> clazz) {
        return pointcutExpression.couldMatchJoinPointsInType(clazz);
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return pointcutExpression.matchesMethodExecution(method).alwaysMatches();
    }

    @Override
    public ClassFilter getClassFilter() {
        return this;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }
}
