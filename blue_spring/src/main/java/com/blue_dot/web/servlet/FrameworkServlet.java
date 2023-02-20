package com.blue_dot.web.servlet;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.blue_dot.beans.BeansException;
import com.blue_dot.web.context.ConfigurableWebApplicationContext;
import com.blue_dot.web.context.WebApplicationContext;
import com.blue_dot.web.context.support.XmlWebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Jason
 * @CreationDate 2023/02/05 - 19:25
 * @Description ：Spring 的 Web 框架的基础 servlet。提供与Spring应用上下文的集成
 *
 * 子类必须实现doService来处理请求。
 * 子类可以覆盖initFrameworkServlet()以进行自定义框架初始化。
 */
public abstract class FrameworkServlet extends HttpServlet {
    //初始化ApplicationContext的配置文件定位
    private String configLocation;

    //FrameworkServlet内部的ApplicationContext，存取bean在这里执行
    private ConfigurableWebApplicationContext webApplicationContext;

    /*------------------------------初始化------------------------------------*/

    @Override
    public void init() throws ServletException {
        initParams();

        try {
            this.webApplicationContext = initWebApplicationContext();

            //空方法，用于扩展
            initFrameworkServlet();
        }catch (Exception e){
            throw new BeansException("webApplicationContext 初始化失败",e);
        }
    }

    private void initParams() {
        System.out.println(getServletConfig().getInitParameter("configLocation"));
        this.configLocation = getServletConfig().getInitParameter("configLocation");
    }

    private synchronized ConfigurableWebApplicationContext initWebApplicationContext() {
        ConfigurableWebApplicationContext rootContext = getWebApplicationContextFromServletContext(getServletContext(), WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        try{
            if (rootContext == null){
                if (StrUtil.isNotEmpty(configLocation)){
                    rootContext = new XmlWebApplicationContext(configLocation);
                }else {
                    rootContext = new XmlWebApplicationContext();
                }
            }
        }catch (Exception e){
            throw new BeansException("创建XmlWebApplicationContext出现异常");
        }


        this.webApplicationContext = rootContext;

        onRefresh(webApplicationContext);

        return rootContext;
    }

    /**
     * 空方法，子类通过重写这个方法实现容器刷新后的 自身刷新逻辑
     */
    protected void onRefresh(ConfigurableWebApplicationContext webApplicationContext) {

    }

    /**
     * 从服务器的ServletContext中获取唯一的WebApplicationContext
     * @param servletContext 服务器的ServletContext
     * @param contextName ApplicationContext的name
     */
    private ConfigurableWebApplicationContext getWebApplicationContextFromServletContext(ServletContext servletContext, String contextName){
        Assert.notNull(servletContext, "ServletContext must not be null");
        Object attr = servletContext.getAttribute(contextName);
        if (attr == null) {
            return null;
        }
        if (attr instanceof RuntimeException) {
            throw (RuntimeException) attr;
        }
        if (attr instanceof Error) {
            throw (Error) attr;
        }
        if (attr instanceof Exception) {
            throw new IllegalStateException((Exception) attr);
        }
        if (!(attr instanceof ConfigurableWebApplicationContext)) {
            throw new IllegalStateException("Context attribute is not of type WebApplicationContext: " + attr);
        }
        return (ConfigurableWebApplicationContext) attr;
    }

    /**
     * 在设置任何 bean 属性并加载 WebApplicationContext 之后，将调用此方法。
     * 默认实现是空的；子类可以覆盖此方法以执行它们需要的任何初始化。
     */
    protected void initFrameworkServlet() {
    }

    /*------------------------------销毁程序------------------------------------*/

    @Override
    public void destroy() {
        super.destroy();
    }

    /*------------------------------提供服务的实现方法------------------------------------*/

    //TODO 具体区分不同请求方式暂时先这样，后面再补充逻辑
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    @Override
    protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    /*------------------------------提供服务的具体逻辑------------------------------------*/

    /**
     * 处理此请求，TODO 无论结果如何都发布一个事件。
     * 实际的事件处理是由抽象的doService模板方法执行的
     */
    protected final void processRequest(HttpServletRequest request, HttpServletResponse response){
        try {
            doService(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract void doService(HttpServletRequest request, HttpServletResponse response) throws Exception;

    public ConfigurableWebApplicationContext getWebApplicationContext() {
        return webApplicationContext;
    }
}
