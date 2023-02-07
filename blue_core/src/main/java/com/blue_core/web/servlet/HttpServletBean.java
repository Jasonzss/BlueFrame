package com.blue_core.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * @Author Jason
 * @CreationDate 2023/02/05 - 19:25
 * @Description ：将web.xml中配置的初始化参数读取出来，并设置到DispatcherServlet相应的属性中。
 * 不过这部分功能被改写到DispatcherServlet 和 FrameworkServlet 的init方法中去了
 *
 * 暂时用不上
 */
@Deprecated
public abstract class HttpServletBean extends HttpServlet {

    /**
     * 将配置参数映射到此 servlet 的 bean 属性。
     * 并使用模板方法模式，调用子类的方法进行初始化。
     */
    @Override
    public void init() throws ServletException {
        super.init();

        // 让子类实现他们自己需要的逻辑
        initServletBean();
    }

    protected void initServletBean() throws ServletException { }

    @Override
    public String getServletName() {
        return (getServletConfig() != null ? getServletConfig().getServletName() : null);
    }
}
