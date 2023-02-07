package com.blue_core.web.context;

import com.blue_core.context.ConfigurableApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
 * @Author Jason
 * @CreationDate 2023/02/05 - 16:49
 * @Description ：
 */
public interface ConfigurableWebApplicationContext extends WebApplicationContext, ConfigurableApplicationContext {

    void setServletContext(ServletContext servletContext);

    void setServletConfig(ServletConfig servletConfig);

    ServletConfig getServletConfig();

    void setConfigLocation(String configLocation);

    void setConfigLocations(String... configLocations);

    String[] getConfigLocations();
}
