package com.blue_core.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * MVC framework SPI, allowing parameterization of the core MVC workflow.
 *
 * <p>Interface that must be implemented for each handler type to handle a request.
 * This interface is used to allow the {@link DispatcherServlet} to be indefinitely
 * extensible. The {@code DispatcherServlet} accesses all installed handlers through
 * this interface, meaning that it does not contain code specific to any handler type.
 *
 * <p>Note that a handler can be of type {@code Object}. This is to enable
 * handlers from other frameworks to be integrated with this framework without
 * custom coding, as well as to allow for annotation-driven handler objects that
 * do not obey any specific Java interface.
 *
 * <p>This interface is not intended for application developers. It is available
 * to handlers who want to develop their own web workflow.
 *
 * <p>Note: {@code HandlerAdapter} implementors may implement the {Ordered}
 * interface to be able to specify a sorting
 * order (and thus a priority) for getting applied by the {@code DispatcherServlet}.
 * Non-Ordered instances get treated as lowest priority.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * 处理器适配器：用于统一各类处理器接口，以方便外部（DispatcherServlet）使用
 */
public interface HandlerAdapter {
    /**
     * 判断当前适配器是否可以用来适配这个处理器，不适配的话这个处理器就不能通过适配器调用 handle() 方法
     * @param handler 处理器
     * @return 适配器是否支持此处理器
     */
    boolean supports(Object handler);

    /**
     * 使用给定的处理器处理请求，不同处理器的这个处理工作流程可能会有很大地不同。所以使用适配器模式来统一接口以方便外部使用
     */
    Object handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;
}
