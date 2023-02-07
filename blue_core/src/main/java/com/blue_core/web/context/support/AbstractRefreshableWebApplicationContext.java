package com.blue_core.web.context.support;

import com.blue_core.beans.factory.support.DefaultListableBeanFactory;
import com.blue_core.context.support.AbstractRefreshableApplicationContext;
import com.blue_core.context.support.AbstractRefreshableConfigApplicationContext;
import com.blue_core.web.context.ConfigurableWebApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
 * @Author Jason
 * @CreationDate 2023/02/05 - 16:50
 * @Description ：
 */
public abstract class AbstractRefreshableWebApplicationContext extends AbstractRefreshableConfigApplicationContext implements ConfigurableWebApplicationContext {
    /** Servlet context that this context runs in. */
    private ServletContext servletContext;

    /** Servlet config that this context runs in, if any. */
    private ServletConfig servletConfig;

    /*-----------------ConfigurableWebApplicationContext的实现方法---------------------------------*/

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public ServletContext getServletContext() {
        return this.servletContext;
    }

    @Override
    public void setServletConfig(ServletConfig servletConfig) {
        this.servletConfig = servletConfig;
        if (servletConfig != null && this.servletContext == null) {
            setServletContext(servletConfig.getServletContext());
        }
    }

    @Override
    public ServletConfig getServletConfig() {
        return this.servletConfig;
    }

    /*-----------------AbstractRefreshableApplicationContext的实现方法---------------------------------*/

}
