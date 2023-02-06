package com.blue_core.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Jason
 * @CreationDate 2023/02/05 - 19:25
 * @Description ：Spring 的 Web 框架的基础 servlet。提供与Spring应用上下文的集成，基于JavaBean的整体解决方案。
 * 此类提供以下功能：
 *      TODO 管理每个 servlet 的WebApplicationContext实例。
 *      TODO 在请求处理时发布事件，无论请求是否被成功处理。
 *
 * 子类必须实现doService来处理请求。
 * 因为这扩展了HttpServletBean而不是直接扩展 HttpServlet，所以 bean 属性自动映射到它。
 * 子类可以覆盖initFrameworkServlet()以进行自定义初始化。
 *
 * 目前尚内不支持 MVC 与 ApplicationContext 的集成使用
 */
public abstract class FrameworkServlet extends HttpServletBean{

    /*------------------------------初始化------------------------------------*/

    @Override
    protected void initServletBean() throws ServletException {
        //TODO 初始化ApplicationContext

        initFrameworkServlet();
    }

    /**
     * 在设置任何 bean 属性并加载 WebApplicationContext 之后，将调用此方法。
     * 默认实现是空的；子类可以覆盖此方法以执行它们需要的任何初始化。
     */
    protected void initFrameworkServlet() throws ServletException { }

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
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected final void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            doService(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract void doService(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
