package com.blue_dot.web.servlet.handler;

import com.blue_dot.beans.BeansException;
import com.blue_dot.beans.factory.InitializingBean;
import com.blue_dot.context.ApplicationContext;
import com.blue_dot.context.ApplicationContextAware;
import com.blue_dot.web.bind.annotation.RequestMapping;
import com.blue_dot.web.servlet.HandlerExecutionChain;
import com.blue_dot.web.servlet.HandlerInterceptor;
import com.blue_dot.web.servlet.HandlerMapping;
import com.blue_dot.web.servlet.mvc.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @Author Jason
 * @CreationDate 2023/02/05 - 22:38
 * @Description ：请求 与 请求处理器 映射关系的简单实现
 * TODO 目前仅支持一种固定的映射方式即：http://localhost:8080/mvc_test/my/t02 -> /my/t02。后续其他映射方式待添加
 */
public class SimpleHandlerMapping implements HandlerMapping, InitializingBean, ApplicationContextAware {

    private List<HandlerInterceptor> interceptors = new ArrayList<>();

    private Map<String,Object> handlers = new HashMap<>();
    private ApplicationContext applicationContext;

    @Override
    public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
        return getHandlerExecutionChain(findMappedHandler(request),request);
    }

    protected Object findMappedHandler(HttpServletRequest request){
        String path = getPath(request);
        return handlers.get(path);
    }

    protected HandlerExecutionChain getHandlerExecutionChain(Object handler, HttpServletRequest request) {
        HandlerExecutionChain chain = (handler instanceof HandlerExecutionChain ?
                (HandlerExecutionChain) handler : new HandlerExecutionChain(handler));

        String lookupPath = getPath(request);

        //组装处理器链，在处理器前后加上拦截器
        for (HandlerInterceptor interceptor : this.interceptors) {
            if (interceptor instanceof MappedInterceptor) {
                MappedInterceptor mappedInterceptor = (MappedInterceptor) interceptor;
                if (mappedInterceptor.matches(lookupPath)) {
                    chain.addInterceptor(mappedInterceptor.getInterceptor());
                }
            }
            else {
                chain.addInterceptor(interceptor);
            }
        }
        return chain;
    }

    private String getPath(HttpServletRequest request) {
        //request中相关的参数
        request.getRequestURI();    // /mvc_test/my/t01
        request.getRequestURL();    // http://localhost:8080/mvc_test/my/t01
        request.getMethod();        // GET
        request.getContextPath();   // /mvc_test
        request.getServletPath();   // /my/t01

        return request.getServletPath();
    }

    public void addHandler(Object handler){
        Method[] methods = handler.getClass().getMethods();
        for (Method m : methods) {
            if (m.isAnnotationPresent(RequestMapping.class)){
                handlers.put(getRequestMappingPath(handler, m),new HandlerMethod(handler,m));
            }
        }
    }

    private String getRequestMappingPath(Object handler, Method m){
        String path;
        String methodPath;

        path = handler.getClass().getAnnotation(RequestMapping.class).value();

        methodPath = m.getAnnotation(RequestMapping.class).value();
        methodPath = "".equals(methodPath) ? "/" + m.getName() : methodPath;


        return path +  methodPath;
    }

    public void addInterceptor(HandlerInterceptor interceptor){
        interceptors.add(interceptor);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initHandlerMethods();
    }

    protected void initHandlerMethods() {
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            Object bean = applicationContext.getBean(name);
            if (bean.getClass().isAnnotationPresent(RequestMapping.class)) {
                addHandler(bean);
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
