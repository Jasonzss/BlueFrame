package com.blue_core.web.servlet;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import com.blue_core.beans.BeansException;
import com.blue_core.context.ApplicationContext;
import com.blue_core.core.io.ResourceLoader;
import com.blue_core.utils.ClassUtil;
import com.blue_core.web.context.ConfigurableWebApplicationContext;
import com.blue_core.web.servlet.handler.SimpleHandlerMapping;
import com.blue_core.web.servlet.mvc.method.annotation.SimpleRequestMappingHandlerAdapter;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    // 处理器映射：映射的是请求与处理方法，因为可能存在多种映射规则，所以这个属性是List类型的
    private List<HandlerMapping> handlerMappings;

    //处理器适配器：用于统一各类处理器接口，以方便DispatcherServlet使用
    private List<HandlerAdapter> handlerAdapters;

    /*-----------------------------初始化--------------------------------*/

    /**
     * 刷新DispatcherServlet，第一次执行的话相当于是 初始化
     */
    @Override
    protected void onRefresh(ConfigurableWebApplicationContext webApplicationContext) {
        initHandlerMappings(webApplicationContext);
        initHandlerAdapters(webApplicationContext);
    }

    //TODO 暂时先这样初始化这些组件，可能写死了不太好

    private void initHandlerAdapters(ConfigurableWebApplicationContext webApplicationContext) {
        this.handlerAdapters = null;


        Map<String, HandlerAdapter> beansOfType = webApplicationContext.getBeansOfType(HandlerAdapter.class);
        this.handlerAdapters = new ArrayList<>(beansOfType.size());
        beansOfType.forEach((k,v) -> {
            this.handlerAdapters.add(v);
        });

        if (CollectionUtil.isEmpty(this.handlerAdapters)){
            this.handlerAdapters = getDefaultHandlerAdapters();
        }
    }

    private List<HandlerAdapter> getDefaultHandlerAdapters() {
        ArrayList<Class<? extends HandlerAdapter>> classes = Lists.newArrayList(SimpleRequestMappingHandlerAdapter.class);
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
        beansOfType.forEach((k,v) -> {
            this.handlerMappings.add(v);
        });

        if (CollectionUtil.isEmpty(handlerMappings)){
            this.handlerMappings = getDefaultHandlerMappings();
        }
    }

    private List<HandlerMapping> getDefaultHandlerMappings() {
        ArrayList<Class<? extends HandlerMapping>> classes = Lists.newArrayList(SimpleHandlerMapping.class);
        List<HandlerMapping> handlerMappings = new ArrayList<>();

        classes.forEach((c) -> {
            HandlerMapping bean = getWebApplicationContext().getAutowireCapableBeanFactory().createBean(c);
            handlerMappings.add(bean);
        });

        return handlerMappings;
    }

    /*-----------------------------业务处理核心流程--------------------------------*/

    /**
     * 执行业务
     */
    @Override
    protected void doService(HttpServletRequest request, HttpServletResponse response) {
        // Make framework objects available to handlers and view objects.
        request.setAttribute(WEB_APPLICATION_CONTEXT_ATTRIBUTE, getWebApplicationContext());

        //执行请求分发
        logger.info("DispatcherServlet#hashCode："+this.hashCode());
        doDispatch(request, response);
    }

    /**
     * 请求分发的核心逻辑
     * @param request 请求
     * @param response 响应
     */
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) {
        //TODO 检查是否上传文件的请求，是的话将request包装成 特殊的request，上传任务结束后还要关闭对应的资源

        Object result = null;
        HandlerExecutionChain mappedHandler = null;
        try{
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
            e.printStackTrace();
        }

        //处理结果
        processDispatchResult(request, response, mappedHandler, result);
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
    protected void noHandlerFound(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
     * 处理异常结果 和 渲染结果
     */
    private void processDispatchResult(HttpServletRequest request, HttpServletResponse response, HandlerExecutionChain mappedHandler, Object result) {
        //TODO 如果处理存在异常，则对异常进行包装处理

        //TODO 如果存在ModelAndView且内部数据正常，则进行数据渲染

        //将数据输出回前端 TODO 先只搞一个返回String的
        if (result instanceof String){
            try {
                response.getWriter().write((String) result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        //TODO 数据处理完毕，触发Handler中的Interceptor的回调函数
    }

    public void setHandlerMappings(List<HandlerMapping> handlerMappings) {
        this.handlerMappings = handlerMappings;
    }

    public void setHandlerAdapters(List<HandlerAdapter> handlerAdapters) {
        this.handlerAdapters = handlerAdapters;
    }
}
