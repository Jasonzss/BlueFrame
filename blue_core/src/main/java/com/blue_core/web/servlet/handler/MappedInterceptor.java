package com.blue_core.web.servlet.handler;

import com.blue_core.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Jason
 * @CreationDate 2023/02/05 - 22:53
 * @Description ：使用了装饰者模式，但是没有对HandlerInterceptor的行为进行装饰。
 * 而是添加了匹配属性 includePatterns & excludePatterns
 */
public final class MappedInterceptor implements HandlerInterceptor {
    //请求路径中 存在 这些匹配条件时，说明此拦截器（Interceptor）和这个请求相匹配
    private final String[] includePatterns;

    //请求路径中 不存在 这些匹配条件时，说明此拦截器（Interceptor）和这个请求相匹配
    private final String[] excludePatterns;

    //拦截器本体
    private final HandlerInterceptor interceptor;

    public MappedInterceptor(String[] includePatterns, HandlerInterceptor interceptor) {
        this(includePatterns, null, interceptor);
    }

    /**
     * Create a new MappedInterceptor instance.
     * @param includePatterns the path patterns to map (empty for matching to all paths)
     * @param excludePatterns the path patterns to exclude (empty for no specific excludes)
     * @param interceptor the HandlerInterceptor instance to map to the given patterns
     */
    public MappedInterceptor(String[] includePatterns, String[] excludePatterns,
                             HandlerInterceptor interceptor) {

        this.includePatterns = includePatterns;
        this.excludePatterns = excludePatterns;
        this.interceptor = interceptor;
    }

    /**
     * 判断请求路径是否和当前拦截器映射匹配
     * @param path 请求路径
     * @return 是否匹配
     */
    public boolean matches(String path){
        if (includePatterns != null){
            for (String includeStr : includePatterns){
                if (!path.contains(includeStr)){
                    return false;
                }
            }
        }
        if (excludePatterns != null){
            for (String excludeStr : excludePatterns){
                if (path.contains(excludeStr)){
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return interceptor.preHandle(request,response,handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, Object result) throws Exception {
        interceptor.postHandle(request,response,handler,result);
    }

    public HandlerInterceptor getInterceptor() {
        return interceptor;
    }
}
