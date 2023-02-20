package com.blue_dot.web.servlet.mvc.method.support;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import com.blue_dot.web.servlet.mvc.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Parameter;

/**
 * @Author Jason
 * @CreationDate 2023/02/06 - 15:00
 * @Description ：表示可以调用执行的方法，比父类多了方法执行所需要的参数
 */
public class InvocableHandlerMethod extends HandlerMethod {
    public InvocableHandlerMethod(HandlerMethod handlerMethod) {
        super(handlerMethod);
    }

    public Object invoke(HttpServletRequest request, Object... providedArgs) throws Exception {
        Object[] args;
        Object result;

        args = getMethodArgumentValues(request, providedArgs);
        result = ReflectUtil.invoke(getBean(), getMethod(), args == null || args.length == 0 ? null : args);

        return result;
    }

    /**
     * Get the method argument values for the current request, checking the provided
     * argument values and falling back to the configured argument resolvers.
     * <p>The resulting array will be passed into {doInvoke}.
     * @since 5.1.2
     */
    protected Object[] getMethodArgumentValues(HttpServletRequest request,
                                               Object... providedArgs) throws Exception {

        Parameter[] parameters = getParameters();
        if (ObjectUtil.isEmpty(parameters)) {
            return null;
        }

        Object[] args = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            args[i] = findProvidedArgument(parameter, request, providedArgs);
        }

        return args;
    }
}
