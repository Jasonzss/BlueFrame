package com.blue_dot.web.servlet.result_handler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Jason
 * @CreationDate 2023/02/10 - 17:11
 * @Description ：请求转发处理器，当处理器返回的结果为 forward: 开头的String类的话，就会转发请求到其后面的uri中
 */
public class ForwardHandler extends AbstractResultHandler{
    public static final String FORWARD_URI_PREFIX = "forward:";

    @Override
    public boolean support(Object result) {
        return result instanceof String && ((String) result).trim().startsWith(FORWARD_URI_PREFIX);
    }

    @Override
    public int getPriority() {
        return -2;
    }

    @Override
    protected String getContentType() {
        return null;
    }

    @Override
    public void outputResult(Object result, HttpServletRequest request, HttpServletResponse response) {
        String uri = ((String)result).trim().substring(FORWARD_URI_PREFIX.length());
        try {
            request.getRequestDispatcher(uri).forward(request,response);
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }
}
