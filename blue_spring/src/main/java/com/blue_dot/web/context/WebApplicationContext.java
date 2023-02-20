package com.blue_dot.web.context;

import com.blue_dot.context.ApplicationContext;

import javax.servlet.ServletContext;

/**
 * @Author Jason
 * @CreationDate 2023/02/05 - 16:48
 * @Description ：继承自ApplicationContext接口，添加了web环境下相关的功能
 */
public interface WebApplicationContext extends ApplicationContext {
    String ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE = WebApplicationContext.class.getName() + ".ROOT";

    /**
     * 获取ServletContext（Servlet上下文：当前web应用的相关信息、资源，即上下文）
     * @return 当前web应用的ServletContext
     */
    ServletContext getServletContext();
}
