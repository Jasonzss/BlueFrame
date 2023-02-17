package com.blue_core.web.servlet.exception_resolver;

import com.blue_core.web.servlet.HandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * @Author Jason
 * @CreationDate 2023/02/14 - 22:30
 * @Description ：
 */
public abstract class AbstractExceptionResolver implements HandlerExceptionResolver {
    private Class<?>[] mappedHandlerClasses;


    @Override
    public void resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (shouldApplyTo(handler)){
            doResolveException(request, response, handler, ex);
        }
    }

    /**
     * 判断当前异常解析器是否能应用于此解析器
     * @param handler 异常产生的处理器
     * @return 是否可以处理此异常
     */
    public boolean shouldApplyTo(Object handler){
        if (handler != null){
            if (this.mappedHandlerClasses == null){
                return true;
            }else {
                for (Class<?> clazz : mappedHandlerClasses){
                    if (clazz.isInstance(handler)){
                        return true;
                    }
                }
            }
        }

        //当handler不存在且当前异常解析器对类没有要求时返回true
        return (this.mappedHandlerClasses == null);
    }

    public abstract void doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex);

    @Override
    public int compareTo(HandlerExceptionResolver o) {
        return this.getPriority() - o.getPriority();
    }


    public void setMappedHandlerClasses(Class<?>[] mappedHandlerClasses) {
        this.mappedHandlerClasses = mappedHandlerClasses;
    }

    public Class<?>[] getMappedHandlerClasses() {
        return mappedHandlerClasses;
    }
}
