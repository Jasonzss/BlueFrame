package com.blue_core.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * @Author Jason
 * @CreationDate 2023/02/05 - 19:25
 * @Description ：将Servlet中配置的参数读取出来，并设置到相应的属性中。
 * 例如：TODO 暂时想不到能配置哪些属性
 */
public abstract class HttpServletBean extends HttpServlet {
    /**
     * 将配置参数映射到此 servlet 的 bean 属性。
     * 并使用模板方法模式，调用子类的方法进行初始化。
     */
    @Override
    public void init() throws ServletException {
        super.init();
        //获取Servlet配置并设置

        // 让子类实现他们自己需要的逻辑
        initServletBean();
    }

    protected void initServletBean() throws ServletException { }
}
