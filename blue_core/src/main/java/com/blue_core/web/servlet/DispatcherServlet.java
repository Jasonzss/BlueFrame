package com.blue_core.web.servlet;

import com.blue_core.beans.BeansException;
import com.blue_core.core.io.ResourceLoader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * @Author Jason
 * @CreationDate 2023/02/04 - 21:46
 * @Description ：TODO 先只考虑一种默认情况，可能逻辑会有耦合。后期有空再来抽象
 * 请求分发的核心类，项目中唯一的Servlet。
 * 初始化时：从Spring容器中取出响应的组件（属性），如：处理器映射器、处理器适配器、视图解析器等
 * 执行业务时：
 */
public class DispatcherServlet extends FrameworkServlet{
    private static final String DEFAULT_CONTEXT_CONFIG_NAME = "contextConfigLocation";
    private static final String DEFAULT_CONTEXT_CONFIG_LOCATION = ResourceLoader.CLASSPATH_URL_PREFIX+"applicationContext.xml";

    //Spring配置的位置
    private String contextConfigLocation;

    // 处理器映射：映射的是请求与处理方法，因为可能存在多种映射规则，所以这个属性是List类型的
    private List<HandlerMapping> handlerMappings;

    //处理器适配器：用于统一各类处理器接口，以方便DispatcherServlet使用
    private List<HandlerAdapter> handlerAdapters;

    public final List<HandlerMapping> getHandlerMappings() {
        return (this.handlerMappings != null ? Collections.unmodifiableList(this.handlerMappings) : null);
    }

    /*-----------------------------业务处理核心流程--------------------------------*/

    /**
     * 执行业务
     */
    @Override
    protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //TODO 保留request的参数快照，在request处理完成后把参数恢复。不知道为什么这样。

        //TODO 设置框架对象以用于 处理器（Handler）和 视图对象（View）

        //执行请求分发
        doDispatch(request, response);
    }

    /**
     * 请求分发的核心逻辑
     * @param request 请求
     * @param response 响应
     */
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) {
        //TODO 检查是否上传文件的请求，是的话将request包装成上传文件request，上传任务结束后还要关闭对应的资源
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
