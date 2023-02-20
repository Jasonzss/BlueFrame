package com.blue_dot.web.servlet;

import cn.hutool.core.collection.CollectionUtil;
import com.blue_dot.beans.BeansException;
import com.blue_dot.web.context.ConfigurableWebApplicationContext;
import com.blue_dot.web.multipart.MultipartHttpServletRequest;
import com.blue_dot.web.multipart.MultipartRequest;
import com.blue_dot.web.multipart.MultipartResolver;
import com.blue_dot.web.multipart.hutool.HutoolMultipartResolver;
import com.blue_dot.web.servlet.exception_resolver.SimpleExceptionResolver;
import com.blue_dot.web.servlet.handler.SimpleHandlerMapping;
import com.blue_dot.web.servlet.mvc.method.annotation.SimpleRequestMappingHandlerAdapter;
import com.blue_dot.web.servlet.result_handler.*;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Author Jason
 * @CreationDate 2023/02/04 - 21:46
 * @Description ：TODO 先只考虑一种默认情况，可能逻辑会有耦合。后期有空再来抽象
 * 请求分发的核心类，项目中唯一的Servlet。
 * 初始化时：从Spring容器中取出响应的组件（属性），如：处理器映射器、处理器适配器、视图解析器等
 * 执行业务时：
 */
public class DispatcherServlet extends FrameworkServlet{
    public static final String WEB_APPLICATION_CONTEXT_ATTRIBUTE = DispatcherServlet.class.getName() + ".CONTEXT";

    public static final boolean USE_DEFAULT_EXCEPTION_HANDLER = true;

    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    //是否使用默认的异常处理器
    private boolean useDefaultExceptionHandler = USE_DEFAULT_EXCEPTION_HANDLER;

    // 处理器映射：映射的是请求与处理方法，因为可能存在多种映射规则，所以这个属性是List类型的
    private List<HandlerMapping> handlerMappings;

    //处理器适配器：用于统一各类处理器接口，以方便DispatcherServlet使用
    private List<HandlerAdapter> handlerAdapters;

    //结果处理器：当请求处理器（Controller等）处理完请求后返回结果，使用这些结果处理器来处理返回的结果给前端
    private SortedSet<ResultHandler> resultHandlers;

    //全局异常解析器：当请求处理器抛出异常且没有处理时，由异常解析器来做异常的兜底处理，以免返回奇奇怪怪的异常给前端
    private SortedSet<HandlerExceptionResolver> exceptionResolvers;

    //文件解析器：当用户发送了文件上传的请求，解析这个请求，并将包装好的文件操作类（UploadFile）提供给开发人员使用
    private MultipartResolver multipartResolver;
    /*-----------------------------初始化--------------------------------*/

    /**
     * 刷新DispatcherServlet，重新加载DispatcherServlet的组件，第一次执行的话相当于是 初始化
     */
    @Override
    protected void onRefresh(ConfigurableWebApplicationContext webApplicationContext) {
        //如果下面这些组件用户自己配置了，初始化就不会采用默认策略，即不会加载用不上的组件，但要确保是真的用不上
        initHandlerMappings(webApplicationContext);
        initHandlerAdapters(webApplicationContext);
        initResultHandler(webApplicationContext);
        initExceptionResolvers(webApplicationContext);
        initMultipartResolver(webApplicationContext);
    }


    //TODO 暂时先这样初始化这些组件，可能写死了不太好，具体修改方案参考 SpringMVC 的 Dispatcher 方法的 getDefaultStrategies

    private void initHandlerAdapters(ConfigurableWebApplicationContext webApplicationContext) {
        this.handlerAdapters = null;


        Map<String, HandlerAdapter> beansOfType = webApplicationContext.getBeansOfType(HandlerAdapter.class);
        this.handlerAdapters = new ArrayList<>(beansOfType.size());
        beansOfType.forEach((k,v) -> this.handlerAdapters.add(v));

        if (CollectionUtil.isEmpty(this.handlerAdapters)){
            this.handlerAdapters = getDefaultHandlerAdapters();
        }
    }

    private List<HandlerAdapter> getDefaultHandlerAdapters() {
        ArrayList<Class<? extends HandlerAdapter>> classes = Lists.newArrayList(
                SimpleRequestMappingHandlerAdapter.class
        );

        List<HandlerAdapter> adapters = new ArrayList<>();

        classes.forEach((c) -> {
            HandlerAdapter bean = getWebApplicationContext().getAutowireCapableBeanFactory().createBean(c);
            adapters.add(bean);
        });

        return adapters;
    }

    private void initHandlerMappings(ConfigurableWebApplicationContext webApplicationContext) {
        this.handlerMappings = null;


        Map<String, HandlerMapping> beansOfType = webApplicationContext.getBeansOfType(HandlerMapping.class);
        this.handlerMappings = new ArrayList<>(beansOfType.size());
        beansOfType.forEach((k,v) -> this.handlerMappings.add(v));

        if (CollectionUtil.isEmpty(handlerMappings)){
            this.handlerMappings = getDefaultHandlerMappings();
        }
    }

    private List<HandlerMapping> getDefaultHandlerMappings() {
        ArrayList<Class<? extends HandlerMapping>> classes = Lists.newArrayList(
                SimpleHandlerMapping.class
        );

        List<HandlerMapping> handlerMappings = new ArrayList<>();

        classes.forEach((c) -> {
            HandlerMapping bean = getWebApplicationContext().getAutowireCapableBeanFactory().createBean(c);
            handlerMappings.add(bean);
        });

        return handlerMappings;
    }


    private void initResultHandler(ConfigurableWebApplicationContext webApplicationContext) {
        this.resultHandlers = null;

        Map<String, ResultHandler> beansOfType = webApplicationContext.getBeansOfType(ResultHandler.class);
        this.resultHandlers = new TreeSet<>();
        beansOfType.forEach((k,v) -> this.resultHandlers.add(v));

        if (CollectionUtil.isEmpty(resultHandlers)){
            this.resultHandlers = getDefaultResultHandlers();
        }
    }

    private SortedSet<ResultHandler> getDefaultResultHandlers() {
        ArrayList<Class<? extends ResultHandler>> classes = Lists.newArrayList(
                DefaultResultHandler.class,
                ExcelResultHandler.class,
                FileResultHandler.class,
                RenderedImageResultHandler.class,
                RedirectHandler.class,
                ForwardHandler.class
        );

        SortedSet<ResultHandler> resultHandlers = new TreeSet<>();

        classes.forEach((c) -> {
            ResultHandler bean = getWebApplicationContext().getAutowireCapableBeanFactory().createBean(c);
            resultHandlers.add(bean);
        });

        return resultHandlers;
    }

    private void initExceptionResolvers(ConfigurableWebApplicationContext webApplicationContext) {
        this.exceptionResolvers = null;

        Map<String, HandlerExceptionResolver> beansOfType = webApplicationContext.getBeansOfType(HandlerExceptionResolver.class);
        this.exceptionResolvers = new TreeSet<>();
        beansOfType.forEach((k,v) -> this.exceptionResolvers.add(v));

        if (CollectionUtil.isEmpty(exceptionResolvers) && useDefaultExceptionHandler){
            this.exceptionResolvers = getDefaultExceptionResolvers();
        }
    }

    private SortedSet<HandlerExceptionResolver> getDefaultExceptionResolvers() {
        ArrayList<Class<? extends HandlerExceptionResolver>> classes = Lists.newArrayList(
                SimpleExceptionResolver.class
        );

        SortedSet<HandlerExceptionResolver> resultHandlers = new TreeSet<>();

        classes.forEach((c) -> {
            HandlerExceptionResolver bean = getWebApplicationContext().getAutowireCapableBeanFactory().createBean(c);
            resultHandlers.add(bean);
        });

        return resultHandlers;
    }


    private void initMultipartResolver(ConfigurableWebApplicationContext webApplicationContext) {
        this.multipartResolver = null;

        try{
            this.multipartResolver = webApplicationContext.getBean(MultipartResolver.class);
        }catch (Exception e){
            //默认使用的 multipartResolver
            this.multipartResolver = new HutoolMultipartResolver();
        }
    }
    /*-----------------------------业务处理核心流程--------------------------------*/

    /**
     * 执行业务
     */
    @Override
    protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // Make framework objects available to handlers and view objects.
        request.setAttribute(WEB_APPLICATION_CONTEXT_ATTRIBUTE, getWebApplicationContext());

        //执行请求分发
        doDispatch(request, response);
    }

    /**
     * 请求分发的核心逻辑
     * @param request 请求
     * @param response 响应
     */
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HandlerExecutionChain mappedHandler = null;
        HttpServletRequest processedRequest = request;
        //用于判断当前请求是否为文件上传请求，是的话在请求处理完毕后需要执行文件处理资源的释放
        boolean multipartRequestParsed = false;


        try{
            Object result = null;
            Exception dispatchException = null;

            try{
                //检查是否上传文件的请求，是的话将request包装成 MultipartHttpServletRequest，上传任务结束后还要关闭对应的资源
                processedRequest = checkMultipart(request);
                multipartRequestParsed = (processedRequest != request);


                //获取Handler（处理器链）
                mappedHandler = getHandler(request);

                if (mappedHandler == null || mappedHandler.getHandler() == null){
                    //找不到对应的Handler
                    noHandlerFound(request,response);
                    return;
                }

                //使用 HandlerAdapter（处理器适配器）包装Handler
                HandlerAdapter handlerAdapter = getHandlerAdapter(mappedHandler.getHandler());

                //前置处理（拦截）
                if (!mappedHandler.applyPreHandle(request, response)) {
                    return;
                }

                //实际的处理器
                result = handlerAdapter.handle(request, response, mappedHandler.getHandler());

                //后置处理（拦截）
                mappedHandler.applyPostHandle(request, response, result);
            } catch (Exception e) {
                dispatchException = e;
            }catch (Throwable err){
                dispatchException = new BeansException("Handler dispatch failed",err);
            }

            //处理结果
            processDispatchResult(request, response, mappedHandler, result,dispatchException);
        }catch (Throwable err){
            err.printStackTrace();
        }finally {
            if (multipartRequestParsed){
                if (this.multipartResolver != null) {
                    //确认是文件上传请求
                    if (processedRequest != null) {
                        //关闭上传文件所用到的资源
                        this.multipartResolver.cleanupMultipart((MultipartHttpServletRequest) processedRequest);
                    }
                }
            }

        }

    }

    /**
     * 判断目标请求提交的表单是否为Multipart类型（即文件上传类型的表单）
     * 是的话包装成文件解析器指定的request类型（默认 HutoolMultipartHttpServletRequest）
     * 不是的话直接返回原 request
     * @param request 目标请求
     */
    protected HttpServletRequest checkMultipart(HttpServletRequest request){
        if (this.multipartResolver != null && this.multipartResolver.isMultipart(request)) {
            if (request.getAttribute(MultipartRequest.HAS_RESOLVED) != null){
                // 如果该请求已经被包装过就不用再包装，这通常发生在请求转发和重定向中
                // 如果仍继续进行包装，会发现请求中的数据流 inputStream 已经无法再次读取了，故 IOException
                // 好像是这个逻辑与hutool的逻辑由冲突
                return request;
            }else {
                //该请求还没被包装过，使用 multipartResolver 包装一下
                return this.multipartResolver.resolveMultipart(request);
            }

        }
        // 不是Multipart请求，返回原请求
        return request;
    }

    /**
     * 根据request，使用处理器映射器找到处理 request 的处理器（链）
     */
    protected HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
        if (this.handlerMappings != null) {
            for (HandlerMapping mapping : this.handlerMappings) {
                //遍历所有的 HandlerMapping
                HandlerExecutionChain handler = mapping.getHandler(request);
                if (handler != null) {
                    //返回目标处理器
                    return handler;
                }
            }
        }
        //没有对应的处理器，则返回null
        return null;
    }

    /**
     * 找不到对应的处理器，执行对应的处理
     */
    protected void noHandlerFound(HttpServletRequest request, HttpServletResponse response) {
        throw new BeansException("找不到【"+request.getMethod()+":"+request.getRequestURI()+"】对应的处理器");
    }

    /**
     * 根据处理器类型获取对应的适配器
     * @param handler 处理器
     * @return 适配器
     */
    private HandlerAdapter getHandlerAdapter(Object handler) {
        if (this.handlerAdapters != null) {
            for (HandlerAdapter adapter : this.handlerAdapters) {
                if (adapter.supports(handler)) {
                    return adapter;
                }
            }
        }
        throw new BeansException("找不到【"+handler+"】处理器对应的适配器，DispatcherServlet需要适配器才能调用对应的处理器（Handler）");
    }


    /**
     * 处理异常结果 或 渲染结果【二选一】
     */
    private void processDispatchResult(HttpServletRequest request, HttpServletResponse response,
                                       HandlerExecutionChain mappedHandler, Object result,
                                       Exception exception) throws Exception {
        if (exception != null){
            if (exceptionResolvers == null || exceptionResolvers.size() == 0){
                //没有配置异常解析器，那直接抛
                throw exception;
            }else {
                //配置了异常解析器，遍历每个异常解析器来处理异常
                for (HandlerExceptionResolver resolver : exceptionResolvers) {
                    resolver.resolveException(request,response,mappedHandler.getHandler(),exception);
                }
            }
        }else {
            //将数据输出回前端
            ResultHandler resultHandler = new DefaultResultHandler();
            for (ResultHandler r : resultHandlers) {
                if (r.support(result)){
                    //找到对应的结果处理器对结果进行处理
                    resultHandler = r;
                    break;
                }
            }

            resultHandler.handleResult(result,request,response);
        }

        //TODO 数据处理完毕，触发Handler中的Interceptor的回调函数【不清楚这个回调能做些什么】
    }

    public void setHandlerMappings(List<HandlerMapping> handlerMappings) {
        this.handlerMappings = handlerMappings;
    }

    public void setHandlerAdapters(List<HandlerAdapter> handlerAdapters) {
        this.handlerAdapters = handlerAdapters;
    }

    public boolean isUseDefaultExceptionHandler() {
        return useDefaultExceptionHandler;
    }

    public void setUseDefaultExceptionHandler(boolean useDefaultExceptionHandler) {
        this.useDefaultExceptionHandler = useDefaultExceptionHandler;
    }
}
